export function useQueueWorker() {
	const cock = 45;

	const { isOnline } = useIsOnline();

	onMounted(() => {
		console.log("mounted");
		console.log("is online? ", isOnline.value);
	});

	return {
		cock,
	};
}
