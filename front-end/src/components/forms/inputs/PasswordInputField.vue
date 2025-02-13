<script setup>
import { ref } from 'vue'

const props = defineProps({
  modelValue: String,
  id: String,
  placeholder: String,
  required: {
    type: Boolean,
    default: false
  },
  autocomplete: String
})

defineEmits(['update:modelValue'])

const showPassword = ref(false)
</script>

<template>
  <div class="password-container">
    <input
      :value="modelValue"
      @input="$emit('update:modelValue', $event.target.value)"
      :type="showPassword ? 'text' : 'password'"
      :id="id"
      :required="required"
      :autocomplete="autocomplete"
      :placeholder="placeholder"
      :title="placeholder"
      class="input-field"
    />
    <button 
      type="button" 
      @click="showPassword = !showPassword" 
      title="Afficher le mot de passe"
      class="toggle-button"
    >
      <i :class="['fas', showPassword ? 'fa-eye' : 'fa-eye-slash']"></i>
    </button>
  </div>
</template>

<style scoped>
.password-container {
  position: relative;
  width: 100%;
}

.input-field {
  display: block;
  
  box-sizing: border-box;

  width: 100%;
  margin-top: 0.25rem;
  padding: 0.5rem 0.75rem;
  border: 1px solid var(--text-color);
  border-radius: 0.375rem;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);
  font-size: 0.875rem;
  color: var(--text-color);
  background-color: var(--background-color);
  transition: all 0.2s ease-in-out;
  padding-right: 2.5rem; /* Added to make room for the eye icon */
}

.input-field:focus {
  outline: none;
  border-color: var(--primary-color);
  box-shadow: 0 0 0 2px rgba(44, 62, 80, 0.2);
}

.toggle-button {
  position: absolute;
  right: 0.5rem;
  top: 50%;
  transform: translateY(-50%);
  background: none;
  border: none;
  cursor: pointer;
  color: var(--text-color);
  opacity: 0.7;
  transition: opacity 0.2s ease-in-out;
}

.toggle-button:hover {
  opacity: 1;
}
</style>