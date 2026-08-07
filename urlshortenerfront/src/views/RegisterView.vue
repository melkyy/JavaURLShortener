<template>
    <div class="login-container">
        <Card class="login-card">
            <template #title>
                <h2 class="text-center m-0">Sign Up</h2>
            </template>
            <template #subtitle>
                <p class="text-center m-0 text-muted">Please sign up to continue</p>
            </template>
            <template #content>
                <form @submit.prevent="handleRegister" class="flex flex-column gap-4">

                    <div class="flex flex-column gap-2">
                        <label for="username" class="font-semibold">Username</label>
                        <IconField>
                            <InputIcon class="pi pi-user" />
                            <InputText id="username" v-model="form.username" placeholder="Enter your username"
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
                        <label for="confirmPassword" class="font-semibold">Confirm Password</label>
                        <IconField>
                            <InputIcon class="pi pi-lock" />
                            <Password id="confirmPassword" v-model="form.confirmPassword"
                                placeholder="Confirm your password" :feedback="false" toggleMask
                                :class="{ 'p-invalid': errors.confirmPassword }" fluid />
                        </IconField>
                        <small v-if="errors.confirmPassword" class="p-error">{{ errors.confirmPassword }}</small>
                    </div>
                    <div class="flex flex-column gap-2">
                        <a href="/login" class="text-sm text-blue-500 hover:underline">Already have an account? Login</a>
                    </div>
                    <Button type="submit" label="Sign Up" icon="pi pi-user-plus" :loading="loading"
                        class="w-full mt-2" />
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
import { UserService } from '@/services/UsersService';
// Component state
const loading = ref(false);
const form = reactive({
    username: '',
    password: '',
    confirmPassword: ''
});

const errors = reactive({
    username: '',
    password: '',
    confirmPassword: ''
});

// Mock registration submission validation and handler
const validateForm = () => {
    let isValid = true;
    errors.username = '';
    errors.password = '';

    if (!form.username.trim()) {
        errors.username = 'Username is required.';
        isValid = false;
    }
    if (form.password !== form.confirmPassword) {
        errors.password = 'Passwords do not match.';
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

const handleRegister = async () => {
    if (!validateForm()) return;

    loading.value = true;

    try {
        // Simulate API Call
        const result = await UserService.SignUp(form.username, form.password);
        if (result.success) {
            console.log('Form Submitted successfully:', form);
            alert('Registration successful! (Check console for payload)');
            window.location.href = '/'; // Redirect to login page after successful registration
        }else {
            console.error('Registration failed:', result.message);
            alert(`Registration failed: ${result.message}`);
        }

    } catch (error) {
        console.error('Registration failed:', error);
    } finally {
        loading.value = false;
    }
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