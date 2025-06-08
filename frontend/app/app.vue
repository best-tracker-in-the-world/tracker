<template>
	<UApp>
		<VitePwaManifest />
		<NuxtLayout>
			<NuxtPage />
		</NuxtLayout>
	</UApp>
</template>

<script setup lang="ts">
const colorMode = useColorMode();
const settings = useSettingsStore();

onMounted(async () => {
	await settings.loadSettings();
	colorMode.value = settings.settings?.theme ?? "light";
	console.log('theme applied globally - ', colorMode.value);
});

</script>