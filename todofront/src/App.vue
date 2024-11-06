<template>
  <header v-if="!hideHeaderFooter">
    <Header />
  </header>
  <main>
    <RouterView/>
  </main>
  <footer v-if="!hideHeaderFooter">
    <Footer />
  </footer>
</template>

<script setup>
import { RouterView, useRoute, } from "vue-router";
import {computed}from 'vue';
import Footer from "./components/Footer.vue";
import Header from "./components/Header.vue";

const route = useRoute()
const hideHeaderFooter = computed(() => route.meta.hideHeaderFooter)
</script>