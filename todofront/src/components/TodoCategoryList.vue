<template>
  <div class="w-36 ml-20">
    <div class="space-y-5">
      <div v-for="category in categories" :key="category.id"
        class="flex items-center justify-between bg-gray-100 rounded-[18px] py-2 px-3 cursor-pointer hover:bg-gray-200"
        @click="$emit('select-category', category)">
        <div class="flex items-center gap-2">
          <span class="text-gray-400" >{{ category.isPublic===1 ? '🌐' : '🔒' }}</span>
          <span class="text-xs font-bold" :style="{ color: category.color }">{{ category.title }}</span>
        </div>
        <span class="text-gray-400">+</span>
      </div>
    </div>
  </div>
</template>

<script setup>
defineProps({
  categories: {
    type: Array,
    required: true
  }
});

defineEmits(['select-category']);
</script>
