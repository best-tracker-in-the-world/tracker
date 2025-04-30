"use client";

import { usePathname } from "next/navigation";
import useLogout from "@/hooks/useLogout";
import Link from "next/link";

type Props = {
	items: Item[];
};

type Item = {
	path: string;
	name: string;
	icon: string;
	function?: () => void;
};

// Aside menu itself
const Aside: React.FC<Props> = ({ items }) => {
	const pathname = usePathname();
	const isActive = (path: string) => pathname === path;

	return (
		<aside className="p-4 shadow-md bg-gray-800 color-white w-fit h-screen">
			<nav className="h-full">
				<ul className="flex flex-col gap-2 h-full">
					{items.map((item) => (
						<AsideItem
							key={item.name}
							item={item}
							isActive={isActive}
						/>
					))}
				</ul>
			</nav>
		</aside>
	);
};

// Aside menu item
const AsideItem: React.FC<{
	item: Item;
	isActive: (path: string) => boolean;
}> = ({ item, isActive }) => {
	const handleLogout = useLogout();

	return (
		<li className="last:mt-auto">
			<Link
				onClick={(e) => {
					if (item.name === "Logout") {
						e.preventDefault();
						handleLogout();
					}
				}}
				href={item.path}
				className={`${isActive(item.path) && "bg-gray-700"}
							${item.name === "Logout" && "text-red-700!"} 
							flex items-center gap-2 p-2 rounded-md hover:bg-gray-700 
							transition-colors duration-200 text-white md:text-xs`}
			>
				<i
					className={`pi pi-${item.icon}`}
					style={{ fontSize: "1rem" }}
				/>
				{item.name}
			</Link>
		</li>
	);
};

export default Aside;
