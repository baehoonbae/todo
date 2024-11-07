<template>
    <div class="h-screen flex flex-col" @keydown.enter="signUp">
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
                        v-model="user.userName" @blur="checkUserName" />
                    <p v-if="isUserNameEmpty && isUserNameBlurred" class="text-sm text-red-400 pl-2 pt-1">닉네임을 입력해주세요.
                    </p>
                    <p v-else-if="isUserNameDuplicated && isUserNameBlurred" class="text-sm text-red-400 pl-2 pt-1">사용할
                        수
                        없는 닉네임입니다.</p>
                </div>
                <div>
                    <input type="text" placeholder="아이디 등록"
                        class="w-full px-4 py-3 font-semibold rounded-lg focus:outline-none bg-[#F2F2F2]"
                        v-model="user.userId" @blur="checkUserId" />
                    <p v-if="isUserIdEmpty && isUserIdBlurred" class="text-sm text-red-400 pl-2 pt-1">아이디를 입력해주세요.</p>
                    <p v-else-if="isUserIdDuplicated && isUserIdBlurred" class="text-sm text-red-400 pl-2 pt-1">사용할 수 없는
                        아이디입니다.</p>
                </div>
                <div>
                    <input type="password" placeholder="비밀번호 등록"
                        class="w-full px-4 py-3 font-semibold rounded-lg focus:outline-none bg-[#F2F2F2]"
                        v-model="user.userPassword" @blur="checkUserPassword" />
                    <p v-if="isUserPasswordEmpty && isUserPasswordBlurred" class="text-sm text-red-400 pl-2 pt-1">패스워드를
                        입력해주세요.</p>
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
import { ref, computed } from 'vue';
import axios from 'axios';

const user = ref({
    userName: '',
    userId: '',
    userPassword: '',
})

const isUserNameEmpty = computed(() => user.value.userName.trim() === '');
const isUserIdEmpty = computed(() => user.value.userId.trim() === '');
const isUserPasswordEmpty = computed(() => user.value.userPassword.trim() === '');

// blur 이벤트가 발생했는지 여부를 체크
const isUserNameBlurred = ref(false);
const isUserIdBlurred = ref(false);
const isUserPasswordBlurred = ref(false);

const isUserNameDuplicated = ref(false);
const isUserIdDuplicated = ref(false);

const checkUserName = () => {
    isUserNameBlurred.value = true;
    isUserNameEmpty.value = user.value.userName.trim() === '';
    if (!isUserNameEmpty.value) {
        checkDuplicateUserName();
    }
};

const checkUserId = () => {
    isUserIdBlurred.value = true;
    isUserIdEmpty.value = user.value.userId.trim() === '';
    if (!isUserIdEmpty.value) {
        checkDuplicateUserId();
    }
};

const checkUserPassword = () => {
    isUserPasswordBlurred.value = true;
    isUserPasswordEmpty.value = user.value.userPassword.trim() === '';
};


const signUp = async () => {
    // 하나라도 비어 있으면 요청을 보내지 않음
    if (isUserNameEmpty.value || isUserIdEmpty.value || isUserPasswordEmpty.value) {
        alert('모든 필드를 입력해주세요.');
        return;
    }
    await axios
        .post('http://localhost:8097/todo/api/user/signup', user.value)
        .then((res) => {
            alert('회원가입이 완료되었습니다.');
            router.push('/');
        })
        .catch((err) => {
            alert('회원가입에 실패하였습니다.');
            return;
        });
}

const checkDuplicateUserName = async () => {
    await axios
        .get(`http://localhost:8097/todo/api/user/check-username/${user.value.userName}`, {
            validateStatus: (status) => {
                return status >= 200 && status < 300 || status === 409;
            }
        })
        .then((res) => {
            if (res.status === 409) {
                isUserNameDuplicated.value = true;
            } else {
                isUserNameDuplicated.value = false;
            }
        })
        .catch(err => {
            console.log(err);
        })
}

const checkDuplicateUserId = async () => {
    await axios
        .get(`http://localhost:8097/todo/api/user/check-userid/${user.value.userId}`, {
            validateStatus: (status) => {
                return status >= 200 && status < 300 || status === 409;
            }
        })
        .then((res) => {
            if (res.status === 409) {
                isUserIdDuplicated.value = true;
            } else {
                isUserIdDuplicated.value = false;
            }
        })
        .catch(err => {
            console.error(err);
        })
}

const router = useRouter();

const goBack = () => {
    router.go(-1);
};
</script>

<style scoped></style>