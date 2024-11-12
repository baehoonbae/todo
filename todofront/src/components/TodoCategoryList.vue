<template>
  <div class="w-auto max-w-[800px] ml-20" @click.self="closeAddTodo">
    <div class="space-y-5">
      <div v-for="category in categoryStore.categories" :key="category.id">
        <div
          class="flex items-center justify-between bg-gray-100 rounded-[18px] py-2 px-3 cursor-pointer hover:bg-gray-200 w-fit"
          @click="selectedCategory = category">
          <div class="flex items-center gap-2 flex-1 overflow-hidden">
            <GlobeAltIcon v-if="category.isPublic" class="w-4 h-4 text-gray-400 flex-shrink-0" />
            <LockClosedIcon v-else class="w-4 h-4 text-gray-400 flex-shrink-0" />
            <span class="text-sm font-bold truncate" :style="{ color: category.color }">
              {{ truncateText(category.title) }}
            </span>
          </div>
          <span class="text-gray-400 ml-2 flex-shrink-0">+</span>
        </div>
        <div v-if="selectedCategory && selectedCategory.id === category.id" class="mt-4 space-y-2">
          <div class="flex items-center gap-2 px-2">
            <input type="checkbox" class="w-[16px] h-[16px] rounded border-gray-300 mb-2" />
            <input type="text" placeholder="할 일 입력" class="pb-2 w-[344px] text-mg outline-none caret-blue-500"
              :style="{ 'border-bottom': `2px solid ${selectedCategory.color}` }" v-model="newTodo"
              @keyup.enter="addTodo" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useAuthStore } from "@/stores/auth";
import { useCategoryStore } from "@/stores/category";
import { GlobeAltIcon, LockClosedIcon } from "@heroicons/vue/24/outline";
import { onMounted, watch, ref, nextTick } from "vue";

const authStore = useAuthStore();
const categoryStore = useCategoryStore();

const selectedCategory = ref(null);
const newTodo = ref('');
const closeAddTodo = () => {
  selectedCategory.value = null;
  newTodo.value = '';
};

const truncateText = (text) => {
  return text.length > 20 ? text.slice(0, 20) + '...' : text;
};

const addTodo = () => {
  if (newTodo.value.trim() && selectedCategory.value) {
    todos.value.push({
      id: todos.value.length + 1,
      title: newTodo.value,
      completed: false
    });
    newTodo.value = '';
  }
};

onMounted(() => {
  if (authStore.user.isAuthenticated) {
    categoryStore.fetchCategories();
  }
});

watch(
  () => authStore.user.isAuthenticated,
  (isAuthenticated) => {
    if (isAuthenticated) {
      categoryStore.fetchCategories();
    }
  }
);
</script>
