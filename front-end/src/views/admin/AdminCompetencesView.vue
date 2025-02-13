<script setup>
import { useAdmin } from '@/services/admin';
import { computed, onMounted, ref } from 'vue';

const { getAllCompetences } = useAdmin();
const competences = ref(null);

const isLoading = computed(() => competences.value === null);

onMounted(async () => {
    try{
        competences.value = await getAllCompetences();
    } catch (error) {
        console.error(error);
    }
});

</script>

<template>
    <div class="competences-list">
        <h1>Liste des Compétences</h1>
    </div>

    <div v-if="isLoading">
        <p>Chargement...</p>
    </div>

    <div v-else>
        <div v-if="competences.length === 0">
            <p>Aucun compétences trouvé</p>
        </div>
        <div v-else v-for="competence in competences" :key="competence">
            <div class="card">
                <h1>Competence</h1>
            </div>
        </div>
    </div>
</template>

<style scoped>
</style>