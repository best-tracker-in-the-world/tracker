import DefaultLayout from "@/layouts/default/layout";

export default function Dashboard() {
	const componentsList = [
		{ id: 1, type: "WeightTile", title: "Weight", span: 1 },
		// { id: 2, type: "GoalTile", title: "Weight", span: 1 },
		// { id: 3, type: "FoodTile", title: "Weight", span: 2 },
	];

	return (
		<DefaultLayout>
			<div className="dashboard"></div>
		</DefaultLayout>
	);
}
