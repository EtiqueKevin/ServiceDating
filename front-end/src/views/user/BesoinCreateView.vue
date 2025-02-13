<script setup>
import besoinForm from '@/components/forms/BesoinForm.vue'
</script>

<template>
  <main class="main-container">
    <besoin-form/>
  </main>
</template>

<style scoped>
.main-container {
  display: flex;
  flex-grow: 1;
  justify-content: center;
  align-items: flex-start;
  margin-top: 20px;
  padding-left: 16px;
  padding-right: 16px;
  height: 100%;
}
</style>