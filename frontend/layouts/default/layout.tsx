import Header from "@/components/widgets/layouts/Header";
import Aside from "@/components/widgets/layouts/Aside";

export default function DefaultLayout({
	children,
}: {
	children: React.ReactNode;
}) {
	// TODO: USE ROUTER?
	const asideRouter = [
		{ name: "Dashboard", path: "/dashboard", icon: "home" },
		{ name: "Settings", path: "/settings", icon: "cog" },
	];

	return (
		<main className="flex">
			{
				<Aside
					items={asideRouter.map((item) => ({
						name: item.name,
						path: item.path,
						icon: item.icon,
					}))}
				/>
			}

			<div className="w-full">
				<Header />
				<div>{children}</div>
			</div>
		</main>
	);
}
