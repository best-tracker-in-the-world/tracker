export default {
	mounted(el: HTMLElement, binding: any) {
		const duration = Number(binding.arg) || 500;
		const handler = binding.value;

		let timer: ReturnType<typeof setTimeout> | null = null;
		let hasTriggered = false;

		const start = (e: Event) => {
			if (e.type === "touchstart") {
				// avoid canceling click unless you really need to
				e.preventDefault();
			}

			el.classList.add("selected");

			timer = setTimeout(() => {
				hasTriggered = true;
				handler?.(e);
			}, duration);
		};

		const cancel = (e: Event) => {
			if (timer) clearTimeout(timer);
			timer = null;
			el.classList.remove("selected");

			// Optional: if long press already triggered, cancel the click
			if (hasTriggered) {
				e.stopImmediatePropagation?.();
				hasTriggered = false;
			}
		};

		el.__tapHoldStart__ = start;
		el.__tapHoldCancel__ = cancel;

		el.addEventListener("touchstart", start, { passive: false });
		el.addEventListener("touchend", cancel);
		el.addEventListener("touchmove", cancel);
		el.addEventListener("mousedown", start);
		el.addEventListener("mouseup", cancel);
		el.addEventListener("mouseleave", cancel);
	},

	unmounted(el: HTMLElement) {
		el.removeEventListener("touchstart", el.__tapHoldStart__);
		el.removeEventListener("touchend", el.__tapHoldCancel__);
		el.removeEventListener("touchmove", el.__tapHoldCancel__);
		el.removeEventListener("mousedown", el.__tapHoldStart__);
		el.removeEventListener("mouseup", el.__tapHoldCancel__);
		el.removeEventListener("mouseleave", el.__tapHoldCancel__);
	},
};
