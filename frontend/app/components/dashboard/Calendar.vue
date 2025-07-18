<template>
	<ClientOnly>
		<DashboardTileWrapper :span="2" :color="'white'">
			<div v-auto-animate class="flex flex-col gap-4" >
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
						class="flex flex-col items-center flex-1 rounded-md py-2 transition-all duration-150 cursor-pointer"
						:class="{
							'today-chip | outline outline-green-500/50 ':
								day.isToday && !day.isSelected,
							'':
								!day.isToday,
							'bg-green-500 text-gray-950 ':
								day.isSelected,
						}"
						@click="modelValueComputed = day.date"
					>
						<span class="capitalize text-xs mb-1">{{ day.name }}</span>
						<span class="text-xl">{{ day.day }}</span>
					</li>
				</ul>
				<UCalendar
					v-show="isDatePickerVisible"
					v-model="modelValueComputed"
					v-auto-animate
					class=""
					:locale="'ru'"
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
	background-color: #00C16A;
	bottom: 0;
	transform: translateY(50%);
}

</style>