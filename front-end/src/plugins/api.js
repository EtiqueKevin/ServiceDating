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

    // intercepte la requête 
    api.interceptors.request.use((config) => {
      return config;
    })

    // intercepte la réponse pour gérer les erreurs
    api.interceptors.response.use((response) => {
      return response
    }, async (error) => {
      return Promise.reject(error);
    });

    app.config.globalProperties.$api = api
  }
}