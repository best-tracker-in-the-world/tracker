import { defineStore } from "pinia";
import { ref, computed } from "vue";
// import * as localForage from "localforage";

export const useAuthStore = defineStore("auth", () => {
	const token = ref<string | null>(null);

	const isLogged = computed(() => !!token.value);

	const isLoggedAsGuest = computed(() => token.value === "guest");

	function logAsGuest() {
		token.value = "guest";
		localStorage.setItem("token", "guest");
	}

	function initFromLocalStorage() {
		const saved = localStorage.getItem("token");
		if (saved) {
			token.value = saved;
		}
	}

	function useLogin(newToken: string) {
		token.value = newToken;
		localStorage.setItem("token", newToken);
	}

	function useLogout() {
		token.value = null;
		localStorage.removeItem("token");
	}

	return {
		token,
		isLogged,
		logAsGuest,
		isLoggedAsGuest,
		useLogin,
		useLogout,
		initFromLocalStorage,
	};
});
