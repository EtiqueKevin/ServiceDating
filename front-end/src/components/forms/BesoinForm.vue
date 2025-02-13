<script setup>
import {computed, ref} from 'vue';
import InputField from "@/components/forms/inputs/InputField.vue";
import {useBesoin} from "@/services/besoin.js";

const nom = ref('');
const libelle = ref('');
const competence = ref([]);

const formData = ref({
  nom: '',
  libelle: '',
  option_id: null,
});

const formValid = computed(() => {
  return formData.value.nom && formData.value.libelle && formData.value.option_id;
});

const {loadCompetence, postBesoin} = useBesoin();

const loadCompetences = async () => {
  competence.value = await loadCompetence();
};

const handleSubmit = async () => {
  const res = await postBesoin(formData.value)
}

loadCompetences();
</script>

<template>
  <div class="form-container">
    <h1 class="title">Création d'un besoin</h1>
    <form @submit.prevent="handleSubmit" class="form">
      <InputField
          v-model="formData.nom"
          type="text"
          id="text"
          required
          autocomplete="text"
          placeholder="Entrez le nom de votre entreprise"
          class="input-field"
      />
      <InputField
          v-model="formData.libelle"
          type="text"
          id="text"
          required
          autocomplete="text"
          placeholder="Libellé du besoin"
          class="input-field"
      />
      <select id="dropdown" v-model="formData.option_id" required>
        <option v-for="option in competence" :key="option.id" :value="option.id">
          {{ option.name }}
        </option>
      </select>
      <button
          type="submit"
          class="submit-button"
          :disabled="!formValid"
          :class="{
          'disabled': !formValid,
          'enabled': formValid
        }"
          title="Creer un besoin"
      >
        Créer
      </button>
    </form>
  </div>
</template>

<style scoped>
.form-container {
  background-color: var(--background-color);
  padding: 2rem;
  border-radius: 1rem;
  box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.25);
  width: 100%;
  max-width: 28rem;
  margin: 0 auto;
  transform: translateY(0);
  transition: all 0.3s;
}


.title {
  font-size: 1.875rem;
  font-weight: bold;
  text-align: center;
  margin-bottom: 2rem;
  color: var(--text-color);
}

.form {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.input-field {
  transition: all 0.3s;
}

.input-field:hover,
.input-field:focus-within {
  box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1);
}

.submit-button {
  width: 100%;
  padding: 0.75rem 1.5rem;
  border-radius: 0.5rem;
  font-weight: 600;
  color: white;
  transition: all 0.3s;
}

.submit-button.enabled {
  background-color: var(--primary-color);
}

.submit-button.enabled:hover {
  background-color: var(--secondary-color);
  box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1);
  transform: scale(1.02);
}

.submit-button.enabled:active {
  transform: scale(0.98);
}

.submit-button.disabled {
  background-color: #9ca3af;
  cursor: not-allowed;
  opacity: 0.6;
}

.foot-button {
  display: block;
  width: 100%;
  text-align: center;
  margin-top: 1.5rem;
  font-size: 0.875rem;
  font-weight: 500;
  color: var(--primary-color);
  transition: color 0.3s;
  background: none;
  border: none;
}

.foot-button:hover {
  color: var(--accent-color);
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(-20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.form-container {
  animation: fadeIn 0.5s ease-out;
}
</style>