"use client";

import { useEffect, useState } from "react";

export function useIsMobile() {
	const [isMobile, setIsMobile] = useState(false);

	useEffect(() => {
		const checkViewport = () => {
			setIsMobile(window.innerWidth < 768);
		};
		// run once
		checkViewport();
		window.addEventListener("resize", checkViewport);
		return () => {
			window.removeEventListener("resize", checkViewport);
		};
	}, []);

	return isMobile;
}
