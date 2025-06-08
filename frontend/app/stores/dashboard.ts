// stores/dashboard.ts
import { defineStore } from "pinia";
import type { dashboardItem } from "@/types/dashboard";
import { PersistenceAdapter } from "@/utils/persistence";
import { useAuthStore } from "~/stores/auth";

export const useDashboardStore = defineStore("dashboard", () => {
	const isGuest = useAuthStore().isLoggedAsGuest;
	// const isGuest = true;
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
		console.log("saveDay", day);
		const plainDay = JSON.parse(JSON.stringify(day));
		days.value.set(day.date, plainDay);
		await adapter.saveDay(plainDay);
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
