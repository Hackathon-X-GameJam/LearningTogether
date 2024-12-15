// type declaration file for Vue Router
declare module './router' {
    import { Router } from 'vue-router';
    const router: Router;
    export default router;
}

// Then, update the import statement in `main.ts`:
import router from './router'