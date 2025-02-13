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

    const getBesoins = async () => {
        try  {
            const res = await api.get('users/besoins');
            return res.data.besoins;
        }catch(err) {
            toast.error('Erreur lors de la récupération de vos besoin');
        }
    }

    return { getCompetences, createBesoin, getBesoins };
};