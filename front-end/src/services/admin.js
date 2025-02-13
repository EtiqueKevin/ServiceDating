import { inject } from 'vue'
import axios from 'axios'

export const useAdmin = () => {
    const api = inject('api');

    const getAllBesoins = async () => {
        try {
            const res = await api.get('/besoins');
            return res.data.besoins;
        } catch (e) {
            return [];
        }
    }

    const createSalarie = async (data) => {
        try {
            await api.post('/utilisateur', data);
            return true;
        } catch (e) {
            return false;
        }
    }

    const getAllSalaries = async () => {
        try {
            const res = await api.get('/salaries');
            return res.data.salaries;
        } catch (e) {
            return [];
        }
    }

    const getAllCompetences = async () => {
        try {
            const res = await api.get('/competences');
            return res.data.competences;
        } catch (e) {
            return [];
        }
    }

    const createCompetence = async (data) => {
        try {
            await api.post('/competences', data);
            return true;
        } catch (e) {
            return false;
        }
    }

    const updateCompetence = async (id, data) => {
        try {
            await api.put(`/competences/${id}`, data);
            return true;
        } catch (e) {
            return false;
        }
    }

    const deleteCompetence = async (id) => {
        try {
            await api.delete(`/competences/${id}`);
            return true;
        } catch (e) {
            return false;
        }
    }

    const getAffectations = async (data, methode) => {
        try {
            const newApi = axios.create({
                baseURL: import.meta.env.VITE_API_OPTI_URL,
                headers: {
                    'Content-Type': 'application/json'
                },
            });
            let other = '';

            if (methode !== "") {
                other = `/${methode}`;
            }
            const res = await newApi.post('/affectations'+other, data);
            return res.data;
        } catch (e) {
            return [];
        }
    }

    const getClients = async () => {
        try {
            const res = await api.get('/competences/clients');
            return res.data.competences_par_user;
        } catch (e) {
            return [];
        }
    }

    return { getAllBesoins, createSalarie, getAllSalaries, getAllCompetences, createCompetence, updateCompetence, deleteCompetence, getAffectations, getClients };
}