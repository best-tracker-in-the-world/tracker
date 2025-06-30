// persistence.ts
import * as localForage from "localforage";
import type { dashboardItem } from "@/types/dashboard";
import type { FoodItem } from "@/types/food";
import type { UserSettings } from "@/types/settings";

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
	async saveUserSettings(settings: UserSettings): Promise<void> {
		console.log("saving settings...", settings);
		await localForage.setItem("user-settings", settings);
		console.log("settings saved successfully", settings);
	}

	async loadUserSettings(): Promise<UserSettings | null> {
		try {
			const settings = await localForage.getItem<UserSettings>(
				"user-settings"
			);

			if (!settings) {
				const date = new Date();
				console.log(date);
				const defaultSettings: UserSettings = {
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
					savedAt: date.toISOString(),
					updatedAt: date.toISOString(),
				};
				await this.saveUserSettings(defaultSettings);
				console.log(
					"No settings found, using default settings",
					defaultSettings
				);
				return defaultSettings;
			}

			console.log("Settings found, using them", settings);
			return settings;
		} catch (error) {
			console.error("Failed to load user settings", error);
			return null;
		}
	}
}
