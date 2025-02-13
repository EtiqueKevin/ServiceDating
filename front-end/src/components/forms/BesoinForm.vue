<script setup>
import {computed, ref} from 'vue';
import InputField from "@/components/forms/inputs/InputField.vue";
import {useBesoin} from "@/services/besoin.js";

const nom = ref('');
const libelle = ref('');
let competences = ref([]);

const formData = ref({
  nom: '',
  libelle: '',
  option_id: null,
});

const formValid = computed(() => {
  return formData.value.nom && formData.value.libelle && formData.value.option_id;
});
const {loadCompetences, postBesoin} = useBesoin();

const handleSubmit = async () => {
  await postBesoin(formValid.value)
}

const loadData = async () => {
  competences = await loadCompetences();
}


loadData();
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
          placeholder="Nom du client ou de l'entreprise"
          class="input-field"
      />
      <Textarea
          v-model="formData.libelle"
          id="text"
          required
          autocomplete="off"
          placeholder="Libellé du besoin"
          class="textArea"
      />

      <select class="competence" id="dropdown">
        <option selected disabled hidden>Compétences</option>
        <option v-for="option in competences" :key="option.id" :value="option.id">
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
          title="Créer un besoin"
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
  max-width: 40rem;
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


.textArea {
  display: block;
  margin-top: 0.25rem;
  padding: 0.5rem 0.75rem;
  border: 1px solid var(--text-color);
  border-radius: 0.375rem;
  height: 10rem;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);
  font-size: 0.875rem;
  color: var(--text-color);
  background-color: var(--background-color);
  transition: all 0.2s ease-in-out;
}

.competence {
  display: block;
  margin-top: 0.25rem;
  padding: 0.5rem 0.75rem;
  border: 1px solid var(--text-color);
  border-radius: 0.375rem;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);
  font-size: 0.875rem;
  color: var(--text-color);
  background-color: var(--background-color);
  transition: all 0.2s ease-in-out;
}
</style>