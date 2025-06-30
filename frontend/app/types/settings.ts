export interface UserSettings {
	name: string | null;
	email: string | null;
	password: string | null;

	theme: string | null;
	language: "ru" | "en";

	currentGoal: number | undefined;
	weight: number | null;
	height: number | null;
	age: number | null;
	gender: "male" | "female" | null;

	savedAt: string;
	updatedAt: string;
}
