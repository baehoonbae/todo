<!-- Bottom Navigation -->
<template>
  <!-- Bottom Navigation -->
  <div class="fixed bottom-0 left-0 right-0">
    <div class="flex justify-center mb-2 gap-[95px] py-3 max-w-3xl mx-auto">
      <div class="flex flex-col items-center">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          class="w-6 h-6"
          viewBox="0 0 24 24"
          fill="none"
          stroke="currentColor"
        >
          <path
            d="M3 9l9-7 9 7v11a2 2 0 01-2 2H5a2 2 0 01-2-2z"
            stroke-width="2"
          />
        </svg>
        <span class="text-xs mt-1">피드</span>
      </div>
      <div class="flex flex-col items-center text-gray-400">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          class="w-6 h-6"
          viewBox="0 0 24 24"
          fill="none"
          stroke="currentColor"
        >
          <circle cx="11" cy="11" r="8" stroke-width="2" />
          <path d="M21 21l-4.35-4.35" stroke-width="2" />
        </svg>
        <span class="text-xs mt-1">둘러보기</span>
      </div>
      <div class="flex flex-col items-center text-gray-400">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          class="w-6 h-6"
          viewBox="0 0 24 24"
          fill="none"
          stroke="currentColor"
        >
          <path
            d="M12 2L2 7l10 5 10-5-10-5zM2 17l10 5 10-5M2 12l10 5 10-5"
            stroke-width="2"
          />
        </svg>
        <span class="text-xs mt-1">알림</span>
      </div>
      <div class="flex flex-col items-center text-gray-400">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          class="w-6 h-6"
          viewBox="0 0 24 24"
          fill="none"
          stroke="currentColor"
        >
          <circle cx="12" cy="7" r="4" stroke-width="2" />
          <path d="M20 21v-2a4 4 0 00-4-4H8a4 4 0 00-4 4v2" stroke-width="2" />
        </svg>
        <span class="text-xs mt-1">My</span>
      </div>
    </div>
  </div>
</template>

<script setup></script>
