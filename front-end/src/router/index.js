import {createRouter, createWebHistory} from 'vue-router'
import {useUserStore} from '@/stores/user'
import DefaultLayout from '@/views/layouts/DefaultLayoutView.vue'
import BlankLayout from '@/views/layouts/BlankLayoutView.vue'

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            component: DefaultLayout,
            children: [
                {
                    path: '',
                    name: 'home',
                    redirect: {name: 'acceuil'}
                },
                {
                    path: 'acceuil',
                    name: 'acceuil',
                    component: () => import('@/views/HomeView.vue'),
                },
                {
                    path: 'besoin/create',
                    name: 'besoin-create',
                    component: () => import('@/views/user/BesoinView.vue'),
                    meta: {requiresAuth: true}
                }
            ]
        },
        {
            path: '/',
            component: BlankLayout,
            children: [
                {
                    path: 'user/connect',
                    name: 'user-connect',
                    component: () => import('@/views/user/ConnexionView.vue'),
                    meta: { requiresAuth: false }
                },
                {
                    path: 'admin',
                    name: 'backoffice',
                    component: () => import('@/views/admin/BackOfficeView.vue'),
                    meta: { requiresAuth: true, requiresAdmin: true },
                    children: [
                        {
                            path: 'besoins',
                            name: 'backoffice-besoins',
                            component: () => import('@/views/admin/AdminBesoinsView.vue'),
                            meta: { requiresAuth: true, requiresAdmin: true }
                        },
                    ]
                }
            ]
        }
    ],
})

router.beforeEach((to, from, next) => {
    // Redirection vers la page d'accueil si la route n'existe pas
    if (to.matched && to.matched.length === 0) {
        next({name: 'home'})
        return
    }

    // Vérification des droits d'accès
    const userStore = useUserStore()
    if (to.meta.requiresAuth && !userStore.isLogged) {
        next({
            name: 'user-connect',
        })
        return
    }

    if (to.name === 'user-connect' && userStore.isLogged) {
        next({name: 'home'})
        return
    }

    if (to.meta.requiresAdmin && !userStore.isAdmin) {
        next({name: 'home'})
        return
    }

    next()
})

export default router
