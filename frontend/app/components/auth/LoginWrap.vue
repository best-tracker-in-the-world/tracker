<template>
	<div class="absolute top-0 right-0 p-4 gap-2 flex">
		<NuxtLink
			v-for="lang in locales"
			:key="lang.name"
			:to="$switchLocalePath(lang.code as 'en' | 'ru')"
			class="rounded-full grid items-center transition-all"
			:class="{
				'outline-2 outline-green-500': locale === lang.code,
			}"
		>
			<UIcon :name="lang.icon" class="size-6"
		/></NuxtLink>
	</div>

	<div
		class="w-screen h-screen flex flex-col gap-4 items-center justify-center bg-gray-100"
	>
		<slot />
		<UPopover placement="bottom" mode="hover" arrow>
			<ULink
				class="opacity-25 hover:opacity-100 transition-all underline decoration-dotted underline-offset-4"
				to="dashboard"
				@click="handleGuestLogin"
				>{{ $t("login.useGuest") }}</ULink
			>
			<template #content>
				<div class="w-[350px] h-fit">
					<p class="block p-4 mx-auto text-sm text-center">
						{{ $t("login.guestInfo") }}
					</p>
				</div>
			</template>
		</UPopover>
	</div>
</template>

<script setup lang="ts">
import { useAuthStore } from "@/stores/auth";

const { locale } = useI18n();
const router = useRouter();
const { logAsGuest } = useAuthStore();

const handleGuestLogin = (e: Event) => {
	e.preventDefault();
	console.log("loged");
	logAsGuest();
	router.push({
		path: "/dashboard",
	});
};

const locales = [
	{
		code: "en",
		name: "English",
		icon: "i-circle-flags-uk",
	},
	{
		code: "ru",
		name: "Русский",
		icon: "i-circle-flags-ru",
	},
];
</script>
