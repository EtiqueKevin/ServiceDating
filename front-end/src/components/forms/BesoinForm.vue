<script setup>
import {computed, onMounted, ref} from 'vue';
import {useBesoin} from "@/services/besoin.js";
import { useRouter } from 'vue-router';

const props = defineProps({
  besoin: {
    type: Object,
    default: null,
  },
});

const emit = defineEmits(['success', 'close']);

const router = useRouter();
const competences = ref([]);

const formData = ref({
  description: '',
  option_id: null,
});

const formValid = computed(() => {
  return formData.value.description && formData.value.option_id;
});

const title = computed(() => {
  return props.besoin ? 'Modifier le besoin' : 'Création d\'un besoin';
});

const buttonText = computed(() => {
  return props.besoin ? 'Modifier' : 'Créer';
});

const {getCompetences, createBesoin, updateBesoin} = useBesoin();

const handleClose = () => {
  emit('close');
};

const handleSubmit = async () => {
  if (!formValid.value) return;

  const data = {
    description: formData.value.description,
    competence_id: formData.value.option_id,
  };

  let success;
  if (props.besoin) {
    success = await updateBesoin(props.besoin.id, data);
  } else {
    success = await createBesoin(data);
  }

  if(success) {
    emit('success');
    router.push({name: 'besoins'});
  }
}

const loadData = async () => {
  competences.value = await getCompetences();
  if (props.besoin) {
    formData.value.description = props.besoin.description;
    formData.value.option_id = props.besoin.competence.id;
  }
}

onMounted(() => {
  loadData();
});
</script>

<template>
  <div class="form-container">
    <h1 class="title">{{ title }}</h1>
    <form @submit.prevent="handleSubmit" class="form">
      <textarea
          v-model="formData.description"
          id="text"
          required
          autocomplete="off"
          placeholder="Description du besoin"
          class="textArea"
      >
      </textarea>

      <select class="competence" id="dropdown" v-model="formData.option_id" required>
        <option selected disabled hidden>Compétences</option>
        <option v-for="option in competences" :key="option.id" :value="option.id">
          {{ option.nom }}
        </option>
      </select>

      <div class="button-container">
        <button
            type="submit"
            class="submit-button"
            :disabled="!formValid"
            :class="{
            'disabled': !formValid,
            'enabled': formValid
          }"
            :title="buttonText"
        >
          {{ buttonText }}
        </button>
        <button
            v-if="props.besoin"
            type="button"
            class="close-button"
            @click="handleClose"
        >
          Fermer
        </button>
      </div>
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

.button-container {
  display: flex;
  gap: 1rem;
  width: 100%;
}

.close-button {
  width: 100%;
  padding: 0.75rem 1.5rem;
  border-radius: 0.5rem;
  font-weight: 600;
  color: white;
  background-color: #6b7280;
  transition: all 0.3s;
}

.close-button:hover {
  background-color: #4b5563;
  box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1);
  transform: scale(1.02);
}

.close-button:active {
  transform: scale(0.98);
}
</style>