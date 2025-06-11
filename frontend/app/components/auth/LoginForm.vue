<template>
	<div
		class="flex flex-col items-center justify-center gap-2 ring-1 ring-gray-300 dark:ring-gray-600 p-4 rounded-lg shadow-md"
	>
		<p class="text-xl my-4">{{ title }}</p>
		<UInput
			v-model="localFormData.email"
			type="email"
			variant="subtle"
			:placeholder="$t('login.email')"
			:disabled="isLoading"
			size="xl"
		/>
		<UInput
			v-model="localFormData.password"
			type="password"
			variant="subtle"
			:placeholder="$t('login.password')"
			:disabled="isLoading"
			size="xl"
		/>
		<UButton
			class="w-full text-center cursor-pointer mt-4"
			block
			:disabled="isLoading"
			size="xl"
			@click="handleClickSubmit"
			>{{ submitText }}</UButton
		>
	</div>
</template>

<script setup lang="ts">
interface FormData {
	email: string;
	password: string;
}

const props = defineProps<{
	formData: FormData;
	title: string;
	submitText: string;
	isLoading: boolean;
}>();

const localFormData = reactive<FormData>({ ...props.formData });

const emit = defineEmits<{
	(e: "update:formData", formData: FormData): void;
	(e: "submit"): void;
}>();

watch(localFormData, (newValue) => {
	emit("update:formData", newValue);
});

function handleClickSubmit() {
	emit("submit");
}
</script>
