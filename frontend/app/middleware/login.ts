import { defineNuxtRouteMiddleware } from "#app";

export default defineNuxtRouteMiddleware((to, from) => {

	const auth = useAuthStore();

	if(auth.isLogged) {
		if(auth.isLoggedAsGuest) {
			console.log("logged as offline user");
		} else {
			console.log("logged as online user");
		}
		return navigateTo("/dashboard");
	} 
	return;
});

