import axios from 'axios'

export default {
  install: (app, options) => {
    const api = axios.create({
      baseURL: options.baseURL,
      timeout: 30000,
      headers: {
        'Content-Type': 'application/json'
      }
    })

    // intercepte la requête pour ajouter le token d'authentification si l'utilisateur est connecté
    api.interceptors.request.use((config) => {
      const userStore = useUserStore();
      const token = userStore.token;
      if (token) {
        config.headers.Authorization = `Bearer ${token}`;
      }
      return config;
    })

    // intercepte la réponse pour gérer les erreurs d'authentification
    api.interceptors.response.use((response) => {
      return response
    }, async (error) => {
      return Promise.reject(error);
    });

    app.config.globalProperties.$api = api
    app.provide('api', api)
  }
}