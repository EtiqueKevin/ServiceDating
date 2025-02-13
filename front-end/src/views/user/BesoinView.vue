<script setup>
import InputField from "@/components/forms/inputs/InputField.vue";
import {ref} from "vue";
import {useBesoin} from "@/services/besoin.js";

let besoins = ref([]);
const {getBesoin} = useBesoin();

const loadBesoin = async () => {
  besoins = getBesoin();
}

getBesoin();

</script>

<template>
  <main>
    <div class="container-search">
      <InputField/>
      <button>Rechercher</button>
    </div>
    <div class="container-besoins">
      <div v-for="besoin in besoins" :key="besoin.id" class="besoin-item">
        <h3>{{ besoin.name }}</h3>
        <p>{{ besoin.description }}</p>
        <button>Modifier</button>
      </div>
    </div>
  </main>
</template>

<style scoped>
main {
  display: flex;
  flex-direction: column;
  height: 100%;
}

.container-search {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
}

.container-besoins {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  height: 500px;
  gap: 16px;
  padding: 16px;
}

.besoin-item {
  border: 1px solid #ddd;
  padding: 10px;
  border-radius: 5px;
  background: #f9f9f9;
  transition: transform 0.3s ease;
}



.besoin-item {
  border: 1px solid #ddd;
  padding: 10px;
  margin: 10px 0;
  border-radius: 5px;
  background: #f9f9f9;
}
</style>