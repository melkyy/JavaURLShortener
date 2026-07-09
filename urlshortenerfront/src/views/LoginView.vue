<template>
    <div class="login-container">
        <Card class="login-card">
            <template #title>
                <h2 class="text-center m-0">Welcome Back</h2>
            </template>
            <template #subtitle>
                <p class="text-center m-0 text-muted">Please sign in to continue</p>
            </template>
            <template #content>
                <form @submit.prevent="handleLogin" class="flex flex-column gap-4">

                    <div class="flex flex-column gap-2">
                        <label for="username" class="font-semibold">Username or Email</label>
                        <IconField>
                            <InputIcon class="pi pi-user" />
                            <InputText id="username" v-model="form.username" placeholder="Enter your email"
                                :class="{ 'p-invalid': errors.username }" fluid />
                        </IconField>
                        <small v-if="errors.username" class="p-error">{{ errors.username }}</small>
                    </div>

                    <div class="flex flex-column gap-2">
                        <label for="password" class="font-semibold">Password</label>
                        <IconField>
                            <InputIcon class="pi pi-lock" />
                            <Password id="password" v-model="form.password" placeholder="Enter your password"
                                :feedback="false" toggleMask :class="{ 'p-invalid': errors.password }" fluid />
                        </IconField>
                        <small v-if="errors.password" class="p-error">{{ errors.password }}</small>
                    </div>
                    <div class="flex flex-column gap-2">
                        <a href="/register" class="text-sm text-blue-500 hover:underline">Don't have an account? Sign
                            Up</a>
                    </div>
                    <Button type="submit" label="Sign In" icon="pi pi-sign-in" :loading="loading" class="w-full mt-2" />

                </form>
            </template>
        </Card>
    </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue';
import Card from 'primevue/card';
import InputText from 'primevue/inputtext';
import Password from 'primevue/password';
import Button from 'primevue/button';
import IconField from 'primevue/iconfield';
import InputIcon from 'primevue/inputicon';
import { URLService } from '@/services/URLService';
import { UserService } from '@/services/UsersService';

// Component state
const loading = ref(false);
const form = reactive({
    username: '',
    password: ''
});

const errors = reactive({
    username: '',
    password: ''
});

// Mock login submission validation and handler
const validateForm = () => {
    let isValid = true;
    errors.username = '';
    errors.password = '';

    if (!form.username.trim()) {
        errors.username = 'Username or Email is required.';
        isValid = false;
    }
    if (!form.password) {
        errors.password = 'Password is required.';
        isValid = false;
    } else if (form.password.length < 6) {
        errors.password = 'Password must be at least 6 characters.';
        isValid = false;
    }

    return isValid;
};

const handleLogin = async () => {
    if (!validateForm()) return;

    loading.value = true;

    try {
        // Simulate API Call
        const result = await UserService.Login(form.username, form.password);
        if(!result.success) {
            alert('Login failed: '+ result.message);
            return;
        }
        console.log('Login result:', result);
        console.log('Form Submitted successfully:', form);
        alert('Login successful! (Check console for payload)');
    } catch (error) {
        console.error('Login failed:', error);
    } finally {
        loading.value = false;
    }

    window.location.href = '/urls';
};
</script>

<style scoped>
.login-container {
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 100vh;
    background-color: var(--surface-ground);
    padding: 1rem;
}

.login-card {
    width: 100%;
    max-width: 400px;
    padding: 1.5rem;
    border-radius: 12px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
}

.text-center {
    text-align: center;
}

.text-muted {
    color: var(--text-color-secondary);
}

.p-error {
    color: var(--p-red-500);
    font-size: 0.875rem;
}

/* Flex utilities fallback if PrimeFlex is not configured */
.flex {
    display: flex;
}

.flex-column {
    flex-direction: column;
}

.gap-2 {
    gap: 0.5rem;
}

.gap-4 {
    gap: 1rem;
}

.w-full {
    width: 100%;
}

.m-0 {
    margin: 0;
}

.mt-2 {
    margin-top: 0.5rem;
}

.font-semibold {
    font-weight: 600;
}
</style>