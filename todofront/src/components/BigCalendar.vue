<template>
  <div class="fixed inset-0 bg-white z-50 p-6">
    <!-- 상단 프로필 영역 -->
    <div class="flex items-center gap-3 mb-8">
      <div class="w-14 h-14 bg-gray-100 rounded-full flex items-center justify-center">me</div>
      <div class="w-14 h-14 bg-gray-100 rounded-full flex items-center justify-center">
        <svg class="w-4 h-4" viewBox="0 0 24 24" fill="none" stroke="currentColor">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
        </svg>
      </div>
    </div>

    <!-- 달력 내용 -->
    <div class="mb-4 flex items-center">
      <div class="w-14 h-14 bg-gray-100 rounded-full mr-4"></div>
      <span class="text-lg font-bold">me</span>
    </div>

    <div class="flex justify-between items-center mb-6">
      <div>
        <span class="text-lg font-bold">{{ currentYear }}년 {{ currentMonth }}월</span>
        <span class="ml-2 text-gray-600">✓ 0 😊 0 ❤️ 0</span>
      </div>
      <div class="flex gap-4">
        <button @click="previousMonth">←</button>
        <button @click="nextMonth">→</button>
      </div>
    </div>

    <!-- 달력 그리드 -->
    <div class="grid grid-cols-7 text-center mb-2">
      <div class="text-[0.8rem] h-8 flex items-center justify-center">월</div>
      <div class="text-[0.8rem] h-8 flex items-center justify-center">화</div>
      <div class="text-[0.8rem] h-8 flex items-center justify-center">수</div>
      <div class="text-[0.8rem] h-8 flex items-center justify-center">목</div>
      <div class="text-[0.8rem] h-8 flex items-center justify-center">금</div>
      <div class="text-[0.8rem] h-8 flex items-center justify-center text-blue-500">토</div>
      <div class="text-[0.8rem] h-8 flex items-center justify-center text-red-500">일</div>
    </div>

    <div class="grid grid-cols-7 gap-1">
      <!-- 빈 칸들 -->
      <template v-for="empty in firstDayOfMonth" :key="'empty-'+empty">
        <div class="aspect-square w-12 h-12"></div>
      </template>

      <!-- 날짜들 -->
      <template v-for="day in daysInMonth" :key="day">
        <div
          class="aspect-square w-12 h-12 flex items-center justify-center rounded-full text-[0.8rem] cursor-pointer"
          :class="{
            'hover:bg-gray-50': true,
            'bg-gray-200': isToday(day)
          }"
          @click="selectDate(day)"
        >
          {{ day }}
        </div>
      </template>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'

// 현재 날짜 상태 관리
const currentDate = ref(new Date())
const currentYear = computed(() => currentDate.value.getFullYear())
const currentMonth = computed(() => currentDate.value.getMonth() + 1)

// 해당 월의 첫 번째 날의 요일 구하기
const firstDayOfMonth = computed(() => {
  const firstDay = new Date(currentYear.value, currentMonth.value - 1, 1).getDay()
  return firstDay === 0 ? 6 : firstDay - 1 // 월요일을 시작으로 조정
})

// 해당 월의 총 일수 구하기
const daysInMonth = computed(() => {
  return new Date(currentYear.value, currentMonth.value, 0).getDate()
})

// 오늘 날짜 체크
const isToday = (day) => {
  const today = new Date()
  return today.getDate() === day && 
         today.getMonth() === currentDate.value.getMonth() &&
         today.getFullYear() === currentDate.value.getFullYear()
}

// 날짜 선택
const selectDate = (day) => {
  // 날짜 선택 로직 구현
}

// 이전 달로 이동
const previousMonth = () => {
  currentDate.value = new Date(currentYear.value, currentMonth.value - 2, 1)
}

// 다음 달로 이동
const nextMonth = () => {
  currentDate.value = new Date(currentYear.value, currentMonth.value, 1)
}
</script>