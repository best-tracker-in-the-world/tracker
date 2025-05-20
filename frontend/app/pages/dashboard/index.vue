<template>
	<ClientOnly>
		<div class="flex flex-col gap-4 bg-slate-50/50 w-screen h-screen p-2">
			<DashboardCalendar v-model="selectedDate" />
			<!-- dashboard body wrap -->
			<div class="grid grid-cols-2 gap-4">
				<!-- 1. WEIGHT -->
				<DashboardWeight
					:value="selectedDayData?.weight ?? 0"
					:is-loaded="loadedStatus.weight"
					:span="1"
				/>
				<!-- 2. GOAL -->
				<DashboardGoal
					:current="selectedDayCalories ?? 0"
					:max="selectedDayData?.caloricGoal ?? 2500"
					:span="1"
					:is-loaded="loadedStatus.goal"
				/>
			</div>

			<!-- <span>{{ selectedDayData }}</span> -->
			<!-- <span>{{ selectedDate }}</span> -->
		</div>
	</ClientOnly>
</template>

<script setup lang="ts">
import mockData from "~/data/dashboardData.json";
import { CalendarDate } from "@internationalized/date";

const { t } = useI18n();

const today = new Date();

// v-model for calendar
const selectedDate = ref(
	new CalendarDate(
		today.getFullYear(),
		today.getMonth() + 1,
		today.getDate()
	)
);

// mock data
// replace with data from API
// probably in Pinia
// eslint-disable-next-line @typescript-eslint/no-explicit-any
const dashboardData = mockData as any;

// get the data for the selected day
const selectedDayData = computed(() => {
	const year = selectedDate.value.year;
	const month = String(selectedDate.value.month).padStart(2, "0");
	const day = String(selectedDate.value.day).padStart(2, "0");
	const formattedDate = `${year}-${month}-${day}`;
	return dashboardData.find((data) => data.date === formattedDate);
});

// todays current calories for Goal component
const selectedDayCalories = computed(() => {
	if (!selectedDayData.value) return 0;
	return selectedDayData.value.foodLogs.reduce(
		(acc: number, cur: { caloricContent: number; weight: number }) =>
			acc + cur.caloricContent * (cur.weight / 100),
		0
	);
});

const loadedStatus = reactive({
	weight: false,
	goal: false,
	foods: false,
});

// to test loading to loaded transition
onMounted(() => {
	setTimeout(() => {
		loadedStatus.weight = true;
		loadedStatus.goal = true;
	}, 1500);
});

definePageMeta({
	layout: "app",
});
</script>
