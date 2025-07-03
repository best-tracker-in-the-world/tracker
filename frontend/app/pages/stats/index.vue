<template><div>stats page</div></template>

<script setup lang="ts">
const isMobile = useIsMobile();

definePageMeta({
	layout: isMobile? "app-main" : "app-returnable",
});
</script>
