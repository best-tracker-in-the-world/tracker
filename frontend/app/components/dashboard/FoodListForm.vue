<template>
	<UForm
		ref="formRef"
		v-auto-animate
		:schema="schema"
		:state="state"
		class="w-full flex flex-col gap-4"
		@submit="onSubmit"
	>
		<UiDev>
			id: {{ computedId }}
		</UiDev>

		<!-- NAME -->
		<UFormField
			name="name"
			:label="$t('dashboard.foodList.name')"
			required
		>
			<UInput
				ref="nameInputRef"
				v-model="state.name"
				type="text"
				class="w-full"
				size="xl"
			/>
		</UFormField>

		<!-- WEIGHT -->
		<UFormField
			name="weight"
			:label="
				$t('dashboard.weight.title') +
				', ' +
				$t('dimensions.g') +
				'.'
			"
			required
		>
			<UInput
				v-model="state.weight"
				type="number"
				class="w-full"
				size="xl"
			/>
		</UFormField>

		<!-- CALORIC CONTENT -->
		<UFormField
			name="caloricContent"
			:label="
				$t('dashboard.caloricContent') +
				', ' +
				$t('dimensions.kcal') +
				'.'
			"
			required
		>
			<UInput
				v-model="state.caloricContent"
				type="number"
				class="w-full"
				size="xl"
			/>
		</UFormField>

		<!-- NUTRIENTS -->
		<UCollapsible>
			<UiAdditionalLink
				v-model="isAdditionalVisible"
				v-auto-animate
				class="my-4"
				:label="$t('foodList.additionalInfo')"
			/>
			<template #content>
				<div class="flex gap-4 mb-2">
					<UFormField
						name="protein"
						:label="$t('foodList.protein')"
					>
						<UInput
							v-model="state.protein"
							type="number"
							class="w-full"
							size="xl"
						/>
					</UFormField>
					<UFormField name="carbs" :label="$t('foodList.carbs')">
						<UInput
							v-model="state.carbs"
							type="number"
							class="w-full"
							size="xl"
						/>
					</UFormField>
					<UFormField name="fat" :label="$t('foodList.fat')">
						<UInput
							v-model="state.fat"
							type="number"
							class="w-full"
							size="xl"
						/>
					</UFormField>
				</div>
			</template>
		</UCollapsible>

		<!-- checkboxes was here -->

		<!-- SUBMIT -->
		<UButton
			:loading="isSubmitting"
			type="submit"
			color="info"
			class="w-full items-center grid mt-8"
			size="xl"
		>
			{{ $t("dashboard.foodList.add") }}
		</UButton>
	</UForm>
</template>

<script setup lang="ts">
import { z } from "zod";
import type { FormSubmitEvent } from "#ui/types";

const { t } = useI18n();

const emit = defineEmits<{
	submit: [data: any];
}>();

const { latestFoodItemId } = useLatestFoodItemId();

// zod schema
const schema = z.object({
	name: z
		.string()
		.min(1, t("foodList.error.name.tooShort"))
		.max(50, t("foodList.error.name.tooLong")),
	weight: z.coerce
		.number()
		.min(1, t("foodList.error.weight.tooSmall"))
		.max(5000, t("foodList.error.weight.tooBig")),
	caloricContent: z.coerce
		.number()
		.min(1, t("foodList.error.cal.tooSmall"))
		.max(900, t("foodList.error.cal.tooBig")),
	protein: z.coerce
		.number()
		.min(0, t("foodList.error.nutrition.tooSmall"))
		.max(100, t("foodList.error.nutrition.tooBig"))
		.nullable()
		.optional(),
	carbs: z.coerce
		.number()
		.min(0, t("foodList.error.nutrition.tooSmall"))
		.max(100, t("foodList.error.nutrition.tooBig"))
		.nullable()
		.optional(),
	fat: z.coerce
		.number()
		.min(0, t("foodList.error.nutrition.tooSmall"))
		.max(100, t("foodList.error.nutrition.tooBig"))
		.nullable()
		.optional(),
	addedAt: z.string().optional(),
	id: z.number().optional(),
});

type Schema = z.output<typeof schema>;

const state = reactive({
	name: "",
	weight: 100,
	caloricContent: 0,
	protein: null as number | null,
	carbs: null as number | null,
	fat: null as number | null,
	addedAt: new Date().toISOString().substring(11, 16),
});

const computedId = computed(() => latestFoodItemId.value + 1);

const isAdditionalVisible = ref(false);
const isSubmitting = ref(false);
const nameInputRef = ref<ComponentPublicInstance | null>(null);
const formRef = ref();

onMounted(() => {
	if (nameInputRef.value) {
		nameInputRef.value.$el.querySelector("input").focus();
	}
});

const onSubmit = async (event: FormSubmitEvent<Schema>) => {
	isSubmitting.value = true;

	try {
		const formData = {
			...event.data,
			addedAt: state.addedAt,
			id: computedId.value,
		};

		emit("submit", formData);

		Object.assign(state, {
			name: "",
			weight: 100,
			caloricContent: 0,
			protein: null,
			carbs: null,
			fat: null,
			isFavorite: false,
			addedAt: new Date().toISOString().substring(11, 16),
		});

		nextTick(() => {
			if (nameInputRef.value) {
				nameInputRef.value.$el.querySelector("input").focus();
			}
		});
	} catch (error) {
		console.error("Error submitting food item:", error);
	} finally {
		isSubmitting.value = false;
	}
};
</script>
