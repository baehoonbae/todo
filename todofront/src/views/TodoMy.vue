<template>
    <div class="flex flex-col min-h-screen bg-white">
        <!-- Header -->
        <header class="flex items-center justify-center px-4 py-3">
            <div class="w-full max-w-[950px] flex items-center justify-between">
                <!-- 왼쪽: 뒤로가기 버튼 -->
                <div class="flex items-center">
                    <button class="text-gray-600" @click="goBack">
                        <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24"
                            stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7" />
                        </svg>
                    </button>
                </div>
                <!-- 가운데: 제목 -->
                <h1 class="font-bold absolute left-1/2 transform -translate-x-1/2">마이페이지</h1>
                <!-- 오른쪽: 완료 버튼 -->
                <button class="font-bold text-gray-600 px-4"></button>
            </div>
        </header>
        <!-- 프로필 이미지 -->
        <!-- Main Content -->
        <main class="flex-1 flex justify-center items-start bg-white">
            <div class="w-[950px] mx-auto">
                <div class="justify-items-center py-10">
                    <div class="w-20 h-20 bg-[#ebeef0] rounded-full"></div>
                    <button class="text-[0.78rem] font-bold text-blue-500 mt-4">프로필 이미지</button>
                </div>
                <div class="space-y-[0.6rem]">
                    <!-- Category Input -->
                    <button
                        class="flex justify-between items-center  px-6 w-[950px] h-[50px] text-[0.9rem] font-medium p-2 rounded-[20px] bg-[#f6f6f6]"
                        @click="showChangeNameModal = true">
                        <span>이름</span>
                        <span class="font-bold">{{ localUser.userName }}
                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                                stroke="currentColor" class="w-4 h-4 inline">
                                <path stroke-linecap="round" stroke-linejoin="round" d="M8.25 4.5l7.5 7.5-7.5 7.5" />
                            </svg>
                        </span>
                    </button>
                    <button
                        class="flex justify-between items-center  px-6 w-[950px] h-[50px] text-[0.9rem] font-medium p-2 rounded-[20px] bg-[#f6f6f6]"
                        @click="showChangeDescriptionModal = true">
                        <span>자기소개</span>
                        <span class="font-bold">{{ localUser.description || '등록안됨' }}
                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                                stroke="currentColor" class="w-4 h-4 inline">
                                <path stroke-linecap="round" stroke-linejoin="round" d="M8.25 4.5l7.5 7.5-7.5 7.5" />
                            </svg>
                        </span>
                    </button>
                </div>
            </div>
        </main>
    </div>
    <TodoMyChangeName v-if="showChangeNameModal" @close="showChangeNameModal = false" :user="user" />
    <TodoMyChangeDescription v-if="showChangeDescriptionModal" @close="showChangeDescriptionModal = false" :user="user" />
</template>

<script setup>
import { useRouter } from 'vue-router';
import { ref, computed } from 'vue';
import TodoMyChangeName from '@/components/TodoMyChangeName.vue';
import TodoMyChangeDescription from '@/components/TodoMyChangeDescription.vue';

const router = useRouter();

const goBack = () => {
    router.go(-1);
};

const props = defineProps(['user']);
const localUser = computed(() => ({...props.user}));

const showChangeNameModal = ref(false);
const showChangeDescriptionModal = ref(false);

</script>