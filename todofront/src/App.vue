<template>
  <header v-if="!hideHeader">
    <Header />
  </header>
  <main>
    <RouterView />
  </main>
  <footer v-if="!hideFooter">
    <Footer />
  </footer>
</template>

<style>
</style>

<script setup>
import { RouterView, useRoute } from "vue-router";
import { computed, onMounted, watch } from "vue";
import Footer from "./components/Footer.vue";
import Header from "./components/Header.vue";
import { useAuthStore } from "./stores/auth";

const route = useRoute();
const authStore = useAuthStore();

const hideHeader = computed(() => route.meta.hideHeader);
const hideFooter = computed(() => route.meta.hideFooter);

// accessToken 변경 감지
watch(
  () => sessionStorage.getItem("accessToken"),
  (newToken) => {
    if (newToken) {
      authStore.checkAuth(); // store의 checkAuth 메서드 호출
    }
  }
);

// 컴포넌트 마운트 시 인증 상태 확인
onMounted(async () => {
  await authStore.checkAuth();
});
</script>
