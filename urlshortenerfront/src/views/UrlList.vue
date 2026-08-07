<template>
    <Toast></Toast>
    <CrudLayout title="URL Management" subtitle="Manage your shortened URLs">
        <!-- Action Bar -->
        <template v-slot:actions>
            <Button class="btn btn-primary" v-on:click="$emit('handleCreate')">Create New URL Shortener</Button>
        </template>
        <!-- URL List Table -->
        <Card>
            <template #content>
                <DataTable :value="urls" :loading="loading" paginator :rows="10" dataKey="id">
                    <Column field="id" header="ID" sortable></Column>
                    <Column field="originalUrl" header="original URL" sortable></Column>
                    <Column field="hashedCode" header="Hashed Code" sortable></Column>
                    <Column field="websiteShortened" header="Website">
                        <template #body="slotProps">
                            <a target="_blank" :href="slotProps.data.websiteShortened">{{
                                slotProps.data.websiteShortened }}</a>
                        </template>
                    </Column>
                    <Column field="createdDate" header="Created At" sortable></Column>
                    <Column class="w-32">
                        <template #body="slotProps">
                            <Button icon="pi pi-copy" v-on:click="copyToClipboard(slotProps.data.websiteShortened)"
                                severity="secondary" rounded></Button>
                        </template>
                    </Column>
                          <Column class="w-32">
                        <template #body="slotProps">
                            <Button icon="pi pi-trash" v-on:click="deleteUrl(slotProps.data.id)"
                                severity="secondary" rounded></Button>
                        </template>
                    </Column>
                </DataTable>
            </template>
        </Card>

    </CrudLayout>
</template>
<script setup lang="ts">
import CrudLayout from '@/layout/CrudLayout.vue';
import DataTable from 'primevue/datatable';
import Column from 'primevue/column';
import Button from 'primevue/button';
import Card from 'primevue/card';
import { ref, onMounted } from 'vue';
import { URLService } from '../services/URLService';
import type { UrlShortener } from '../type/url';
import { useToast } from 'primevue/usetoast';
import { Toast } from 'primevue';
const emit = defineEmits(['handleCreate']);
const urls = ref<UrlShortener[]>([]);
const loading = ref(false);

const loadUrls = async () => {
    try {
        loading.value = true;
        const response = await URLService.getAllUrls();
        urls.value = response.map((el) => ({
            ...el,
            "websiteShortened": "http://localhost:8082/" + el.hashedCode
        }));
        console.log(urls);
    } catch (error) {
        console.log('Error fetching URLs:', error);
    } finally {
        loading.value = false;
    }
}

const deleteUrl = async (id: number) => {
    try {
        loading.value = true;
        await URLService.deleteUrl(id);
        urls.value = urls.value.filter(url => url.id !== id);
        alert('URL deleted successfully!');
    } catch (error) {
        console.log('Error deleting URL:', error);
    } finally {
        loading.value = false;
    }
}

const toast = useToast();

function copyToClipboard(text: string) {
    navigator.clipboard.writeText(text)
        .then(() => {
            toast.add({ severity: 'info', summary: "Copied to clipboard", detail: "Copied", life: 1000, });
        })
        .catch(err => console.error("Failed to copy text: ", err));
}
onMounted(() => {
    loadUrls();
});
</script>