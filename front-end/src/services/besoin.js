import {inject} from "vue";
import {useToast} from 'vue-toastification';


export const useBesoin = () => {

    const toast = useToast();
    const api = inject("api");

    const loadCompetence = async () => {
        try {
            const res = await this.api.get('competences');
            return res.data;
        } catch (err) {
            toast.error('Erreur lors de la récupération des compétences !')
        }
    }

    const postBesoin = async (data) => {
        try {
            const res = await this.api.post('besoin', data);
            return true;
        } catch (err) {
            toast.error('Erreur lors de la mise en ligne de votre besoin');
            return false;
        }
    }
}