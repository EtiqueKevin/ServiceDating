<script setup>
import {computed, onMounted, ref, watch} from "vue";
import {useBesoin} from "@/services/besoin.js";
import BesoinForm from '@/components/forms/BesoinForm.vue';
import { useToast } from 'vue-toastification';

const toast = useToast();
const besoins = ref([]);
const filterStatus = ref('all');
const {getBesoins} = useBesoin();
const showEditModal = ref(false);
const selectedBesoin = ref(null);

const isLoading = computed(() => besoins.value === null);

// Pagination
const currentPage = ref(1);
const itemsPerPage = ref(5);
const totalPages = ref(1);

const filteredBesoins = computed(() => {
  if (!besoins.value) return [];
  if (filterStatus.value === 'all') return besoins.value;
  return besoins.value.filter(b => b.status === parseInt(filterStatus.value));
});


const fetchBesoins = async () => {
  isLoading.value = true;
  try {
    const { besoins: data, totalPages: pages, currentPage: page } = await getBesoins(currentPage.value, itemsPerPage.value);
    besoins.value = data;
    totalPages.value = pages;
    currentPage.value = page;
  } catch (error) {
    console.error(error);
  } finally {
    isLoading.value = false;
  }
};

const paginatedBesoins = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage.value;
  const end = start + itemsPerPage.value;
  return filteredBesoins.value.slice(start, end);
});

watch(filterStatus, fetchBesoins);
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

const handleEditClick = (besoin) => {
    selectedBesoin.value = besoin;
    showEditModal.value = true;
};

const handleSuccess = async () => {
    showEditModal.value = false;
    try {
        await fetchBesoins();
        toast.success('Besoin mis à jour avec succès');
    } catch (error) {
        console.error(error);
    }
};

const goToPage = (page) => {
  if (page >= 1 && page <= totalPages.value) {
    currentPage.value = page;
    fetchBesoins();
  }
};

onMounted(async () => {
  try {
    await fetchBesoins();
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
      <div v-for="besoin in paginatedBesoins" :key="besoin.id" class="card">
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
          <button class="edit-button" @click="handleEditClick(besoin)">
            <i class="fas fa-edit"></i>
            Modifier
          </button>
        </div>
      </div>
    </div>

    <!-- Modal -->
    <div v-if="showEditModal" class="modal-overlay">
      <div class="modal-content">
        <BesoinForm
          :besoin="selectedBesoin"
          @success="handleSuccess"
          @close="showEditModal = false"
        />
      </div>
    </div>

    <div class="pagination" v-if="totalPages > 1">
      <button @click="goToPage(currentPage - 1)" :disabled="currentPage === 1">Précédent</button>
      <span>Page {{ currentPage }} sur {{ totalPages }}</span>
      <button @click="goToPage(currentPage + 1)" :disabled="currentPage === totalPages">Suivant</button>
    </div>
  </div>
</template>

<style scoped>
.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 20px;
}

.pagination button {
  margin: 0;
  padding: 0.5rem 1rem;
  background-color: #3498db;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.2s;
}

.pagination button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.pagination span {
  margin: 0 10px;
  font-weight: bold;
}

.status-badge {
  padding: 5px 10px;
  border-radius: 5px;
}

.besoins-container {
  box-sizing: border-box;
  width: 100%;
  padding: 3rem;
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
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
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
  display: flex;
  justify-content: space-between;
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

.edit-button:hover {
  background-color: #217dbb;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  background-color: white;
  border-radius: 8px;
  max-width: 500px;
  width: 90%;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}
</style>