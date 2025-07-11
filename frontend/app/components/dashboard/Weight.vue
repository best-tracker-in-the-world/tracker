<template>
	<DashboardTileWrapper
		:title="wrapperProps.title"
		:color="wrapperProps.color"
		:span="span"
		:icon="wrapperProps.icon"
		class="wrapper h-full max-w-full"
		:class="{ pressing: isPressed, completed: isCompleted }"
		v-bind="handlers"
		@dblclick="handleClick"
	>
		<template #header>
			<UIcon
				v-if="isLoaded"
				name="i-heroicons-plus"
				class="h-5 w-5 grid items center translate-x-1 dark:text-gray-400"
				@click="handleClick"
			/>
		</template>
		<USkeleton
			v-if="!isLoaded"
			class="h-[75%] w-[75%] mx-auto rounded-xl bg-gray-900/10 dark:bg-gray-100/10"
		/>
		<span
			v-else
			class="text-[15vw] md:text-[70px] font-bold text-center flex flex-col justify-center h-full justify-end"
		>
			<!-- weight value -->
			<span
				class="dark:text-gray-100 my-auto"
				:class="value ? 'h-fit my-auto' : 'opacity-10'"
			>
				<span>
					<!-- ?? -->
					{{ value ?? "??" }}
				</span>
				<span class="text-[6vw] md:text-[16px]">{{
					$t("dimensions.kg")
				}}</span>
			</span>

		</span>

		<UiModal v-model="isModalOpen" :title="$t('dashboard.weight.add')">
			<div class="flex flex-col gap-4">
				<UInput
					ref="inputRef"
					v-model="formData.weight"
					size="xl"
					type="number"
					color="neutral"
					:step="0.1"
				/>
				<UButton
					size="xl"
					class="dark:bg-gray-900 dark:text-gray-300 grid items-center"
					color="neutral"
					:label="$t('dashboard.weight.add')"
					@click="
						$emit('weightSubmit', formData.weight);
						isModalOpen = false;
					"
				/>
			</div>
		</UiModal>
	</DashboardTileWrapper>
</template>

<script setup lang="ts">
import type { dashboardItem } from "@/types/dashboard";
const { t } = useI18n();

interface Props {
	isLoaded?: boolean;
	span?: number;
	value?: number | null;
}

withDefaults(defineProps<Props>(), {
	isLoaded: false,
	span: 1,
	value: null,
});

const formData = reactive({
	weight: 90 as dashboardItem["weight"],
});

const isModalOpen = ref(false);

const inputRef = ref<ComponentPublicInstance | null>(null);

const handleClick = () => {
	isModalOpen.value = true;
	nextTick(() => {
		if (!inputRef.value) return;
		inputRef.value.$el.querySelector("input").focus();
	});
};

const wrapperProps = {
	title: t("dashboard.weight.title"),
	color: "gray",
	icon: "i-heroicons-scale",
};

const { handlers, isPressed, isCompleted } = useLongPress(() => {
	isModalOpen.value = true;
});
</script>

<style scoped>
.wrapper {
	transition: all 0.2s ease-in-out;
	overflow: clip;
	position: relative;
	cursor: pointer;
}

.wrapper.pressing {
	scale: 0.95;
}

.wrapper::after {
	content: "";
	position: absolute;
	top: 0;
	left: 0;
	height: 100%;
	width: 300%;
	background-color: rgba(0, 0, 0, 0.05);
	rotate: 0deg;
	transition: transform 0.7s linear;
	transform: translateX(-43%) translateY(100%);
	filter: blur(25px);
	rotate: 45deg;
	transform-origin: 0 center;
	z-index: 1;
}

.wrapper.pressing::after {
	transform: translateX(-43%);
}
.wrapper.completed::after {
	transform: translateX(-43%);
}
</style>
