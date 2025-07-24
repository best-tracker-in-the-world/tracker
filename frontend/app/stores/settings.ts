import { defineStore } from "pinia";
import type { UserSettings } from "@/types/settings";
import { PersistenceAdapter } from "@/utils/persistence";

export const useSettingsStore = defineStore("settings", () => {
	const isGuest = useAuthStore().isLoggedAsGuest;
	const adapter = new PersistenceAdapter(isGuest);
	const isDesktopMenuOpen = ref(true);

	// select between old hamburger menu and bottom navigation bar
	const useBottonNavigationBar = true

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
		useBottonNavigationBar,
		isDesktopMenuOpen,
	};
});
