"use client";

import { useIsMobile } from "@/hooks/isMobile";
import { usePathname } from "next/navigation";
import Link from "next/link";

type Props = {
	items: Item[];
};

type Item = {
	path: string;
	name: string;
	icon: string;
};

const Aside: React.FC<Props> = ({ items }) => {
	const isMobile = useIsMobile();
	const pathname = usePathname();
	const isActive = (path: string) => {
		return pathname === path;
	};
	return (
		!isMobile && (
			<aside className=" p-4 shadow-md bg-gray-800 color-white w-fit h-screen">
				<nav className="h-full">
					<ul className="flex flex-col gap-2 outline-1 h-full">
						{items.map((item) => (
							<li key={item.name}>
								<Link
									href={item.path}
									className={` ${
										isActive(item.path) &&
										"bg-gray-700"
									} flex items-center gap-2 p-2 px-4 rounded-md hover:bg-gray-700 transition-colors duration-200 text-white`}
								>
									<i
										className={`pi pi-${item.icon}`}
										style={{
											fontSize: "1.25rem",
										}}
									/>

									{item.name}
								</Link>
							</li>
						))}
					</ul>
				</nav>
			</aside>
		)
	);
};

export default Aside;
