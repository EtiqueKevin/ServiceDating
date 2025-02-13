<script setup>
import { ref, onMounted, computed } from 'vue';
import InputField from '@/components/forms/inputs/InputField.vue';
import CompetenceForm from '@/components/forms/CompetenceForm.vue';
import { useAdmin } from '@/services/admin';
import { useToast } from 'vue-toastification';

const props = defineProps({
    salarie: {
        type: Object,
        default: null
    }
});

const { getAllCompetences, createSalarie } = useAdmin();
const emit = defineEmits(['success', 'close']);

const toast = useToast();
const availableCompetences = ref([]);
const selectedCompetenceId = ref('');

const formData = ref({
    email: '',
    mdp: '',
    name: '',
    surname: '',
    phone: '',
    competences: []
});

const unselectedCompetences = computed(() => {
    return availableCompetences.value.filter(comp => 
        !formData.value.competences.some(selected => selected.id === comp.id)
    );
});

const showCompetenceForm = ref(false);

const handleCompetenceSuccess = async () => {
    showCompetenceForm.value = false;
    // Refresh the competences list
    try {
        availableCompetences.value = await getAllCompetences();
    } catch (error) {
        console.error('Error reloading competences:', error);
        toast.error('Erreur lors du rechargement des compétences');
    }
};

const addCompetence = () => {
    if (!selectedCompetenceId.value) return;
    
    formData.value.competences.push({
        id: selectedCompetenceId.value,
        interet: 5
    });
    selectedCompetenceId.value = '';
};

const removeCompetence = (competenceId) => {
    formData.value.competences = formData.value.competences.filter(c => c.id !== competenceId);
};

const updateInteret = (competenceId, value) => {
    const competence = formData.value.competences.find(c => c.id === competenceId);
    if (competence) {
        competence.interet = parseInt(value);
    }
};

const getCompetenceName = (id) => {
    return availableCompetences.value.find(c => c.id === id)?.nom || 'Unknown';
};

const handleSubmit = async () => {
    if (formData.value.competences.length === 0) {
        toast.warning('Veuillez sélectionner au moins une compétence');
        return;
    }
    
    let success = await createSalarie(formData.value);
    if (!success) {
        toast.error('Erreur lors de la création du salarié');
        return;
    }

    emit('success');
};

onMounted(async () => {
    try {
        availableCompetences.value = await getAllCompetences();
        if (props.salarie) {
            formData.value = { ...props.salarie };
        }
    } catch (error) {
        console.error('Error loading competences:', error);
    }
});
</script>

<template>
    <div class="form-container">
        <div class="form-header">
            <h2>{{ salarie ? 'Modifier' : 'Ajouter' }} un salarié</h2>
            <button class="close-button" @click="$emit('close')" title="Fermer">×</button>
        </div>
        
        <form @submit.prevent="handleSubmit" class="salarie-form">
            <InputField
                v-model="formData.email"
                type="email"
                id="email"
                placeholder="Email"
                required
                autocomplete="email"
            />

            <InputField
                v-model="formData.mdp"
                type="password"
                id="mdp"
                placeholder="Mot de passe"
                :required="!salarie"
                autocomplete="new-password"
            />

            <InputField
                v-model="formData.name"
                id="name"
                placeholder="Nom"
                required
            />

            <InputField
                v-model="formData.surname"
                id="surname"
                placeholder="Prénom"
                required
            />

            <InputField
                v-model="formData.phone"
                type="text"
                id="phone"
                placeholder="Téléphone"
                required
            />

            <div class="competences-section">
                <div class="competence-header">
                    <select 
                        v-model="selectedCompetenceId"
                        class="competence-select"
                    >
                        <option value="">Sélectionner une compétence</option>
                        <option 
                            v-for="comp in unselectedCompetences"
                            :key="comp.id"
                            :value="comp.id"
                        >
                            {{ comp.nom }}
                        </option>
                    </select>
                    <button 
                        type="button" 
                        class="add-competence-button"
                        @click="addCompetence"
                        :disabled="!selectedCompetenceId"
                    >
                        Ajouter
                    </button>
                    <button 
                        type="button" 
                        class="create-competence-button"
                        @click="showCompetenceForm = true"
                    >
                        Nouvelle compétence
                    </button>
                </div>

                <div v-if="formData.competences.length === 0" class="no-competences">
                    Aucune compétence sélectionnée
                </div>

                <div v-else class="selected-competences">
                    <div 
                        v-for="comp in formData.competences" 
                        :key="comp.id"
                        class="competence-item"
                    >
                        <span class="competence-name">{{ getCompetenceName(comp.id) }}</span>
                        <div class="competence-controls">
                            <input 
                                type="range" 
                                min="0" 
                                max="10" 
                                :value="comp.interet"
                                @input="e => updateInteret(comp.id, e.target.value)"
                                class="interest-slider"
                            />
                            <span class="interest-value">{{ comp.interet }}/10</span>
                            <button 
                                type="button"
                                class="remove-competence-button"
                                @click="removeCompetence(comp.id)"
                                title="Supprimer"
                            >
                                ×
                            </button>
                        </div>
                    </div>
                </div>
            </div>

            <div class="form-actions">
                <button type="button" class="cancel-button" @click="$emit('close')">
                    Annuler
                </button>
                <button type="submit" class="submit-button">
                    {{ salarie ? 'Modifier' : 'Créer' }}
                </button>
            </div>
        </form>
        <div v-if="showCompetenceForm" class="modal-overlay">
            <CompetenceForm
                @success="handleCompetenceSuccess"
                @close="showCompetenceForm = false"
            />
        </div>
    </div>
