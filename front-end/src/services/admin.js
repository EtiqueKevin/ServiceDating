import { inject } from 'vue'

export const useAdmin = () => {
    const api = inject('api');

    const getAllBesoins = async () => {
        try {
            const res = await api.get('admin/besoins');
            console.log(res);
            return res.data;
        } catch (e) {
            return [];
        }
    }

    const createSalarie = async (data) => {
        try {
            await api.post('admin/salaries', data);
            return true;
        } catch (e) {
            return false;
        }
    }

    const getAllSalaries = async () => {
        try {
            const res = await api.get('admin/salaries');
            return res.data;
        } catch (e) {
            return [];
        }
    }

    const getAllCompetences = async () => {
        try {
            const res = await api.get('admin/competences');
            return res.data;
        } catch (e) {
            return [];
        }
    }

    const createCompetence = async (data) => {
        try {
            await api.post('admin/competences', data);
            return true;
        } catch (e) {
            return false;
        }
    }

    const updateCompetence = async (id, data) => {
        try {
            await api.put(`admin/competences/${id}`, data);
            return true;
        } catch (e) {
            return false;
        }
    }

    const deleteCompetence = async (id) => {
        try {
            await api.delete(`admin/competences/${id}`);
            return true;
        } catch (e) {
            return false;
        }
    }

    return { getAllBesoins, createSalarie, getAllSalaries, getAllCompetences, createCompetence, updateCompetence, deleteCompetence };
}