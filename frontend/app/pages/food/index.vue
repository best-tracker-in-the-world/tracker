<template>
	<div>
		{{ foodItems }}
	</div>
</template>

<script setup lang="ts">
import { useFoodStore } from "@/stores/food";
import { ref, onMounted } from "vue";

const foodStore = useFoodStore();

const foodItems = ref([]);
const favorites = ref([]);
const showLogDialog = ref(false);
const selectedFood = ref(null);
const logWeight = ref(100);

const newFood = ref({
	name: "",
	calories: 0,
	protein: 0,
	carbs: 0,
	fat: 0,
});

onMounted(async () => {
	await foodStore.loadFoodItems();
	foodItems.value = foodStore.foodItems;
	favorites.value = foodStore.favorites;
});

function addNewFood() {
	const foodItem = {
		id: Date.now(),
		...newFood.value,
	};
	foodStore.addFoodItem(foodItem);
	newFood.value = {
		name: "",
		calories: 0,
		protein: 0,
		carbs: 0,
		fat: 0,
	};
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
</script>
