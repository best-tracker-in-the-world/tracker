<template>
	<Teleport v-if="modelValue" to="body">
		<div
			ref="modal"
			class="modal fixed z-[100] w-screen bg-white"
			:class="computedClass"
		>
			<slot name="header">
				<div class="flex items-center justify-between mb-4">
					<UIcon
						name="i-heroicons-x-mark"
						class="opacity-0"
						size="32"
					/>
					<p class="text-md">{{ title }}</p>
					<UIcon
						name="i-heroicons-x-mark"
						size="32"
						@click="emit('update:modelValue', false)"
					/>
				</div>
			</slot>
			<slot />
		</div>
		<div
			class="backdrop fixed inset-0 bg-black/50 z-[99]"
			@click="emit('update:modelValue', false)"
		/>
	</Teleport>
</template>

<script setup lang="ts">
import { onClickOutside } from "@vueuse/core";

interface Props {
	modelValue?: boolean;
	title?: string;
	position?: "left" | "right" | "top" | "bottom" | "center";
}

const props = withDefaults(defineProps<Props>(), {
	modelValue: false,
	title: "",
	position: "center",
});

const modal = ref<HTMLElement | null>(null);
const isMobile = useIsMobile();
const emit = defineEmits(["update:modelValue"]);
const isOpen = toRef(props, "modelValue");

onClickOutside(modal, () => {
	if (props.modelValue) {
		emit("update:modelValue", false);
	}
});

useScrollLock(isOpen);

const computedClass = computed(() => {
	return {
		...getPosClasses(props.position),
	};
});

const getPosClasses = (position: string) => {
	if (isMobile) {
		return {
			"bottom-0 p-8 rounded-t-3xl": true,
		};
	}
	switch (position) {
		case "left":
			return {
				"left-0": true,
			};
		case "right":
			return {
				"right-0": true,
			};
		case "top":
			return {
				"top-0": true,
			};
		case "bottom":
			return {
				"bottom-0": true,
			};
		case "center":
			return {
				"top-1/2": true,
			};
	}
};
</script>

<style scoped>
.modal {
	transition: transform 0.15s ease-in-out, opacity 0.15s linear;
	@starting-style {
		transform: translateY(100%);
		opacity: 0;
	}
}
</style>
