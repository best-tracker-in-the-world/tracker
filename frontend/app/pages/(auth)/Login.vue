<template>
	<AuthLoginWrap>
		<AuthLoginForm
			:title="$t('login.login')"
			:form-data="formData"
			:is-loading="isLoading"
			@update:form-data="onUpdateFormData"
			@submit="onSubmit"
		/>
	</AuthLoginWrap>
</template>

<script setup lang="ts">
import { login } from "@/services/authService";
import { useAuthStore } from "@/stores/auth";
const { t } = useI18n();

definePageMeta({
	middleware: "auth",
});

const { useLogin } = useAuthStore();
const router = useRouter();
const error = ref<Error | null>(null);
const isLoading = ref(false);
const toast = useToast();

function onError(err: string | Error) {
	toast.add({
		title: String(t("error")),
		description: String(err),
		color: "error",
	});
}

const formData = reactive({
	email: "example@email.com",
	password: "12345678",
});

function onUpdateFormData(updated: typeof formData) {
	Object.assign(formData, updated);
}

async function onSubmit() {
	isLoading.value = true;
	try {
		const result = (await login(formData)) as { accessToken: string };
		console.log("Login result:", result.accessToken);
		if (result.accessToken) {
			useLogin(result.accessToken);
			router.push("/dashboard");
		}
	} catch (err: Error | unknown) {
		console.log(err);
		error.value = err as Error;
		onError(error.value);
	} finally {
		isLoading.value = false;
	}
}
</script>
