<template>
  <div class="max-w-[950px] mx-auto p-4">
    <!-- 헤더 -->
    <header class="flex items-center justify-between px-4 py-3">
      <button class="p-2" @click="router.go(-1)">
        <ChevronLeftIcon class="w-6 h-6 text-gray-600" />
      </button>
      <h1 class="font-bold">카테고리</h1>
      <button class="p-2" @click="router.push('/category')">
        <PlusIcon class="w-6 h-6 text-gray-600" />
      </button>
    </header>

    <!-- 카테고리 목록 -->
    <div
      class="space-y-3 h-[calc(100vh-160px)] overflow-auto [&::-webkit-scrollbar]:hidden [-ms-overflow-style:none] [scrollbar-width:none]">
      <div v-for="category in categoryStore.categories" :key="category.id" @click="goUpdate(category.id)"
        class="flex items-center p-3 rounded-lg transition-colors cursor-pointer">
        <span class="text-base font-semibold bg-[#f2f2f2] px-3.5 py-2 rounded-full"
          :style="{ color: category.color }">{{ category.title }}</span>
        <div class="ml-auto flex items-center gap-2">
          <GlobeAltIcon v-if="category.isPublic" class="w-5 h-5 text-gray-400" />
          <LockClosedIcon v-else class="w-5 h-5 text-gray-400" />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useAuthStore } from "@/stores/auth";
import { useCategoryStore } from "@/stores/category";
import {
  ChevronLeftIcon,
  PlusIcon,
  GlobeAltIcon,
  LockClosedIcon,
} from "@heroicons/vue/24/outline";
import { onMounted } from "vue";
import { useRouter } from "vue-router";

const authStore = useAuthStore();
const categoryStore = useCategoryStore();

const router = useRouter();

const goUpdate = (id) => {
  router.push({
    name: 'category-update',
    params: { id }
  });
}

onMounted(async () => {
  await categoryStore.fetchCategories();
});

router.afterEach(async () => {
  await categoryStore.fetchCategories();
});
</script>
