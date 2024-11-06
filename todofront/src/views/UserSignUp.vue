<template>
    <div class="h-screen flex flex-col">
        <div class="w-[950px] px-4 mx-auto mt-0">
            <div class="flex items-center mb-8 pt-4">
                <button class="p-2" @click="goBack">
                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                        stroke="currentColor" class="w-6 h-6">
                        <path stroke-linecap="round" stroke-linejoin="round" d="M15.75 19.5L8.25 12l7.5-7.5" />
                    </svg>
                </button>
                <span class="flex-1 text-center font-bold">가입하기</span>
                <div class="w-10"></div>
            </div>

            <div class="space-y-2">
                <div>
                    <input type="text" placeholder="닉네임 등록"
                        class="w-full px-4 py-3 font-semibold rounded-lg focus:outline-none bg-[#F2F2F2]"
                        v-model="user.userName" />
                </div>
                <div>
                    <input type="text" placeholder="아이디 등록"
                        class="w-full px-4 py-3 font-semibold rounded-lg focus:outline-none bg-[#F2F2F2]"
                        v-model="user.userId" />
                </div>
                <div>
                    <input type="password" placeholder="비밀번호 등록"
                        class="w-full px-4 py-3 font-semibold rounded-lg focus:outline-none bg-[#F2F2F2]"
                        v-model="user.userPassword" />
                </div>
            </div>

            <div class="fixed bottom-0 left-1/2 transform -translate-x-1/2 w-[950px] px-4 pb-8">
                <button @click="signUp"
                    class="w-[125px] mx-auto block bg-black text-white py-4 rounded-full font-semibold hover:bg-gray-800">
                    가입 완료
                </button>
            </div>
        </div>
    </div>
</template>

<script setup>
import { useRouter } from 'vue-router';
import { ref } from 'vue';
import axios from 'axios';

const user = ref({
    userName: '',
    userId: '',
    userPassword: '',
})

const signUp = async () => {
    await axios
        .post('http://localhost:8097/todo/api/user/signup', user.value)
        .then((res) => {
            alert('회원가입이 완료되었습니다.');
            router.push('/');
        })
        .catch((err) => {
            console.log(err.response.data);
            alert('회원가입에 실패하였습니다.');
        });
}

const router = useRouter();

const goBack = () => {
    router.go(-1);
};
</script>

<style scoped></style>