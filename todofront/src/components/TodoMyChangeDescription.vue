<template>
    <div class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
        <div class="bg-white rounded-2xl p-6 w-[90%] max-w-[400px]" @keydown.esc="$emit('close')"
            @keydown.enter="changeDescription">
            <!-- 입력 폼 -->
            <div class="mb-6">
                <input v-model="newDescription" type="text" placeholder="새로운 자기소개를 입력하세요"
                    class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:border-black"
                    :class="{ 'border-red-500': error }" />
                <p v-if="error" class="mt-2 text-sm text-red-500">{{ error }}</p>
            </div>
            <!-- 버튼 -->
            <div class="flex justify-center gap-3">
                <button @click="$emit('close')" class="w-full px-4 py-2 text-gray-600 hover:bg-gray-100 rounded-lg">
                    취소
                </button>
                <button @click="changeDescription"
                    class="w-full px-4 py-2 bg-black font-semibold text-white rounded-lg hover:bg-gray-800"
                    :disabled="!isValid">
                    {{ loading ? '변경 중...' : '변경하기' }}
                </button>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, computed, watch } from 'vue';
import axios from 'axios';
const newDescription = ref('');
const error = ref('');
const loading = ref(false);

watch(newDescription, (newValue) => {
    if (newValue.length > 25) {
        error.value = '자기소개는 25자를 초과할 수 없습니다.';
    } else {
        error.value = '';
    }
});

const isValid = computed(() => {
    return newDescription.value &&
        !loading.value &&
        !error.value &&
        newDescription.value.length <= 25;
});

const changeDescription = async () => {
    if (!newDescription.value) {
        error.value = '자기소개를 입력해주세요.';
        return;
    }

    if (newDescription.value.length > 25) {
        error.value = '자기소개는 25자를 초과할 수 없습니다.';
        return;
    }

    try {
        const userSeq = sessionStorage.getItem('userSeq');
        const accessToken = sessionStorage.getItem('accessToken');
        loading.value = true;

        await axios.put(`http://localhost:8097/todo/api/user/${userSeq}`, {
            userSeq: props.user.userSeq,
            description: newDescription.value,
            userId: props.user.userId,
        }, {
            headers: {
                'Authorization': `Bearer ${accessToken}`
            }
        });

        loading.value = false;
        emit('update:description', newDescription.value);
        emit('close');
        window.location.reload();
    } catch (err) {
        error.value = '자기소개 변경에 실패했습니다.';
        loading.value = false;
    }
};

const emit = defineEmits(['close', 'update:description']);
const props = defineProps(['user']);
</script>