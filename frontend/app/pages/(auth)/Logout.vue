<template>
	<h1>logout page kek</h1>
</template>

<script setup lang="ts">
import { useAuthStore } from "@/stores/auth";

const { useLogout } = useAuthStore();
const router = useRouter();

onMounted(() => {
	console.log("Logout page mounted");
	useLogout();
	setTimeout(() => {
		router.push("/");
	}, 1000);
});
</script>
