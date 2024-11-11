<template>
  <div class="flex flex-col min-h-screen bg-white">
    <!-- Header -->
    <header class="flex items-center justify-center px-4 py-3">
      <div class="w-full max-w-[950px] flex items-center justify-between">
        <!-- 왼쪽: 뒤로가기 버튼 -->
        <div class="flex items-center">
          <button class="text-gray-600" @click="goBack">
            <ChevronLeftIcon class="h-6 w-6" />
          </button>
        </div>
        <!-- 가운데: 제목 -->
        <h1 class="font-bold absolute left-1/2 transform -translate-x-1/2">카테고리 등록</h1>
        <!-- 오른쪽: 완료 버튼 -->
        <button class="font-bold text-gray-600 px-4" @click="addCategory">완료</button>
      </div>
    </header>

    <!-- 메인 컨텐츠 -->
    <main class="flex-1 flex justify-center items-start">
      <div class="w-[950px] p-4 mx-auto">
        <div class="space-y-6">
          <!-- 카테고리 입력 -->
          <div class="space-y-2">
            <input type="text" v-model="category.title"
              class="w-full p-2 rounded-md border-b-[3px] focus:outline-none" 
              :style="{ borderBottomColor: selectedColor, color: selectedColor }"
              placeholder="카테고리 입력" />
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
              <div class="flex items-center justify-between relative">
                <span class="text-sm text-gray-700">색상</span>
                <input type="color" v-model="selectedColor" @change="selectColor"
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
import axios from 'axios';
import { useRouter } from 'vue-router';
import { ref, watch } from 'vue';
import { ChevronLeftIcon, ChevronDownIcon, GlobeAltIcon, LockClosedIcon } from '@heroicons/vue/24/outline';

const accessToken = sessionStorage.getItem('accessToken');
const userSeq = sessionStorage.getItem('userSeq');
const showDropdown = ref(false);
const showColorPicker = ref(false);

const category = ref({
  userSeq: userSeq,
  color: '',
  title: '',
  isPublic: true,
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

const selectColor = () => {
  category.value.color = selectedColor.value;
  showColorPicker.value = false;
};

const addCategory = async () => {
  try {
    const response = await axios.post(
      'http://localhost:8097/todo/api/category/add',
      category.value,
      {
        headers: {
          Authorization: `Bearer ${accessToken}`
        }
      }
    );

    if (response.data) {
      alert('카테고리가 성공적으로 추가되었습니다.');
      router.push('/');
    }
  } catch (error) {
    console.error('카테고리 추가 실패:', error.response?.data || error.message);
    if (error.response?.status === 401) {
      alert('인증 토큰이 만료되었거나 유효하지 않습니다.');
    } else {
      alert('카테고리 추가에 실패했습니다.');
    }
  }
};

const router = useRouter();

const goBack = () => {
  router.go(-1);
};

const selectedColor = ref('#000000');

// selectedColor가 변경될 때마다 category.color 업데이트
watch(selectedColor, (newColor) => {
  category.value.color = newColor;
});

</script>