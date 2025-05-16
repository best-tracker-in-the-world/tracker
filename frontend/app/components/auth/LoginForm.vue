<template>
	<div
		class="flex flex-col items-center justify-center gap-2 ring-1 ring-gray-300 p-4 rounded-lg shadow-md"
	>
		<p class="text-xl my-4">{{ title }}</p>
		<UInput
			v-model="localFormData.email"
			type="email"
			placeholder="Email"
			:disabled="isLoading"
		/>
		<UInput
			v-model="localFormData.password"
			type="password"
			placeholder="Password"
			:disabled="isLoading"
		/>
		<UButton
			class="w-full text-center"
			:disabled="isLoading"
			@click="handleClickSubmit"
			>Login</UButton
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
