import { ref, onMounted, onUnmounted } from "vue";

export default function useIsOnline() {
	const isOnline = ref(import.meta.browser ? navigator.onLine : true);

	async function verifyConnection() {
		if (!import.meta.browser) {
			isOnline.value = window.navigator.onLine;
			console.log("isOnline", isOnline.value);

			return;
		}
	}

	function handleOnline() {
		isOnline.value = true;
		verifyConnection();
		console.info("online event");
	}

	function handleOffline() {
		isOnline.value = false;
		verifyConnection();
		console.warn("offline event");
	}

	let intervalId: ReturnType<typeof setInterval>;

	onMounted(() => {
		window.addEventListener("online", handleOnline);
		window.addEventListener("offline", handleOffline);
		intervalId = setInterval(verifyConnection, 1000);
	});

	onUnmounted(() => {
		clearInterval(intervalId);
		window.removeEventListener("online", handleOnline);
		window.removeEventListener("offline", handleOffline);
	});

	return { isOnline };
}
