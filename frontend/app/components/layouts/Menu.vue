<template>
	<div
		v-if="isMounted" 
		class="transition-all"
		:class="{
      '!mr-0': isMobile,
      'mr-64': isDesktopMenuOpen,
      'mr-16': !isDesktopMenuOpen,
    }"
		>
		<!-- desktop -->
		<aside
			v-if="!isMobile"
			class="group/sidebar flex flex-col h-screen fixed left-0 top-0 transition-all duration-300 ease-in-out bg-white dark:bg-gray-900 border-r border-gray-200 dark:border-gray-800 shadow-lg dark:shadow-gray-900/20"
			:class="isDesktopMenuOpen ? 'w-64' : 'w-16'"
		>
			<!-- Header Section -->
			<div
				class="flex items-center h-16 px-4 border-b border-gray-200 dark:border-gray-800"
			>
				<div class="flex items-center justify-between w-full">
					<!-- Logo -->
					<Transition
						name="fade-slide"
						enter-active-class="transition-all duration-300 ease-out"
						enter-from-class="opacity-0 translate-x-2"
						enter-to-class="opacity-100 translate-x-0"
						leave-active-class="transition-all duration-200 ease-in"
						leave-from-class="opacity-100 translate-x-0"
						leave-to-class="opacity-0 -translate-x-2"
					>
						<div
							v-if="isDesktopMenuOpen"
							class="flex items-center space-x-3"
						>
							<div
								class="flex items-center justify-center w-8 h-8 rounded-lg bg-gradient-to-br from-green-500 to-green-600"
							>
								<UIcon
									name="i-heroicons-scale"
									class="w-5 h-5 text-white"
								/>
							</div>
							<h1
								class="text-lg font-semibold text-gray-900 dark:text-white"
							>
								{{ $t('menu.title') }}
							</h1>
						</div>
					</Transition>

					<!-- Toggle Button -->
					<UButton
						variant="ghost"
						size="sm"
						square
						class="ml-auto shrink-0 hover:bg-gray-100 dark:hover:bg-gray-800"
						:class="{ 'ml-0': !isDesktopMenuOpen }"
						@click="isDesktopMenuOpen = !isDesktopMenuOpen"
					>
						<UIcon
							name="i-heroicons-chevron-left"
							class="w-4 h-4 transition-transform duration-300 text-gray-500 dark:text-gray-400"
							:class="{ 'rotate-180': !isDesktopMenuOpen }"
						/>
					</UButton>
				</div>
			</div>

			<!-- Navigation -->
			<nav class="flex-1 px-3 py-4 overflow-y-auto">
				<ul class="flex flex-col space-y-1 min-h-full">
					<li
						v-for="(link, index) in sidebarLinks"
						:key="link.name"
						:class="{ 'mt-auto': link.isBottomItem }"
					>
						<UTooltip
							:text="$t(link.name)"
							:disabled="isDesktopMenuOpen"
							:popper="{ placement: 'right' }"
						>
							<ULink
								:href="link.href"
								class="group/item relative flex items-center w-full px-2 py-1.5 text-sm font-medium rounded-lg transition-all duration-200 hover:bg-gray-100 dark:hover:bg-gray-800 text-gray-700 dark:text-gray-300 hover:text-gray-900 dark:hover:text-white hover:ring-1 hover:ring-gray-300 dark:hover:ring-gray-700"
								:class="{
									'justify-center':
										!isDesktopMenuOpen,
								}"
								active-class="bg-green-100 dark:bg-green-900 dark:text-green-400"
							>
								<!-- Icon -->
								<UIcon
									:name="link.icon"
									class="w-5 h-5 shrink-0 transition-colors duration-200"
									:class="[
										link.color ||
											'text-gray-500 dark:text-gray-400 group-hover/item:text-gray-700 dark:group-hover/item:text-gray-300',
									]"
								/>

								<!-- Label -->
								<Transition
									name="fade-slide"
									enter-active-class="transition-all duration-300 ease-out delay-75"
									enter-from-class="opacity-0 translate-x-2"
									enter-to-class="opacity-100 translate-x-0"
									leave-active-class="transition-all duration-200 ease-in"
									leave-from-class="opacity-100 translate-x-0"
									leave-to-class="opacity-0 -translate-x-2"
								>
									<span
										v-if="isDesktopMenuOpen"
										class="ml-3 truncate"
									>
										{{ $t(link.name) }}
									</span>
								</Transition>
							</ULink>
						</UTooltip>
					</li>
				</ul>
			</nav>

			<Transition
				name="fade"
				enter-active-class="transition-opacity duration-300 delay-150"
				leave-active-class="transition-opacity duration-200"
			>
				<div
					v-if="!isDesktopMenuOpen"
					class="absolute top-4 left-1/2 transform -translate-x-1/2"
				>
					<div
						class="flex items-center justify-center w-8 h-8 rounded-lg bg-gradient-to-br from-green-500 to-green-600 cursor-pointer"
					>
						<UIcon
							name="i-heroicons-scale"
							class="w-5 h-5 text-white"
							@click="isDesktopMenuOpen = true"
						/>
					</div>
				</div>
			</Transition>
		</aside>
		<!-- mobile -->
		<template v-if="isMobile">
			<!-- hamburger menu -->
			<div
				v-if="!useBottonNavigationBar"
				class="fixed bottom-4 right-4 z-10"
			>
				<!-- btn -->
				<UButton
					ref="mobileMenuButton"
					class="rounded-full w-14 h-14 active:scale-90 transition-all"
					@click="handleMenuClick"
				>
					<UIcon
						:name="
							isMenuOpen
								? 'i-heroicons-x-mark'
								: 'i-heroicons-bars-3'
						"
						class="size-10"
					/>
				</UButton>
				<!-- menu -->
				<Transition>
					<nav
						v-if="isMenuOpen"
						ref="mobileMenu"
						class="absolute -top-5 -translate-y-full right-0 slide-in-right"
						@click.stop
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
										>{{ $t(link.name) }}</span
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
			<!-- mobile - navigation bar -->
			<LayoutsBottomNavigationBar v-else />
		</template>
	</div>
</template>
<script setup lang="ts">
import routes from "./routes.json";
const { isMobile } = useIsMobile();

const isMenuOpen = ref(false);
const isMounted = ref(false);
const isDesktopMenuOpen = ref(true);
const isButtonClicked = ref(false);
const { useBottonNavigationBar } = useSettingsStore();

onMounted(() => {
	isMounted.value = true;
});

function handleMenuClick(event: Event) {
	event.stopPropagation();
	event.preventDefault();

	isButtonClicked.value = true;
	isMenuOpen.value = !isMenuOpen.value;

	setTimeout(() => {
		isButtonClicked.value = false;
	}, 100);
}

const mobileMenu = ref<HTMLElement | null>(null);
const mobileMenuButton = ref<HTMLElement | null>(null);

onMounted(() => {
	const handleDocumentClick = (event: Event) => {
		if (!isMenuOpen.value || isButtonClicked.value) return;

		const menuEl = mobileMenu.value;
		const buttonEl =
			mobileMenuButton.value?.$el || mobileMenuButton.value;

		if (
			menuEl &&
			!menuEl.contains(event.target as Node) &&
			buttonEl &&
			!buttonEl.contains(event.target as Node)
		) {
			isMenuOpen.value = false;
		}
	};

	document.addEventListener("click", handleDocumentClick);

	onUnmounted(() => {
		document.removeEventListener("click", handleDocumentClick);
	});
});

const sidebarLinks = routes;
</script>

<style scoped>
.menu-icon {
	position: relative;
}
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
	width: 150px;
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
