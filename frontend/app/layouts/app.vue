<template>
	<div class="flex">
		<LazyLayoutsMenu />
		<slot />
	</div>
</template>

<script setup lang="ts">
// STORE IMPORT IS HERE TO MAKE PINIA VISIBLE IN NUXT DEVTOOLS
// REMOVE ON PROD(OR NOT)
import { useAuthStore } from "@/stores/auth";
const auth = useAuthStore();

onMounted(async () => {
	auth.initFromLocalStorage();
});
</script>
