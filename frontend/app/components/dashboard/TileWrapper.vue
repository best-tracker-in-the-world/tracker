<template>
	<div
		class="tile-wrapper flex flex-col gap-2 p-4 rounded-[25px] shadow-xl bg-linear-45 to-white bg-white"
		:class="`from-${color}-200 text-${color}-900 ${
			span === 1 ? 'aspect-[1/1.33]' : 'col-span-2'
		}`"
	>
		<header class="flex items-center gap-2">
			<UIcon :name="icon" />
			<span> {{ title }}</span>
		</header>
		<div class="grid flex-1 items-center align-center">
			<slot />
		</div>
	</div>
</template>

<script setup lang="ts">
interface Props {
	title?: string | null;
	color?: string | null;
	span?: number;
	icon?: string | null;
}
// eslint-disable-next-line @typescript-eslint/no-unused-vars
const props = withDefaults(defineProps<Props>(), {
	title: "",
	color: "",
	icon: "",
	span: 2,
});
</script>

<style></style>
