<script setup>
import {ref, onMounted} from 'vue';

const secondTitle = "Ce site web a été conçu dans le cadre du Crazy Charly Day";
const displayedSecondTitle = ref("");
let indexSecondTitle = 0;

const titleSecond = () => {
  if (indexSecondTitle < secondTitle.length) {
    displayedSecondTitle.value += secondTitle[indexSecondTitle];
    indexSecondTitle++;
    setTimeout(titleSecond, 40);
  }
};

onMounted(() => {
  titleSecond();
});
</script>


<template>
  <main>
    <h1 class="title">Bienvenue sur ServiceDating</h1>
    <h2 class="second-title">{{ displayedSecondTitle }}</h2>
  </main>
</template>

<style scoped>
main {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100%;
}

.title {
  text-align: center;
  font-weight: bold;
  font-size: 2rem;
}

.second-title {
  text-align: center;
}

</style>