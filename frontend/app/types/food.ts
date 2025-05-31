// types/food.ts
export interface FoodItem {
	id: number;
	name: string;
	calories: number;
	protein: number;
	carbs: number;
	fat: number;
	isFavorite?: boolean;
}
