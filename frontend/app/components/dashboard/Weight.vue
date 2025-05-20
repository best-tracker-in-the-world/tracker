<template>
	<DashboardTileWrapper
		:title="wrapperProps.title"
		:color="wrapperProps.color"
		:span="span"
		:icon="wrapperProps.icon"
	>
		<USkeleton
			v-if="!isLoaded"
			class="h-[75%] w-[75%] mx-auto rounded-xl bg-gray-900/10"
		/>
		<span
			v-else
			class="text-[15vw] font-bold text-center flex flex-col justify-center h-full justify-end"
		>
			<!-- weight value -->
			<span :class="value ? 'h-fit my-auto' : 'opacity-10'">
				<span>
					{{ value ?? "XX" }}
				</span>
				<span class="text-[6vw]">{{ $t("dimensions.kg") }}</span>
			</span>
			<!-- add button -->
		</span>
		<UButton
			v-if="!value && isLoaded"
			:label="$t('dashboard.weight.add')"
			class="increased-click-area w-full rounded-xl text-center mt-auto block bg-violet-500 hover:bg-violet-600"
		/>
	</DashboardTileWrapper>
</template>

<script setup lang="ts">
const { t } = useI18n();

interface Props {
	isLoaded?: boolean;
	span?: number;
	value: number | null;
}

const props = withDefaults(defineProps<Props>(), {
	isLoaded: false,
	span: 1,
	value: null,
});

const wrapperProps = {
	title: t("dashboard.weight.title"),
	color: "gray",
	icon: "i-heroicons-scale",
};
</script>
