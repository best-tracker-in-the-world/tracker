"use client";

import { useUserStore } from "@/stores/userStore";
import { useRouter } from "next/navigation";

export default function useLogout() {
	const router = useRouter();

	return () => {
		localStorage.removeItem("token");
		useUserStore.setState({ name: null, id: null, email: null });
		router.push("/login");
		console.log("Logout successful", useUserStore.getState());
	};
}
