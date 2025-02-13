<script setup>
import { ref, computed, watch } from 'vue'
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
const password2 = ref('');
const passwordsMatch = ref(true);

const formValid = computed(() => {
    return passwordsMatch.value && 
           password.value && 
           email.value
})

watch([password, password2], ([newPass, newPass2]) => {
    passwordsMatch.value = !newPass2 || newPass === newPass2
})

const handleSubmit = async () => {
    if (!formValid.value) return
    const userData = {
            email: email.value,
            mdp: password.value,
        };
        
        const success = await userStore.signUp(userData);
        if (success) {
            const redirectPath = router.currentRoute.value.redirectedFrom?.name || 'home';
            router.push({name : redirectPath});
        }
}
</script>

<template>
    <div class="form-container">
        <div class="logo-container">
            <img src="@/assets/logo.png" alt="Logo" class="logo" />
        </div>
        <h1 class="title">Inscription</h1>
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
                autocomplete="new-password"
                placeholder="Mot de passe"
            />
            <div class="password-group">
                <PasswordInputField
                    v-model="password2"
                    id="password2"
                    required
                    autocomplete="new-password"
                    placeholder="Confirmer le mot de passe"
                    :class="{ 
                        'invalid-input': !passwordsMatch && password2, 
                        'valid-input': passwordsMatch && password2 
                    }"
                />
                <div v-if="!passwordsMatch && password2" class="error-message">
                    Les mots de passe ne correspondent pas
                </div>
            </div>
            <button 
                type="submit" 
                class="submit-button" 
                :disabled="!formValid"
                :class="{ 
                    'disabled': !formValid,
                    'enabled': formValid
                }"
                title="S'inscrire"
            >    
                S'inscrire
            </button>
        </form>
        <button @click="$emit('switch-form')" class="foot-button" title="Se connecter">
            Déjà un compte ? Se connecter
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
    animation: fadeIn 0.5s ease-out;
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

.password-group {
    display: flex;
    flex-direction: column;
    gap: 0.5rem;
}

.invalid-input {
    border-color: #ef4444;
}

.invalid-input:focus {
    border-color: #ef4444;
    box-shadow: 0 0 0 2px rgba(239, 68, 68, 0.2);
}

.valid-input {
    border-color: #22c55e;
}

.valid-input:focus {
    border-color: #22c55e;
    box-shadow: 0 0 0 2px rgba(34, 197, 94, 0.2);
}

.error-message {
    color: #ef4444;
    font-size: 0.875rem;
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
</style>