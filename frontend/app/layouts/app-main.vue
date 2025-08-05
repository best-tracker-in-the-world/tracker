<template>
	<div class="flex h-full">
		<LazyLayoutsMenu />
		<div
			class="w-screen max-w-screen"
			:class="{
				'pt-14 w-full': isMobile && route.currentRoute.value.name !== 'dashboard',
				'w-[calc(100%-256px)]': !isMobile && isDesktopMenuOpen,
				'w-[calc(100%-64px)]': !isMobile && !isDesktopMenuOpen,
			}"
		>
			<div
				v-if="route.currentRoute.value.name !== 'dashboard'"
				class="h-14 p-4 border-b border-gray-200 dark:border-gray-800 font-bold shadow-xs bg-white dark:bg-gray-900 z-10 flex items-center justify-between"
				:class="{
					'fixed top-0 w-full': isMobile,
				}"
			>
				<span>
					{{
						$t(
							`menu.${String(
								route.currentRoute.value.name
							)}`
						)
					}}
				</span>
				<div id="header-button-teleport" />
			</div>
			<slot />
		</div>
	</div>
</template>

<script setup lang="ts">
// STORE IMPORT IS HERE TO MAKE PINIA VISIBLE IN NUXT DEVTOOLS
// REMOVE ON PROD(OR NOT)
import { useAuthStore } from "@/stores/auth";

const auth = useAuthStore();
const route = useRouter();
const { isMobile } = useIsMobile();
const settingsStore = useSettingsStore();
const { isDesktopMenuOpen } = storeToRefs(settingsStore);

onMounted(async () => {
	auth.initFromLocalStorage();
	if (!auth.isLogged) {
		navigateTo("/login");
	}
});
</script>
