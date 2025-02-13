<script setup>
import {computed, onMounted, ref} from "vue";
import {useBesoin} from "@/services/besoin.js";

const besoins = ref([]);
const filterStatus = ref('all');
const {getBesoins} = useBesoin();

const isLoading = computed(() => besoins.value === null);

const filteredBesoins = computed(() => {
  if (!besoins.value) return [];
  if (filterStatus.value === 'all') return besoins.value;
  return besoins.value.filter(b => b.status === parseInt(filterStatus.value));
});

const formatDate = (dateString) => {
  return new Date(dateString).toLocaleDateString('fr-FR');
};

const getStatusLabel = (status) => {
  switch (status) {
    case 0:
      return 'En attente';
    case 1:
      return 'Terminé';
    default:
      return 'Inconnu';
  }
};

onMounted(async () => {
  try {
    besoins.value = await getBesoins();
  } catch (error) {
    console.error(error);
  }
});

</script>

<template>
  <div class="besoins-container">
    <h1>Liste des Besoins</h1>

    <div class="filters" v-if="!isLoading">
      <select v-model="filterStatus">
        <option value="all">Tous les statuts</option>
        <option value="0">En attente</option>
        <option value="1">Terminé</option>
      </select>
    </div>

    <div v-if="isLoading" class="loading">
      <div class="spinner"></div>
      <span>Chargement...</span>
    </div>

    <div v-else-if="filteredBesoins.length === 0" class="empty-state">
      Aucun besoin trouvé
    </div>

    <div v-else class="besoins-grid">
      <div v-for="besoin in filteredBesoins" :key="besoin.id" class="card">
        <div class="card-header">
          <h2>{{ besoin.competence.nom }}</h2>
        </div>
        <div class="card-body">
          <div class="client-info">
            <h3>Client</h3>
            <p class="client-name">
              {{ besoin.client.surname }} {{ besoin.client.name }}
            </p>
            <p class="client-phone">
              <i class="bi bi-telephone"></i> {{ besoin.client.phone }}
            </p>
          </div>
          <div class="besoin-details">
            <h3>Détails</h3>
            <p>{{ besoin.description }}</p>
            <p class="date">
              Demande faite le {{ formatDate(besoin.date_demande) }}
            </p>
          </div>
        </div>
        <div class="card-footer">
          <span :class="['status-badge', `status-${besoin.status}`]">
            {{ getStatusLabel(besoin.status) }}
          </span>
          <button class="edit-button">Modifier</button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.besoins-container {
  width: 80%;
  margin: 0 auto;
  height: 100%;
  overflow: auto;
}

h1 {
  text-align: center;
  margin-bottom: 2rem;
  color: #333;
}

.filters {
  margin-bottom: 2rem;
  text-align: right;
}

.filters select {
  padding: 0.5rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 1rem;
}

.loading {
  text-align: center;
  padding: 2rem;
}

.spinner {
  border: 4px solid #f3f3f3;
  border-top: 4px solid #3498db;
  border-radius: 50%;
  width: 40px;
  height: 40px;
  animation: spin 1s linear infinite;
  margin: 0 auto;
}

@keyframes spin {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}

.empty-state {
  text-align: center;
  padding: 2rem;
  background: #f8f9fa;
  border-radius: 8px;
}

.besoins-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 1.5rem;
}

.card {
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s;
}

.card:hover {
  transform: translateY(-2px);
}

.card-header {
  padding: 1rem;
  border-bottom: 1px solid #eee;
}

.card-header h2 {
  margin: 0;
  font-size: 1.2rem;
  color: #2c3e50;
}

.card-body {
  padding: 1rem;
}

.client-info, .besoin-details {
  margin-bottom: 1rem;
}

h3 {
  font-size: 1rem;
  color: #666;
  margin-bottom: 0.5rem;
}

.client-name {
  font-weight: 600;
  margin-bottom: 0.25rem;
}

.client-phone {
  color: #666;
}

.date {
  font-size: 0.9rem;
  color: #666;
  margin-top: 0.5rem;
}

.card-footer {
  padding: 1rem;
  border-top: 1px solid #eee;
}

.status-badge {
  padding: 0.25rem 0.75rem;
  border-radius: 1rem;
  font-size: 0.875rem;
  font-weight: 500;
}

.status-0 {
  background: #ffeeba;
  color: #856404;
}

.status-1 {
  background: #d1ecf1;
  color: #0c5460;
}

.status-2 {
  background: #d4edda;
  color: #155724;
}

.edit-button {
  margin: 0;
  padding: 0.5rem 1rem;
  background-color: #3498db;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.2s;
}


</style>