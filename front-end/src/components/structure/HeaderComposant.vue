<script setup>
import MenuList from '@/components/list/MenuList.vue';
import { RouterLink } from 'vue-router';
import { useUserStore } from '@/stores/user';
import { useRouter } from 'vue-router';

const router = useRouter();
const userStore = useUserStore();

const logOut = async () => {
  await router.push('/');
  userStore.signOut();
};
</script>

<template>
  <header class="header">
    <nav class="nav-container">
      <div class="nav-content">
        <!-- Logo -->
        <router-link to="/" 
          class="logo-link" 
          title="Retour a l'accueil">
          <img class="logo" src="@/assets/logo.png" alt="Logo" />
          <span class="brand-name">ServiceDating</span>
        </router-link>

        <!-- Navigation -->
        <div class="nav-items">
          <!-- Espace visible aux clients -->
          <MenuList :show-icon="true" v-if="userStore.isLogged" class="menu-text">
            <template #text>
              Espace client
            </template>
            <RouterLink to="/besoin/create" class="nav-link" title="Créer un besoin">
              <div class="button-content">
                <span class="icon-text">Créer un besoin</span>
              </div>
            </RouterLink>
          </MenuList>

          <!-- Espace visible aux admins -->
          <template v-if="userStore.isAdmin">
    <div class="separator"></div>
    <RouterLink to="/admin" class="nav-link" title="Panel administrateur">
        <div class="button-content">
            <i class="fas fa-user-shield"></i>
            <span class="icon-text">Backoffice</span>
        </div> 
    </RouterLink>
</template>

          <div class="separator"></div>
          
          <template v-if="!userStore.isLogged">
              <RouterLink to="/user/connect" class="nav-link" title="Connexion">
                <div class="button-content">
                  <i class="fas fa-sign-in-alt"></i>
                  <span class="icon-text">Connexion</span>
                </div> 
              </RouterLink>
          </template>
          <template v-else>
            <button @click="logOut" class="nav-link" title="Déconnexion">
              <div class="button-content">
                <i class="fas fa-sign-out-alt"></i>
                <span class="icon-text">Déconnexion</span>
              </div>
            </button>
          </template>
        </div>
      </div>
    </nav>
  </header>
</template>

<style scoped>
.header {
  position: sticky;
  top: 0;
  z-index: 50;
  backdrop-filter: blur(4px);
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  background-color: var(--background-color);
  transition: background-color 0.2s ease;
}

.nav-container {
  margin: 0 auto;
  padding: 0 1rem;
}

@media (min-width: 640px) {
  .nav-container {
    padding: 0 1.5rem;
  }
}

@media (min-width: 1024px) {
  .nav-container {
    padding: 0 2rem;
  }
}

.nav-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 3rem;
}

.logo-link {
  display: flex;
  align-items: center;
  flex-shrink: 0;
  transition: opacity 0.2s ease;
}

.logo-link:hover {
  opacity: 0.8;
}

.logo {
  height: 2rem;
  width: auto;
}

.brand-name {
  margin-left: 0.5rem;
  font-size: 1.25rem;
  font-weight: bold;
  color: var(--text-color);
}

.nav-items {
  display: flex;
  align-items: center;
  gap: 1rem;
  height: 100%;
}

.button-content {
  font-size: 1rem;
  display: flex;
  align-items: center;
  gap: 0.1rem;
}

.menu-text {
  color: var(--text-color);
}

.nav-link {
  text-decoration: none;
  color: var(--text-color);
  border: none;
  background-color: transparent;
  transition: color 0.2s ease;
}

.nav-link:hover {
  color: var(--secondary-color);
}

.icon-text {
  margin-left: 0.5rem;
}

.separator {
  height: 1.5rem;
  width: 1px;
  background-color: var(--primary-color);
}
</style>