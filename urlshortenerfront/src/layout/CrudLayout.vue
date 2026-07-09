<template>
    <div class="p-4 w-full mx-auto flex flex-column gap-4">
        <!-- Header Action Bar -->
        <header class="flex justify-content-between flex-wrap gap-4">
            <div>
                <h1 class="text-2xl font-semibold text-color m-0 align-items-center justify-content-center">{{ title }}
                </h1>
                <p v-if="subtitle" class="text-sm text-gray-600 mt-1 m-0">{{ subtitle }}</p>
            </div>
            <div class="flex gap-2  align-items-center justify-content-center">
                <slot name="actions"></slot>
            </div>
        </header>
        <main class="w-full  align-items-center">
            <slot></slot>
        </main>
    </div>
</template>
<script setup lang="ts">
defineProps<{
    title: string;
    subtitle?: string;
}>();

</script>
<style scoped></style>