<template>
  <div class="container mx-auto px-4 max-w-[945px] mt-[28px]">
    <div class="bg-white p-6 rounded-lg">
      <!-- 달력 내용 -->
      <div class="mb-4 flex items-center">
        <RouterLink to="" class="w-14 h-14 bg-gray-100 rounded-full mr-4"></RouterLink>
        <RouterLink to="/my" class="flex flex-col">
          <div class="text-[0.9rem] font-bold">{{ user.userName }}</div>
          <div class="text-[0.81rem] font-medium text-gray-400">{{ user.description || '프로필에 자기소개를 입력해보세요.' }}</div>
        </RouterLink>
      </div>

      <div class="flex justify-between items-center mb-6">
        <div>
          <span class="text-[1.05rem] font-bold">{{ currentYear }}년 {{ currentMonth }}월</span>
          <span class="ml-2 font-semibold text-gray-600">✓ 0 😊 0 ❤️ 0</span>
        </div>
        <div class="flex gap-4">
          <button @click="previousMonth" class="text-gray-600 hover:text-gray-800">&lt;</button>
          <button @click="nextMonth" class="text-gray-600 hover:text-gray-800">&gt;</button>
        </div>
      </div>

      <!-- 달력 그리드 -->
      <div class="grid grid-cols-7 text-center mb-2 border-b-[1px] w-full">
        <div class="text-xs h-8 flex items-center justify-center">월</div>
        <div class="text-xs h-8 flex items-center justify-center">화</div>
        <div class="text-xs h-8 flex items-center justify-center">수</div>
        <div class="text-xs h-8 flex items-center justify-center">목</div>
        <div class="text-xs h-8 flex items-center justify-center">금</div>
        <div class="text-xs h-8 flex items-center justify-center text-blue-500">토</div>
        <div class="text-xs h-8 flex items-center justify-center text-red-500">일</div>
      </div>

      <div class="grid grid-cols-7 justify-items-center gap-1">
        <!-- 빈 칸들 -->
        <template v-for="empty in firstDayOfMonth" :key="'empty-' + empty">
          <div class="aspect-square w-12 h-12"></div>
        </template>

        <!-- 날짜들 -->
        <template v-for="day in daysInMonth" :key="day">
          <div class="aspect-square w-12 h-12 flex items-center justify-center rounded-full text-sm cursor-pointer my-5"
            :class="{
              'text-blue-500': isSaturday(day),
              'text-red-500': isSunday(day),
              'hover:bg-gray-300': true,
              'bg-gray-200': isToday(day),
            }" @click="selectDate(day)">
            {{ day }}
          </div>
        </template>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from "vue";

// 현재 날짜 상태 관리
const currentDate = ref(new Date());
const currentYear = computed(() => currentDate.value.getFullYear());
const currentMonth = computed(() => currentDate.value.getMonth() + 1);

// 해당 월의 첫 번째 날의 요일 구하기
const firstDayOfMonth = computed(() => {
  const firstDay = new Date(
    currentYear.value,
    currentMonth.value - 1,
    1
  ).getDay();
  return firstDay === 0 ? 6 : firstDay - 1; // 월요일을 시작으로 조정
});

// 해당 월의 총 일수 구하기
const daysInMonth = computed(() => {
  return new Date(currentYear.value, currentMonth.value, 0).getDate();
});

// 오늘 날짜 체크
const isToday = (day) => {
  const today = new Date();
  return (
    today.getDate() === day &&
    today.getMonth() === currentDate.value.getMonth() &&
    today.getFullYear() === currentDate.value.getFullYear()
  );
};

// 토요일 체크
const isSaturday = (day) => {
  const date = new Date(currentYear.value, currentMonth.value - 1, day);
  return date.getDay() === 6;
};

// 일요일 체크
const isSunday = (day) => {
  const date = new Date(currentYear.value, currentMonth.value - 1, day);
  return date.getDay() === 0;
};

// 날짜 선택
const selectDate = (day) => {
  // 날짜 선택 로직 구현
};

// 이전 달로 이동
const previousMonth = () => {
  currentDate.value = new Date(currentYear.value, currentMonth.value - 2, 1);
};

// 다음 달로 이동
const nextMonth = () => {
  currentDate.value = new Date(currentYear.value, currentMonth.value, 1);
};

defineProps(['user']);
</script>
