export interface userSettings {
	name: string | null;
	email: string | null;
	password: string | null;

	theme: 'light' | 'dark';
	language: 'ru' | 'en';

	currentGoal: number;
	weight: number | null;
	height: number | null;
	age: number | null;
	gender: 'male' | 'female' | null;
}
