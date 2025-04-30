"use client";

import Header from "@/components/widgets/layouts/Header";
import Aside from "@/components/widgets/layouts/Aside";
import MobileMenu from "@/components/widgets/layouts/MobileMenu";
import { useIsMobile } from "@/hooks/isMobile";

export default function DefaultLayout({
	children,
}: {
	children: React.ReactNode;
}) {
	const isMobile = useIsMobile();
	// TODO: USE ROUTER?
	const asideRouter = [
		{ name: "Dashboard", path: "/dashboard", icon: "home" },
		{ name: "Settings", path: "/settings", icon: "cog" },
		{ name: "Logout", path: "", icon: "power-off" },
	];

	return (
		<main className="flex">
			{!isMobile && (
				<Aside
					items={asideRouter.map((item) => ({
						name: item.name,
						path: item.path,
						icon: item.icon,
					}))}
				/>
			)}

			{isMobile && (
				<MobileMenu
					items={asideRouter.map((item) => ({
						name: item.name,
						path: item.path,
						icon: item.icon,
					}))}
				/>
			)}

			<div className="w-full">
				<Header />
				<div>{children}</div>
			</div>
		</main>
	);
}
