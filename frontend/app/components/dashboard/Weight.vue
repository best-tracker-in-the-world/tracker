<template>
	<DashboardTileWrapper
		:title="wrapperProps.title"
		:color="wrapperProps.color"
		:span="span"
		:icon="wrapperProps.icon"
	>
		<USkeleton
			v-if="!isLoaded"
			class="h-[75%] w-[75%] mx-auto rounded-xl bg-gray-900/10 dark:bg-gray-100/10"
		/>
		<span
			v-else
			class="text-[15vw] font-bold text-center flex flex-col justify-center h-full justify-end"
		>
			<!-- weight value -->
			<span class="dark:text-gray-100" :class="value ? 'h-fit my-auto' : 'opacity-10'">
				<span>
					{{ value ?? "??" }}
				</span>
				<span class="text-[6vw]">{{ $t("dimensions.kg") }}</span>
			</span>
			<!-- add button -->
		</span>
		<UButton
			v-if="!value && isLoaded"
			:label="$t('dashboard.weight.add')"
			size="xl"
			class="increased-click-area w-full rounded-xl text-center mt-auto block bg-gray-800 hover:bg-gray-900 dark:bg-gray-900 dark:text-gray-300"
			:class="value === 0 ? 'animate-pulse' : '' "
			@click="handleClick"
		/>

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
	console.log("cock");
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
</script>
