"use client";

import { useState } from "react";
import useLogout from "@/hooks/useLogout";
import Link from "next/link";

type Props = {
	items: Item[];
	isActive?: (path: string) => boolean;
	onClick?: () => void;
};

type Item = {
	path: string;
	name: string;
	icon: string;
	function?: () => void;
};

// Mobile menu main component
const MobileMenu: React.FC<Props> = ({ items }) => {
	console.log("Aside items", items);

	const [isActive, setIsActive] = useState(false);

	return (
		<div className="fixed bottom-2 right-2">
			<div className="bg-gray-800 rounded-full p-2 shadow-lg w-12 h-12 grid 
							items-center justify-center">
				<i
					onClick={() => setIsActive(!isActive)}

					className={`pi pi-${isActive ? "times" : "bars"
						} text-2xl text-white`}
					style={{ fontSize: "1.5rem" }}
				/>
			</div>
			<div className={`${isActive ? '-translate-y-4 opacity-100' : 'translate-y-0 opacity-0'}
							absolute bottom-[100%] right-0 w-fit h-fit 
							gap-2 flex flex-col transition-all duration-300 ease-in-out
							items-end`}>
				{items.map((item) => (
					<MobileMenuItem
						onClick={() => setIsActive(!isActive)}
						key={item.name}
						item={item}
						isActive={() => false}

					/>
				))}
			</div>
		</div>
	);
};

// Mobile menu item component
const MobileMenuItem: React.FC<{
	item: Item;
	isActive: (path: string) => boolean;
	onClick: () => void;
}> = ({ item, onClick }) => {
	const handleLogout = useLogout();

	return (
		<Link
			onClick={(e) => {
				onClick();
				if (item.name === "Logout") {
					e.preventDefault();
					handleLogout();
				}
			}}
			href={item.path}
			className="text-white bg-gray-800 w-fit flex justify-end items-center gap-2 py-2 px-3 rounded-md"
		>
			{item.name}
			<i
				className={`pi pi-${item.icon}`}
				style={{ fontSize: "1rem" }}
			/>
		</Link>
	);
};

export default MobileMenu;
