<script setup>
import { onMounted, ref } from 'vue';
import { useAdmin } from '@/services/admin';

const { getAffectations, getClients, getAllSalaries, getAllCompetences } = useAdmin();
const selectedMethod = ref('');
const result = ref(null);
const loading = ref(false);
const competences = ref([]);

const getCompetenceDetails = (skillId) => {
    const comp = competences.value.find(c => c.id === skillId);
    return comp ? `${comp.nom} - ${comp.description}` : skillId;
};

const methods = [
    { value: '', label: 'Par défaut' },
    { value: 'glouton', label: 'Glouton' },
    { value: 'random', label: 'Aléatoire' },
    { value: 'evolution', label: 'Evolution' }
];

const handleSubmit = async () => {
    loading.value = true;
    try {
        const clients = await getClients();
        const salaries = await getAllSalaries();

        // Transformation des données clients
        const transformedClients = clients.map(client => ({
            name: `${client.user.name}_${client.user.surname}`,
            besoins: [{
                client: `${client.user.name}_${client.user.surname}`,
                skills: client.competences.map(comp => comp.id)
            }]
        }));

        // Transformation des données salariés
        const transformedSalaries = salaries.map(salarie => ({
            name: salarie.name,
            competences: salarie.competences.reduce((acc, comp) => {
                acc[comp.id] = comp.interet;
                return acc;
            }, {})
        }));

        // Création de l'objet de données final
        const data = {
            clients: transformedClients,
            salaries: transformedSalaries
        };

        competences.value = await getAllCompetences();
        result.value = await getAffectations(data, selectedMethod.value || '');
    } catch (error) {
        console.error('Erreur lors de la récupération des affectations:', error);
    } finally {
        loading.value = false;
    }
};
</script>

<template>
    <div class="affections-container">
        <h1>Affectations</h1>
        
        <form @submit.prevent="handleSubmit" class="affections-form">
            <div class="form-group">
                <label for="method">Sélectionner la méthode d'algorithme :</label>
                <select 
                    id="method"
                    v-model="selectedMethod"
                    class="method-select"
                >
                    <option 
                        v-for="method in methods" 
                        :key="method.value" 
                        :value="method.value"
                    >
                        {{ method.label }}
                    </option>
                </select>
            </div>

            <button 
                type="submit" 
                class="submit-button"
                :disabled="loading"
            >
                {{ loading ? 'Traitement en cours...' : 'Générer les affectations' }}
            </button>
        </form>

        <div v-if="result" class="results">
            <h2>Résultats :</h2>
            <div class="assignments">
                <h3>Affectations :</h3>
                <div v-for="(assignment, salarie) in result.assignations" 
                     :key="salarie" 
                     class="assignment-card">
                    <h4>{{ salarie }}</h4>
                    <div class="assignment-details">
                        <p><strong>Client :</strong> {{ assignment.besoin.client }}</p>
                        <div class="skills">
                            <strong>Compétences :</strong>
                            <ul>
                                <li v-for="skill in assignment.besoin.skills" 
                                    :key="skill">
                                    {{ getCompetenceDetails(skill) }}
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
/* Les styles restent inchangés car ils ne contiennent pas de texte à traduire */
.affections-container {
    padding: 20px;
    max-width: 800px;
    margin: 0 auto;
}

.affections-form {
    margin-top: 20px;
    padding: 20px;
    border: 1px solid #ddd;
    border-radius: 8px;
}

.form-group {
    margin-bottom: 20px;
}

label {
    display: block;
    margin-bottom: 8px;
    font-weight: bold;
}

.method-select {
    width: 100%;
    padding: 8px;
    border: 1px solid #ddd;
    border-radius: 4px;
    font-size: 16px;
}

.submit-button {
    background-color: #4CAF50;
    color: white;
    padding: 10px 20px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 16px;
}

.submit-button:disabled {
    background-color: #cccccc;
    cursor: not-allowed;
}

.submit-button:hover:not(:disabled) {
    background-color: #45a049;
}

.results {
    margin-top: 20px;
    padding: 20px;
    border: 1px solid #ddd;
    border-radius: 8px;
}

pre {
    white-space: pre-wrap;
    word-wrap: break-word;
}

.score {
    font-size: 1.2em;
    margin-bottom: 20px;
    padding: 10px;
    background-color: #f5f5f5;
    border-radius: 4px;
}

.assignments {
    display: grid;
    gap: 20px;
}

.assignment-card {
    border: 1px solid #ddd;
    border-radius: 8px;
    padding: 15px;
    background-color: #fff;
    box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.assignment-card h4 {
    margin: 0 0 10px 0;
    color: #2c3e50;
    border-bottom: 1px solid #eee;
    padding-bottom: 5px;
}

.assignment-details {
    margin-left: 10px;
}

.skills ul {
    margin: 5px 0;
    padding-left: 20px;
}

.skills li {
    margin: 3px 0;
}
</style>