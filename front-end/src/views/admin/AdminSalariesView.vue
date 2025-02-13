<script setup>
import { useAdmin } from '@/services/admin';
import { computed, onMounted, ref } from 'vue';
import SalarieForm from '@/components/forms/SalarieForm.vue';
import { useToast } from 'vue-toastification';

const toast = useToast();
const { getAllSalaries, createSalarie, getAllCompetences } = useAdmin();
const salaries = ref(null);
const selectedSalarie = ref(null);
const showEditModal = ref(false);
const isLoading = computed(() => salaries.value === null);
const competences = ref(null);

const handleAddClick = () => {
    selectedSalarie.value = null;
    showEditModal.value = true;
};

const handleSuccess = async () => {
    showEditModal.value = false;
    try {
        salaries.value = await getAllSalaries();
        toast.success('Salarié enregistré avec succès');
    } catch (error) {
        console.error(error);
        toast.error('Erreur lors de l\'enregistrement du salarié');
    }
};

const getCompetenceName = (competenceId) => {
  const competence = competences.value?.find(c => c.id === competenceId);
  return competence?.nom || 'Compétence inconnue';
};

const getCompetenceDescription = (competenceId) => {
  const competence = competences.value?.find(c => c.id === competenceId);
  return competence?.description || '';
};

onMounted(async () => {
    try {
        salaries.value = await getAllSalaries();
        competences.value = await getAllCompetences();
        console.log(salaries.value);
        console.log(competences.value);
    } catch (error) {
        console.error(error);
        toast.error('Erreur lors du chargement des salariés');
    }
});
</script>

<template>
    <div class="salaries-container">
        <div class="header">
            <h1 class="page-title">Liste des salariés</h1>
            <button @click="handleAddClick" class="add-button" title="Ajouter un salarié">
                <i class="fas fa-plus"></i>
                Ajouter un salarié
            </button>
        </div>

        <div v-if="isLoading" class="loading">
            <div class="spinner"></div>
            <span>Chargement...</span>
        </div>

        <div v-else>
            <div v-if="salaries.length === 0" class="empty-state">
                <i class="fas fa-inbox fa-3x"></i>
                <p>Aucun salarié trouvé</p>
            </div>
            <div v-else class="cards-container">
    <div v-for="salarie in salaries" :key="salarie.id" class="card">
      <div class="card-header">
        <i class="fas fa-user"></i>
        <h2>{{ salarie.surname }} {{ salarie.name }}</h2>
      </div>
      <p>Téléphone: {{ salarie.phone }}</p>
      
      <!-- New section for competences -->
      <div class="competences-section">
        <h3>Compétences:</h3>
        <div class="competences-list">
          <div v-for="comp in salarie.competences" :key="comp.id" class="competence-item">
            <div class="competence-header">
              <span class="competence-name">{{ getCompetenceName(comp.id) }}</span>
              <span class="competence-interest">Intérêt: {{ comp.interet }}/10</span>
            </div>
            <p class="competence-description">{{ getCompetenceDescription(comp.id) }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
        </div>

        <div v-if="showEditModal" class="modal-overlay">
            <div class="modal-content">
                <SalarieForm
                    :salarie="selectedSalarie"
                    @success="handleSuccess"
                    @close="showEditModal = false"
                />
            </div>
        </div>
    </div>
</template>

<style scoped>
.salaries-container {
    padding: 2rem;
    max-width: 1200px;
    margin: 0 auto;
}

.header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 2rem;
}

.page-title {
    color: #2c3e50;
    margin: 0;
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

.add-button {
    padding: 0.5rem 1rem;
    background-color: #2ecc71;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.2s;
    font-size: 1rem;
}

.add-button:hover {
    background-color: #27ae60;
}

.add-button i {
    margin-right: 0.5rem;
}

.cards-container {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
    gap: 20px;
    padding: 20px;
}

.card {
    background-color: #fff;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    padding: 20px;
    transition: transform 0.2s ease;
}

.card:hover {
    transform: translateY(-5px);
}

.card-header {
    display: flex;
    align-items: center;
    gap: 0.5rem;
    margin-bottom: 1rem;
}

.card-header i {
    color: #3498db;
    font-size: 1.2rem;
}

.card h2 {
    margin: 0;
    color: #2c3e50;
    font-size: 1.2rem;
}

.card p {
    margin: 0.5rem 0;
    color: #666;
}

.button-group {
    display: flex;
    justify-content: space-between;
    gap: 0.5rem;
    margin-top: 1rem;
}

.edit-button, .delete-button {
    padding: 0.5rem 1rem;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.2s;
    display: flex;
    align-items: center;
    gap: 0.5rem;
}

.edit-button {
    background-color: #3498db;
}

.edit-button:hover {
    background-color: #2980b9;
}

.delete-button {
    background-color: #e74c3c;
}

.delete-button:hover {
    background-color: #c0392b;
}

.empty-state {
    text-align: center;
    padding: 2rem;
    color: #666;
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 1rem;
}

.empty-state i {
    color: #95a5a6;
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
    max-height: 90vh;
    overflow-y: auto;
}


.competences-section {
  margin-top: 1rem;
  border-top: 1px solid #eee;
  padding-top: 1rem;
}

.competences-section h3 {
  color: #2c3e50;
  font-size: 1.1rem;
  margin-bottom: 0.5rem;
}

.competences-list {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.competence-item {
  background-color: #f8f9fa;
  padding: 0.5rem;
  border-radius: 4px;
}

.competence-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 0.25rem;
}

.competence-name {
  font-weight: bold;
  color: #2c3e50;
}

.competence-interest {
  color: #3498db;
  font-size: 0.9rem;
}

.competence-description {
  font-size: 0.9rem;
  color: #666;
  margin: 0;
}
</style>