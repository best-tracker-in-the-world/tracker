<template>
	<DashboardTileWrapper
		:title="wrapperProps.title"
		:color="wrapperProps.color"
		:span="span"
		:icon="wrapperProps.icon"
		class="wrapper h-full max-w-full"
		:class="{ pressing: isPressed, completed: isCompleted }"
		v-bind="handlers"
		@dblclick="handleClick"
	>
		<template #header>
			<UIcon
				v-if="isLoaded"
				name="i-heroicons-plus"
				class="h-5 w-5 grid items center translate-x-1 dark:text-gray-400"
				@click="handleClick"
			/>
		</template>
		<USkeleton
			v-if="!isLoaded"
			class="h-[75%] w-[75%] mx-auto rounded-xl bg-gray-900/10 dark:bg-gray-100/10"
		/>
		<span
			v-else
			class="text-[15vw] md:text-[70px] font-bold text-center flex flex-col justify-center h-full justify-end"
		>
			<!-- weight value -->
			<span
				class="dark:text-gray-100 my-auto"
				:class="value ? 'h-fit my-auto' : 'opacity-10'"
			>
				<span>
					<!-- ?? -->
					{{ value ?? "??" }}
				</span>
				<span class="text-[6vw] md:text-[16px]">{{
					$t("dimensions.kg")
				}}</span>
			</span>
		</span>

		<UiModal v-model="isModalOpen" :title="$t('dashboard.weight.add')">
			<UForm
				ref="formRef"
				:schema="schema"
				:state="formData"
				@submit="onSubmit"
			>
				<div class="flex flex-col gap-4 w-full">
					<UFormField
						name="weight"
						:label="$t('dashboard.weight.title')"
					>
						<UInput
							ref="inputRef"
							v-model="formData.weight"
							size="xl"
							type="number"
							color="neutral"
							class="w-full"
							:step="0.1"
							:placeholder="$t('dashboard.weight.placeholder')"
						/>
					</UFormField>
					
					<UButton
						size="xl"
						type="submit"
						class="dark:bg-gray-900 dark:text-gray-300 grid items-center"
						color="neutral"
						:label="$t('dashboard.weight.add')"
						:loading="isSubmitting"
					/>
				</div>
			</UForm>
		</UiModal>
	</DashboardTileWrapper>
</template>

<script setup lang="ts">
import { z } from 'zod';
import type { FormSubmitEvent } from '#ui/types';

const { t } = useI18n();

interface Props {
	isLoaded?: boolean;
	span?: number;
	value?: number | null;
}

withDefaults(defineProps<Props>(), {
	isLoaded: false,
	span: 1,
	value: null,
});

const emit = defineEmits<{
	weightSubmit: [weight: number];
}>();

const schema = z.object({
	weight: z.coerce.number()
		.min(1, t('validation.weight.min'))
		.max(300, t('validation.weight.max'))
		.refine(val => val > 0, {
			message: t('validation.weight.required')
		})
});

type Schema = z.output<typeof schema>;

const formData = reactive({
	weight: undefined as number | undefined,
});

const isModalOpen = ref(false);
const isSubmitting = ref(false);
const formRef = ref();
const inputRef = ref<ComponentPublicInstance | null>(null);

const handleClick = () => {
	isModalOpen.value = true;
	formData.weight = undefined;
	nextTick(() => {
		if (!inputRef.value) return;
		inputRef.value.$el.querySelector("input").focus();
	});
};

const onSubmit = async (event: FormSubmitEvent<Schema>) => {
	isSubmitting.value = true;
	
	try {
		emit('weightSubmit', event.data.weight);
		isModalOpen.value = false;
		formData.weight = undefined;
	} catch (error) {
		console.error('Error submitting weight:', error);
	} finally {
		isSubmitting.value = false;
	}
};

watch((isModalOpen), (value) => {
		if(value) {
			nextTick(() => {
				if (!inputRef.value) return;
				inputRef.value.$el.querySelector("input").focus();
			})
		}
})

const wrapperProps = {
	title: t("dashboard.weight.title"),
	color: "gray",
	icon: "i-heroicons-scale",
};

const { handlers, isPressed, isCompleted } = useLongPress(() => {
	isModalOpen.value = true;
});
</script>

<style scoped>
.wrapper {
	transition: all 0.2s ease-in-out;
	overflow: clip;
	position: relative;
	cursor: pointer;
}

.wrapper.pressing {
	scale: 0.95;
}

.wrapper::after {
	content: "";
	position: absolute;
	top: 0;
	left: 0;
	height: 100%;
	width: 300%;
	background-color: rgba(0, 0, 0, 0.05);
	rotate: 0deg;
	transition: transform 0.7s linear;
	transform: translateX(-43%) translateY(100%);
	filter: blur(25px);
	rotate: 45deg;
	transform-origin: 0 center;
	z-index: 1;
}

.wrapper.pressing::after {
	transform: translateX(-43%);
}
.wrapper.completed::after {
	transform: translateX(-43%);
}
</style>