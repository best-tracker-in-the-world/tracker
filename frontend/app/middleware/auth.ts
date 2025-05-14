// redirect if logged in
import { defineNuxtRouteMiddleware } from "#app";
import { useAuthStore } from "@/stores/auth";

export default defineNuxtRouteMiddleware((to, from) => {
	const { isLogged } = useAuthStore();
	console.log("isLogged", isLogged);
	if (isLogged) {
		return navigateTo("/dashboard");
	}
});
