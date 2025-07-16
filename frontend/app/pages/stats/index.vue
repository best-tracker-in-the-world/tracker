<template>
	<div class="w-full">
		<ClientOnly>
			<div class="w-full">
				<UTabs v-model="currentTab" :items="tabs" />
			</div>
			<div class="w-full h-[400px] min-w-0">
				<VChart :option="option" autoresize class="w-full h-full" />
			</div>
		</ClientOnly>
	</div>
</template>

<script setup lang="ts">
import "echarts";
import type { RadioGroupItem } from "@nuxt/ui";

const { t } = useI18n();

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

const generateDateRange = (endDateString: string, days: number) => {
	const endDate = new Date(endDateString);
	const dates: string[] = [];
	const labels: string[] = [];
	const weights: number[] = [];
	const calories: number[] = [];
	const shortDays = ['sun', 'mon', 'tue', 'wed', 'thu', 'fri', 'sat'];
	
	for (let i = days - 1; i >= 0; i--) {
		const currentDate = new Date(endDate);
		currentDate.setDate(endDate.getDate() - i);
		const dateString = currentDate.toISOString().split('T')[0];
		
		dates.push(dateString);
		labels.push(t(`calendar.${shortDays[currentDate.getDay()]}`));
		
		const dayData = dashboard.days.find(x => x.value.date === dateString);
		weights.push(dayData?.value.weight || 0);
		
		const totalCalories = dayData?.value.foodLogs.reduce((sum, log) => sum + (log.caloricContent || 0), 0) || 0;
		calories.push(totalCalories);
	}
	
	return { dates, labels, weights, calories };
};

const updateChartData = () => {
	const today = new Date().toISOString().split('T')[0];
	const periodDays = { week: 7, month: 30, year: 365 };
	const days = periodDays[currentTab.value] || 7;
	
	const { dates, labels, weights, calories } = generateDateRange(today, days);
	
	option.value.xAxis.data = labels;
	option.value.series[0].data = weights;
	// option.value.series[1].data = new Array(weights.length).fill(2000); 
};

const option = ref<ECOption>({
	width: "80%",
	height: "auto",
	xAxis: {
		type: "category",
		data: ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"],
	},
	yAxis: {},
	series: [
		{
			name: "weight",
			type: "line",
			symbol: "none",
			data: [100, 94, 99, 95, 90, 91, 89.1],
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

const isMobile = useIsMobile();
const dashboard = useDashboardStore();

watch(() => currentTab.value, updateChartData);

onMounted(async () => {
	await dashboard.loadAllDays();
	console.log(dashboard.days);
	// updateChartData();
});

definePageMeta({
	layout: isMobile ? "app-main" : "app-returnable",
});
</script>