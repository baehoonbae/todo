<template>
  <div class="container mx-auto px-4 max-w-3xl">

    <div class="flex justify-center gap-20">
      <!-- Left Calendar Section -->
      <div class="w-72">
        <!-- User Profile -->
        <div class="flex items-center gap-2 mb-6">
          <div class="w-10 h-10 bg-gray-200 rounded-full"></div>
          <span class="text-sm">me</span>
          <div class="ml-auto">
            <div class="w-6 h-6 text-blue-400 flex items-center justify-center">
              <svg xmlns="http://www.w3.org/2000/svg" class="w-5 h-5" viewBox="0 0 24 24" fill="none" stroke="currentColor">
                <circle cx="12" cy="12" r="10" stroke-width="2"/>
                <path d="M12 8v8M8 12h8" stroke-width="2"/>
              </svg>
            </div>
          </div>
        </div>

        <!-- Date and Stats -->
        <div class="flex items-center gap-4 mb-6">
          <span class="text-sm font-medium">2024년 11월</span>
          <div class="flex items-center gap-2">
            <span class="text-sm">👋 0</span>
            <span class="text-sm">❤️ 0</span>
          </div>
        </div>

        <!-- Calendar Navigation -->
        <div class="flex justify-between items-center mb-4">
          <div class="flex gap-4">
            <button class="text-gray-400">&lt;</button>
            <button class="text-gray-400">&gt;</button>
          </div>
        </div>

        <!-- Calendar -->
        <div class="mb-8">
          <!-- Weekdays -->
          <div class="grid grid-cols-7 mb-2">
            <div class="text-center text-xs">월</div>
            <div class="text-center text-xs">화</div>
            <div class="text-center text-xs">수</div>
            <div class="text-center text-xs">목</div>
            <div class="text-center text-xs">금</div>
            <div class="text-center text-xs text-blue-500">토</div>
            <div class="text-center text-xs text-red-500">일</div>
          </div>

          <!-- Days -->
          <div class="grid grid-cols-7 gap-1">
            <template v-for="i in 30" :key="i">
              <div
                class="aspect-square w-8 h-8 flex items-center justify-center rounded-full text-sm cursor-pointer"
                :class="{
                  'bg-gray-100': i < 5,
                  'bg-black text-white': i === 5,
                  'hover:bg-gray-50': i > 5
                }"
              >
                {{ i }}
              </div>
            </template>
          </div>
        </div>
      </div>

      <!-- Right Categories Section -->
      <div class="w-48">
        <div class="space-y-2">
          <div v-for="(category, index) in ['카테고리 1', '카테고리 2', '카테고리 3', 'dfgdfgdfg']" 
               :key="index"
               class="flex items-center justify-between bg-gray-50 rounded-lg py-2 px-3 cursor-pointer hover:bg-gray-100">
            <div class="flex items-center gap-2">
              <span class="text-gray-400">🔒</span>
              <span class="text-sm text-gray-600">{{ category }}</span>
            </div>
            <span class="text-gray-400">+</span>
          </div>
        </div>
      </div>
    </div>

    
  </div>
</template>

<script setup>
// 필요한 경우 여기에 추가 로직을 구현할 수 있습니다
</script>