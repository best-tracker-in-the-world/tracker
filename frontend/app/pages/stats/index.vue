<template>
	<div class="w-full p-4">
		<ClientOnly>
			<div class="max-w-[80%] mx-auto">
				<UTabs v-model="currentTab" :items="tabs" />
			</div>
			<div class="w-full h-[400px]">
				<VChart
					:option="weightOptions"
					autoresize
					class="w-full h-full"
				/>
				<VChart
					:option="caloriesOptions"
					autoresize
					class="w-full h-full"
				/>
			</div>
		</ClientOnly>
	</div>
</template>

<script setup lang="ts">
import "echarts";

const { t } = useI18n();
const userSettings = useSettingsStore();

interface TabItems {
	label: string;
	value: string;
}

const currentTab = ref("week");
const tabs = ref<TabItems[]>([
	{
		label: t("stats.week"),
		value: "week",
	},
	{
		label: t("stats.month"),
		value: "month",
	},
	// {
	//   label: t("stats.year"),
	//   value: "year",
	// },
]);

const shortDays = ["sun", "mon", "tue", "wed", "thu", "fri", "sat"];
const weightsData = ref<number[]>([]);
const actualWeightsData = ref<(number | null)[]>();
const interpolatedWeightsData = ref<(number | null)[]>();
const goalData = ref<number[]>();
const chartLabels = ref<string[]>();

const weightOptions = computed(() => ({
	width: "80%",
	height: "auto",
	xAxis: {
		type: "category",
		data:
			chartLabels.value ||
			shortDays.map((day) => t(`calendar.${day}`)),
	},
  axisLabel: {
    formatter: function(value : string) {
      return value.replace(' ', '\n');
    },
    align: 'center',
    verticalAlign: 'top',
    margin: 15,
    fontSize: 11,
  },
	yAxis: {
		type: "value",
		min:
			(userSettings.settings?.weight ??
				Math.min(...weightsData.value.filter((w) => w > 0))) - 5,
		max: Math.max(...weightsData.value.filter((w) => w > 0)) + 10,
		axisLabel: {
			formatter: "{value}",
		},
		axisPointer: {
			snap: true,
		},
	},
	series: [
		// existing weight data
		{
			name: "weight",
			type: "line",
			symbol: "circle",
			symbolSize: 6,
			smooth: 0.3,
			data: actualWeightsData.value || weightsData.value,
			lineStyle: {
				color: "#00c16a",
				width: 2,
			},
			itemStyle: {
				color: "#00c16a",
			},
			areaStyle: {
				color: "rgba(0, 193, 106, 0.2)",
			},
		},
		// idea: show missing weight as dashed
		// line taken from next available weight
		// ...(interpolatedWeightsData.value && interpolatedWeightsData.value.some(w => w !== null) ? [{
		//   name: "interpolated ddd",
		//   type: "line",
		//   symbol: "circle",
		//   symbolSize: 4,
		//   smooth: 0.3,
		//   data: interpolatedWeightsData.value,
		//   lineStyle: {
		//     color: "#00c16a",
		//     width: 2,
		//     type: "dashed",
		//   },
		//   itemStyle: {
		//     color: "#00c16a",
		//     borderColor: "#ffffff",
		//     borderWidth: 1,
		//   },
		// }] : []),
		{
			name: "weight goal",
			type: "line",
			data: goalData.value,
			lineStyle: {
				color: "rgba(255, 0, 0, 0.5)",
				type: "dashed",
			},
			symbol: "none",
			markPoint: {
				symbol: "true",
				itemStyle: {
					color: "transparent",
				},
				data:
					goalData.value && goalData.value.length > 0
						? [
								{
									coord: [
										goalData.value.length - 1,
										goalData.value[
											goalData.value.length - 1
										],
									],
									value: `${
										goalData.value[
											goalData.value.length - 1
										]
									} kg`,
									label: {
										show: true,
										position: "right",
										formatter: t(
											"settings.goal.title"
										),
										color: "rgba(255, 0, 0, 0.5)",
										fontSize: 12,
									},
								},
						  ]
						: [],
			},
		},
	],
}));

