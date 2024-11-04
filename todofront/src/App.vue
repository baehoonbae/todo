<script setup>
import { RouterLink, RouterView } from "vue-router";
import { ref } from "vue";

const isCalenderOnly = ref(true);
const toggleLink = () => {
  isCalenderOnly.value = !isCalenderOnly.value;
};
</script>

<template>
  <header>
    <div class="wrapper">
      <nav>
        <RouterLink to="/">Home</RouterLink>
        <butten @click="toggleLink">
          <RouterLink to="isCalenderOnly?'/onlyCalender':'/calenderAndCategory'">
            {{ isCalenderOnly ? "달력만 표시" : "달력+카테고리" }}
          </RouterLink>
        </butten>
        <RouterLink to="/login">로그인</RouterLink>
        <RouterLink to="/join">회원가입</RouterLink>
      </nav>
    </div>
  </header>

  <RouterView />

  <footer>
    
  </footer>
</template>

<style scoped></style>
