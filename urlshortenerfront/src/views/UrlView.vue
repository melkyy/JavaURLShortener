<template>
    <UrlList v-if="!isCreating" @handleCreate="handleCreate"></UrlList>
    <UrlForm v-else @onSuccess="handleFormSuccess"></UrlForm>
</template>

<script setup lang="ts">
import UrlForm from './UrlForm.vue';
import UrlList from './UrlList.vue';
import { ref } from 'vue';
const isCreating = ref(false);

const handleCreate = () => {
    isCreating.value = true;
}
const handleFormSuccess = () => {
    isCreating.value = false;
}
</script>
<style scoped></style>