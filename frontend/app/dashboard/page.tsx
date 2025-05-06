'use client';

import DefaultLayout from "@/layouts/default/layout";
import { useState } from "react";
// import { Calendar } from 'primereact/calendar';


import Weight from "@/components/widgets/dashboard/Weight";
import Goal from "@/components/widgets/dashboard/Goal";
import Foods from "@/components/widgets/dashboard/Foods";

const mockData = [
	{
		date: "2025-05-01",
		weight: 90,
		foods: [
			{
				name: "chicken",
				caloricValue: 100,
				quantity: 200,
			},
			{
				name: "chicken",
				caloricValue: 100,
				quantity: 200,
			},
			{
				name: "chicken",
				caloricValue: 100,
				quantity: 200,
			},
			{
				name: "chicken",
				caloricValue: 100,
				quantity: 200,
			},
		]
	}
]

export default function Dashboard() {


	const [selectedDate, setSelectedDate] = useState('2025-05-01');

	const [todaysData, setTodaysData] = useState(mockData[0]);


	return (
		<DefaultLayout>
			<div className="dashboard">
				<Weight />
				<Goal />
				<Foods items={mockData[0].foods} />
			</div>
		</DefaultLayout>
	);
}
