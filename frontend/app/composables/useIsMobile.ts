import { ref, onMounted, onUnmounted } from "vue";

export const useIsMobile = () => {
	const isMobile = ref(false);

	const update = () => {
		isMobile.value = window.innerWidth < 756;
	};

	onMounted(() => {
		update();
		window.addEventListener("resize", update);
	});

	onUnmounted(() => {
		window.removeEventListener("resize", update);
	});

	return { isMobile };
};
