<template>
  <div class="flex flex-col min-h-screen bg-white">
    <!-- Header -->
    <header class="flex items-center justify-center px-4 py-3">
      <div class="w-full max-w-[950px] flex items-center justify-between">
        <!-- 왼쪽: 뒤로가기 버튼 -->
        <div class="flex items-center">
          <button class="text-gray-600" @click="goBack">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              class="h-6 w-6"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M15 19l-7-7 7-7"
              />
            </svg>
          </button>
        </div>
        <!-- 가운데: 제목 -->
        <h1
          class="font-bold absolute left-1/2 transform -translate-x-1/2"
        >
          카테고리 등록
        </h1>
        <!-- 오른쪽: 완료 버튼 -->
        <button class="font-bold text-gray-600 px-4" @click="addCategory">완료</button>
      </div>
    </header>

    <!-- Main Content -->
    <main class="flex-1 flex justify-center items-start bg-white">
      <div class="w-[950px] p-4 mx-auto">
        <div class="space-y-6">
          <!-- Category Input -->
          <div class="space-y-2">
            <input
              type="text"
              v-model="categoryName"
              class="w-full p-2 rounded-md border-b-[5px] focus:outline-none caret-blue-600"
              placeholder="카테고리 입력"
            />
          </div>
          <!-- Public/Private Setting -->
          <div class="space-y-2 pb-2 border-b-[1px]">
            <div class="flex items-center justify-between">
              <div class="flex items-center space-x-2">
                <span class="text-sm text-gray-700">공개설정</span>
                <button class="p-1 rounded-full bg-gray-100"></button>
              </div>
              <button class="flex items-center space-x-2">
                <span class="text-sm text-gray-600">나만보기</span>
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  class="h-5 w-5 text-gray-400"
                  viewBox="0 0 20 20"
                  fill="currentColor"
                >
                  <path
                    fill-rule="evenodd"
                    d="M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z"
                    clip-rule="evenodd"
                  />
                </svg>
              </button>
            </div>
          </div>
          <!-- Color Selection -->
          <div class="flex items-center justify-between py-2 border-b-[1px]">
            <label class="text-sm text-gray-700">색상</label>
            <button class="flex items-center space-x-2">
              <div class="w-5 h-5 bg-black rounded-full"></div>
              <svg
                xmlns="http://www.w3.org/2000/svg"
                class="h-5 w-5 text-gray-400"
                viewBox="0 0 20 20"
                fill="currentColor"
              >
                <path
                  fill-rule="evenodd"
                  d="M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z"
                  clip-rule="evenodd"
                />
              </svg>
            </button>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup>
import axios from 'axios';
import {ref} from 'vue';

const addCategoryUrl='http://localhost:8097/api/category/add'
const category= ref({});
const addCategory= async()=>{
  await axios
  .get(addCategoryUrl)
  .then(({data})=>{
    console.log(data);
  })
  .catch((e)=>{
    console.log(e);
    alert('카테고리 추가 실패');
  })
};

const router = useRouter();

const goBack = () => {
    router.go(-1);
};

</script>