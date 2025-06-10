import { defineNuxtRouteMiddleware } from "#app";

export default defineNuxtRouteMiddleware((to, from) => {
	if (import.meta.server) return;

	console.log("auth middleware triggered");

	const auth = useAuthStore();
	auth.initFromLocalStorage();

	if (to.path === '/dashboard') {
		return; // already on dashboard, no need to redirect
	}

	if(to.path === '/login' && auth.isLogged) {
		console.log("u r logged! redirecting to dashboard");
		return navigateTo("/dashboard");
	}
});
