<template>
	<div class="absolute top-0 right-0 p-4 gap-2 flex">
		<NuxtLink
			v-for="lang in locales"
			:key="lang.name"
			:to="$switchLocalePath(`${lang.code}`)"
			class="rounded-full grid items-center transition-all"
			:class="{
				'outline-2 outline-green-500': locale === lang.code,
			}"
		>
			<UIcon :name="lang.icon" class="size-6"
		/></NuxtLink>
	</div>

	<div
		class="w-screen h-screen flex items-center justify-center bg-gray-100"
	>
		<slot />
	</div>
</template>

<script setup lang="ts">
const { locale } = useI18n();

const locales = [
	{
		code: "en",
		name: "English",
		icon: "i-circle-flags-uk",
	},
	{
		code: "ru",
		name: "Русский",
		icon: "i-circle-flags-ru",
	},
];
</script>
