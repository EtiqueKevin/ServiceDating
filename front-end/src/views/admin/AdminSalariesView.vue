<script setup>
import { useAdmin } from '@/services/admin';
import { computed, onMounted, ref } from 'vue';

const { getAllSalaries } = useAdmin();
const salaries = ref(null);

const isLoading = computed(() => salaries.value === null);

onMounted(async () => {
    try{
        salaries.value = await getAllSalaries();
    } catch (error) {
        console.error(error);
    }
});

</script>

<template>
    <div class="salarie-list">
        <h1>Liste des salariés</h1>
    </div>

    <div v-if="isLoading">
        <p>Chargement...</p>
    </div>

    <div v-else>
        <div v-if="salaries.length === 0">
            <p>Aucun Salarié trouvé</p>
        </div>
        <div v-else v-for="salarie in salaries" :key="salarie">
            <div class="card">
                <h1>salarie</h1>
            </div>
        </div>
    </div>
</template>

<style scoped>
</style>