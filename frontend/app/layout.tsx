import type { Metadata } from "next";
import "./globals.css";

export const metadata: Metadata = {
	title: "Tracker app",
	description: "Track your weight and calorie intake",
};

export default function RootLayout({
	children,
}: Readonly<{
	children: React.ReactNode;
}>) {
	return (
		<html lang="en">
			<body>{children}</body>
		</html>
	);
}
