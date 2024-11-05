<template>
  <header>
    <Header @toggleCalendar="toggleCalendar"></Header>
  </header>
  <main>
    <HomeView />
    <Transition name="calendar">
      <BigCalendar v-if="isCalendarOpen" @close="closeCalendar" />
    </Transition>
  </main>

  <footer>
    <Footer></Footer>
  </footer>
</template>

<script setup>
import { RouterLink, RouterView } from "vue-router";
import { ref } from "vue";
import HomeView from "./views/HomeView.vue";
import Header from "./components/Header.vue";
import Footer from "./components/Footer.vue";
import BigCalendar from "./components/BigCalendar.vue";

const isCalendarOpen = ref(false);
const toggleCalendar = () => {
  isCalendarOpen.value = !isCalendarOpen.value;
};

const closeCalendar = () => {
  isCalendarOpen.value = false;
};
</script>

<style scoped>
.calendar-enter-active,
.calendar-leave-active {
  transition: opacity 0.3s ease;
}

.calendar-enter-from,
.calendar-leave-to {
  opacity: 0;
}
</style>
