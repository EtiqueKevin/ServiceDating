<script setup>
import { onMounted, ref } from 'vue';
import { useAdmin } from '@/services/admin';

const { getAffectations, getClients, getAllSalaries } = useAdmin();
const selectedMethod = ref('');
const result = ref([]);
const loading = ref(false);

const methods = [
    { value: '', label: 'Default' },
    { value: 'glouton', label: 'Glouton' },
    { value: 'random', label: 'Random' }
];

const handleSubmit = async () => {
    loading.value = true;
    try {
        const clients = await getClients();
        const salaries = await getAllSalaries();

        // Transform clients data
        const transformedClients = clients.map(client => ({
            name: `${client.user.name}_${client.user.surname}`,
            besoins: [{
                client: `${client.user.name}_${client.user.surname}`,
                skills: client.competences.map(comp => comp.id)
            }]
        }));

        // Transform salaries data
        const transformedSalaries = salaries.map(salarie => ({
            name: salarie.name,
            competences: salarie.competences.reduce((acc, comp) => {
                acc[comp.id] = comp.interet;
                return acc;
            }, {})
        }));

        // Create the final data object
        const data = {
            clients: transformedClients,
            salaries: transformedSalaries
        };

        console.log(transformedSalaries);

        result.value = await getAffectations(data, selectedMethod.value || '');
        console.log('Affections:', result.value);
    } catch (error) {
        console.error('Error fetching affections:', error);
    } finally {
        loading.value = false;
    }
};

</script>

<template>
    <div class="affections-container">
        <h1>Affections</h1>
        
        <form @submit.prevent="handleSubmit" class="affections-form">
            <div class="form-group">
                <label for="method">Select Algorithm Method:</label>
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
                {{ loading ? 'Processing...' : 'Generate Affections' }}
            </button>
        </form>

        <!-- Display results if any -->
        <div v-if="result.length > 0" class="results">
            <h2>Results:</h2>
            <pre>{{ result }}</pre>
        </div>
    </div>
</template>

<style scoped>
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
</style>