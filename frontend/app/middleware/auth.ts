import { defineNuxtRouteMiddleware } from "#app";
import { useAuthStore } from "@/stores/auth";

export default defineNuxtRouteMiddleware((to, from) => {
	if (import.meta.server) return;

	const auth = useAuthStore();
	auth.initFromLocalStorage();

	if (auth.token) {
		return navigateTo("/dashboard");
	}
});
