import { defineStore } from 'pinia'
import { useToast } from 'vue-toastification';

const toast = useToast();

export const useUserStore = defineStore('user', {
    state: () => ({
        accessToken: null,
        refreshToken: null,
        role: null,
    }),

    actions: {
        async signIn(email, password) {
            
            try {
                const credentials = btoa(`${email}:${password}`);
                const res = await this.$api.post('signin', {}, {
                    headers: {
                        'Authorization': `Basic ${credentials}`
                    }
                });
                this.accessToken = res.data.atoken;
                this.refreshToken = res.data.rtoken;
                this.role = res.data.role;
                toast.success('Connexion réussie');
                return true;
            } catch (e) {
                toast.error('Erreur lors de la connexion');
                return false;
            }
        },
        
        async signUp(data) {
            try {
                const res = await this.$api.post('register', data);
                this.accessToken = res.data.atoken;
                this.refreshToken = res.data.rtoken;
                this.role = res.data.role;
                toast.success('Inscription réussie');
                return true;
            } catch (e) {
                toast.error('Erreur lors de l\'inscription');
                return false;
            }
        },

        signOut() {
            this.accessToken = null;
            this.refreshToken = null;
            this.role = null;
            toast.success('Déconnexion réussie');
        },
        
        setTokens(accessToken, refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        },
    },

    getters: {
        isLogged(state) {
            return state.accessToken !== null;
        },
        token(state){
            return state.accessToken
        },
        getRefreshToken(state){
            return state.refreshToken
        },
        isAdmin(state){
            return state.role === 100;
        },
    },

    persist: {
        enabled: true,
        strategies: [
            { storage: localStorage, paths: ['accessToken', 'refreshToken', 'role'] }
        ]
    }
})