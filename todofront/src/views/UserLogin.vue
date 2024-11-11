<template>
    <div @keydown.enter="login" class="h-screen flex flex-col">
        <div class="w-[950px] px-4 mx-auto mt-0">
            <div class="flex items-center mb-8 pt-4">
                <button class="p-2" @click="goBack">
                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                        stroke="currentColor" class="w-6 h-6">
                        <path stroke-linecap="round" stroke-linejoin="round" d="M15.75 19.5L8.25 12l7.5-7.5" />
                    </svg>
                </button>
                <span class="flex-1 text-center font-bold">로그인</span>
                <div class="w-10"></div>
            </div>

            <form @submit.prevent="login" class="space-y-2">
                <div>
                    <input type="text" placeholder="아이디" v-model="loginData.userId" :disabled="isLoading"
                        class="w-full px-4 py-3 font-semibold rounded-lg focus:outline-none bg-[#F2F2F2]" />
                </div>
                <div class="relative">
                    <input :type="showPassword ? 'text' : 'password'" placeholder="비밀번호"
                        v-model="loginData.userPassword" :disabled="isLoading"
                        class="w-full px-4 py-3 font-semibold rounded-lg focus:outline-none bg-[#F2F2F2]" />
                    <button type="button" @click="showPassword = !showPassword"
                        class="absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-500">
                        <EyeIcon v-if="showPassword" class="w-5 h-5" />
                        <EyeSlashIcon v-else class="w-5 h-5" />
                    </button>
                </div>

                <div v-if="errorMessage" class="text-red-500 text-sm mt-2">
                    {{ errorMessage }}
                </div>

            </form>
            <div class="mt-8">
                <button :disabled="isLoading || !isFormValid" class="w-[75px] mx-auto block bg-black text-sm text-white py-4 rounded-full font-bold 
                           hover:bg-gray-800 disabled:bg-gray-400 disabled:cursor-not-allowed" @click="login">
                    {{ isLoading ? '로딩중...' : '확인' }}
                </button>
            </div>

            <div class="mt-8 flex justify-center space-x-4">
                <button class="text-sm text-gray-400 hover:text-gray-600" @click="router.push('/signup')">
                    회원가입
                </button>
                <span class="text-gray-300">|</span>
                <button class="text-sm text-gray-400 hover:text-gray-600">
                    비밀번호를 잊으셨나요?
                </button>
            </div>
        </div>
    </div>
</template>

<script setup>
import { useRouter } from 'vue-router';
import { ref, computed } from 'vue';
import axios from 'axios';
import { EyeIcon, EyeSlashIcon } from '@heroicons/vue/24/outline';

const router = useRouter();
const isLoading = ref(false);
const showPassword = ref(false);
const errorMessage = ref('');

const loginData = ref({
    userId: '',
    userPassword: '',
});

const isFormValid = computed(() => {
    return loginData.value.userId.trim() !== '' &&
        loginData.value.userPassword.trim() !== '';
});

const login = async () => {
    if (!isFormValid.value || isLoading.value) return;

    errorMessage.value = '';
    isLoading.value = true;

    try {
        const response = await axios.post('http://localhost:8097/todo/api/user/login',
            loginData.value,
            { withCredentials: true }
        );
        const data = response.data;

        if (data.accessToken) {
            sessionStorage.setItem('accessToken', data.accessToken);
            sessionStorage.setItem('userSeq', data.userSeq);
            sessionStorage.setItem('userName', data.userName);
            axios.defaults.headers.common['Authorization'] = `Bearer ${data.accessToken}`;
            router.push('/');
            // window.location.reload();
        } else {
            errorMessage.value = '로그인 응답에 토큰이 없습니다.';
        }
    } catch (error) {
        console.error('Login error:', error);
        if (error.response) {
            if (error.response.status === 401) {
                errorMessage.value = '아이디 또는 비밀번호가 올바르지 않습니다.';
            } else {
                errorMessage.value = error.response.data.message || '서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.';
            }
        } else if (error.request) {
            errorMessage.value = '서버에 연결할 수 없습니다. 네트워크 연결을 확인해주세요.';
        } else {
            errorMessage.value = '로그인 처리 중 오류가 발생했습니다.';
        }
    } finally {
        isLoading.value = false;
    }
};

const goBack = () => {
    router.go(-1);
};
</script>

<style scoped>
.fade-enter-active,
.fade-leave-active {
    transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
    opacity: 0;
}
</style>