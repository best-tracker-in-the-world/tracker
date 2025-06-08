<!-- pages/food.vue -->
<template>
	<ClientOnly>
		<div class="p-4 px-2 flex-auto flex flex-col flex-auto">
			<!-- Food list -->

			<div v-if="!foodItems.length" class="grid items-center">
				<p class="text-center">
					{{ $t("Your food list is empty") }} <br />
					{{ $t("Start by adding some food") }}
				</p>
			</div>

			<div v-else class="flex flex-col">
				<ul class="decoration-none flex flex-col gap-2">
					<li
						v-for="(item, index) in foodItems"
						:key="item.name + '-' + index"
					>
						<div
							class="flex justify-between gap-3 p-2 py-4 ring-1 ring-gray-200 pl-4 shadow-sm rounded-2xl items-center"
						>
							<p class="block w-1/2 truncate h-fit">
								{{ item.name.charAt(0).toUpperCase() + item.name.slice(1) }}
							</p>
							<span
								class="mr-auto h-fit text-xs opacity-50"
							>
								{{ item.caloricContent }}
								{{ $t("dimensions.kcal") }}
							</span>
							<UIcon
								name="i-heroicons-heart"
								:class="[ item.isFavorite ? 'fill-red-500' : 'opacity-25' ]"
								class="w-7 h-7"
							/>
							<UButton
								class="rounded-xl mr-2"
								icon="i-heroicons-plus"
								@click="openLogDialog(item)"
							/>
						</div>
					</li>
				</ul>
			</div>

			<UButton class="mt-auto grid items-center" size="xl" @click="isFoodFormModalOpen = true"
				>{{ $t("dashboard.foodList.add") }}</UButton
			>
		</div>

		<!-- add new food modal -->
		<UiModal v-model="isFoodFormModalOpen">
			<DashboardFoodListForm @submit="addNewFood" />
		</UiModal>
		<!-- add new food modal -->
		<UiModal
			v-model="showLogDialog"
			:title="$t('foodlist.addFoodAt') + ' ' + selectedDate"
		>
			<div class="bg-white rounded-lg w-full">
				<h3 class="text-2xl w-full text-center font-semibold mb-4">
					{{ selectedFood?.name }}
				</h3>
				<div class="mb-4">
					<UFormField
						:error="
							logWeight < 1 || logWeight > 5000
								? $t('foodList.error.weightWrong')
								: ''
						"
						:label="$t('dashboard.weight.title')"
					>
						<UInput
							class="w-full"
							v-model="logWeight"
							type="number"
							min="1"
							required
							size="xl"
						/>
					</UFormField>
				</div>
				<div class="flex justify-between *:w-1/2 space-x-3">
					<UButton
						color="neutral"
						class="grid items-center"
						size="xl"
						variant="outline"
						@click="showLogDialog = false"
					>
						Cancel
					</UButton>
					<UButton
						class="grid items-center"
						color="neutral"
						size="xl"
						@click="logFood"
					>
						Log Food
					</UButton>
				</div>
			</div>
		</UiModal>
	</ClientOnly>
</template>

<script setup lang="ts">
import { useFoodStore } from "@/stores/food";
import { ref, onMounted } from "vue";
import type { FoodItem } from "@/types/food";

const foodStore = useFoodStore();
const dashboardStore = useDashboardStore();
const foodItems = ref<FoodItem[]>([]);
const favorites = ref<FoodItem[]>([]);
const showLogDialog = ref(false);
const isFoodFormModalOpen = ref(false);
const selectedFood = ref(null);
const logWeight = ref(100);

const selectedDate = computed(() => {
	let str = dashboardStore.selectedDate.substring(8);
	str += "." + dashboardStore.selectedDate.substring(5, 7);
	return str;
});

onMounted(async () => {
	await foodStore.loadFoodItems();
	foodItems.value = foodStore.foodItems;
	favorites.value = foodStore.favorites;
});

function addNewFood(formData: FoodItem) {
	foodStore.addFoodItem(formData);
	isFoodFormModalOpen.value = false;
}

function toggleFavorite(food) {
	foodStore.toggleFavorite(food);
	food.isFavorite = !food.isFavorite;
}

function openLogDialog(food) {
	selectedFood.value = food;
	logWeight.value = 100;
	showLogDialog.value = true;
}

async function logFood() {
	if (selectedFood.value && logWeight.value > 0) {
		// console.log("food", selectedFood.value);
		// console.log("weight", logWeight.value);
		await foodStore.logFoodToCurrentDay(
			selectedFood.value,
			logWeight.value
		);
		showLogDialog.value = false;
	}
}

definePageMeta({
	layout: "app-returnable",
});
</script>
