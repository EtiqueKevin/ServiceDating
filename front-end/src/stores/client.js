import { defineStore } from 'pinia'
import { useToast } from 'vue-toastification';

const toast = useToast();

export const useClientStore = defineStore('client', {
    state: () => ({

    }),

    actions: {
        
    },

    getters: {
    },

    persist: {
        enabled: true,
        strategies: [
            { storage: localStorage, paths: [] }
        ]
    }
})