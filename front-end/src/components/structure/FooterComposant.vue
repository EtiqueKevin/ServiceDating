<script setup>
const currentYear = new Date().getFullYear();
</script>

<template>
  <footer class="footer">
    <div class="container">
      <div class="footer-content">
        <div class="footer-brand">
          <span>{{ currentYear }} ServiceDating</span>
        </div>
        
        <div class="footer-links">
          <a href="https://github.com/EtiqueKevin/ServiceDating" 
             title="Répository du projet" 
             class="footer-link">
            <i class="fab fa-github"></i>
          </a>
        </div>
      </div>
    </div>
  </footer>
</template>

<style scoped>
.footer {
  background-color: var(--background-color);
  backdrop-filter: blur(8px);
  color: var(--text-color);
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 1rem;
}

.footer-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem 0;
  font-size: 0.875rem;
}

.footer-brand {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.footer-links {
  display: flex;
  gap: 1rem;
}

.footer-link {
  color: var(--primary-color);
  text-decoration: none;
  transition: color 0.2s ease;
}

.footer-link:hover {
  color: var(--accent-color);
}

.footer-link i {
  font-size: 1.25rem;
}
</style>