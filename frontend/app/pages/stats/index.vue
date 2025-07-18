<template>
	<div class="w-full">
		<ClientOnly>
			<div class="w-full">
				<UTabs v-model="currentTab" :items="tabs" />
			</div>
			<div class="w-full h-[400px] min-w-0">
				<VChart :option="options" autoresize class="w-full h-full" />
			</div>
		</ClientOnly>
	</div>
</template>

<script setup lang="ts">
import "echarts";
import type { RadioGroupItem } from "@nuxt/ui";

const { t } = useI18n();
const userSettings = useSettingsStore();

console.log('settings', userSettings)

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
	{
		label: t("stats.year"),
		value: "year",
	},
]);

const shortDays = ['sun', 'mon', 'tue', 'wed', 'thu', 'fri', 'sat'];
const weightsData = ref<number[]>();


const options = ref({
	width: "80%",
	height: "auto",
	xAxis: {
		type: "category",
		data: shortDays.map(day => t(`calendar.${day}`)	),
	},
	yAxis: {},
	series: [
		{
			name: "weight",
			type: "line",
			symbol: "none",
			data: weightsData.value,
		},
		{
			name: "weight goal",
			type: "line",
			data: [55,55],
			lineStyle: {
				color: "red",
				type: "dashed",
			},
			symbol: "none",
		},
	],
});

const generateDateRange = (endDateString: string, days: number) => {
	const endDate = new Date(endDateString);
	const dates: string[] = [];
	const labels: string[] = [];
	const weights: number[] = [];
	const calories: number[] = [];

	
	for (let i = days - 1; i >= 0; i--) {
		const currentDate = new Date(endDate);
		currentDate.setDate(endDate.getDate() - i);
		const dateString = currentDate.toISOString().split('T')[0];
		
		dates.push(dateString);
		labels.push(t(`calendar.${shortDays[currentDate.getDay()]}`));
		
		const dayData = Array.from(dashboard.days.entries()).find(([key, value]) => value.date === dateString);
		weights.push(dayData?.[1].weight ?? 0)


	}
	return { dates, labels, weights, calories };
};

const updateChartData = () => {
	const today = new Date().toISOString().split('T')[0];
	const periodDays = { week: 7, month: 30, year: 365 };
	const days = periodDays[currentTab.value as keyof typeof periodDays] || 7;
	
	const { weights } = generateDateRange(today!, days);
	weightsData.value = weights;
	console.log(options.value.series[0])
	console.log(options.value.series[0])
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