<template>
    <Card class="shadow-md border border-surface rounded-xl overflow-hidden max-w-2xl mx-auto">
        <template #content>
            <!-- URL Creation Form -->
            <div class="mb-6">
                <h2 class="text-xl font-semibold text-color mb-2">Create a New Shortened URL</h2>
                <p class="text-sm text-gray-600">Enter the URL you want to shorten and click "Create Shortened URL".</p>
            </div>
            <div class="grid grid-cols-1 gap-6 mb-6">
                <div class="flex flex-col gap-2">
                    <label for="url" class="font-medium text-sm text-color">URL To shortener</label>
                    <InputText v-model="form.url" id="url" type="text" placeholder="Enter the URL to shorten"
                        :invalid="v$.url.$error" />
                    <small v-if="v$.url.$error" class="p-error">URL is required</small>
                </div>
            </div>
            <!-- Clean Action Footer Layout: Border handles the separation cleanly -->
            <div class="flex flex-col sm:flex-row justify-between items-center gap-4 pt-4 border-t border-surface">
                <Button label="Return to List" icon="pi pi-arrow-left" severity="secondary" text
                    class="w-full sm:w-auto order-2 sm:order-1" @click="$emit('onSuccess')" />
                <Button label="Generatea Short Link" icon="pi pi-magic-wand" severity="success" v-on:click="handleSave"
                    class="w-full sm:w-auto order-1 sm:order-2 px-5 font-semibold" />
            </div>
        </template>

    </Card>
</template>
<script setup lang="ts">
import Card from 'primevue/card';
import InputText from 'primevue/inputtext';
import Button from 'primevue/button';
import { ref, onMounted } from 'vue';
import { useVuelidate } from '@vuelidate/core';
import { URLService } from '../services/URLService';
import { Divider } from 'primevue';
//TODO user_id adding to the form
const emit = defineEmits(['onSuccess'])

const submitting = ref<boolean>(false);
const form = ref<Omit<{ url: string }, never>>({
    url: ''
});
const rules = {
    url: { required: true, },
}
const v$ = useVuelidate(rules, form);

const handleSave = async () => {
    v$.value.$touch();
    if (v$.value.$invalid) {
        return;
    }
    try {
        submitting.value = true;
        //TODO call the API to create a new shortened URL
        const url = form.value.url;
        const result: any = await URLService.createUrl(url);
        if (result.success) {
            //TODO show success message and redirect to the URL list page
            alert('Shortened URL created successfully!');
            emit('onSuccess');
        } else {
            //TODO show error message
            alert('Failed to create shortened URL: ' + result.message);
        }
    } catch (error) {
        console.log('Error creating shortened URL:', error);
    } finally {
        submitting.value = false;
    }
}

</script>
<style scoped></style>