<template>
	<ClientOnly>
		<DashboardTileWrapper :span="2" :color="'white'">
			<div class="flex flex-col gap-4" v-auto-animate>
				<div
					class="flex items-center gap-2 align-center justify-end"
					@click="isDatePickerVisible = !isDatePickerVisible"
				>
					<span>
						{{ headerDate }}
					</span>
					<div>
						<UButton
							color="neutral"
							variant="outline"
							class="rounded-xl"
							size="xl"
							icon="i-heroicons-calendar"
						/>
					</div>
				</div>
				<ul class="flex justify-between gap-2">
					<li
						v-for="(day, index) in selectedWeek"
						:key="index"
						class="flex flex-col items-center outline-2 outline-gray-200 dark:outline-gray-700 flex-1 rounded-xl py-2 transition-colors duration-150 cursor-pointer"
						:class="{
							'outline-dashed outline-gray-500 dark:outline-gray-400! dark:bg-gray-900':
								day.isToday,
							'bg-gray-100 dark:bg-gray-900 text-gray-900 dark:text-gray-100':
								!day.isToday,
							'bg-gray-900 text-white dark:text-gray-300 dark:bg-gray-700!':
								day.isSelected,
						}"
						@click="modelValueComputed = day.date"
					>
						<span class="capitalize">{{ day.name }}</span>
						<span>{{ day.day }}</span>
					</li>
				</ul>
				<UCalendar
					v-show="isDatePickerVisible"
					v-model="modelValueComputed"
					class=""
					:locale="'ru'"
					v-auto-animate
				/>
			</div>
		</DashboardTileWrapper>
	</ClientOnly>
</template>

<script setup lang="ts">
import { CalendarDate } from "@internationalized/date";

const vAutoAnimate = useAutoAnimate();

const props = defineProps<{
	modelValue: CalendarDate;
}>();

const emit = defineEmits<{
	(e: "update:modelValue", value: CalendarDate): void;
}>();

const modelValueComputed = computed({
	get: () => props.modelValue,
	set: (val) => {
		if (val instanceof CalendarDate) {
			emit("update:modelValue", val);
		}
		if (isDatePickerVisible.value) isDatePickerVisible.value = false;
	},
});

const isDatePickerVisible = ref(false);
const { t } = useI18n();

const weekdays = [
	t("calendar.monday"),
	t("calendar.tuesday"),
	t("calendar.wednesday"),
	t("calendar.thursday"),
	t("calendar.friday"),
	t("calendar.saturday"),
	t("calendar.sunday"),
];

const months = [
	t("calendar.january"),
	t("calendar.february"),
	t("calendar.march"),
	t("calendar.april"),
	t("calendar.may"),
	t("calendar.june"),
	t("calendar.july"),
	t("calendar.august"),
	t("calendar.september"),
	t("calendar.october"),
	t("calendar.november"),
	t("calendar.december"),
];

const headerDate = computed(() => {
	const date = modelValueComputed.value;
	return `${date.day} ${months[date.month - 1]} ${date.year}`;
});

const selectedWeek = computed(() => {
	if (!modelValueComputed.value) return [];

	const now = new Date();
	const today = new CalendarDate(
		now.getFullYear(),
		now.getMonth() + 1,
		now.getDate()
	);

	const dayOfWeek = modelValueComputed.value.toDate().getDay();
	const diffToMonday = (dayOfWeek + 6) % 7;
	const startOfWeek = modelValueComputed.value.subtract({
		days: diffToMonday,
	});

	return Array.from({ length: 7 }).map((_, i) => {
		const day = startOfWeek.add({ days: i });
		const weekdayIndex = (day.toDate().getDay() + 6) % 7;

		const isToday =
			day.day === today.day &&
			day.month === today.month &&
			day.year === today.year;

		const isSelected =
			day.day === modelValueComputed.value.day &&
			day.month === modelValueComputed.value.month &&
			day.year === modelValueComputed.value.year;

		return {
			name: weekdays[weekdayIndex],
			day: day.day,
			isToday,
			isSelected,
			date: day,
		};
	});
});

watch(modelValueComputed, () => {
	isDatePickerVisible.value = false;
});
</script>
