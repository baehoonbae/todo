<template>
    <div class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50" ref="modalRef" tabindex="0"
        @click="$emit('close')">
        <div class="bg-white rounded-2xl p-6 w-[90%] max-w-[400px]" @click.stop>
            <!-- 입력 폼 -->
            <div class="mb-6">
                <input v-model="newUserName" type="text" placeholder="새로운 닉네임을 입력하세요" @keydown.esc="$emit('close')"
                    @keydown.enter="changeUserName"
                    class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:border-black"
                    :class="{ 'border-red-500': error }" ref="inputRef" @compositionstart="onCompositionStart"
                    @compositionend="onCompositionEnd" @input="handleInput" />
                <p v-if="error" class="mt-2 text-sm text-red-500">{{ error }}</p>
            </div>
            <!-- 버튼 -->
            <div class="flex justify-center gap-3">
                <button @click="$emit('close')" class="w-full px-4 py-2 text-gray-600 hover:bg-gray-100 rounded-lg">
                    취소
                </button>
                <button @click="changeUserName"
                    class="w-full px-4 py-2 bg-black font-semibold text-white rounded-lg hover:bg-gray-800"
                    :disabled="!isValid">
                    {{ loading ? '변경 중...' : '변경하기' }}
                </button>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import axios from 'axios';
import { useAuthStore } from '@/stores/auth';
const newUserName = ref('');
const error = ref('');
const loading = ref(false);
const isDuplicateChecked = ref(false);
let timeout = null;
const isComposing = ref(false);
const emit = defineEmits(['close', 'update:userName']);
const props = defineProps(['user']);
const inputRef = ref(null);
const authStore = useAuthStore();

const isValid = computed(() => {
    return newUserName.value && !loading.value && !error.value && isDuplicateChecked.value;
});

const onCompositionStart = () => {
    isComposing.value = true;
};

const onCompositionEnd = () => {
    isComposing.value = false;
    debouncedCheckUserName();
};

const handleInput = (e) => {
    newUserName.value = e.target.value;
    if (!isComposing.value) {
        debouncedCheckUserName();
    }
};

const debouncedCheckUserName = () => {
    isDuplicateChecked.value = false;
    error.value = '';

    if (timeout) {
        clearTimeout(timeout);
    }

    timeout = setTimeout(() => {
        checkUserNameDuplicate();
    }, 300);
};

const checkUserNameDuplicate = async () => {
    if (!newUserName.value) {
        error.value = '닉네임을 입력해주세요.';
        isDuplicateChecked.value = false;
        return;
    }

    try {
        const response = await axios.get(`http://localhost:8097/todo/api/user/check-username/${newUserName.value}`);
        if (response.data) {
            error.value = '';
            isDuplicateChecked.value = true;
        } else {
            error.value = '사용할 수 없는 닉네임입니다.';
            isDuplicateChecked.value = false;
        }
    } catch (err) {
        if (err.response?.status === 409) {
            error.value = '사용할 수 없는 닉네임입니다.';
            isDuplicateChecked.value = false;
        } else {
            error.value = '닉네임 중복 확인 중 오류가 발생했습니다.';
            isDuplicateChecked.value = false;
        }
    }
};

const changeUserName = async () => {
    if (!newUserName.value) {
        error.value = '닉네임을 입력해주세요.';
        return;
    }

    await checkUserNameDuplicate();

    if (!isDuplicateChecked.value) {
        error.value = '닉네임 중복 확인이 필요합니다.';
        return;
    }

    try {
        const userSeq = authStore.user.userSeq;
        const userId = authStore.user.userId;
        const accessToken = sessionStorage.getItem('accessToken');
        loading.value = true;

        await axios.put(`http://localhost:8097/todo/api/user/${userSeq}`, {
            userSeq: userSeq,
            userName: newUserName.value,
            userId: userId,
        }, {
            headers: {
                'Authorization': `Bearer ${accessToken}`
            }
        });

        loading.value = false;
        emit('update:userName', newUserName.value);
        emit('close');
        window.location.reload();
    } catch (err) {
        error.value = '닉네임 변경에 실패했습니다.';
        loading.value = false;
    }
};

onMounted(async () => {
    await Promise.resolve();
    inputRef.value?.focus();
});
</script>