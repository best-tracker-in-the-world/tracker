export interface dashboardItem {
	date: string;
	weight: number;
	caloricGoal: number;
	foodLogs: {
		id: number;
		name: string;
		caloricContent: number;
		weight: number;
		protein: number;
		carbs: number;
		fat: number;
	}[];
}

export type dashboardData = dashboardItem[];
