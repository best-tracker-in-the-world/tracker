// persistence.ts
import * as localForage from "localforage";
import type { dashboardItem } from "@/types/dashboard";
import type { FoodItem } from "@/types/food";
import type { userSettings } from "@/types/settings";

export class PersistenceAdapter {
	private isGuest: boolean;

	constructor(isGuest: boolean) {
		this.isGuest = isGuest;
	}

	async loadAll(): Promise<Record<string, dashboardItem>> {
		if (this.isGuest) {
			const data: Record<string, dashboardItem> = {};
			await localForage.iterate((value, key) => {
				if (key.startsWith("dashboard-")) {
					const date = key.replace("dashboard-", "");
					data[date] = value as dashboardItem;
				}
			});
			return data;
		} else {
			// server load
			return {};
		}
	}

	async saveDay(day: dashboardItem): Promise<void> {
		if (this.isGuest) {
			await localForage.setItem(`dashboard-${day.date}`, day);
		} else {
			// server save
			console.log("Saving to server:", day);
		}
	}

	async loadFoodItems(): Promise<FoodItem[]> {
		if (this.isGuest) {
			const items =
				(await localForage.getItem<FoodItem[]>("food-items")) || [];
			return items;
		}
		return [];
	}

	async saveFoodItem(item: FoodItem): Promise<void> {
		if (this.isGuest) {
			console.log("trting to save item", item);
			const items = await this.loadFoodItems();
			items.push(item);
			await localForage.setItem(
				"food-items",
				JSON.parse(JSON.stringify(items))
			);
		}
	}
	async saveUserSettings(settings: userSettings): Promise<void> {
		if (this.isGuest) {
			await localForage.setItem("user-settings", settings);
			console.log('settings saved successfully', settings)
		}
	}



	async loadUserSettings(): Promise<userSettings | null> {
		if (this.isGuest) {
			const settings = await localForage.getItem<userSettings>(
				"user-settings"
			);
			console.log('settings loaded successfully', settings)
			if (!settings) {
				const defaultSettings: userSettings = {
					name: "Guest",
					email: "example@email.com",
					password: "**********",
					language: "ru",
					currentGoal: 2000,
					weight: null,
					height: null,
					age: null,
					gender: null,
					theme: null,
				};
				await this.saveUserSettings(defaultSettings);
				console.log('no settings found, using default settings', defaultSettings)
				return defaultSettings;
			}
			return settings;
		} else {
			// get from server
		}
		return null;
	}
}
