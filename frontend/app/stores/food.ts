// stores/food.ts
import { defineStore } from "pinia";
import { ref } from "vue";
import { PersistenceAdapter } from "@/utils/persistence";
import { useAuthStore } from "~/stores/auth";
import type { FoodItem } from "@/types/food";

export const useFoodStore = defineStore("food", () => {
	const isGuest = useAuthStore().isLoggedAsGuest;
	const adapter = new PersistenceAdapter(isGuest);

	const foodItems = ref<FoodItem[]>([]);
	const favorites = ref<FoodItem[]>([]);
	const currentGoal = useSettingsStore().currentGoal;

	async function loadFoodItems() {
		if (isGuest) {
			const items = await adapter.loadFoodItems();
			foodItems.value = items;
		} else {
			// get from server
		}
	}

	async function addFoodItem(item: FoodItem) {
		foodItems.value.push(item);
		if (isGuest) {
			await adapter.saveFoodItem(item);
		} else {
			// save to server
		}
	}

	async function toggleFavorite(item: FoodItem) {
		const index = favorites.value.findIndex((f) => f.id === item.id);
		if (index === -1) {
			favorites.value.push(item);
			if (!isGuest) {
				// send favorite to server
			}
		} else {
			favorites.value.splice(index, 1);
			if (!isGuest) {
				// unfavorite from server
			}
		}
	}

	async function logFoodToCurrentDay(item: FoodItem, weight: number) {
		const dashboardStore = useDashboardStore();
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

		day.foodLogs.push({
			id: Date.now(),
			name: item.name,
			caloricContent: item.caloricContent,
			weight: weight,
			protein: item.protein,
			carbs: item.carbs,
			fat: item.fat,
		});

		await dashboardStore.saveDay(day);
	}

	return {
		foodItems,
		favorites,
		loadFoodItems,
		addFoodItem,
		toggleFavorite,
		logFoodToCurrentDay,
	};
});
