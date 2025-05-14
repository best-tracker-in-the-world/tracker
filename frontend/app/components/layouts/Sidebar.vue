<template>
	<aside class="w-64 bg-gray-800 text-white h-screen flex flex-col">
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
</template>
<script setup lang="ts">
const sidebarLinks = [
	{
		name: "Dashboard",
		href: "/dashboard",
		icon: "i-heroicons-home",
	},
	{
		name: "Settings",
		href: "/settings",
		icon: "i-heroicons-cog-6-tooth",
	},
	{
		name: "Stats",
		href: "/stats",
		icon: "i-heroicons-chart-bar",
	},
	{
		name: "Logout",
		href: "/logout",
		icon: "i-heroicons-arrow-right-start-on-rectangle",
		color: "text-red-500",
	},
];
</script>
