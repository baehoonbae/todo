<!-- Bottom Navigation -->
<template>
  <!-- Bottom Navigation -->
  <div class="fixed bottom-0 left-0 right-0 bg-white">
    <div class="flex justify-center mb-2 gap-[95px] py-3 max-w-3xl mx-auto">
      <RouterLink to="/" :class="['flex flex-col items-center']" @click="isMyPage = false">
        <svg xmlns="http://www.w3.org/2000/svg" class="w-6 h-6" viewBox="0 0 24 24" fill="none"
          :stroke="!isMyPage ? 'black' : '#9CA3AF'">
          <path d="M3 9l9-7 9 7v11a2 2 0 01-2 2H5a2 2 0 01-2-2z" stroke-width="2" />
        </svg>
        <span class="text-xs mt-1" :class="!isMyPage ? 'text-black' : 'text-gray-400'">Home</span>
      </RouterLink>
      <RouterLink to="/my" :class="['flex flex-col items-center']" @click="isMyPage = true">
        <svg xmlns="http://www.w3.org/2000/svg" class="w-6 h-6" viewBox="0 0 24 24" fill="none"
          :stroke="isMyPage ? 'black' : '#9CA3AF'">
          <circle cx="12" cy="7" r="4" stroke-width="2" />
          <path d="M20 21v-2a4 4 0 00-4-4H8a4 4 0 00-4 4v2" stroke-width="2" />
        </svg>
        <span class="text-xs mt-1" :class="isMyPage ? 'text-black' : 'text-gray-400'">My</span>
      </RouterLink>
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();
const isMyPage = ref(route.path === '/my');

watch(() => route.path, (newPath) => {
  isMyPage.value = newPath === '/my';
});
</script>
