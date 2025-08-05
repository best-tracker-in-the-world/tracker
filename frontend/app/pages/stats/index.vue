<template>
	<div class="w-full p-4">
		<ClientOnly>
			<div class="max-w-[80%] mx-auto">
				<UTabs v-model="currentTab" :items="tabs" />
			</div>
			<div
				class="w-full h-[350px] mt-2"
				@click="weightAreLabelsVisible = !weightAreLabelsVisible"
			>
				<VChart
					:option="weightOptions"
					autoresize
					class="w-full h-full"
				/>
			</div>
			<div
				class="w-full h-[350px] mt-4"
				@click="calAreLabelsVisible = !calAreLabelsVisible"
			>
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
const caloriesData = ref<number[]>([]);
const actualCaloriesData = ref<(number | null)[]>();
const calorieGoalData = ref<number[]>();
const weightAreLabelsVisible = ref<boolean>(false);
const calAreLabelsVisible = ref<boolean>(false);

const weightOptions = computed(() => ({
	width: "85%",
	height: "auto",
	title: {
		text:
			t("stats.weight.title") + ", " + t("stats.weight.unit") ||
			"Weight",
		left: "center",
		textStyle: {
			fontSize: 16,
		},
	},
	xAxis: {
		type: "category",
		data:
			chartLabels.value ||
			shortDays.map((day) => t(`calendar.${day}`)),
	},
	axisLabel: {
		formatter: function (value: string) {
			return value.replace(" ", "\n");
		},
		align: "center",
		verticalAlign: "top",
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
			label: {
				show: weightAreLabelsVisible.value,
				position: "top",
				color: "#333", 
				fontSize: 10,
				fontWeight: "bold",
				backgroundColor: "rgba(255,255,255,0.9)", 
				borderRadius: 4,
				padding: [4, 6],
				formatter: "{c}",
				border: "1px solid #aaa",
				borderColor: "#00c16a",
				borderWidth: 1,
			},
		},
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
										show: false,
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

const caloriesOptions = computed(() => ({
	width: "80%",
	height: "auto",
	title: {
		text:
			t("stats.calories.title") + ", " + t("stats.calories.unit") ||
			"Calories",
		left: "center",
		textStyle: {
			fontSize: 16,
		},
	},
	xAxis: {
		type: "category",
		data:
			chartLabels.value ||
			shortDays.map((day) => t(`calendar.${day}`)),
		axisLabel: {
			formatter: function (value: string) {
				return value.replace(" ", "\n");
			},
			align: "center",
			verticalAlign: "top",
			margin: 15,
			fontSize: 11,
		},
	},
	yAxis: {
		type: "value",
		min:
			Math.min(
				...(actualCaloriesData.value?.filter(
					(c): c is number => c !== null && c > 0
				) || [0]),
				...(calorieGoalData.value?.filter((c) => c > 0) || [0])
			) - 200,
		max:
			Math.max(
				...(actualCaloriesData.value?.filter(
					(c): c is number => c !== null && c > 0
				) || [2000]),
				...(calorieGoalData.value?.filter((c) => c > 0) || [2000])
			) + 200,
		axisLabel: {
			formatter: "{value}",
		},
		axisPointer: {
			snap: true,
		},
	},
	series: [
		// calories eaten
		{
			name: "calories eaten",
			type: "line",
			symbol: "circle",
			symbolSize: 6,
			smooth: 0.3,
			data: actualCaloriesData.value || caloriesData.value,
			lineStyle: {
				color: "#ff6b35",
				width: 2,
			},
			itemStyle: {
				color: "#ff6b35",
			},
			areaStyle: {
				color: "rgba(255, 107, 53, 0.2)",
			},
			label: {
				show: calAreLabelsVisible.value,
				position: "top",
				color: "#333", // label text color
				fontSize: 12,
				fontWeight: "bold",
				backgroundColor: "rgba(255,255,255,0.9)", // optional background
				borderRadius: 4,
				padding: [4, 6], // top/bottom, left/right
				formatter: "{c}", // or custom format
				border: "1px solid #aaa",
				borderColor: "#ff6b35",
				borderWidth: 1,
			},
		},
		// calorie goal
		{
			name: "calorie goal",
			type: "line",
			data: calorieGoalData.value,
			lineStyle: {
				color: "rgba(0, 123, 255, 0.5)",
				type: "dashed",
				width: 2,
			},
			symbol: "none",
			markPoint: {
				symbol: "true",
				itemStyle: {
					color: "transparent",
				},
				data:
					calorieGoalData.value &&
					calorieGoalData.value.length > 0
						? [
								{
									coord: [
										calorieGoalData.value.length -
											1,
										calorieGoalData.value[
											calorieGoalData.value
												.length - 1
										],
									],
									value: `${
										calorieGoalData.value[
											calorieGoalData.value
												.length - 1
										]
									} kcal`,
									label: {
										show: false,
										position: "right",
										formatter:
											t(
												"settings.calorie_goal"
											) || "Calorie Goal",
										color: "rgba(0, 123, 255, 0.5)",
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

const calculateTotalCalories = (foodLogs: any[]): number => {
	if (!foodLogs || foodLogs.length === 0) return 0;
	return foodLogs.reduce((total, log) => {
		return total + (log.caloricContent || 0);
	}, 0);
};

const generateDateRange = (endDateString: string, days: number) => {
	const endDate = new Date(endDateString);
	const dates: string[] = [];
	const labels: string[] = [];
	const weights: number[] = [];
	const actualWeights: (number | null)[] = [];
	const interpolatedWeights: (number | null)[] = [];
	const calories: number[] = [];
	const actualCalories: (number | null)[] = [];

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
			labels.push(
				day + " " + t(`calendar.${monthName.toLocaleLowerCase()}`)
			);
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

		const totalCalories = dayData?.[1].foodLogs
			? calculateTotalCalories(dayData[1].foodLogs)
			: 0;

		calories.push(totalCalories);

		if (totalCalories > 0) {
			actualCalories.push(totalCalories);
		} else {
			actualCalories.push(null);
		}
	}

	return {
		dates,
		labels,
		weights,
		actualWeights,
		interpolatedWeights,
		calories,
		actualCalories,
	};
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

	const {
		weights,
		labels,
		actualWeights,
		interpolatedWeights,
		calories,
		actualCalories,
	} = generateDateRange(today!, periodDays);
	//weight
	weightsData.value = weights;
	actualWeightsData.value = actualWeights;
	interpolatedWeightsData.value = interpolatedWeights;
	chartLabels.value = labels;

	goalData.value = [];
	for (let i = 0; i < periodDays; i++) {
		goalData.value.push(userSettings.settings?.weight || 0);
	}
	// calories
	caloriesData.value = calories;
	actualCaloriesData.value = actualCalories;

	calorieGoalData.value = [];
	for (let i = 0; i < periodDays; i++) {
		const calorieGoal =
			userSettings.settings?.currentGoal ||
			dashboard.days.get(labels[i])?.caloricGoal ||
			2000;
		calorieGoalData.value.push(calorieGoal);
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
