<template>
	<nav
		class="top-shadow | p-4 fixed bottom-0 w-screen z-1 bg-white dark:bg-gray-900"
	>
		<ul class="flex justify-between items-center mx-auto max-w-[300px]">
			<template v-for="(route, index) in routes" :key="route.name">
				<!-- hide logout btn -->

				<li v-if="routes.length - 1 !== index">
					<NuxtLink
						class="flex flex-col items-center gap-2"
						:to="route.href"
					>
						<UIcon
						class="transition-all text-gray-500 text-xl"
							:class="{
								'text-green-400':
									index === currentRoute,
							}"
							:name="route.icon"
						/>
						<span
							class="text-xs text-gray-400 transition-all"
							:class="{
								'text-green-400':
									index === currentRoute,
							}"
							>{{ $t(route.name) }}</span
						>
					</NuxtLink>
				</li>
			</template>
		</ul>
	</nav>
</template>

<script setup lang="ts">
import routes from "./routes.json";

const paths = useRoute();

const currentRoute = computed(() => {
	return routes.findIndex((x) => x.href === paths.path);
});
</script>

<style scoped>
body {
	background: red;
}
.top-shadow {
	box-shadow: rgba(17, 17, 26, 0.1) 0px 1px 0px,
		rgba(17, 17, 26, 0.1) 0px 8px 24px,
		rgba(17, 17, 26, 0.1) 0px 16px 48px;
}
</style>
