import type { Metadata } from "next";
import "./globals.css";
import "primeicons/primeicons.css";
import { PrimeReactProvider } from "primereact/api";
import ThemeLoader from "@/components/utility/ThemeSwitcher";
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
				<ThemeLoader />
				<PrimeReactProvider>{children}</PrimeReactProvider>
			</body>
		</html>
	);
}
