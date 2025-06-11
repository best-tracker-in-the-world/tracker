<template>
	<AuthLoginWrap>
		<AuthLoginForm
			:title="$t('login.login')"
			:form-data="formData"
			:is-loading="isLoading"
			:submit-text="$t('login.submit')"
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
	middleware: "login",
});

const { useLogin } = useAuthStore();
const router = useRouter();
const error = ref<Error | null>(null);
const isLoading = ref(false);
const toast = useToast();

const formData = reactive({
	email: "",
	password: "",
});

function onUpdateFormData(updated: typeof formData) {
	Object.assign(formData, updated);
}

function onError(err: string | Error) {
	toast.add({
		title: String(t("error")),
		description: String(err),
		color: "error",
	});
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
	} catch (err: any) {
		console.log(err?.data);
		error.value = err as Error;
		onError(err?.data.replace(/^\w+:\s*/, ""));
	} finally {
		isLoading.value = false;
	}
}
</script>
