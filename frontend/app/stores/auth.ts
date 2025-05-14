import { defineStore } from "pinia";
import { ref, computed } from "vue";

export const useAuthStore = defineStore("auth", () => {
	const token = ref<string | null>(
		import.meta.client ? localStorage.getItem("token") : null
	);

	const user = ref<any>(null);

	const isLogged = computed(() => !!token.value);

	function useLogin(newToken: string) {
		console.log("Login with token:", newToken);
		token.value = newToken;
		if (import.meta.client) {
			localStorage.setItem("token", newToken);
		}
		// further decode token here if needed
	}

	function useLogout() {
		token.value = null;
		user.value = null;
		if (import.meta.client) {
			localStorage.removeItem("token");
		}
	}

	return {
		token,
		user,
		isLogged,
		useLogin,
		useLogout,
	};
});
