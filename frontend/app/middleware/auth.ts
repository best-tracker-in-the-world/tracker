import { defineNuxtRouteMiddleware } from "#app";
import { useAuthStore } from "@/stores/auth";

export default defineNuxtRouteMiddleware((to, from) => {
	if (import.meta.server) return;

	const auth = useAuthStore();
	auth.initFromLocalStorage();

	if (auth.token) {
		console.log("you just came through auth middleware");
		return navigateTo("/dashboard");
	}
});
