<template>
	<div class="flex flex-col">
		<header
			class="shadow-lg bottom-0 w-full p-4 flex justify-between items-center"
		>
			<!-- balance space-between spacing -->
			<div class="w-8 opacity-0" />

			<p>{{ $t(`routes.${route.fullPath.replace('/', '')}`) }}</p>

			<ULink
				class="ring-1 ring-gray-400 w-fit h-fit grid items-center p-2 rounded-full"
				href="/dashboard"
			>
				<UIcon name="i-heroicons-arrow-left" class="w-5 h-5" />
			</ULink>
		</header>
		<slot />
	</div>
</template>

<script setup lang="ts">
// STORE IMPORT IS HERE TO MAKE PINIA VISIBLE IN NUXT DEVTOOLS
// REMOVE ON PROD(OR NOT)
import { useAuthStore } from "@/stores/auth";

const route = useRoute();
const colorMode = useColorMode();
const settings = useSettingsStore();

console.log('route', route.fullPath)

const auth = useAuthStore();

onMounted(async () => {
	auth.initFromLocalStorage();
	await settings.loadSettings();
	colorMode.value = settings.settings?.theme ?? "light";
	console.log('theme applied globally - ', colorMode.value);
});
</script>
