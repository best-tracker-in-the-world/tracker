<template>
	<div v-if="isMounted">
		<!-- desktop -->
		<aside
			v-if="!isMobile"
			class="w-64 bg-gray-800 text-white h-screen flex flex-col"
		>
			<div class="flex items-center justify-center h-16 bg-gray-900">
				<UIcon :name="'i-heroicons-fire'" class="w-6 h-6" />
				<h1 class="text-xl font-bold">LOGO</h1>
			</div>
			<nav class="flex flex-col p-4 flex-1">
				<ul class="flex flex-col gap-2 flex-1 justify-start mb-6">
					<li
						v-for="link in sidebarLinks"
						:key="link.name"
						class="text-gray-300 *:hover:text-white last:*:text-red-500 last:mt-auto"
					>
						<ULink
							:href="link.href"
							class="flex items-center hover:bg-gray-900 p-2 rounded-md"
						>
							<UIcon :name="link.icon" class="w-6 h-6" />
							<span class="ml-2">{{ link.name }}</span>
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
			<nav
				v-if="isMenuOpen"
				ref="mobileMenu"
				class="absolute -top-5 -translate-y-full right-0 slide-in-right"
			>
				<ul class="flex flex-col gap-2 items-end">
					<li
						v-for="link in sidebarLinks"
						:key="link.name"
						class="text-gray-300 *:hover:text-white last:*:text-red-500 last:mt-auto"
					>
						<ULink
							:href="link.href"
							class="w-fit flex items-center gap-4 flex justify-end hover:bg-gray-900 p-2 rounded-md ring-1 shadow-md ring-gray-200"
						>
							<span
								class="ml-2 no-wrap whitespace-nowrap"
								>{{ link.name }}</span
							>
							<UIcon :name="link.icon" class="w-6 h-6" />
						</ULink>
					</li>
				</ul>
			</nav>
		</div>
	</div>
</template>
<script setup lang="ts">
const { isMobile } = useIsMobile();
const { t } = useI18n();

const isMenuOpen = ref(false);
const isMounted = ref(false);
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
		name: t("menu.logout"),
		href: "/logout",
		icon: "i-heroicons-arrow-right-start-on-rectangle",
		color: "text-red-500",
	},
];
</script>
