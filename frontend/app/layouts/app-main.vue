<template>
	<div class="flex w-screen h-screen">
		<LazyLayoutsMenu />
		<slot />
	</div>
</template>

<script setup lang="ts">
// STORE IMPORT IS HERE TO MAKE PINIA VISIBLE IN NUXT DEVTOOLS
// REMOVE ON PROD(OR NOT)
import { useAuthStore } from "@/stores/auth";

const auth = useAuthStore();
const colorMode = useColorMode();
const settings = useSettingsStore();

onMounted(async () => {
	auth.initFromLocalStorage();
	await settings.loadSettings();
	colorMode.value = settings.settings?.theme ?? "light";
	console.log('theme applied globally - ', colorMode.value);
});

</script>
