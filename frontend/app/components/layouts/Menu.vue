<template>
	<div v-if="isMounted">
		<!-- desktop -->
		<aside
			v-if="!isMobile"
			class="transition-all bg-gray-800 text-white flex flex-col sticky top-0 h-screen outline-1 outline-gray-800"
			:class="isDesktopMenuOpen ? 'w-64' : 'w-16'"
		>
			<div
				class="flex items-center h-16 mx-6 transition-flex"
				:class="
					isDesktopMenuOpen
						? 'justify-between'
						: 'justify-center'
				"
			>
				<Transition name="menu-items">
					<div
						v-if="isDesktopMenuOpen"
						class="menu-logo | flex items-center justify-left h-16"
					>
						<h1
							class="text-xl font-bold flex align-center gap-2"
						>
							<UIcon
								:name="'i-heroicons-fire'"
								class="w-6 h-6"
							/>LOGO
						</h1>
					</div>
				</Transition>
				<div
					class="transition-margin transition-500 h-fit p-1.5 pb-0 rounded-sm hover:bg-gray-900 cursor-pointer"
					:class="isDesktopMenuOpen ? 'ml-auto' : 'ml-0'"
				>
					<UIcon
						class="w-6 h-6 transition-all opacity-50 hover:opacity-100"
						:class="isDesktopMenuOpen ? '' : 'rotate-180'"
						:name="'i-heroicons-arrow-left-circle'"
						@click="isDesktopMenuOpen = !isDesktopMenuOpen"
					/>
				</div>
			</div>
			<USeparator color="primary" class="w-4/5 mx-auto opacity-25" />
			<nav class="flex flex-col p-4 flex-1">
				<ul class="flex flex-col gap-2 flex-1 justify-start">
					<li
						v-for="link in sidebarLinks"
						:key="link.name"
						class="text-gray-300 *:hover:text-white last:*:text-red-500 last:mt-auto"
					>
						<ULink
							:href="link.href"
							class="flex items-center hover:bg-gray-900 p-2 rounded-md"
							:class="
								isDesktopMenuOpen ? '': 'px-1'"
						>
							<UIcon :name="link.icon" class="min-w-6 min-h-6" />
							<Transition name="menu-items">
								<span
									v-if="isDesktopMenuOpen"
									class="menu-item | ml-2"
									>{{ link.name }}</span
								>
							</Transition>
						</ULink>
					</li>
				</ul>
			</nav>
		</aside>

		<!-- mobile -->
		<div v-if="isMobile" class="fixed bottom-4 right-4 z-10">
			<!-- btn -->
			<UButton
				class="rounded-full w-14 h-14 active:scale-90 transition-all"
			>
				<UIcon
					ref="mobileMenuButton"
					:name="
						isMenuOpen
							? 'i-heroicons-x-mark'
							: 'i-heroicons-bars-3'
					"
					class="size-10"
					@click.stop="handleMenuClick"
				/>
			</UButton>
			<!-- menu -->
			<Transition>
				<nav
					v-if="isMenuOpen"
					ref="mobileMenu"
					class="absolute -top-5 -translate-y-full right-0 slide-in-right"
				>
					<ul class="flex flex-col gap-2 items-end">
						<li
							v-for="link in sidebarLinks"
							:key="link.name"
							class="text-gray-300 *:hover:text-white last:*:text-red-500 last:mt-auto bg-white rounded-md"
						>
							<ULink
								:href="link.href"
								class="w-fit flex items-center gap-4 flex justify-end hover:bg-gray-900 dark:hover:bg-gray-800 dark:bg-gray-700 p-2 rounded-md ring-1 shadow-md ring-gray-200 dark:ring-gray-600"
							>
								<span
									class="ml-2 no-wrap whitespace-nowrap"
									>{{ link.name }}</span
								>
								<UIcon
									:name="link.icon"
									class="w-6 h-6"
								/>
							</ULink>
						</li>
					</ul>
				</nav>
			</Transition>
		</div>
	</div>
</template>
<script setup lang="ts">
const { isMobile } = useIsMobile();
const { t } = useI18n();

const { clear } = useDashboardStore();

const isMenuOpen = ref(false);
const isMounted = ref(false);
const isDesktopMenuOpen = ref(true);

onMounted(() => {
	isMounted.value = true;
});
function handleMenuClick() {
	isMenuOpen.value = !isMenuOpen.value;
}

const mobileMenu = ref<HTMLElement | null>(null);
const mobileMenuButton = ref<HTMLElement | null>(null);

onClickOutside(mobileMenu, () => {
	// close menu if clicked outside and not on menu btn
	if (isMenuOpen.value && mobileMenuButton.value.$el !== event.target) {
		isMenuOpen.value = false;
	}
});

const sidebarLinks = [
	{
		name: t("menu.dashboard"),
		href: "/dashboard",
		icon: "i-heroicons-home",
	},
	{
		name: t("menu.settings"),
		href: "/settings",
		icon: "i-heroicons-cog-6-tooth",
	},
	{
		name: t("menu.stats"),
		href: "/stats",
		icon: "i-heroicons-chart-bar",
	},
	{
		name: t("menu.food"),
		href: "/food",
		icon: "i-fluent-food-apple-24-regular",
	},
	{
		name: t("menu.logout"),
		href: "/logout",
		icon: "i-heroicons-arrow-right-start-on-rectangle",
		color: "text-red-500",
	},
];
</script>

<style scoped>
.v-enter-active,
.v-leave-active {
	transition: transform 0.25s ease-out, opacity 0.4s ease,
		rotate 0.4s ease-out;
	transform-origin: bottom right;
}

.v-enter-from,
.v-leave-to {
	opacity: 0;
	transform: translateX(30px) translateY(30px) rotate(90deg);
}

.menu-item,
.menu-logo {
	width: 120px;
	display: flex;
	overflow: clip;
	white-space: nowrap;
}

.menu-items-enter-active,
.menu-items-leave-active {
	transition: width 0.125s linear;
}

.menu-items-enter-from,
.menu-items-leave-to {
	width: 0px;
}
</style>
