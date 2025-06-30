export default defineNuxtPlugin((nuxtApp) => {
	const config = useRuntimeConfig();
	const apiUrl = config.public.NUXT_PUBLIC_API_URL;
	nuxtApp.provide("apiUrl", apiUrl);
});
