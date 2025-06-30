<template>
	<div class="w-full h-full">
		<UForm
			class="h-screen p-4 gap-2 flex flex-col mb-16"
			:state="state"
			:style="isUnsaved ? 'height: calc(100vh + 64px)' : ''"
		>
			<!-- PRIMARY -->
			<!-- NAME, EMAIL, PASSWORD -->

			<div
				class="ring-1 p-4 py-4 pb-6 ring-gray-100 rounded-2xl flex flex-col gap-4"
			>
				<h2 class="mb-4 text-md">
					{{ $t("dashboard.userSettings.primary") }}:
				</h2>
				<UFormField :label="$t('dashboard.userSettings.name')">
					<UInput
						v-model="state.name"
						class="w-full"
						size="xl"
						:disabled="auth.isLoggedAsGuest"
					/>
				</UFormField>
				<UFormField :label="$t('dashboard.userSettings.email')">
					<UInput
						v-model="state.email"
						class="w-full"
						size="xl"
						type="email"
						:disabled="auth.isLoggedAsGuest"
					/>
				</UFormField>
				<UFormField :label="$t('dashboard.userSettings.password')">
					<UInput
						v-model="state.password"
						class="w-full"
						size="xl"
						type="password"
						:disabled="auth.isLoggedAsGuest"
					/>
				</UFormField>
			</div>

			<!-- APP -->
			<!-- LANGUAGE, THEME -->

			<div
				class="ring-1 p-4 py-4 pb-6 my-8 ring-gray-100 rounded-2xl flex flex-col gap-4"
			>
				<h2 class="mb-4 text-md">
					{{ $t("dashboard.userSettings.app") }}:
				</h2>
				<UFormField :label="$t('dashboard.userSettings.theme')">
					<USelect
						v-model="state.theme"
						:items="
							availableThemes.map((theme) => ({
								value: theme,
								label: $t(`themes.${theme}`),
							}))
						"
						class="w-full"
						size="xl"
					/>
				</UFormField>
				<UFormField :label="$t('dashboard.userSettings.language')">
					<USelect
						v-model="state.language"
						:items="
							availableLanguages.map((lang) => ({
								value: lang,
								label: $t(`languages.${lang}`),
							}))
						"
						class="w-full"
						size="xl"
						@change="handleLanguageChange"
					/>
				</UFormField>
			</div>

			<!-- STATS -->
			<!-- CURRENT GOAL, WEIGHT, HEIGHT, AGE, GENDER -->

			<div
				class="ring-1 p-4 py-4 pb-6 ring-gray-100 rounded-2xl flex flex-col gap-4"
			>
				<h2 class="mb-4 text-md">
					{{ $t("dashboard.userSettings.stats") }}:
				</h2>
				<UFormField
					:label="$t('dashboard.userSettings.currentGoal')"
				>
					<UInput
						v-model="state.currentGoal"
						class="w-full"
						size="xl"
						type="number"
					/>
				</UFormField>
				<div class="flex gap-2 *:w-full">
					<UFormField
						:label="$t('dashboard.userSettings.weight')"
					>
						<UInput
							v-model="state.weight"
							class="w-full"
							size="xl"
							type="number"
						/>
					</UFormField>
					<UFormField
						:label="$t('dashboard.userSettings.height')"
					>
						<UInput
							v-model="state.height"
							class="w-full"
							size="xl"
							type="number"
						/>
					</UFormField>
					<UFormField :label="$t('dashboard.userSettings.age')">
						<UInput
							v-model="state.age"
							class="w-full"
							size="xl"
							type="number"
						/>
					</UFormField>
				</div>
				<UFormField :label="$t('dashboard.userSettings.gender')">
					<USelect
						v-model="state.gender"
						:items="
							avaliableGenders.map((gender) => ({
								value: gender,
								label: $t(`gender.${gender}`),
							}))
						"
						class="w-full"
						size="xl"
					/>
				</UFormField>
			</div>

			<div
				v-if="isUnsaved"
				ref="saveChanges"
				class="save-changes fixed top-0 left-0 right-0 mb-4 p-4 bg-red-500 rounded-b-xl shadow-xl bg-white dark:bg-gray-800"
			>
				<h4>{{ $t("settings.saveChanges") }}</h4>
				<div class="flex gap-2">
					<UButton
						:label="$t('settings.cancel')"
						class="w-full grid items-center mt-auto"
						size="xl"
						variant="outline"
						@click="handleCancelSave()"
					/>
					<UButton
						:label="$t('settings.save')"
						class="w-full grid items-center mt-auto"
						size="xl"
						variant="solid"
						@click="handleSettingsSave()"
					/>
				</div>
			</div>
		</UForm>
	</div>
