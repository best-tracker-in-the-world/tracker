<template>
	<UForm v-auto-animate :state="state" class="w-full flex flex-col gap-4">
		<!-- NAME -->

		<UFormField label="name">
			<UInput
				v-model="state.name"
				type="text"
				class="w-full"
				size="xl"
			/>
		</UFormField>

		<!-- WEIGHT -->

		<UFormField label="weight">
			<UInput
				v-model="state.weight"
				type="number"
				class="w-full"
				size="xl"
			/>
		</UFormField>

		<!-- CALORIC CONTENT -->

		<UFormField label="caloric-content">
			<UInput
				v-model="state.caloricContent"
				type="number"
				class="w-full"
				size="xl"
			/>
		</UFormField>

		<!-- ADDITIONAL  -->

		<!-- toggle -->
		<div v-auto-animate class="mb-4">
			<UiAdditionalLink
				v-model="isAdditionalVisible"
				v-auto-animate
				class="my-4"
				label="Additional info"
			/>

			<!-- NUTRIENTS -->
			<div v-if="isAdditionalVisible" class="flex gap-4">
				<UFormField label="protein">
					<UInput
						v-model="state.protein"
						class="w-full"
						size="xl"
					/>
				</UFormField>
				<UFormField label="carbs">
					<UInput
						v-model="state.carbs"
						class="w-full"
						size="xl"
					/>
				</UFormField>
				<UFormField label="fat">
					<UInput v-model="state.fat" class="w-full" size="xl" />
				</UFormField>
			</div>
		</div>

		<!-- checkboxes -->

		<div id="checkboxes" class="flex flex-col gap-2 w-fit">
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
			class="w-full items-center grid"
			size="xl"
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
	name: "name",
	weight: 100,
	caloricContent: 400,
	protein: null,
	carbs: null,
	fat: null,
	addToFavorites: false,
	addToFoodList: true,
});

const isAdditionalVisible = ref(false);

const handleSumbit = (e: Event) => {
	e.preventDefault();
	$emit("submit", state);
};
</script>
