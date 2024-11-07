<template>
    <div class="h-screen flex flex-col" @keydown.enter="login">
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

            <div class="space-y-2">
                <div>
                    <input type="text" placeholder="아이디"
                        class="w-full px-4 py-3 font-semibold rounded-lg focus:outline-none bg-[#F2F2F2]"
                        v-model="loginData.userId" />
                </div>
                <div>
                    <input type="password" placeholder="비밀번호"
                        class="w-full px-4 py-3 font-semibold rounded-lg focus:outline-none bg-[#F2F2F2]"
                        v-model="loginData.userPassword" />
                </div>
            </div>
            <div class="mt-8">
                <button @click="login"
                    class="w-[75px] mx-auto block bg-black text-sm text-white py-4 rounded-full font-bold hover:bg-gray-800">
                    확인
                </button>
            </div>
            <div class="mt-8 flex justify-center">
                <button class="border-b-black border-b-[1.5px] text-sm text-gray-400" @click="">
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

const loginData = ref({
    userId: '',
    userPassword: '',
});

const isUserIdEmpty = computed(() => loginData.value.userId.trim() === '');
const isUserPasswordEmpty = computed(() => loginData.value.userPassword.trim() === '');

const login = async () => {
    if (isUserIdEmpty.value) {
        alert('아이디를 입력하세요.');
        return;
    } else if (isUserPasswordEmpty.value) {
        alert('비밀번호를 입력하세요.');
        return;
    }
    await axios
        .post('http://localhost:8097/todo/api/user/login', loginData.value)
        .then(({ data }) => {
            const token = data.token;
            if (token) {
                localStorage.setItem('token', token);
                axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
            }
            alert('로그인이 완료되었습니다.');
            router.push('/');
        })
        .catch((err) => {
            console.log(err.response.data);
            alert('아이디 또는 비밀번호가 일치하지 않습니다.');
        })
        .finally(() => {
            console.log('로그인 폼을 비웁니다!');
            loginData.value = {
                userId: '',
                userPassword: '',
            };
        });
}

const router = useRouter();

const goBack = () => {
    router.go(-1);
};
</script>

<style scoped></style>