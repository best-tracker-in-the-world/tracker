<template>
	<ClientOnly>
		<DashboardTileWrapper :span="2" :color="'white'">
			<div
				class="flex flex-col gap-4 w-full max-w-full overflow-scroll scroll-hidden"
			>
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
				<div
					class="week-calendar | max-w-[100%] w-full max-w-full overflow-scroll scroll-hidden py-1"
				>
					<ul class="flex gap-2">
						<li
							v-for="(day, index) in selectedWeek"
							:ref="day.isToday ? 'todayRef' : null"
							:key="index"
							class="min-w-[48px] flex flex-col items-center rounded-md py-2 cursor-pointer"
							:class="{
								'today-chip | outline outline-green-500/50':
									day.isToday && !day.isSelected,
								'selected-chip | bg-green-500 text-gray-950':
									day.isSelected,
							}"
							@click="modelValueComputed = day.date"
						>
							<span class="capitalize text-xs mb-1">{{
								day.name
							}}</span>
							<span class="text-xl">{{ day.day }}</span>
						</li>
					</ul>
				</div>
				<UCalendar
					v-show="isDatePickerVisible"
					v-model="modelValueComputed"
					:locale="'ru'"
					@update:model-value="selectFromCalendar = true"
				/>
			</div>
		</DashboardTileWrapper>
	</ClientOnly>
</template>

<script setup lang="ts">
import { CalendarDate } from "@internationalized/date";

const props = defineProps<{
	modelValue: CalendarDate;
}>();

const weekBaseDate = ref<CalendarDate>(props.modelValue);

const emit = defineEmits<{
	(e: "update:modelValue", value: CalendarDate): void;
}>();

const selectFromCalendar = ref(false);

const todayRef = ref<HTMLElement | null>(null);

const modelValueComputed = computed({
	get: () => props.modelValue,
	set: (val) => {
		if (val instanceof CalendarDate) {
			emit("update:modelValue", val);
		}
		if (isDatePickerVisible.value) isDatePickerVisible.value = false;
	},
});

watch(modelValueComputed, (newVal) => {
	if (!selectFromCalendar.value) return;

	weekBaseDate.value = newVal;
	selectFromCalendar.value = false;

	nextTick(() => {
		setTimeout(() => {
			const selectedChip = document.querySelector(".selected-chip");
			if (selectedChip) {
				selectedChip.scrollIntoView({
					inline: "start",
					block: "nearest",
					behavior: "smooth",
				});
			}
		}, 100);
	});
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
	if (!weekBaseDate.value) return [];

	const now = new Date();
	const today = new CalendarDate(
		now.getFullYear(),
		now.getMonth() + 1,
		now.getDate()
	);

	const dayOfWeek = weekBaseDate.value.toDate().getDay();
	const diffToMonday = (dayOfWeek + 6) % 7;
	const currentMonday = weekBaseDate.value.subtract({ days: diffToMonday });

	return Array.from({ length: 24 }).map((_, i) => {
		const day = currentMonday.subtract({ days: 12 }).add({ days: i });
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

onMounted(async () => {
	await nextTick();

	setTimeout(() => {
		if (todayRef.value) {
			todayRef.value[0].scrollIntoView({
				inline: "start",
				block: "nearest",
			});
		}
	}, 50);
});
</script>

<style scoped>
.today-chip {
	position: relative;
}

.today-chip::after {
	transition: all 300ms ease;
	content: "";
	position: absolute;
	width: 7px;
	height: 7px;
	border-radius: 100%;
	background-color: #00c16a;
	bottom: 0;
	transform: translateY(50%);
}

.week-calendar {
	opacity: 1;
	transition: opacity 300ms linear;
	@starting-style {
		opacity: 0;
	}
}
</style>
