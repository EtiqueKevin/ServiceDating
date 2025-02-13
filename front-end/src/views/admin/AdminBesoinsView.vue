<script setup>
import { useAdmin } from '@/services/admin';
import { computed, onMounted, ref } from 'vue';

const { getAllBesoins } = useAdmin();
const besoins = ref(null);

const isLoading = computed(() => besoins.value === null);

onMounted(async () => {
    try{
        besoins.value = await getAllBesoins();
    } catch (error) {
        console.error(error);
    }
});

</script>

<template>
    <div class="besoins-list">
        <h1>Liste des Besoins</h1>
    </div>

    <div v-if="isLoading">
        <p>Chargement...</p>
    </div>

    <div v-else>
        <div v-if="besoins.length === 0">
            <p>Aucun besoin trouvé</p>
        </div>
        <div v-else v-for="besoin in besoins" :key="besoin">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">{{ besoin.title }}</h5>
                    <p class="card-text">{{ besoin.description }}</p>
                    <span class="badge bg-primary">{{ besoin.status }}</span>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
</style>