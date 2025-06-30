<template>
	<UApp>
		<ColorScheme>
			<VitePwaManifest />
			<NuxtLayout>
				<NuxtPage />
			</NuxtLayout>
		</ColorScheme>
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
	try {
		await settings.loadSettings();
		console.log(settings);
		if (settings.settings?.theme) {
			console.log(
				"saved theme preference loaded - ",
				settings.settings?.theme
			);
			colorMode.value = settings.settings?.theme;
		}
		console.log("colorMode", colorMode.value);
	} catch (error) {
		console.log(error);
	}
});
</script>
