// stores/dashboard.ts
import { defineStore } from "pinia";
import type { dashboardItem } from "@/types/dashboard";
import { PersistenceAdapter } from "@/utils/persistence";

export const useDashboardStore = defineStore("dashboard", () => {
	const isGuest = true; // set dynamically in your app
	const adapter = new PersistenceAdapter(isGuest);

	// Store all days as a Map keyed by date string
	const days = ref(new Map<string, dashboardItem>());
	const selectedDate = ref("");

	async function loadAllDays() {
		const all = await adapter.loadAll();
		days.value = new Map(Object.entries(all));
	}

	function getDay(date: string): dashboardItem | undefined {
		return days.value.get(date);
	}

	async function saveDay(day: dashboardItem) {
		days.value.set(day.date, day);
		await adapter.saveDay(day);
	}
	function clear() {
		days.value.clear();
		selectedDate.value = "";
	}

	return {
		days,
		selectedDate,
		loadAllDays,
		getDay,
		saveDay,
		clear,
	};
});
