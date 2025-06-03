<!-- pages/food.vue -->
<template>
	<ClientOnly>
		<div
			class="p-4 outline-1 flex-auto flex flex-col bg-red-500 flex-auto"
		>
			<!-- Food list -->

			<div v-if="!foodItems.length" class="grid items-center">
				<p class="text-center">
					{{ $t("Your food list is empty") }} <br >
					{{ $t("Start by adding some food") }}
				</p>
			</div>
			<div v-else class="">
				{{ foodItems }}
			</div>

			<!-- Log dialog -->
			<div
				v-if="showLogDialog"
				class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center"
			>
				<div class="bg-white p-6 rounded-lg max-w-md w-full">
					<h3 class="text-lg font-semibold mb-4">
						Log {{ selectedFood?.name }}
					</h3>
					<div class="mb-4">
						<label class="block text-sm font-medium mb-1"
							>Weight (grams)</label
						>
						<input
							v-model="logWeight"
							type="number"
							class="w-full p-2 border rounded"
							min="1"
							required
						>
					</div>
					<div class="flex justify-end space-x-3">
						<button
							class="px-4 py-2 border rounded hover:bg-gray-100"
							@click="showLogDialog = false"
						>
							Cancel
						</button>
						<button
							class="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
							@click="logFood"
						>
							Log Food
						</button>
					</div>
				</div>
			</div>

			<!-- add new food modal -->
			<UiModal v-model="isFoodFormModalOpen">
				<DashboardFoodListForm @submit="addNewFood" />
			</UiModal>

			<UButton @click="isFoodFormModalOpen = true"
				>Add New Food</UButton
			>
		</div>
	</ClientOnly>
</template>

<script setup lang="ts">
import { useFoodStore } from "@/stores/food";
import { ref, onMounted } from "vue";
import type { FoodItem } from "@/types/food";

const foodStore = useFoodStore();

const foodItems = ref([]);
const favorites = ref([]);
const showLogDialog = ref(false);
const selectedFood = ref(null);
const logWeight = ref(100);

const isFoodFormModalOpen = ref(false);

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
