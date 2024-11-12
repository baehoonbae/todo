<template>
  <div class="w-auto max-w-[144px] ml-20">
    <div class="space-y-5">
      <div
        v-for="category in store.categories"
        :key="category.id"
        class="flex items-center justify-between bg-gray-100 rounded-[18px] py-2 px-3 cursor-pointer hover:bg-gray-200 w-fit"
        @click="$emit('select-category', category)"
      >
        <div class="flex items-center gap-2 flex-1 overflow-hidden">
          <GlobeAltIcon
            v-if="category.isPublic"
            class="w-4 h-4 text-gray-400 flex-shrink-0"
          />
          <LockClosedIcon
            v-else
            class="w-4 h-4 text-gray-400 flex-shrink-0"
          />
          <span
            class="text-sm font-bold truncate"
            :style="{ color: category.color }"
          >
            {{ truncateText(category.title) }}
          </span>
        </div>
        <span class="text-gray-400 ml-2 flex-shrink-0">+</span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useAuthStore } from "@/stores/auth";
import { GlobeAltIcon, LockClosedIcon } from "@heroicons/vue/24/outline";
import { onMounted, watch } from "vue";

const store = useAuthStore();

defineEmits(["select-category"]);

// 텍스트 자르기 함수
const truncateText = (text) => {
  return text.length > 20 ? text.slice(0, 20) + '...' : text;
};

// 컴포넌트 마운트시 카테고리 가져오기
onMounted(() => {
  if (store.user.isAuthenticated) {
    store.fetchCategories();
  }
});

// 로그인 상태 변경 감지하여 카테고리 업데이트
watch(
  () => store.user.isAuthenticated,
  (isAuthenticated) => {
    if (isAuthenticated) {
      store.fetchCategories();
    }
  }
);
</script>
