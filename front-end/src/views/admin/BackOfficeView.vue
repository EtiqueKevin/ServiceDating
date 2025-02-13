<script setup>
import { RouterView, RouterLink, useRoute } from 'vue-router';
const route = useRoute();
</script>

<template>
  <div class="back-office">
    <nav class="admin-nav">
      <h1 class="welcome-admin">Back Office</h1>
      <RouterLink to="/admin/besoins">
        <i class="fas fa-list"></i>
        <span>Gérer les besoins</span>
      </RouterLink>
      <RouterLink to="/admin/salaries">
        <i class="fas fa-users"></i>
        <span>Gérer les salariés</span>
      </RouterLink>
      <RouterLink to="/admin/competences">
        <i class="fas fa-book"></i>
        <span>Gérer les compétences</span>
      </RouterLink>
      <RouterLink to="/" class="menu-return">
        <i class="fas fa-arrow-left"></i>
        <span>Retour au Menu</span>
      </RouterLink>
    </nav>
    
    <div class="admin-content">
      <div v-if="route.path === '/admin'" class="welcome-content">
        <h2>Bienvenue dans l'espace administrateur</h2>
        <p>Sélectionnez une option dans le menu pour commencer la gestion.</p>
      </div>
      <RouterView v-else />
    </div>
  </div>
</template>

<style scoped>
.back-office {
  display: grid;
  grid-template-columns: 250px 1fr;
  min-height: 100vh;
}

.admin-nav {
  background: var(--background-color);
  padding: 1rem;
  display: flex;
  flex-direction: column;
  gap: 1rem;
  box-shadow: 4px 0 8px rgba(0, 0, 0, 0.1);
}

.admin-nav a {
  color: var(--primary-color);
  text-decoration: none;
  padding: 0.5rem 1rem;
  border-radius: 4px;
  transition: background-color 0.3s ease;
  background: var(--background-color);
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.admin-nav i {
  width: 20px;
  text-align: center;
}

.admin-nav a:hover {
  background: var(--secondary-color);
  color: white; 
}

.admin-content {
  padding: 2rem;
  overflow-y: auto;
  color: var(--text-color);
}

.menu-return {
  margin-top: auto;
}

.menu-return:hover {
  background: var(--secondary-color) !important;
}

.welcome-admin {
  text-align: center;
  padding-bottom: 1rem;
  border-bottom: 2px solid var(--secondary-color);
}

.welcome-content {
  text-align: center;
  padding: 2rem;
}

.welcome-content h2 {
  color: var(--primary-color);
  margin-bottom: 1rem;
}

.welcome-content p {
  color: var(--text-color);
  font-size: 1.1rem;
}
</style>