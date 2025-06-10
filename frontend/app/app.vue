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

watch(
	() => settings.settings?.theme,
	(val) => {
		if (!val) return;
		colorMode.value = val;
	}
);

onMounted(async () => {
	await settings.loadSettings();
	colorMode.value = settings.settings?.theme;
});
</script>
