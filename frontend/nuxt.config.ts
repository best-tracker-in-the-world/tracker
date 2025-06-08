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
		"@pinia/nuxt",
		"@nuxtjs/i18n",
		"@formkit/auto-animate",
		"@vueuse/nuxt",
		"@vite-pwa/nuxt",
	],
	pwa: {
		manifest: {
			name: "Tracker App",
			short_name: "Tracker App",
			description: "testing description cock ass",
			icons: [
				{
					src: "icons/192.png",
					sizes: "192x192",
					type: "image/png",
				},
			],
		},
		workbox: {
			navigateFallbackDenylist: [/^\/__nuxt_devtools__/],
			navigateFallback: "/dashboard",
		},
		devOptions: {
			enabled: true,
			type: "module",
		},
	},
	css: ["~/assets/css/main.css"],

	future: {
		compatibilityVersion: 4,
	},
	i18n: {
		defaultLocale: "ru",
		locales: [
			{ code: "en", name: "English", file: "en.json" },
			{ code: "ru", name: "Русский", file: "ru.json" },
		],
		lazy: true,
		langDir: "locales/",
	},

	compatibilityDate: "2024-11-27",

	runtimeConfig: {
		public: {
			NUXT_PUBLIC_API_URL: process.env.NUXT_PUBLIC_API_URL,
		},
	},
});
