<template>
	<div class="w-screen h-screen bg-green-200 flex flex-col">
		<header
			class="outline-red ring-1 bottom-0 w-full p-4 flex justify-between items-center"
		>
			<div class="w-8 h-8 outline" />

			<ULink
				class="ring-1 ring-gray-400 w-fit h-fit grid items-center p-2 rounded-full"
				href="/dashboard"
			>
				<UIcon name="i-heroicons-arrow-left" class="w-8 h-8" />
			</ULink>
		</header>
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
