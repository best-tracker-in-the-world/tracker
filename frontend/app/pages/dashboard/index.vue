<template>
	<ClientOnly>
		<div
			class="flex flex-col gap-4 w-screen h-screen p-2 mb-[100px]"
		>
			<DashboardCalendar v-model="selectedDate as any" />
			<!-- dashboard body wrap -->
			<div class="grid grid-cols-2 gap-4">
				<!-- 1. WEIGHT -->
				<DashboardWeight
					:value="selectedDayData?.weight ?? 0"
					:is-loaded="loadedStatus.weight"
					:span="1"
					@weight-submit="handleWeightSubmit"
				/>
				<!-- 2. GOAL -->
				<DashboardGoal
					:current="selectedDayCalories ?? 0"
					:max="selectedDayData?.caloricGoal ?? currentGoal"
					:span="1"
					:is-loaded="loadedStatus.goal"
				/>
				<!-- 3. FOOD -->
				<DashboardFoodList
					:items="selectedDayData?.foodLogs ?? []"
					:span="2"
					:is-loaded="loadedStatus.foods"
					@food-submit="handleFoodSubmit"
				/>
			</div>
		</div>
	</ClientOnly>
</template>

<script setup lang="ts">
import { CalendarDate } from "@internationalized/date";
import { useDashboardStore } from "@/stores/dashboard";
import type { dashboardItem } from "@/types/dashboard";
import { useSettingsStore } from "~/stores/settings";


const currentGoal = useSettingsStore().currentGoal;

definePageMeta({
	layout: "app-main",
});

const dashboardStore = useDashboardStore();

const today = new Date();
const selectedDate = ref(
	new CalendarDate(
		today.getFullYear(),
		today.getMonth() + 1,
		today.getDate()
	)
);
// save initial value to store
dashboardStore.selectedDate = selectedDate.value.toString();

const loadedStatus = reactive({
	weight: false,
	goal: false,
	foods: false,
});

onMounted(async () => {
	await dashboardStore.loadAllDays();
	// skeleton test
	setTimeout(() => {
		loadedStatus.weight = true;
		loadedStatus.goal = true;
		loadedStatus.foods = true;
	}, 1000);
});

// sync store's and component's date
watch(selectedDate, (newDate) => {
	const dateStr = `${newDate.year}-${String(newDate.month).padStart(
		2,
		"0"
	)}-${String(newDate.day).padStart(2, "0")}`;
	dashboardStore.selectedDate = dateStr;
});

// computed selected day data
const selectedDayData = computed(() => {
	return dashboardStore.getDay(dashboardStore.selectedDate) ?? null;
});

// get calories based on selected day
const selectedDayCalories = computed(() => {
	if (!selectedDayData.value) return 0;
	return selectedDayData.value.foodLogs.reduce(
		(acc, cur) => acc + cur.caloricContent * (cur.weight / 100),
		0
	);
});

// SUBMITS

// weight
async function handleWeightSubmit(weight: number) {
	const date = dashboardStore.selectedDate;
	let day = dashboardStore.getDay(date);
	if (!day) {
		day = {
			date,
			weight: 0,
			caloricGoal: currentGoal,
			foodLogs: [],
		};
	}
	day.weight = weight;
	await dashboardStore.saveDay(day);
}

// food

async function handleFoodSubmit(data: dashboardItem["foodLogs"][0]) {
	const date = dashboardStore.selectedDate;
	let day = dashboardStore.getDay(date);
	if (!day) {
		day = {
			date,
			weight: 0,
			caloricGoal: currentGoal,
			foodLogs: [],
		};
	}
	day.foodLogs.push(data);
	await dashboardStore.saveDay(day);
}
</script>
