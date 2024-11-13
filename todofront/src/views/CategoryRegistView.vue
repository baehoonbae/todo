<template>
  <div class="max-w-[950px] mx-auto p-4">
    <!-- Header -->
    <header class="flex items-center justify-between px-4 py-3">
      <button class="p-2" @click="router.go(-1)">
        <ChevronLeftIcon class="h-6 w-6 text-gray-600" />
      </button>
      <h1 class="font-bold pl-1">카테고리 등록</h1>
      <button class="font-bold text-gray-600 px-4" @click="handleAddCategory" :class="{ 'opacity-50': !isValid }">
        완료
      </button>
    </header>

    <!-- 메인 컨텐츠 -->
    <main class="flex-1 flex justify-center items-start">
      <div class="w-[950px] p-4 mx-auto">
        <div class="space-y-6">
          <!-- 카테고리 입력 -->
          <div class="space-y-2">
            <input type="text" v-model="category.title" class="w-full p-2 rounded-md border-b-[3px] focus:outline-none"
              :style="{ borderBottomColor: category.color, color: category.color }" placeholder="카테고리 입력" />
            <p v-if="error" class="text-sm text-red-500">{{ error }}</p>
          </div>

          <!-- 공개 설정 세팅 -->
          <div class="space-y-4">
            <div class="space-y-2 pb-2 border-b-[1px]">
              <div class="flex items-center justify-between relative">
                <div class="flex items-center space-x-2">
                  <span class="text-sm text-gray-700">공개설정</span>
                </div>
                <button @click="toggleDropdown" class="flex items-center space-x-2">
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
                <div v-if="showDropdown" class="absolute right-0 top-8 w-32 bg-white border rounded-md shadow-lg z-10">
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
              <div class="flex items-center justify-between relative" @click="toggleColorPicker">
                <span class="text-sm text-gray-700">색상</span>
                <input type="color" v-model="category.color"
                  class="w-8 h-8 rounded-full overflow-hidden [&::-webkit-color-swatch-wrapper]:p-0 [&::-webkit-color-swatch]:border-none [&::-webkit-color-swatch]:rounded-full left-0"
                  style="direction: ltr;" />
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';
import { ref, computed, watch } from 'vue';
import { ChevronLeftIcon, ChevronDownIcon, GlobeAltIcon, LockClosedIcon } from '@heroicons/vue/24/outline';
import { useCategoryStore } from '@/stores/category';
import { useAuthStore } from '@/stores/auth';

const authStore = useAuthStore();
const error = ref('');
const userSeq = authStore.user.userSeq;
const showDropdown = ref(false);
const showColorPicker = ref(false);
const categoryStore = useCategoryStore();
const router = useRouter();
const category = ref({
  userSeq: userSeq,
  color: '#000000',
  title: '',
  isPublic: true,
});

const isValid = computed(() => {
  if (!category.value.title.trim()) {
    error.value = '카테고리 이름을 입력해주세요';
    return false;
  }
  if (category.value.title.trim().length < 1) {
    error.value = '카테고리 이름은 1글자 이상 입력해주세요';
    return false;
  }
  error.value = '';
  return true;
});

const toggleDropdown = () => {
  showDropdown.value = !showDropdown.value;
};

const selectVisibility = (isPublic) => {
  category.value.isPublic = isPublic;
  showDropdown.value = false;
};

const toggleColorPicker = () => {
  showColorPicker.value = !showColorPicker.value;
};

const handleAddCategory = async () => {
    if (!isValid.value) return;
    
    try {
        await categoryStore.fetchAddCategory(category.value);
    } catch (err) {
        error.value = err.message;
    }
};

</script>