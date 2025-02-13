<script setup>
import { RouterView, RouterLink } from 'vue-router';
</script>

<template>
  <div class="back-office">
    <nav class="admin-nav">
      <RouterLink to="/admin/users">
        <i class="fas fa-users"></i>
        <span>Gérer Utilisateurs</span>
      </RouterLink>
      <RouterLink to="/" class="menu-return">
        <i class="fas fa-arrow-left"></i>
        <span>Retour au Menu</span>
      </RouterLink>
    </nav>
    
    <div class="admin-content">
      <RouterView>
        <!-- Default content when no specific route is active -->
        <template #default>
          <div class="welcome-admin">
            <h1>Bienvenue dans le Back Office</h1>
            <p>Sélectionnez une option dans le menu pour commencer.</p>
          </div>
        </template>
      </RouterView>
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

.admin-nav a.router-link-active {
  background: var(--accent-color);
}

.admin-content {
  padding: 2rem;
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
  padding: 2rem;
}

.welcome-admin h1 {
  color: var(--accent-color);
  margin-bottom: 1rem;
}

.welcome-admin p {
  color: var(--text-color);
  font-size: 1.1rem;
}
</style>