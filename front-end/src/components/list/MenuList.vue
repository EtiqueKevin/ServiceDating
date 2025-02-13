<script setup>
import { ref } from 'vue';

const props = defineProps({
  upIcon: {
    type: String,
    default: 'fa-chevron-up'
  },
  downIcon: {
    type: String,
    default: 'fa-chevron-down'
  },
  showIcon: {
    type: Boolean,
    default: true
  }
});

const isHovered = ref(false);
</script>

<template>
  <div 
    class="menu-container"
    @mouseenter="isHovered = true"
    @mouseleave="isHovered = false"
  >
    <button class="menu-button">
      <slot name="button-content">
        <span class="button-text">
          <slot name="text"></slot>
        </span>
        <slot name="icon" :is-hovered="isHovered" v-if="showIcon">
          <i :class="`fas ${isHovered ? upIcon : downIcon} icon`"></i>
        </slot>
      </slot>
    </button>
    
    <transition
      enter-active-class="enter-active"
      enter-from-class="enter-from"
      enter-to-class="enter-to"
      leave-active-class="leave-active"
      leave-from-class="leave-from"
      leave-to-class="leave-to"
    >
      <div v-if="isHovered" class="dropdown-menu">
        <slot></slot>
      </div>
    </transition>
  </div>
</template>

<style scoped>
.menu-container {
  position: relative;
  height: 100%;
}

.menu-button {
  display: flex;
  align-items: center;
  height: 100%;
  padding: 0 1rem;
  transition: color 0.2s;
  color: var(--text-color);
  border: none;
  background-color: transparent;
}

.menu-button:hover {
  color: var(--secondary-color);
}

.button-text:hover {
  text-decoration: underline;
}

.icon {
  font-size: 1.25rem;
  margin-left: 0.5rem;
  color: var(--primary-color);
}

.dropdown-menu {
  position: fixed;
  top: 3rem;
  right: 0;
  left: 0;
  display: flex;
  justify-content: center;
  gap: 1rem;
  padding: 1rem;
  z-index: 50;
  backdrop-filter: blur(4px);
  background-color: var(--background-color);
  border-top: 1px solid var(--primary-color);
}

@media (max-width: 640px) {
  .dropdown-menu {
    flex-direction: column;
    align-items: center;
  }
}

/* Transition classes */
.enter-active {
  transition: all 0.3s ease-out;
  transition-delay: 75ms;
}

.enter-from {
  opacity: 0;
  transform: translateY(-1rem);
}

.enter-to {
  opacity: 1;
  transform: translateY(0);
}

.leave-active {
  transition: all 0.2s ease-in;
}

.leave-from {
  opacity: 1;
  transform: translateY(0);
}

.leave-to {
  opacity: 0;
  transform: translateY(-1rem);
}
</style>