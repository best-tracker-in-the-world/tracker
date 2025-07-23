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
			<UIcon
				v-if="isLoaded"
				name="i-heroicons-plus"
				class="h-5 w-5 outline grid items center translate-x-1 dark:text-gray-400 cursor-pointer"
				variant="ghost"
				@click="isModalOpen = true"
			/>
		</template>

		<div v-auto-animate class="h-full flex justify-center w-full">
			<!-- list of food -->
			<ul
				v-if="isLoaded"
				v-auto-animate
				class="py-4 pointer-events-auto w-full flex flex-col gap-2"
			>
				<li
					v-for="(item, index) in items"
					:key="'food-' + index"
					v-auto-animate
					class="py-2 rounded-md dark:bg-gray-900/50 mx-4"
				>
					<!-- default view -->
					<div
						class="food-item-content | items-center gap-2 max-w-full"
					>
						<div
							class="overflow-clip max-w-[50vw] flex flex-col"
						>
							<span
								class="truncate font-semibold dark:text-gray-100 text-md"
								>{{ item.name }}</span
							>
							<span
								class="text-gray-400 dark:text-gray-600 text-xs"
								>{{ item.addedAt }}</span
							>
						</div>

						<span
							class="font-light text-[10px] ring-1 w-fit ml-auto h-fit ring-slate-300 dark:ring-gray-600 dark:text-gray-400 px-2 py-1 font-semibold rounded-md font-mono text-gray-800 dark:text-gray-100 mr-1"
							>{{ item.weight }}
							{{ t("dimensions.g") }}
						</span>
						<span
							class="font-light text-[10px] whitespace-nowrap w-fit h-fit ml-auto bg-gray-200 dark:bg-gray-700 px-2 py-1 font-semibold rounded-md font-mono text-gray-800 dark:text-gray-300 mr-1"
							>{{
								item.caloricContent *
								(item.weight / 100)
							}}
							{{ t("dimensions.kcal") }}
						</span>

						<UDropdownMenu
							:items="[
								[
									{
										label: 'Duplicate',
										icon: 'i-lucide-copy',
										onSelect: () =>
											addMoreOfItem(item),
									},
									{
										label: 'Delete',
										icon: 'i-lucide-trash',
										onSelect: () =>
											removeItem(index),
									},
								],
							]"
							:ui="{
								content: 'w-48',
							}"
						>
							<UButton
								icon="i-mdi-dots-horizontal"
								color="neutral"
								variant="ghost"
							/>
						</UDropdownMenu>
					</div>
					<!-- edit view -->
	
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
				class="h-32 p-4 flex flex-col items-center gap-2 mt-auto min-h-2/3 min-w-full"
			>
				<p class="opacity-50 dark:text-gray-400 text-center">
					{{ $t("dashboard.foodList.empty") }}
				</p>
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
import type { FoodItem } from "@/types/food";
import { useFoodStore } from "@/stores/food";
import { useSettingsStore } from "~/stores/settings";

const currentGoal = useSettingsStore().settings?.currentGoal;
const foodStore = useFoodStore();
const dashboardStore = useDashboardStore();
const { t } = useI18n();

// Get latest food ID
const { latestFoodItemId } = useLatestFoodItemId();

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
		addedAt: string;
	}>;
}

const wrapper = ref<HTMLElement | null>(null);
onClickOutside(wrapper, () => {
	if (isEditing.value) {
		isEditing.value = false;
	}
});

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
const isModalOpen = ref(false);
const $emit = defineEmits(["food-submit", "food-remove"]);

// Computed next ID that updates when latestFoodItemId changes
const nextFoodId = computed(() => latestFoodItemId.value + 1);

async function handleFoodSubmit(data: dashboardItem["foodLogs"][0]) {
	// Adding to dashboard
	const date = dashboardStore.selectedDate;
	let day = dashboardStore.getDay(date);
	if (!day) {
		day = {
			date,
			weight: 0,
			caloricGoal: currentGoal,
			foodLogs: [],
		};
	}
	day.foodLogs.push(data);
	await dashboardStore.saveDay(day);

	// Adding to food list with proper ID
	const foodItem: FoodItem = {
		id: nextFoodId.value,
		name: data.name,
		caloricContent: data.caloricContent,
		protein: data.protein,
		carbs: data.carbs,
		fat: data.fat,
		createdAt: new Date().toISOString(),
		updatedAt: new Date().toISOString(),
	};

	await foodStore.addFoodItem(foodItem);

	// close modal
	isModalOpen.value = false;
}

// Additional helper functions for edit mode
async function removeItem(index: number) {
	const date = dashboardStore.selectedDate;
	const day = dashboardStore.getDay(date);
	if (day && day.foodLogs) {
		day.foodLogs.splice(index, 1);
		await dashboardStore.saveDay(day);
	}
}

const editMenuItems = ref<DropdownMenuItem[][]>([
	[
		{
			label: "Dublicate",
			icon: "i-lucide-user",
			onselect: () => {
				addMoreOfItem(item);
			},
		},
		{
			label: "Delete",
			icon: "i-lucide-credit-card",
		},
	],
]);

async function addMoreOfItem(item: any) {
	const date = dashboardStore.selectedDate;
	let day = dashboardStore.getDay(date);
	if (!day) {
		day = {
			date,
			weight: 0,
			caloricGoal: currentGoal,
			foodLogs: [],
		};
	}

	// Add another serving of the same item
	day.foodLogs.push({ ...item });
	await dashboardStore.saveDay(day);
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

.food-item-content {
	display: grid;
	grid-template-columns: 5fr 60px 70px 30px;
}
</style>
