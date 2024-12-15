<script setup lang="ts">
import { onMounted, ref } from 'vue';
import GroupContainer from "@/components/GroupContainer.vue";
import {useRouter} from "vue-router";

import axios from 'axios';

const router = useRouter()

const courses = ref([]);
const cos = ref('')

const user = ref({account: {username: "", password:""}})

onMounted(async () => {
  try {
    const response = await axios.get('http://localhost:8080/students/' + localStorage.getItem('userID'));
    console.log('user:', response.data);
    user.value = response.data;
  } catch (error) {
    console.error('Error fetching subjects:', error);
  }
});

onMounted(async () => {
  try {
    const response = await axios.get('http://localhost:8080/courses');
    console.log('Courses:', response.data);
    courses.value = response.data;
  } catch (error) {
    console.error('Error fetching subjects:', error);
  }
});

const itemProps = (item:any) => {
  return {
    title: item.courseName,
  }
}

const saveSettings = async () => {
  try {
    console.log(user._rawValue)
    await axios.post('http://localhost:8080/students', user._rawValue, {
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('sessionToken')}`
      }
    });
    //user.value = response.data;
    alert('Settings saved successfully');
  } catch (error) {
    console.error('Error creating group:', error);
    alert('Failed to save settings');
  }
};
</script>

<template>
  <div style="width: 100vw; display:flex; align-items: center; justify-content: center;">
  <v-card id="usercard"
  prepend-icon="$vuetify"
  width="400">
    <template v-slot:title>
      <span class="font-weight-black" style="color: #f2f2f2">{{ user.account.username }}</span>
    </template>
    <v-select label="Course of study" variant="solo" v-model="user.cos" :items="courses" :item-props="itemProps" bg-color="#97cbcb"></v-select>
    <div>Semester</div>
    <v-slider
        v-model="user.semester"
        :max="21"
        :min="0"
        :value-now="0"
        :step="1"
        thumb-label
        color="#f2f2f2">

    </v-slider>
    <div>Change password</div>
    <v-text-field placeholder="Enter new password..." variant="solo" v-model="user.account.password" bg-color="#97cbcb"></v-text-field>
    <v-btn right-0 @click="saveSettings" color="#97cbcb">Submit</v-btn>
  </v-card>
  </div>
</template>

<style scoped>

#usercard {
  background-color: #2a2a2a;
  color: whitesmoke;
  padding: 30px;
  position: relative;
  align-self: center;
}
</style>