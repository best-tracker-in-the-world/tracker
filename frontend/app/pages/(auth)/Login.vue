<template>
	<AuthLoginWrap>
		<div>
			<USelect
				v-model="locale"
				:items="localeCodes"
				item-text="name"
				item-value="value"
				class="mb-4"
			/>
		</div>
		<AuthLoginForm
			:title="$t('login')"
			:form-data="formData"
			@update:form-data="onUpdateFormData"
			@submit="onSubmit"
			:error="error"
			:is-loading="isLoading"
		/>
	</AuthLoginWrap>
</template>

<script setup lang="ts">
import { login } from "@/services/authService";
import { useAuthStore } from "@/stores/auth";
definePageMeta({
	middleware: "auth",
});

const { token, isLogged, useLogin } = useAuthStore();
const router = useRouter();
// move to component
const { locales, locale } = useI18n();

const selectedLocale = ref(locale.value);

const localeCodes = computed(() => locales.value.map((l: any) => l.code));

watch(locales, () => {
	console.log("Locales:", locales.value);
	console.log("Locale Options:", localeOptions.value);
});

watch(locale, (newLocale) => {
	selectedLocale.value = newLocale;
});

const formData = reactive({
	email: "njiah@njiah.ru",
	password: "15965400Uf",
});

//

function onUpdateFormData(updated: typeof formData) {
	Object.assign(formData, updated);
}

const error = ref(null);
const isLoading = ref(false);

async function onSubmit() {
	isLoading.value = true;
	try {
		const result = await login(formData);
		console.log("Login result:", result);
		if (result) {
			useLogin(result.accessToken);
			router.push("/dashboard");
		}
	} catch (err: any) {
		console.log(err.status);
		console.log(err.data);
		error.value = err.data;
	} finally {
		isLoading.value = false;
	}
}
</script>
