<template>
  <div class="max-w-[950px] mx-auto p-4">
    <!-- 헤더 -->
    <div class="flex justify-between items-center mb-6">
      <button class="p-2" @click="goBack">
        <ChevronLeftIcon class="w-6 h-6 text-gray-600" />
      </button>
      <h1 class="text-xl font-semibold">카테고리</h1>
      <button class="p-2" @click="goRegist">
        <PlusIcon class="w-6 h-6 text-gray-600" />
      </button>
    </div>

    <!-- 카테고리 목록 -->
    <div class="space-y-3 h-[calc(100vh-160px)] overflow-auto">
      <div
        v-for="category in authStore.categories"
        :key="category.id"
        class="flex items-center p-3 rounded-lg transition-colors cursor-pointer"
      >
        <span
          class="text-base font-semibold bg-[#f2f2f2] px-3.5 py-2 rounded-full"
          :style="{ color: category.color }"
          >{{ category.title }}</span
        >
        <div class="ml-auto flex items-center gap-2">
          <GlobeAltIcon
            v-if="category.isPublic"
            class="w-5 h-5 text-gray-400"
          />
          <LockClosedIcon v-else class="w-5 h-5 text-gray-400" />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useAuthStore } from "@/stores/auth";
import { useRouter } from "vue-router";
import {
  ChevronLeftIcon,
  PlusIcon,
  GlobeAltIcon,
  LockClosedIcon,
} from "@heroicons/vue/24/outline";

const router = useRouter();

const goBack = () => {
  router.go(-1);
};

const goRegist=()=>{
  router.push('/category');
}

const authStore = useAuthStore();
</script>
