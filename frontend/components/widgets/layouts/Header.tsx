"use client";

import { useUserStore } from "@/stores/userStore";

export default function Header() {
	const { name, email } = useUserStore();
	return (
		<header className="flex p-4 shadow-md justify-start md:justify-end md:hidden">
			<div className="flex items-center gap-2 ">
				<div className="flex h-10 w-10  md:order-1 items-center justify-center rounded-full bg-blue-500 text-white ">
					<i
						className="pi pi-user"
						style={{ fontSize: "1.25rem" }}
					/>
				</div>
				<div className="flex flex-col">
					<span className="text-sm font-bold md:text-end">
						{name}
					</span>
					<span className="text-xs opacity-50">{email}</span>
				</div>
			</div>
		</header>
	);
}
