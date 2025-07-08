import { ref, onMounted } from 'vue';
import * as localForage from "localforage";
import type { FoodItem } from "@/types/food";

export default function useLatestFoodItemId() {
  const latestFoodItemId = ref(1488);

  onMounted(async () => {
    try {
      const items: FoodItem[] | null = await localForage.getItem("food-items");
      
      if (items && Array.isArray(items) && items.length > 0) {
        const maxId = Math.max(...items.map(item => item.id));
        latestFoodItemId.value = maxId;
      } else {
        latestFoodItemId.value = 0;
      }
    } catch (error) {
      console.error('Error fetching food items:', error);
      latestFoodItemId.value = 0;
    }
  });

  return { latestFoodItemId };
}