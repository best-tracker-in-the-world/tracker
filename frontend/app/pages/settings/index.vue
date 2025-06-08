<template>
	<div class="w-full h-full">
		<UForm class="outline">
			<UFormField
				v-for="item in settings"
				:key="item.title"
				:label="item.title"
			>
				<component :is="item.component" />
			</UFormField>
		</UForm>
	</div>
</template>

<script setup lang="ts">
import { UInput } from "#components";
const { t } = useI18n();

const settings = [
	{
		title: t("settings.goal.title"),
		type: "number",
		component: UInput,
	},
	{
		title: t("settings.weight.title"),
		type: "number",
		component: UInput,
	},
];

definePageMeta({
	layout: "returnable",
});
</script>
