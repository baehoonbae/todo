<template>
  <div class="flex justify-between mt-4 mx-auto px-4 max-w-[950px] bg-white">
    <!-- Profile Section -->
    <div class="flex items-center mb-8 gap-4">
      <div class="w-12 h-12 bg-gray-200 rounded-full"></div>
      <div class="w-12 h-12 bg-gray-200 rounded-full"></div>
    </div>
    <!-- User Info Section -->
    <div class="flex gap-4">
      <RouterLink v-if="isCalendarOpen" to="/calendar">
        <CalendarDaysIcon @click="isCalendarOpen = !isCalendarOpen"
          class="w-6 h-6 text-gray-800 cursor-pointer hover:text-black" />
      </RouterLink>
      <RouterLink v-else to="/big-calendar">
        <CalendarDaysIcon @click="isCalendarOpen = !isCalendarOpen"
          class="w-6 h-6 text-gray-800 cursor-pointer hover:text-black" />
      </RouterLink>
      <div class="relative">
        <EllipsisHorizontalIcon @click="isDropdownOpen = !isDropdownOpen"
          class="w-6 h-6 text-gray-800 cursor-pointer hover:text-black" />
        <Transition name="dropdown">
          <div v-if="isDropdownOpen"
            class="absolute right-0 mt-2 w-[155px] font-semibold bg-white rounded-[20px] shadow-[0_4px_20px_rgba(0,0,0,0.15)] py-2 z-50">
            <RouterLink class="max-h-10 block px-4 pb-0.5 text-sm text-gray-700" to="/category">
              <div class="flex justify-between items-center">
                <span class="text-center">카테고리 등록</span>
                <span class="text-blue-500 text-2xl">+</span>
              </div>
            </RouterLink>
            <div class="h-[1px] bg-gray-100"></div>
            <RouterLink class="block px-4 py-2 text-sm text-gray-700" to="/category-list">카테고리 관리</RouterLink>
            <div class="h-[1px] bg-gray-100"></div>
            <RouterLink to="#" class="block px-4 py-2 text-sm text-gray-700">루틴 관리</RouterLink>
            <div class="h-[1px] bg-gray-100"></div>
            <RouterLink to="#" class="block px-4 py-2 text-sm text-gray-700">시간 알림</RouterLink>
          </div>
        </Transition>
      </div>
    </div>
  </div>
</template>

<script setup>
import {
  CalendarDaysIcon,
  EllipsisHorizontalIcon,
} from "@heroicons/vue/24/outline";
import { onMounted, onUnmounted, ref } from "vue";
import { RouterLink } from "vue-router";

const isDropdownOpen = ref(false);
const isCalendarOpen = ref(false);

// 드롭다운 외부 클릭 감지를 위한 이벤트 리스너
const handleClickOutside = (event) => {
  const dropdown = document.querySelector(".relative");
  if (dropdown && !dropdown.contains(event.target)) {
    isDropdownOpen.value = false;
  }
};

// 컴포넌트가 마운트될 때 이벤트 리스너 추가
onMounted(() => {
  document.addEventListener("click", handleClickOutside);
});

// 컴포넌트가 언마운트될 때 이벤트 리스너 제거
onUnmounted(() => {
  document.removeEventListener("click", handleClickOutside);
});
</script>

<style scoped>
.dropdown-enter-active,
.dropdown-leave-active {
  transition: all 0.3s ease;
}

.dropdown-enter-from,
.dropdown-leave-to {
  opacity: 0;
  transform: translateY(-10px);
}
</style>
