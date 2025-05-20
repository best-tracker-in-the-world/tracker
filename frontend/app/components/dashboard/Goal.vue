<template>
	<DashboardTileWrapper
		ref="progressWrap"
		:title="wrapperProps.title"
		:color="wrapperProps.color"
		:span="span"
		:icon="wrapperProps.icon"
	>
		<USkeleton
			v-if="!isLoaded"
			class="h-[75%] w-[75%] mx-auto rounded-xl bg-gray-900/10"
		/>
		<div v-else class="">
			<RadialProgress
				class="rotate-180 mx-auto"
				:diameter="wrapperWidth / 1.5"
				:animate-speed="300"
				:completed-steps="isOverflowing ? max : current"
				:total-steps="max"
				:stroke-width="!isMobile ? 18 : 12"
				:inner-stroke-width="!isMobile ? 24 : 16"
				:inner-stroke-color="'#e0e0e0'"
				:start-color="
					isOverflowing ? 'orange' : 'oklch(69.6% 0.17 162.48)'
				"
				:stop-color="
					isOverflowing ? 'orange' : 'oklch(69.6% 0.17 162.48)'
				"
			>
				<span class="rotate-180 text-[2.5vw]">
					<span>{{ current }} / {{ max }}</span>
				</span>
			</RadialProgress>
		</div>
	</DashboardTileWrapper>
</template>

<script setup lang="ts">
import RadialProgress from "vue3-radial-progress";
const { t } = useI18n();
const { isMobile } = useIsMobile();
const progressWrap = ref<HTMLElement | null>(null);

interface Props {
	current?: number | undefined;
	max?: number | undefined;
	isLoaded?: boolean;
	span?: number;
}

// check if current is greater than max
const isOverflowing = computed(() => {
	return (props.current ?? 0) > (props.max ?? 2000);
});
const wrapperWidth = ref(0);
const updateWidth = () => {
	if (progressWrap.value?.$el) {
		wrapperWidth.value =
			progressWrap.value.$el.getBoundingClientRect().width;
	}
};

const props = withDefaults(defineProps<Props>(), {
	current: 0,
	max: 2000,
	isLoaded: false,
	span: 1,
});

const wrapperProps = {
	title: t("dashboard.goal.title"),
	color: "gray",
	icon: "i-heroicons-chart-pie",
};

onMounted(() => {
	updateWidth();
	window.addEventListener("resize", updateWidth);
});

onBeforeUnmount(() => {
	window.removeEventListener("resize", updateWidth);
});
</script>
