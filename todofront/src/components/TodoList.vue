<template>
    <div v-for="todo in todoStore.todos" :key="todo.id">
        <div v-if="todo.categoryId === props.categoryId" class="flex items-center gap-2 pt-[15px] pl-[0.7px]">
            <div class="relative w-[24px] h-[24px] cursor-pointer" @click="handleDone(todo.id)">
                <svg v-if="todo.isDone" class="w-[21.5px] h-[21.5px] text-black" viewBox="0 0 20 20"
                    fill="currentColor">
                    <rect width="18" height="18" x="1" y="1" rx="4" fill="currentColor" />
                    <path fill="white"
                        d="M13.293 7.293a1 1 0 0 1 1.414 1.414l-5 5a1 1 0 0 1-1.414 0l-2.5-2.5a1 1 0 0 1 1.414-1.414L9 11.586l4.293-4.293z" />
                </svg>
                <div v-else class="w-[21.5px] h-[21.5px] rounded border bg-[#dadddf] border-gray-300"></div>
            </div>
            <button @click="goTodoMenu" class="flex">
                <div class="w-[344px] text-sm text-left">{{ todo.content }}</div>
                <EllipsisHorizontalIcon class="w-[21.5px] h-[21.5px]"></EllipsisHorizontalIcon>
            </button>
        <div/>
        </div> 
    </div>
</template>

<script setup>
import { useTodoStore } from "@/stores/todo";
import { onMounted, watch, } from "vue";
import { EllipsisHorizontalIcon } from "@heroicons/vue/24/outline";

const userSeq = sessionStorage.getItem('userSeq');
const props = defineProps({
    categoryId: {
        type: Number,
        required: true
    }
});

const todoStore = useTodoStore();

const handleDone = async (id) => {
    try {
        const todo = todoStore.todos.find(t => t.id === id);
        await todoStore.fetchTodoUpdate({
            id: id,
            isDone: !todo.isDone
        });
    } catch (error) {
        console.error('할 일 상태 업데이트 실패:', error);
    }
};

onMounted(async () => {
    await todoStore.fetchTodos(userSeq);
});

watch(() => props.categoryId, async (newCategoryId) => {
    if (newCategoryId) {
        await todoStore.fetchTodos(userSeq);
    }
});
</script>