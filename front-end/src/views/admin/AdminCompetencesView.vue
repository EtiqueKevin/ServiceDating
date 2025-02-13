<script setup>
import { useAdmin } from '@/services/admin';
import { computed, onMounted, ref } from 'vue';
import CompetenceForm from '@/components/forms/CompetenceForm.vue';
import { useToast } from 'vue-toastification';

const toast = useToast();
const { getAllCompetences, deleteCompetence } = useAdmin();
const competences = ref(null);
const selectedCompetence = ref(null);
const showEditModal = ref(false);

const isLoading = computed(() => competences.value === null);

const handleEditClick = (competence) => {
    selectedCompetence.value = competence;
    showEditModal.value = true;
};

const handleDeleteClick = async (competence) => {
    if (confirm(`Êtes-vous sûr de vouloir supprimer la compétence "${competence.nom}" ?`)) {
        try {
            await deleteCompetence(competence.id);
            competences.value = await getAllCompetences();
            toast.success('Compétence supprimée avec succès');
        } catch (error) {
            console.error(error);
        }
    }
};

const handleAddClick = () => {
    selectedCompetence.value = null;
    showEditModal.value = true;
};

const handleSuccess = async () => {
    showEditModal.value = false;
    try {
        competences.value = await getAllCompetences();
        toast.success('Compétence enregistrée avec succès');
    } catch (error) {
        console.error(error);
    }
};

onMounted(async () => {
    try {
        competences.value = await getAllCompetences();
    } catch (error) {
        console.error(error);
    }
});
</script>

<template>
    <div class="competences-container">
        <div class="header">
            <h1 class="page-title">Liste des Compétences</h1>
            <button @click="handleAddClick" class="add-button" title="Ajouter une compétence">
                <i class="fas fa-plus"></i>
                Ajouter une compétence
            </button>
        </div>

        <div v-if="isLoading" class="loading">
            <div class="spinner"></div>
            <span>Chargement...</span>
        </div>

        <div v-else>
            <div v-if="competences.length === 0" class="empty-state">
                <i class="fas fa-inbox fa-3x"></i>
                <p>Aucune compétence trouvée</p>
            </div>
            <div v-else class="competences-grid">
                <div v-for="competence in competences" 
         :key="competence.id" 
         class="competence-card">
        <div class="competence-header">
            <i class="fas fa-award"></i>
            <h3>{{ competence.nom }}</h3>
        </div>
        <h5>{{ competence.description }}</h5>
        <div class="button-group">
            <button 
                @click="handleEditClick(competence)"
                class="edit-button"
                title="Modifier"
            >
                <i class="fas fa-edit"></i>
                Modifier
            </button>
            <button 
                @click="handleDeleteClick(competence)"
                class="delete-button"
                title="Supprimer"
            >
                <i class="fas fa-trash"></i>
                Supprimer
            </button>
        </div>
    </div>
            </div>
        </div>

        <!-- Modal -->
        <div v-if="showEditModal" class="modal-overlay">
            <div class="modal-content">
                <CompetenceForm
                    :competence="selectedCompetence"
                    @success="handleSuccess"
                    @close="showEditModal = false"
                />
            </div>
        </div>
    </div>
</template>

<style scoped>
.header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 2rem;
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

.edit-button i {
    margin-right: 0.5rem;
}

.competence-header {
    display: flex;
    align-items: center;
    gap: 0.5rem;
    margin-bottom: 0.5rem;
}

.competence-header i {
    color: #3498db;
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

.competences-container {
    padding: 2rem;
    max-width: 1200px;
    margin: 0 auto;
}

.page-title {
    color: #2c3e50;
    margin-bottom: 2rem;
    font-size: 2rem;
}

.loading {
    text-align: center;
    padding: 2rem;
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 1rem;
}

h5{
    margin: 0;
}

.spinner {
    border: 4px solid #f3f3f3;
    border-top: 4px solid #3498db;
    border-radius: 50%;
    width: 40px;
    height: 40px;
    animation: spin 1s linear infinite;
}

@keyframes spin {
    0% { transform: rotate(0deg); }
    100% { transform: rotate(360deg); }
}

.competences-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
    gap: 1rem;
}

.competence-card {
    background: white;
    border-radius: 8px;
    padding: 1rem;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    transition: transform 0.2s ease;
}

.competence-card:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.competence-card h3 {
    margin: 0;
    color: #2c3e50;
    font-size: 1.1rem;
}

.button-group {
    display: flex;
    justify-content: space-between;
    gap: 0.5rem;
    margin-top: 1rem;
}

.delete-button {
    padding: 0.5rem 1rem;
    background-color: #e74c3c;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.2s;
}

.delete-button:hover {
    background-color: #c0392b;
}

.delete-button i {
    margin-right: 0.5rem;
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
    background-color: #2980b9;
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