<template>
	<DashboardTileWrapper
		ref="wrapper"
		:title="wrapperProps.title"
		:color="wrapperProps.color"
		:span="span"
		:icon="wrapperProps.icon"
		:is-selected="isEditing"
		class="p-0! *:first:px-4 *:first:pt-4"
		@dblclick="isEditing = true"
	>
		<!-- controls -->

		<template #header>
			<div v-if="isEditing">
				<UButton
					class="pointer-events-auto"
					icon="i-heroicons-check"
					@click="
						console.log('click');
						isEditing = false;
					"
				/>
			</div>
		</template>
		<div v-auto-animate>
			<!-- list of food -->
			<ul
				v-if="isLoaded"
				v-auto-animate
				class="py-3 pointer-events-auto"
			>
				<li
					v-for="(item, index) in items"
					:key="'food-' + index"
					v-auto-animate
					class="py-1"
				>
					<!-- default view -->
					<div
						v-if="!isEditing"
						class="flex justify-between text-gray-600 px-4 gap-2"
					>
						<div class="overflow-clip max-w-[50vw]">
							<span class="truncate">{{ item.name }}</span>
						</div>
						<span>
							<span class="font-bold text-gray-800 mr-1"
								>{{
									item.caloricContent *
									(item.weight / 100)
								}}
							</span>
							<span class="text-gray-300 font-bold"
								>{{ t("dimensions.kcal") }}.</span
							></span
						>
					</div>
					<!-- edit view -->
					<div
						v-else
						class="flex gap-2 p-4 w-full max-w-full justify-between bg-linear-to-t from-slate-100/50 to-white rounded-md overflow-clip"
					>
						<div class="flex flex-col w-full">
							<!-- top -->
							<div class="edit-item-top">
								<div class="max-w-full truncate mr-4">
									<p
										class="truncate text-xl font-semibold text-gray-700"
									>
										{{ item.name }}
									</p>
								</div>
								<div
									class="whitespace-nowrap max-w-fit"
								>
									{{ item.weight }}
									<span class="opacity-50">{{
										t("dimensions.g")
									}}</span>
								</div>
								<div
									class="whitespace-nowrap max-w-fit"
								>
									{{ item.caloricContent }}
									<span class="opacity-50">{{
										t("dimensions.kcal")
									}}</span>
								</div>
							</div>
							<!-- bottom -->
							<div class="edit-item-bottom">
								<span>
									<span
										>{{ t("foodList.protein") }} :
									</span>
									<span>{{ item.protein }}</span>
								</span>
								<span>
									<span
										>{{ t("foodList.carbs") }} :
									</span>
									<span>{{ item.protein }}</span>
								</span>
								<span>
									<span
										>{{ t("foodList.fat") }} :
									</span>
									<span>{{ item.protein }}</span>
								</span>
							</div>
						</div>
						<!-- edit buttons -->
						<div class="grid grid-rows-2 gap-2">
							<UButton
								icon="i-heroicons-x-mark"
								class="bg-gray-800"
							/>
							<UButton icon="i-heroicons-plus" />
						</div>
					</div>
				</li>
			</ul>
			<!-- skeleton till loaded -->
			<ul v-else class="flex flex-col gap-2 p-4">
				<li v-for="i in 4" :key="i">
					<div class="food-skele-grid">
						<USkeleton class="h-6 w-full" />
						<USkeleton class="h-6 w-full" />
					</div>
				</li>
			</ul>
			<!-- loaded but no data -->
			<div
				v-if="!items?.length && isLoaded"
				class="w-full h-32 p-4 flex flex-col items-center gap-2"
			>
				<p class="opacity-50">
					{{ $t("dashboard.foodList.empty") }}
				</p>
				<UButton
					color="neutral"
					size="xl"
					:label="$t('dashboard.foodList.add')"
					@click="isModalOpen = true"
				/>
			</div>
		</div>

		<UiModal v-model="isModalOpen" :title="$t('dashboard.weight.add')">
			<div class="flex flex-col gap-4">
				<DashboardFoodListForm @submit="handleFoodSubmit" />
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
	items?: Array<{
		name: string;
		caloricContent: number;
		weight: number;
		protein: number | null;
		carbs: number | null;
		fat: number | null;
	}>;
}

const wrapper = ref<HTMLElement | null>(null);
onClickOutside(wrapper, () => {
	if (isEditing.value) {
		isEditing.value = false;
	}
});
// const isFullView = ref(false);
const isEditing = ref(false);

const props = withDefaults(defineProps<Props>(), {
	isLoaded: false,
	span: 2,
	items: () => [
		{
			name: "Food Name",
			caloricContent: 0,
			weight: 0,
			protein: null,
			carbs: null,
			fat: null,
		},
	],
});

const wrapperProps = {
	title: t("dashboard.foodList.title"),
	color: "gray",
	icon: "i-heroicons-bars-3-center-left",
};

// submitting

const isModalOpen = ref(true);
const $emit = defineEmits(["food-submit"]);

function handleFoodSubmit(data: dashboardItem["foodLogs"][0]) {
	$emit("food-submit", data);
}
</script>

<style scoped>
.edit-item-top {
	display: grid;
	grid-template-columns: 4fr 1fr 1.5fr;
	gap: 8px;
}
.edit-item-bottom {
	display: grid;
	grid-template-columns: 1fr 1fr 1fr;
	gap: 8px;
	width: 50%;
	white-space: nowrap;
	font-size: 12px;
	margin-top: 8px;
	opacity: 0.5;
}

.food-skele-grid {
	display: grid;
	grid-template-columns: 5fr 1fr;
	gap: 8px;
}
</style>
