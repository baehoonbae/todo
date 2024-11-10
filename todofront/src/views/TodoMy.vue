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
                        class="flex justify-between items-center  px-6 w-[950px] h-[50px] text-[0.9rem] font-medium p-2 rounded-[20px] bg-[#f6f6f6]">
                        <span>이름</span>
                        <span>ㅇㅇ</span>
                    </button>
                    <button
                        class="flex justify-between items-center  px-6 w-[950px] h-[50px] text-[0.9rem] font-medium p-2 rounded-[20px] bg-[#f6f6f6]">
                        <span>이름</span>
                        <span>ㅇㅇ</span>
                    </button>
                    <button
                        class="flex justify-between items-center px-6  w-[950px] h-[50px] text-[0.9rem] font-medium p-2 rounded-[20px] bg-[#f6f6f6]">
                        <span>이름</span>
                        <span>ㅇㅇ</span>
                    </button>
                </div>
            </div>
        </main>
    </div>
</template>

<script setup>
import { useRouter } from 'vue-router';
const router = useRouter();

const goBack = () => {
    router.go(-1);
};

</script>