<script setup>
import {computed, onMounted, ref} from 'vue';
import InputField from "@/components/forms/inputs/InputField.vue";
import { useAdmin } from '@/services/admin';

const props = defineProps({
    competence: {
        type: Object,
        default: null,
    },
});

const emit = defineEmits(['success', 'close']);

const { createCompetence, updateCompetence } = useAdmin();
const formData = ref({
    name: '',
    description: '',
});

const formValid = computed(() => {
  return formData.value.name && formData.value.description;
});

const title = computed(() => {
  return props.competence ? 'Modifier la compétence' : 'Créer une compétence';
});

const buttonText = computed(() => {
  return props.competence ? 'Modifier' : 'Créer';
});

const handleClose = () => {
  emit('close');
};

const handleSubmit = async () => {
    if (!formValid.value) return;
    if (props.competence) {
        await updateCompetence(props.competence.id, formData.value);
    } else {
        await createCompetence(formData.value);
    }
    formData.value.name = '';
    formData.value.description = '';

    emit('success');
}

onMounted(() => {
  if (props.competence) {
    formData.value.name = props.competence.nom;
    formData.value.description = props.competence.description;
  }
});
</script>

<template>
  <div class="form-container">
    <h1 class="title">{{ title }}</h1>
    <form @submit.prevent="handleSubmit" class="form">
      <InputField
          v-model="formData.name"
          type="text"
          id="text"
          required
          autocomplete="text"
          placeholder="nom de la compétence"
          class="input-field"
      />
      <InputField
          v-model="formData.description"
          type="text"
          id="text"
          required
          autocomplete="off"
          placeholder="Description du besoin"
          class="input-field"
        />

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