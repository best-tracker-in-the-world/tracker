import { watch, onUnmounted, type Ref } from "vue";

export function useScrollLock(active: Ref<boolean>) {
	const lockScroll = () => {
		const scrollBarWidth =
			window.innerWidth - document.documentElement.clientWidth;
		document.body.style.overflow = "hidden";
		document.body.style.paddingRight = `${scrollBarWidth}px`;
	};

	const unlockScroll = () => {
		document.body.style.overflow = "";
		document.body.style.paddingRight = "";
	};

	const stop = watch(
		active,
		(val) => {
			return val ? lockScroll() : unlockScroll();
		},
		{ immediate: true }
	);

	onUnmounted(() => {
		unlockScroll();
		stop();
	});
}
