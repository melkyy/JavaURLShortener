<script setup lang="ts">
import { ref, computed } from 'vue';
import RegisterView from './views/RegisterView.vue';
import LoginView from './views/LoginView.vue';
import UrlView from './views/UrlView.vue';
import NotFound from './views/NotFoundView.vue';
import { Button } from 'primevue';

const routes: { [key: string]: any } = {
  "/": LoginView,
  "/urls": UrlView,
  "/register": RegisterView
}
const currentPath: { value?: string } | any = ref(window.location.pathname || "/");

window.addEventListener('popstate', () => {
  currentPath.value = window.location.pathname;
});

const currentView = computed(() => {
  console.log(window.location.pathname);
  return routes[window.location.pathname] || NotFound

});

const logout = () => {
  localStorage.clear();
  currentPath.value = "/";
  window.location.href = "/";
}

</script>

<template>
  <div class="w-full flex gap-2  align-items-end justify-content-end" v-if="currentPath !== '/' && currentPath !== '/register'"> <Button
      icon="pi pi-sign-out" v-on:click="logout" severity="secondary" rounded></Button>
  </div>
  <component :is="currentView" />
</template>

<style scoped></style>
