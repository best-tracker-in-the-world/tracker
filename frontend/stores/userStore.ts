import { create } from "zustand";

interface User {
	id: number | null;
	name: string;
	email: string;
	theme: "light" | "dark";
	setTheme: (theme: "light" | "dark") => void;
}

export const useUserStore = create<User>((set) => ({
	id: null,
	name: "",
	email: "",
	theme: "light",
	setTheme: (theme: "light" | "dark") => set({ theme }),
}));
