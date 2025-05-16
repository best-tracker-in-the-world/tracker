<template>
	<AuthLoginWrap>
		<AuthLoginForm
			:title="$t('login.register')"
			:form-data="formData"
			:is-loading="isLoading"
			:submit-text="$t('register.submit')"
			@update:form-data="onUpdateFormData"
			@submit="onSubmit"
		/>
	</AuthLoginWrap>
</template>

<script setup lang="ts">
import { register } from "@/services/authService";
const { t } = useI18n();

definePageMeta({
	middleware: "auth",
});

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

async function onSubmit() {
	isLoading.value = true;
	const resonseToast = {
		title: null as string | null,
		description: null as string | null,
		color: null as string | null,
	};
	try {
		const result = (await register(formData)) as string;
		console.log(result);
		resonseToast.title = t("register.success") as string;
		resonseToast.description = result.replace(/^\w+:\s*/, "") as string;
		resonseToast.color = "primary" as string;
	} catch (e: any) {
		resonseToast.description = e?.data.replace(/^\w+:\s*/, "") as string;
		resonseToast.title = t("register.error") as string;
		resonseToast.color = "error" as string;
		error.value = e;
	} finally {
		isLoading.value = false;

		toast.add({
			title: resonseToast.title || "",
			description: resonseToast.description || "",
			color: resonseToast.color as "primary" | "error",
		});
	}
}
</script>
