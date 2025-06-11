<template>
	<div class="absolute top-0 right-0 p-4 gap-2 flex">
		<div
			v-for="lang in locales"
			:key="lang.name"
			class="rounded-full grid items-center transition-all cursor-pointer"
			:class="{
				'outline-3 outline-green-600': locale === lang.code,
			}"
			@click="setLocale(lang.code as 'ru' | 'en')"
		>
			<UIcon :name="lang.icon" class="size-6" />
		</div>
	</div>

	<div
		class="w-screen h-screen flex flex-col gap-4 items-center justify-center"
	>
		<slot />

		<div class="flex items-center align-center justify-center mt-4">
			<p
				class="opacity-50"
			>
				{{
					isOnLoginPage
						? $t("login.alreadyHaveAccount")
						: $t("login.dontHaveAccount")
				}}

			</p>
							<ULink
					class="underline decoration-dotted underline-offset-4 ml-1 opacity-50 hover:opacity-100"
					:to="isOnLoginPage ? 'register' : 'login'"
					>{{
						isOnLoginPage
							? $t("login.register")
							: $t("login.login")
					}}</ULink
				>
		</div>

		<UPopover placement="bottom" mode="hover" arrow>
			<ULink
				class="opacity-50 hover:opacity-100 transition-all underline decoration-dotted underline-offset-4 mt-2"
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

const { locale, setLocale } = useI18n();
const router = useRouter();
const { logAsGuest } = useAuthStore();

const isOnLoginPage = router.currentRoute.value.fullPath === "/login";

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
