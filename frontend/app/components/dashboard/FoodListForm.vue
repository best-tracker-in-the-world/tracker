<template>
	<UForm v-auto-animate :state="state" class="w-full flex flex-col gap-4">
		<!-- NAME -->
		<UFormField
			:label="$t('dashboard.foodlist.name')"
			required
			:error="
				!state.name.length && !isFocused
					? $t('foodList.error.nameTooShort')
					: ''
			"
		>
			<UInput
				ref="nameInputRef"
				v-model="state.name"
				type="text"
				class="w-full"
				size="xl"
				@focus="isFocused = true"
				@blur="isFocused = false"
			/>
		</UFormField>

		<!-- WEIGHT -->

		<UFormField
			:label="
				$t('dashboard.weight.title') +
				', ' +
				$t('dimensions.g') +
				'.'
			"
			:error="
				state.weight > 0 && state.weight < 5000
					? ''
					: $t('foodList.error.weightWrong')
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
			:label="
				$t('dashboard.caloricContent') +
				', ' +
				$t('dimensions.kcal') +
				'.'
			"
			:error="
				state.caloricContent < 1000
					? ''
					: $t('foodList.error.caloricContentWrong')
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
					<UFormField :label="$t('foodList.protein')">
						<UInput
							v-model="state.protein"
							class="w-full"
							size="xl"
						/>
					</UFormField>
					<UFormField :label="$t('foodList.carbs')">
						<UInput
							v-model="state.carbs"
							class="w-full"
							size="xl"
						/>
					</UFormField>
					<UFormField :label="$t('foodList.fat')">
						<UInput
							v-model="state.fat"
							class="w-full"
							size="xl"
						/>
					</UFormField>
				</div>
			</template>
		</UCollapsible>

		<!-- checkboxes -->

		<div id="checkboxes" class="flex flex-col gap-2 w-fit mt-2">
			<!-- add to favorites -->
			<div class="flex gap-2 w-fit align-center">
				<UCheckbox
					v-model="state.addToFavorites"
					:label="$t('dashboard.foodList.favorite')"
					class="w-full"
				/>
				<UPopover portal="#checkboxes" placement="top">
					<template #content>
						<p
							class="p-4 bg-white w-[300px] text-center text-sm"
						>
							{{ $t("dashboard.foodList.favoriteInfo") }}
						</p>
					</template>

					<UIcon
						size="24"
						class="opacity-50"
						name="i-heroicons-question-mark-circle"
					/>
				</UPopover>
			</div>

			<!-- addToFoodList -->
			<div class="flex gap-2 w-fit align-center items-center">
				<UCheckbox
					v-model="state.addToFoodList"
					:label="$t('dashboard.foodList.addToCurrentDay')"
					class="w-full h-fit"
				/>
				<UPopover portal="#checkboxes" placement="top">
					<template #content>
						<p
							class="p-4 bg-white w-[300px] text-center text-sm"
						>
							{{
								$t(
									"dashboard.foodList.addToCurrentDayInfo"
								)
							}}
						</p>
					</template>

					<UIcon
						size="24"
						class="opacity-50"
						name="i-heroicons-question-mark-circle"
					/>
				</UPopover>
			</div>
		</div>

		<!-- SUBMIT -->

		<UButton
			:loading="false"
			type="submit"
			color="info"
			class="w-full items-center grid mt-8"
			:class="isValid ? '' : 'opacity-25'"
			size="xl"
			:disabled="!isValid"
			@click="handleSumbit"
		>
			{{ $t("dashboard.foodList.add") }}
		</UButton>
	</UForm>
</template>

<script setup lang="ts">
import { UiAdditionalLink } from "#components";

const $emit = defineEmits(["submit"]);

const state = reactive({
	name: "",
	weight: 100,
	caloricContent: 0,
	protein: null,
	carbs: null,
	fat: null,
	addToFavorites: false,
	addToFoodList: true,
});

const isAdditionalVisible = ref(false);

const nameInputRef = ref<ComponentPublicInstance | null>(null);

const isFocused = ref(false);

const isValid = computed(() => {
	return (
		state.name.length > 0 &&
		state.caloricContent > 0 &&
		state.weight > 0 &&
		state.weight < 5000
	);
});

onMounted(() => {
	if (nameInputRef.value) {
		nameInputRef.value.$el.querySelector("input").focus();
	}
});

const handleSumbit = (e: Event) => {
	e.preventDefault();
	$emit("submit", state);
};
</script>
