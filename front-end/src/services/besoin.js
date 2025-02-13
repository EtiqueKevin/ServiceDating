import {inject} from "vue";
import {useToast} from 'vue-toastification';


export const useBesoin = () => {
    const toast = useToast();
    const api = inject('api');

    const getCompetences = async () => {
        try {
            const res = await api.get('competences');
            return res.data.competences;
        } catch (err) {
            console.log('Erreur lors de la récupération des compétences !');
        }
    };

    const createBesoin = async (data) => {
        try {
            await api.post('besoins', data);
            return true;
        } catch (err) {
            toast.error('Erreur lors de la mise en ligne de votre besoin');
            return false;
        }
    };
    /*
    const getBesoins = async () => {
        try  {
            const res = await api.get('users/besoins');
            return res.data.besoins;
        }catch(err) {
            toast.error('Erreur lors de la récupération de vos besoin');
        }
    }*/

    const getBesoins = async (page = 1, limit = 5) => {
        try {
            const res = await api.get(`users/besoins?page=${page}&limit=${limit}`);
            return {
                besoins: res.data.besoins,
                totalItems: res.data.totalItems,
                totalPages: res.data.totalPages,
                currentPage: res.data.currentPage,
            };
        } catch (err) {
            toast.error('Erreur lors de la récupération de vos besoins');
            return { besoins: [], totalItems: 0, totalPages: 1, currentPage: 1 };
        }
    };


    const updateBesoin = async (id, data) => {
        try {
            await api.put('besoins/'+id, data);
            return true;
        } catch (err) {
            toast.error('Erreur lors de la mise à jour de votre besoin');
            return false;
        }
    };

    return { getCompetences, createBesoin, getBesoins, updateBesoin };
};