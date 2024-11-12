<template>
    <div class="max-w-[950px] mx-auto p-4" @click="closeDropdown">
        <!-- 헤더 -->
        <header class="flex items-center justify-between px-4 py-3">
            <button class="p-2" @click="router.go(-1)">
                <ChevronLeftIcon class="w-6 h-6 text-gray-600" />
            </button>
            <h1 class="font-bold pl-1">카테고리</h1>
            <button class="p-2 font-bold " @click="categoryStore.fetchCategoryUpdate">
                확인
            </button>
        </header>

        <!-- 메인 컨텐츠 -->
        <main class="flex-1 flex justify-center items-start">
            <div class="w-[950px] p-4 mx-auto">
                <div class="space-y-6">
                    <!-- 카테고리 입력 -->
                    <div class="space-y-2">
                        <input type="text" v-model="category.title"
                            class="w-full p-2 rounded-md border-b-[3px] focus:outline-none"
                            :style="{ borderBottomColor: category.color, color: category.color }"
                            :value="category.title" />
                    </div>

                    <!-- 공개 설정 세팅 -->
                    <div class="space-y-4">
                        <div class="space-y-2 pb-2 border-b-[1px]">
                            <div class="flex items-center justify-between relative">
                                <div class="flex items-center space-x-2">
                                    <span class="text-sm text-gray-700">공개설정</span>
                                </div>
                                <button @click.stop="toggleDropdown" class="flex items-center space-x-2">
                                    <span class="text-sm text-gray-600">
                                        <template v-if="category.isPublic">
                                            <GlobeAltIcon class="w-4 h-4 inline-block" />
                                            전체공개
                                        </template>
                                        <template v-else>
                                            <LockClosedIcon class="w-4 h-4 inline-block" />
                                            나만보기
                                        </template>
                                    </span>
                                    <ChevronDownIcon class="h-5 w-5 text-gray-400" />
                                </button>
                                <!-- 드롭다운 메뉴 -->
                                <div v-if="showDropdown" @click.stop
                                    class="absolute right-0 top-8 w-32 bg-white border rounded-md shadow-lg z-10">
                                    <div @click="selectVisibility(true)"
                                        class="px-4 py-2 hover:bg-gray-100 cursor-pointer text-sm whitespace-nowrap">
                                        <GlobeAltIcon class="w-4 h-4 inline-block" />
                                        전체공개
                                    </div>
                                    <div @click="selectVisibility(false)"
                                        class="px-4 py-2 hover:bg-gray-100 cursor-pointer text-sm whitespace-nowrap">
                                        <LockClosedIcon class="w-4 h-4 inline-block" />
                                        나만보기
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- 색상 선택 -->
                        <div class="pb-2 border-b-[1px]">
                            <div class="flex items-center justify-between relative">
                                <span class="text-sm text-gray-700">색상</span>
                                <input type="color" v-model="category.color"
                                    class="w-8 h-8 rounded-full overflow-hidden [&::-webkit-color-swatch-wrapper]:p-0 [&::-webkit-color-swatch]:border-none [&::-webkit-color-swatch]:rounded-full left-0"
                                    style="direction: ltr;" />
                            </div>
                        </div>

                        <!-- 삭제 버튼 -->
                        <div class="flex justify-center">
                            <button @click="showDeleteModal = true"
                                class="py-2 px-12 font-semibold rounded-md bg-[#f5f5f5] text-red-500 hover:bg-red-100">
                                삭제
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </main>

        <!-- 삭제 확인 모달 -->
        <Transition name="modal">
            <div v-if="showDeleteModal"
                class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
                <div class="bg-white rounded-lg p-6 w-80">
                    <p class="text-gray-600 mb-6 text-center">카테고리를 삭제하시겠습니까?</p>
                    <div class="flex justify-center gap-3">
                        <button @click="showDeleteModal = false"
                            class="w-full px-4 py-2 text-gray-600 hover:bg-gray-100 rounded-md">
                            취소
                        </button>
                        <button @click="categoryStore.fetchCategoryDelete"
                            class="w-full px-4 py-2 bg-red-500 text-white rounded-md hover:bg-red-600">
                            삭제
                        </button>
                    </div>
                </div>
            </div>
        </Transition>
    </div>
</template>

<script setup>
import { onMounted, ref, computed } from 'vue';
import { ChevronLeftIcon, ChevronDownIcon, GlobeAltIcon, LockClosedIcon } from '@heroicons/vue/24/outline';
import { useRouter, useRoute } from 'vue-router';
import { useCategoryStore } from '@/stores/category';

const router = useRouter();
const route = useRoute();
const showDropdown = ref(false);
const showDeleteModal = ref(false);
const categoryStore = useCategoryStore();
const id = route.params.id;
const category = computed(() => categoryStore.category);

const toggleDropdown = () => {
    showDropdown.value = !showDropdown.value;
};

const closeDropdown = () => {
    showDropdown.value = false;
};

const selectVisibility = (isPublic) => {
    category.value.isPublic = isPublic;
    showDropdown.value = false;
};

onMounted(async () => {
    await categoryStore.fetchCategory(id);
});
</script>

<style scoped>
.modal-enter-active,
.modal-leave-active {
    transition: opacity 0.3s ease;
}

.modal-enter-from,
.modal-leave-to {
    opacity: 0;
}
</style>