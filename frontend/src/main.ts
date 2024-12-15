import './assets/main.css'
import 'vuetify/styles' // Ensure Vuetify styles are imported

import { createApp } from 'vue'
import App from './App.vue'
import { createVuetify } from 'vuetify'
import { VTimePicker } from 'vuetify/labs/VTimePicker'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'
import {aliases, mdi} from "vuetify/iconsets/mdi";
import '@mdi/font/css/materialdesignicons.css'
import router from './router'


const vuetify = createVuetify({
    components,
    directives,
    icons: {
        defaultSet: 'mdi'
    },
})


const app = createApp(App)
app.use(vuetify)
app.use(router)
app.mount('#app')