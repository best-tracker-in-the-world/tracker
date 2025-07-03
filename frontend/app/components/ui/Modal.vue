<template>
	<Teleport to="body">
		<Transition :name="isMobile ? 'slide-up' : 'fade'">
			<div
				v-if="showModal"
				ref="modal"
				class="modal fixed z-1 w-screen bg-white dark:bg-gray-800"
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
							class="dark:text-gray-500"
							name="i-heroicons-x-mark"
							size="32"
							@click="closeModal"
						/>
					</div>
				</slot>
				<slot />
			</div>
			<!-- <div
				v-if="props.type !== 'confirm'"
				class="backdrop fixed inset-0 bg-black/50"
				@click="closeModal"
			/> -->
		</Transition>
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
	modelValue: true,
	title: "",
	position: "center",
	type: "default",
});

const modal = ref<HTMLElement | null>(null);
const isMobile = useIsMobile();
const emit = defineEmits(["update:modelValue"]);
const showModal = ref(false);

watch(
	() => props.modelValue,
	(val) => {
		showModal.value = val;
		console.log("showModal", showModal.value);
	}
);

const closeModal = () => {
	emit("update:modelValue", false);
};

onClickOutside(modal, () => {
	if (props.modelValue && props.type !== "confirm") {
		closeModal();
	}
});

useScrollLock(
	props.type !== "confirm" ? computed(() => props.modelValue) : false
);

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
.slide-up-enter-active,
.slide-up-leave-active {
	transition: opacity 0.5s ease;
}

.slide-up-enter-from,
.slide-up-leave-to {
	opacity: 0;
}
</style>
