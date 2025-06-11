<template>
	<Teleport v-if="showModal" to="body">
		<div
			ref="modal"
			class="modal fixed z-1 w-screen bg-white dark:bg-gray-800"
			:class="computedClass"
			:style="{ transform: isClosing ? 'translateY(100%)' : 0 }"
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
						class="dark:text-gray-500"
						name="i-heroicons-x-mark"
						size="32"
						@click="closeModal"
					/>
				</div>
			</slot>
			<slot />
		</div>
		<div
			v-if="props.type !== 'confirm'"
			class="backdrop fixed inset-0 bg-black/50"
			:style="{ opacity: isClosing ? 0 : 0.5 }"
			@click="closeModal"
		/>
	</Teleport>
</template>

<script setup lang="ts">
import { onClickOutside } from "@vueuse/core";

interface Props {
	modelValue?: boolean;
	title?: string;
	position?: "left" | "right" | "top" | "bottom" | "center";
	type?: "default" | "confirm";
}

const props = withDefaults(defineProps<Props>(), {
	modelValue: false,
	title: "",
	position: "center",
	type: "default",
});

const modal = ref<HTMLElement | null>(null);
const isMobile = useIsMobile();
const emit = defineEmits(["update:modelValue"]);

// Proxy values for smooth transition
const showModal = ref(false);
const isClosing = ref(false);
let closeTimeout: ReturnType<typeof setTimeout> | null = null;

watch(
	() => props.modelValue,
	(newVal) => {
		if (newVal) {
			isClosing.value = false;
			showModal.value = true;
		} else {
			// smooth closing
			isClosing.value = true;
			if (closeTimeout) clearTimeout(closeTimeout);
			closeTimeout = setTimeout(() => {
				showModal.value = false;
				isClosing.value = false;
				closeTimeout = null;
			}, 300);
		}
	},
	{ immediate: true }
);

const closeModal = () => {
	emit("update:modelValue", false);
};

onClickOutside(modal, () => {
	if (props.modelValue && props.type !== "confirm") {
		closeModal();
	}
});

useScrollLock(props.type !== "confirm" ? computed(() => props.modelValue) : false);

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
	transition: transform 0.3s ease-in-out, opacity 0.3s linear;
	@starting-style {
		transform: translateY(100%);
		opacity: 0;
	}
}

.backdrop {
	transition: opacity 0.3s linear;
}
</style>

