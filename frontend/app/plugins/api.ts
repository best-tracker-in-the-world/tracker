// plugins/api.ts
export default defineNuxtPlugin((nuxtApp) => {
	const config = useRuntimeConfig();

	// Make sure the variable is correctly accessed
	const apiUrl = config.public.NUXT_PUBLIC_API_URL;

	// Log it to check
	console.log("API URL:", apiUrl);

	// Provide it globally
	nuxtApp.provide("apiUrl", apiUrl);
});
