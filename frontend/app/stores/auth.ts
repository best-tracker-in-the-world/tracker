import { defineStore } from "pinia";
import { ref, computed } from "vue";

export const useAuthStore = defineStore("auth", () => {
	const token = ref<string | null>(null);
	const isLogged = computed(() => !!token.value);

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
		useLogin,
		useLogout,
		initFromLocalStorage,
	};
});
