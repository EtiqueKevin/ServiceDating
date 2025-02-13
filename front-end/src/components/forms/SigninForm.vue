<script setup>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useToast } from 'vue-toastification'
import { useUserStore } from '@/stores/user';
import InputField from '@/components/forms/inputs/InputField.vue'
import PasswordInputField from '@/components/forms/inputs/PasswordInputField.vue';

const router = useRouter();
const userStore = useUserStore();
const toast = useToast();

const email = ref('');
const password = ref('');

const formValid = computed(() => {
    return password.value && email.value
})

const handleSubmit = async () => {
    if (!formValid.value) return
    const success = await userStore.signIn(email.value, password.value);
    if (success) {
        router.push({ name: 'home' });
    }
};
</script>


<template>
    <div class="form-container">
        <div class="logo-container">
            <img src="@/assets/logo.png" alt="Logo" class="logo" />
        </div>
        <h1 class="title">Connexion</h1>
        <form @submit.prevent="handleSubmit" class="form">
            <InputField
                v-model="email"
                type="email"
                id="email"
                required
                autocomplete="email"
                placeholder="Email"
            />
            <PasswordInputField
                v-model="password"
                id="password"
                required
                autocomplete="current-password"
                placeholder="Mot de passe"
            />
            <button 
                type="submit" 
                class="submit-button" 
                :disabled="!formValid"
                :class="{ 
                    'disabled': !formValid,
                    'enabled': formValid
                }"
                title="Se connecter"
            >  
                Se connecter
            </button>
        </form>
        <button @click="$emit('switch-form')" class="foot-button" title="S'inscrire">
            Pas encore de compte ? S'inscrire
        </button>
    </div>
</template>

<style scoped>
.form-container {
    background-color: var(--background-color);
    padding: 2rem;
    border-radius: 1rem;
    box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.25);
    width: 100%;
    max-width: 28rem;
    margin: 0 auto;
    transform: translateY(0);
    transition: all 0.3s;
}

.logo-container {
    display: flex;
    justify-content: center;
    margin-bottom: 1.5rem;
}

.logo {
    width: 6rem;
    height: auto;
    object-fit: contain;
}

.title {
    font-size: 1.875rem;
    font-weight: bold;
    text-align: center;
    margin-bottom: 2rem;
    color: var(--text-color);
}

.form {
    display: flex;
    flex-direction: column;
    gap: 1.5rem;
}

.submit-button {
    width: 100%;
    padding: 0.75rem 1.5rem;
    border-radius: 0.5rem;
    font-weight: 600;
    color: white;
    transition: all 0.3s;
}

.submit-button.enabled {
    background-color: var(--primary-color);
}

.submit-button.enabled:hover {
    background-color: var(--secondary-color);
    box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1);
    transform: scale(1.02);
}

.submit-button.enabled:active {
    transform: scale(0.98);
}

.submit-button.disabled {
    background-color: #9ca3af;
    cursor: not-allowed;
    opacity: 0.6;
}

.foot-button {
    display: block;
    width: 100%;
    text-align: center;
    margin-top: 1.5rem;
    font-size: 0.875rem;
    font-weight: 500;
    color: var(--primary-color);
    transition: color 0.3s;
    background: none;
    border: none;
}

.foot-button:hover {
    color: var(--accent-color);
}

@keyframes fadeIn {
    from { 
        opacity: 0; 
        transform: translateY(-20px); 
    }
    to { 
        opacity: 1; 
        transform: translateY(0); 
    }
}

.form-container {
    animation: fadeIn 0.5s ease-out;
}
</style>