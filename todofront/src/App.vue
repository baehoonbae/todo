<template>
  <header v-if="!hideHeader">
    <Header />
  </header>
  <main>
    <RouterView :user="user" />
  </main>
  <footer v-if="!hideFooter">
    <Footer />
  </footer>
</template>

<script setup>
import { RouterView, useRoute, } from "vue-router";
import { ref, computed, onMounted, watch } from 'vue';
import Footer from "./components/Footer.vue";
import Header from "./components/Header.vue";
import axios from "axios";
const route = useRoute()

const user = ref({});

const hideHeader = computed(() => route.meta.hideHeader)
const hideFooter = computed(() => route.meta.hideFooter)

// 유저 정보 가져오기
const fetchUserInfo = async () => {
  const userSeq = sessionStorage.getItem('userSeq');
  const accessToken = sessionStorage.getItem('accessToken');

  if (!accessToken || !userSeq) {
    return;
  }

  try {
    const response = await axios.get(`http://localhost:8097/todo/api/user/${userSeq}`, {
      headers: {
        Authorization: `Bearer ${accessToken}`
      }
    });

    if (response.data) {
      user.value = response.data;
    } else {
      console.log('유저 정보 조회 실패:', response.data);
    }
  } catch (error) {
    console.error('유저 정보 조회 실패:', error.response?.data || error.message);
    if (error.response?.status === 401) {
      console.error('인증 토큰이 만료되었거나 유효하지 않습니다');
    }
  }
};

// accessToken 변경 감지
watch(() => sessionStorage.getItem('accessToken'), (newToken) => {
  if (newToken) {
    fetchUserInfo();
  }
});

// 컴포넌트 마운트 시 유저 정보 요청
onMounted(() => {
  fetchUserInfo();
});
</script>