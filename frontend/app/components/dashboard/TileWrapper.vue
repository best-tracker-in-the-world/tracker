<template>
	<div
		class="tile-wrapper flex flex-col gap-2 p-4 rounded-[25px] shadow-lg bg-linear-45 to-gray-50 dark:to-gray-800 bg-slate-100 dark:bg-slate-800 transition-all dark:inset-shadow-xl dark:inset-shadow-red-500 light:ring-1 light:ring-gray-200/80"
		:class="`
		from-${color}-200 
		text-${color}-900 
		${span === 1 ? 'aspect-[1/1.33]' : 'col-span-2'}
		${isSelected ? 'shadow-xl shadow-gray-100 ring-1 ring-gray-200' : ''}`"
	>
		<header class="flex align-center justify-between">
			<div class="flex gap-2 alighn-center">
				<span class="w-fit grid items-center dark:text-gray-600">
					<UIcon class="" size="20" :name="icon" />
				</span>
				<p
					class="whitespace-nowrap grid items-center dark:text-gray-500"
				>
					{{ title }}
				</p>
			</div>
			<div>
				<slot name="header" />
			</div>
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
	icon?: string;
	isSelected?: boolean;
}
// eslint-disable-next-line @typescript-eslint/no-unused-vars
const props = withDefaults(defineProps<Props>(), {
	title: "",
	color: "",
	icon: "",
	span: 2,
	isSelected: false,
});
</script>

<style scoped></style>
