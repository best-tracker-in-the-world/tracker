"use client";

import { useUserStore } from "@/stores/userStore";

export default function AuthLayout() {
	const { theme } = useUserStore();
	function handleClick() {
		const newTheme = theme === "dark" ? "light" : "dark";
		useUserStore.setState({ theme: newTheme });
	}

	return (
		<div
			onClick={handleClick}
			className={`cursor-pointer outline-2 p-3 pb-2 rounded-full 
				${theme === "dark" ? "outline-white" : "outline-black"}`}
		>
			<i
				className={`pi ${
					theme === "dark" ? "pi-sun" : "pi-moon"
				}  `}
				style={{ fontSize: "1.5rem" }}
			/>
		</div>
	);
}
