import { defineStore } from "pinia";
import type { UserSettings } from "@/types/settings";
import { PersistenceAdapter } from "@/utils/persistence";

export const useSettingsStore = defineStore("settings", () => {
	const isGuest = useAuthStore().isLoggedAsGuest;
	const adapter = new PersistenceAdapter(isGuest);

	const settings = ref<UserSettings | null>(null);

	async function loadSettings() {
		settings.value = await adapter.loadUserSettings();
	}

	function saveSettings(settings: UserSettings) {
		return adapter.saveUserSettings(settings);
	}

	return {
		settings,
		loadSettings,
		saveSettings,
	};
});
