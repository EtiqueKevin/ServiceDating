<script setup>
defineProps({
  modelValue: String,
  type: {
    type: String,
    default: 'text'
  },
  id: String,
  placeholder: String,
  required: {
    type: Boolean,
    default: false
  },
  autocomplete: String,
})

defineEmits(['update:modelValue'])
</script>

<template>
  <input
      :value="modelValue"
      @input="$emit('update:modelValue', $event.target.value)"
      :type="type"
      :id="id"
      :required="required"
      :autocomplete="autocomplete"
      :placeholder="placeholder"
      :title="placeholder"
      class="input-field"
  />
</template>

<style scoped>
.input-field {
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

.input-field:focus {
  outline: none;
  border-color: var(--primary-color);
  box-shadow: 0 0 0 2px rgba(44, 62, 80, 0.2);
}

</style>