<template>
	<button
		class="flex items-center text-sm underline decoration-dotted underline-offset-2 opacity-50 hover:opacity-100 transition-all gap-1 cursor-pointer w-fit"
		:class="{ 'opacity-100': isToggled }"
		@click="handleClick($event)"
	>
		<slot>
			{{ label }}
		</slot>
		<UIcon
			:class="{ 'rotate-360': isToggled }"
			class="rotate-180 transition-all"
			name="i-heroicons-chevron-down"
			size="12"
		/>
	</button>
</template>

<script setup lang="ts">
const { label } = withDefaults(defineProps<{ label?: string }>(), {
	label: "Additional",
});

const isToggled = defineModel<boolean>();

function handleClick(e: Event) {
	e.preventDefault();
	isToggled.value = !isToggled.value;
}
</script>
