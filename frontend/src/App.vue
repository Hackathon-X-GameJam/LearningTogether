<script setup lang="ts">
import { ref } from 'vue';
import GroupContainer from "@/components/GroupContainer.vue";
import { useRouter } from 'vue-router'

const router = useRouter()

const goHome = () => {
  // This is a vulnerable point, I don't care
  router.push('/home');
};
const goTo = (path:any) => {
  // This is a vulnerable point, I don't care
  router.push(path);
};


</script>

<script lang="ts">
export default {
  data: () => ({
    items: [
      { title: 'Settings', path: "/profile", icon: "mdi-cog" },
      { title: 'Login', path: "/login", icon: "mdi-login" },
    ],
  }),


}
</script>

<template>
  <div style="display:flex; align-items: center; width:100vw">
    <div id="topbar" >
      <v-btn prepend-icon="$vuetify"
             variant="text"
             @click="goHome"
             size="x-large"
      >
        LearnTogether
      </v-btn>

      <v-menu>
        <template v-slot:activator="{ props }">
          <v-btn icon="mdi-account" variant="text" v-bind="props" color="#97cbcb"></v-btn>
        </template>

        <v-list bg-color="#2a2a2a">
          <v-list-item
              v-for="(item, i) in items"
              :key="i"
          >
            <v-btn color="#97cbcb" @click=goTo(item.path)>{{ item.title }}</v-btn>
          </v-list-item>
        </v-list>
      </v-menu>
    </div>

    <!-- <div style=""/>
    <div style="height: 100px;"> </div> -->
    <RouterView />
  </div>

</template>

<style>
body {background-color: azure;
      font-family: Calibri;
      color: black;
      margin: 0;
}


#topbar {
  background-color: #2a2a2a;
  color: whitesmoke;
  height: 100px;
  width: 100vw;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px; /* Add some padding for better appearance */
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); /* Add a shadow for better visibility */
  font-size: xx-large;
}

#toolbar{
  width: 100vw;
  padding: 20px;
  background-color: darkcyan;
}

</style>
