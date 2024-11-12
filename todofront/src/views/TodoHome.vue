<!-- 홈 화면 컴포넌트 -->
<template>
    <div class="h-screen flex justify-center items-center bg-white">
        <div class="flex flex-col items-center gap-8">
            <div class="flex flex-col items-center gap-2">
                <div class="w-[100px] h-[100px]">
                    <CheckCircleIcon class="w-full h-full text-blue-400" />
                </div>
                <h1 class="text-3xl font-extrabold text-black">my todo</h1>
                <p class="text-gray-600 text-sm">할 일을 작성하고 매일을 기록해보세요.</p>
            </div>
            <div class="flex flex-col gap-4 w-full">
                <RouterLink to="/signup"
                    class="w-full py-4 rounded-full text-center text-base font-semibold cursor-pointer transition-all duration-300 ease-in-out bg-black text-white hover:bg-gray-800">
                    회원가입
                </RouterLink>
                <RouterLink to="/login"
                    class="text-gray-600 text-base text-semibold text-center hover:text-black underline">
                    로그인
                </RouterLink>
            </div>
        </div>
    </div>
</template>

<script setup>
import { CheckCircleIcon } from "@heroicons/vue/24/outline";
</script>

<style scoped></style>
