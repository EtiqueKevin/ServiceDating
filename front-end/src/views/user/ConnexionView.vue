<script setup>
import { ref } from 'vue'
import SigninForm from '@/components/forms/SigninForm.vue';
import SignupForm from '@/components/forms/SignupForm.vue';

const connexion = ref(true);
</script>

<template>
    <main class="main-container">
        <SigninForm v-if="connexion" @switch-form="connexion = false" />
        <SignupForm v-else @switch-form="connexion = true" />
    </main>
</template>

<style>
.main-container {
    display: flex;
    flex-grow: 1;
    justify-content: center;
    align-items: flex-start;
    margin-top: 20px;
    padding-left: 16px;
    padding-right: 16px;
}

@media (min-width: 640px) {
    .main-container {
        padding-left: 24px;
        padding-right: 24px;
    }
}

@media (min-width: 1024px) {
    .main-container {
        padding-left: 32px;
        padding-right: 32px;
    }
}
</style>