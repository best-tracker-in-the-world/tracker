import type { Metadata } from "next";
import "./globals.css";
import { PrimeReactProvider } from "primereact/api";

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
			<body>
				<PrimeReactProvider>{children}</PrimeReactProvider>
			</body>
		</html>
	);
}
