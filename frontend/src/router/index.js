import { createRouter, createWebHistory } from 'vue-router';
import App from '@/App.vue'; // Example Home page
import Details from '@/views/Details.vue'; // Details page
import Home from '@/views/Home.vue';
import Tile from "@/views/Tile.vue"; // Home page
import Profile from "@/views/Profile.vue";
import Login from "@/views/Login.vue";

// Define your routes
const routes = [
    {
        path: '/home',
        name: 'Home',
        component: Home, // Home page component
    },
    {
        path: '/addGroup',
        name: 'addGroup',
        component: Tile, // Home page component
    },
    {
        path: '/details/:id',
        name: 'Details',
        component: Details,
        props: true, // Pass the `id` parameter as a prop
    },
    {
        path: '/profile',
        name: 'Profile',
        component: Profile,
    },
    {
        path: '/login',
        name: 'Login',
        component: Login,
    }

];

// Create and export the router instance
const router = createRouter({
    history: createWebHistory(), // Use HTML5 history mode
    routes,
});

export default router;