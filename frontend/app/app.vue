<template>
	<main :class="{'pb-20' : useBottonNavigationBar}">
	<UApp>
		<ColorScheme>
			<VitePwaManifest />
			<NuxtLayout>
				<NuxtPage />
			</NuxtLayout>
		</ColorScheme>
	</UApp>
	</main>
</template>

<script setup lang="ts">
const colorMode = useColorMode();
const settings = useSettingsStore();
const { useBottonNavigationBar } = useSettingsStore()

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
	} catch (error) {
		console.log(error);
	}
});
</script>
