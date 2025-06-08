import { defineStore } from "pinia";
import { ref, computed } from "vue";

export const useUserStore = defineStore("user", () => {
	const theme = ref("light");

	const isDark = computed(() => theme.value === "dark");

	const currentGoal = ref(2500);

	function setTheme(newTheme: string) {
		theme.value = newTheme;
	}
	function toggleTheme() {
		theme.value = theme.value === "dark" ? "light" : "dark";
	}

	return {
		theme,
		isDark,
		setTheme,
		toggleTheme,
		currentGoal,
	};
});
