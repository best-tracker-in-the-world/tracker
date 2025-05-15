<template>
	<AuthLoginWrap>
		<AuthLoginForm
			:title="$t('login')"
			:form-data="formData"
			:error="error"
			:is-loading="isLoading"
			@update:form-data="onUpdateFormData"
			@submit="onSubmit"
		/>
	</AuthLoginWrap>
</template>

<script setup lang="ts">
import { login } from "@/services/authService";
import { useAuthStore } from "@/stores/auth";

definePageMeta({
	middleware: "auth",
});

const { useLogin } = useAuthStore();
const router = useRouter();
const error = ref(null);
const isLoading = ref(false);

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
		const result: any = await login(formData);
		console.log("Login result:", result.accessToken);
		if (result.accessToken) {
			useLogin(result.accessToken);
			router.push("/dashboard");
		}
	} catch (err: any) {
		console.log(err.status, err.data);
		error.value = err.data;
	} finally {
		isLoading.value = false;
	}
}
</script>
