"use client";

import { useEffect } from "react";
import { useUserStore } from "@/stores/userStore";

export default function ThemeLoader() {
	const { theme } = useUserStore();

	useEffect(() => {
		const linkId = "dynamic-theme-css";

		const existing = document.getElementById(linkId);
		if (existing) existing.remove();

		const link = document.createElement("link");
		link.id = linkId;
		link.rel = "stylesheet";
		link.href = theme === "dark" ? "/css/dark.css" : "/css/light.css";

		document.head.appendChild(link);
	}, [theme]);

	return null;
}
