<template>
	<div class="flex">
		<LayoutsSidebar v-if="!isMobile" />
		<slot />
	</div>
</template>

<script setup lang="ts">
// STORE IMPORT IS HERE TO MAKE PINIA VISIBLE IN NUXT DEVTOOLS
// REMOVE ON PROD(OR NOT)
import { useAuthStore } from "@/stores/auth";

const { isMobile } = useIsMobile();
const auth = useAuthStore();

onMounted(async () => {
	auth.initFromLocalStorage();
	console.log(auth.isLogged);
});
</script>
