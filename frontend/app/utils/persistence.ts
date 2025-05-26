// persistence.ts
import * as localForage from "localforage";
import type { dashboardItem } from "@/types/dashboard";

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
}
