import { create } from "zustand";
import { persist } from "zustand/middleware";

interface User {
	id: number | null;
	name: string | null;
	email: string | null;
	theme: "light" | "dark";
	setTheme: (theme: "light" | "dark") => void;
}

export const useUserStore = create<User>()(
	persist(
		(set) => ({
			id: null,
			name: "",
			email: "",
			theme: "light",
			setTheme: (theme: "light" | "dark") => set({ theme }),
		}),
		{
			name: "user-store", // Key for localStorage
		}
	)
);