const findNextAvailableWeight = (
	startDate: string,
	maxDaysAhead: number = 365
): number | null => {
	const start = new Date(startDate);

	for (let i = 1; i <= maxDaysAhead; i++) {
		const checkDate = new Date(start);
		checkDate.setDate(start.getDate() + i);
		const dateString = checkDate.toISOString().split("T")?.[0] ?? "";

		const dayData = Array.from(dashboard.days.entries()).find(
			([key, value]) => value.date === dateString
		);

		if (dayData?.[1].weight && dayData[1].weight > 0) {
			return dayData[1].weight;
		}
	}

	return null;
};

const generateDateRange = (endDateString: string, days: number) => {
	const endDate = new Date(endDateString);
	const dates: string[] = [];
	const labels: string[] = [];
	const weights: number[] = [];
	const actualWeights: (number | null)[] = [];
	const interpolatedWeights: (number | null)[] = [];

	for (let i = days - 1; i >= 0; i--) {
		const currentDate = new Date(endDate);

		if (currentTab.value === "week") {
			currentDate.setDate(endDate.getDate() - i);
			labels.push(t(`calendar.${shortDays[currentDate.getDay()]}`));
		} else if (currentTab.value === "month") {
			const daysBack = Math.floor((29 / 6) * i);
			currentDate.setDate(endDate.getDate() - daysBack);
			const monthName = currentDate.toLocaleDateString("en", {
				month: "short",
			});
			const day = currentDate.getDate();
			labels.push(day + " " + t(`calendar.${monthName.toLocaleLowerCase()}`));
		} else if (currentTab.value === "year") {
			currentDate.setMonth(endDate.getMonth() - i);
			currentDate.setDate(1);
			const monthName = currentDate.toLocaleDateString("en", {
				month: "short",
			});
			labels.push(monthName);
		}

		const dateString = currentDate.toISOString().split("T")?.[0] ?? "";
		dates.push(dateString);

		const dayData = Array.from(dashboard.days.entries()).find(
			([key, value]) => value.date === dateString
		);

		const originalWeight = dayData?.[1].weight ?? 0;
		weights.push(originalWeight);

		if (originalWeight && originalWeight > 0) {
			actualWeights.push(originalWeight);
			interpolatedWeights.push(null);
		} else {
			const nextWeight = findNextAvailableWeight(dateString);
			if (nextWeight) {
				actualWeights.push(null);
				interpolatedWeights.push(nextWeight);
			} else {
				actualWeights.push(null);
				interpolatedWeights.push(null);
			}
		}
	}

	return { dates, labels, weights, actualWeights, interpolatedWeights };
};

const updateChartData = () => {
	const today = new Date().toISOString().split("T")[0];
	let periodDays;

	switch (currentTab.value) {
		case "week":
			periodDays = 7;
			break;
		case "month":
			periodDays = 7;
			break;
		case "year":
			periodDays = 12;
			break;
		default:
			periodDays = 7;
	}

	const { weights, labels, actualWeights, interpolatedWeights } =
		generateDateRange(today!, periodDays);

	weightsData.value = weights;
	actualWeightsData.value = actualWeights;
	interpolatedWeightsData.value = interpolatedWeights;
	chartLabels.value = labels;

	goalData.value = [];
	for (let i = 0; i < periodDays; i++) {
		goalData.value.push(userSettings.settings?.weight || 0);
	}
};

const isMobile = useIsMobile();
const dashboard = useDashboardStore();

watch(() => currentTab.value, updateChartData);

onMounted(async () => {
	await dashboard.loadAllDays();
	updateChartData();
});

definePageMeta({
	layout: isMobile ? "app-main" : "app-returnable",
});
</script>
