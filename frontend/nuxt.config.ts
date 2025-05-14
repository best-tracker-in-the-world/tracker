// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
	devtools: { enabled: true },

	modules: [
		"@nuxt/ui",
		"@nuxt/eslint",
		"@nuxt/fonts",
		"@nuxt/icon",
		"@nuxt/image",
		"@nuxt/scripts",
		"@nuxt/test-utils",
		"@pinia/nuxt",
		"@nuxtjs/i18n",
	],

	css: ["~/assets/css/main.css"],

	future: {
		compatibilityVersion: 4,
	},
	i18n: {
		defaultLocale: "en",
		locales: [
			{ code: "en", name: "English", file: "en.json" },
			{ code: "ru", name: "Russian", file: "ru.json" },
		],
	},

	compatibilityDate: "2024-11-27",

	runtimeConfig: {
		public: {
			NUXT_PUBLIC_API_URL: process.env.NUXT_PUBLIC_API_URL,
		},
	},
});