</template>

<script setup lang="ts">
import { useSettingsStore } from "~/stores/settings";

const switchLocalePath = useSwitchLocalePath();
const auth = useAuthStore();
const saveChanges = ref<HTMLElement | null>(null);

const state = reactive({
	name: "",
	email: "",
	password: "",
	theme: "" as "light" | "dark",
	language: "" as "ru" | "en",
	currentGoal: 2000,
	weight: null as number | null,
	height: null as number | null,
	age: null as number | null,
	gender: "" as "male" | "female" | null,
});

const user = useSettingsStore();
user.loadSettings();

const isUnsaved = ref(false);
const i18n = useI18n();
const availableLanguages = ["en", "ru"];
const availableThemes = ["light", "dark"];
const avaliableGenders = ["male", "female"];
const colorMode = useColorMode();
const isInitialized = ref(false);

// watch(
// 	() => state,
// 	(newSettings) => {
// 		if (!isInitialized.value) return;
// 		console.log("triggered");
// 		isUnsaved.value = true;
// 		state.name = newSettings?.name ?? "";
// 		state.email = newSettings?.email ?? "";
// 		state.password = newSettings?.password ?? "";
// 		state.theme = newSettings?.theme ?? "light";
// 		state.language = newSettings?.language ?? "ru";
// 		state.currentGoal = newSettings?.currentGoal ?? 0;
// 		state.weight = newSettings?.weight ?? null;
// 		state.height = newSettings?.height ?? null;
// 		state.age = newSettings?.age ?? null;
// 		state.gender = newSettings?.gender ?? null;
// 	},
// 	{ deep: true }
// );

function handleSettingsSave() {
	console.log("saving settings...", { ...state });

	const settings = {
		...state,
		gender: state.gender as "male" | "female" | null,
	};
	user.saveSettings(settings);
	isUnsaved.value = false;
}

async function handleCancelSave() {
	await user.loadSettings();

	if (auth.isLoggedAsGuest) {
		state.name = user.settings?.name ?? "";
		state.email = user.settings?.email ?? "";
		state.password = user.settings?.password ?? "";
		state.theme = user.settings?.theme ?? colorMode.preference;
		state.language = user.settings?.language ?? "ru";
		state.currentGoal = user.settings?.currentGoal ?? 2000;
		state.weight = user.settings?.weight ?? null;
		state.height = user.settings?.height ?? null;
		state.age = user.settings?.age ?? null;
		state.gender = user.settings?.gender ?? null;
	} else {
		// get from server
	}
	nextTick(() => {
		saveChanges.value?.classList.add("slide-out-top");
		setTimeout(() => {
			saveChanges.value?.classList.remove("slide-out-top");
			isUnsaved.value = false;
		}, 300);
	});
}

onMounted(() => {
	// get user settings from indexedDB
	if (auth.isLoggedAsGuest) {
		state.name = user.settings?.name ?? "";
		state.email = user.settings?.email ?? "";
		state.password = user.settings?.password ?? "";
		state.theme = user.settings?.theme ?? colorMode.value;
		state.language = user.settings?.language;
		state.currentGoal = user.settings?.currentGoal ?? 0;
		state.weight = user.settings?.weight ?? null;
		state.height = user.settings?.height ?? null;
		state.age = user.settings?.age ?? null;
		state.gender = user.settings?.gender ?? null;
	} else {
		// get from server
	}

	nextTick(async () => {
		await user.loadSettings();
		i18n.locale.value = user.settings?.language || "ru";
		setTimeout(() => {
			isInitialized.value = true;
			console.log("isInitialized", isInitialized.value);
		}, 250);
	});
});

function handleLanguageChange() {
	i18n.locale.value = state.language;
	useRouter().push(switchLocalePath(state.language));
}

definePageMeta({
	layout: "app-returnable",
});
</script>

<style scoped>
.save-changes {
	transition: transform 0.3s ease-in-out, opacity 0.3s linear;
	@starting-style {
		opacity: 0;
		transform: translateY(-100%);
	}
}
</style>