</template>

<style scoped>
.form-container {
    background-color: white;
    border-radius: 8px;
    max-width: 600px;
    width: 100%;
}

.form-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 1rem;
    border-bottom: 1px solid #eee;
}

.form-header h2 {
    margin: 0;
    color: #2c3e50;
}

.close-button {
    background: none;
    border: none;
    font-size: 1.5rem;
    cursor: pointer;
    color: #666;
    padding: 0;
    line-height: 1;
}

.close-button:hover {
    color: #333;
}

.salarie-form {
    padding: 1rem;
    display: flex;
    flex-direction: column;
    gap: 1rem;
}

.competences-section {
    display: flex;
    flex-direction: column;
    gap: 1rem;
    padding: 1rem;
    background-color: #f8f9fa;
    border-radius: 4px;
}

.competence-header {
    display: flex;
    gap: 0.5rem;
}

.competence-select {
    flex: 1;
    padding: 0.5rem;
    border: 1px solid #ddd;
    border-radius: 4px;
    font-size: 0.875rem;
}

.add-competence-button {
    padding: 0.5rem 1rem;
    background-color: #2ecc71;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}

.add-competence-button:disabled {
    background-color: #95a5a6;
    cursor: not-allowed;
}

.no-competences {
    text-align: center;
    color: #666;
    padding: 1rem;
    background-color: white;
    border: 1px dashed #ddd;
    border-radius: 4px;
}

.selected-competences {
    display: flex;
    flex-direction: column;
    gap: 0.5rem;
}

.competence-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0.75rem;
    background-color: white;
    border-radius: 4px;
    box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);
}

.competence-name {
    font-weight: 500;
    color: #2c3e50;
}

.competence-controls {
    display: flex;
    align-items: center;
    gap: 1rem;
}

.interest-slider {
    width: 120px;
    accent-color: #3498db;
}

.interest-value {
    min-width: 3rem;
    text-align: center;
    font-weight: 500;
}

.remove-competence-button {
    background: none;
    border: none;
    color: #e74c3c;
    cursor: pointer;
    font-size: 1.2rem;
    padding: 0;
    line-height: 1;
}

.remove-competence-button:hover {
    color: #c0392b;
}

.form-actions {
    display: flex;
    justify-content: flex-end;
    gap: 1rem;
    margin-top: 0.5rem;
}

.cancel-button, .submit-button {
    padding: 0.5rem 1rem;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 1rem;
    transition: background-color 0.2s;
}

.cancel-button {
    background-color: #95a5a6;
    color: white;
}

.cancel-button:hover {
    background-color: #7f8c8d;
}

.submit-button {
    background-color: #3498db;
    color: white;
}

.submit-button:hover {
    background-color: #2980b9;
}
.create-competence-button {
    padding: 0.5rem 1rem;
    background-color: #9b59b6;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}

.create-competence-button:hover {
    background-color: #8e44ad;
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
</style>