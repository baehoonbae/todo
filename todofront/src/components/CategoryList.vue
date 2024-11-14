<template>
  <div class="w-auto max-w-[800px] ml-20">
    <div class="space-y-5">
      <div v-for="category in categoryStore.categories" :key="category.id">
        <div
          class="flex items-center justify-between bg-gray-100 rounded-[18px] py-2 px-3 cursor-pointer hover:bg-gray-200 w-fit"
          @click.stop="openTodoForm(category)">
          <div class="flex items-center gap-2 flex-1 overflow-hidden">
            <GlobeAltIcon v-if="category.isPublic" class="w-4 h-4 text-gray-400 flex-shrink-0" />
            <LockClosedIcon v-else class="w-4 h-4 text-gray-400 flex-shrink-0" />
            <span class="text-sm font-bold truncate" :style="{ color: category.color }">{{ truncateText(category.title)
              }}</span>
          </div>
          <span class="text-gray-400 ml-2 flex-shrink-0">+</span>
        </div>
        <div class="mb-8">
          <TodoList :categoryId="category.id" />
          <div v-if="selectedCategory && selectedCategory.id === category.id" class="mt-4 space-y-2 mb-8">
            <div class="flex items-center gap-2 px-2">
              <input class="w-[22.5px] h-[22.5px] rounded border bg-[#dadddf] border-gray-300"></input>
              <input type="text" placeholder="할 일 입력" class="pb-2 w-[344px] text-sm outline-none caret-blue-500"
                :ref="el => { if (selectedCategory?.id === category.id) todoInput = el }"
                :style="{ 'border-bottom': `2px solid ${selectedCategory.color}` }" v-model="todo.content" @click.stop
                @keyup.enter="handleAddTodo(todo)" />
            </div>
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
import { onMounted, watch, ref, onUnmounted } from "vue";
import TodoList from "./TodoList.vue";
import { useTodoStore } from "@/stores/todo";

const authStore = useAuthStore();
const categoryStore = useCategoryStore();
const todoStore = useTodoStore();
const selectedCategory = ref(null);
const todo = ref({
  categoryId: null,
  date: new Date().toISOString().split('T')[0],
  content: '',
  userSeq: sessionStorage.getItem('userSeq'),
})
const todoInput = ref(null);

const closeAddTodo = () => {
  selectedCategory.value = null;
  todo.value.content = '';
};

const openTodoForm = (category) => {
  selectedCategory.value = category;
  todo.value.categoryId = category.id;

  setTimeout(() => {
    if (todoInput.value) {
      todoInput.value.focus();
    }
  }, 0);
};

const handleAddTodo = async (todo) => {
  if (todo.content.trim()) {
    try {
      const result = await todoStore.fetchAddTodo(todo);
      if (result?.success) {
        await todoStore.fetchTodos(todo.userSeq);
        todo.content = '';
      }
    } catch (error) {
      console.error('할 일 추가 실패:', error);
    }
  }
  closeAddTodo();
};

const truncateText = (text) => {
  return text.length > 20 ? text.slice(0, 20) + '...' : text;
};

onMounted(() => {
  if (authStore.user.isAuthenticated) {
    categoryStore.fetchCategories();
  }
  window.addEventListener('click', closeAddTodo);
});

watch(
  () => authStore.user.isAuthenticated,
  (isAuthenticated) => {
    if (isAuthenticated) {
      categoryStore.fetchCategories();
    }
  }
);

onUnmounted(() => {
  window.removeEventListener('click', closeAddTodo);
});


</script>
