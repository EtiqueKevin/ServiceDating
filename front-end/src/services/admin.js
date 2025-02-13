import { inject } from 'vue'

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
            await api.post('/salaries', data);
            return true;
        } catch (e) {
            return false;
        }
    }

    const getAllSalaries = async () => {
        try {
            const res = await api.get('/salaries');
            return res.data;
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

    return { getAllBesoins, createSalarie, getAllSalaries, getAllCompetences, createCompetence, updateCompetence, deleteCompetence };
}