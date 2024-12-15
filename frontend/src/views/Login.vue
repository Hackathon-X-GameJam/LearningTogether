<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

const username = ref('');
const password = ref('');
const router = useRouter();

const requestLogin = async () => {
  try {
    console.log('Logging in with:', username.value, password.value);
    const response = await axios.post('http://localhost:8080/account', {
      "username": username.value,
      "password": password.value,
    });
    const sessionToken = response.data.token;
    localStorage.setItem('sessionToken', sessionToken);
    const userID = response.data.id;
    localStorage.setItem('userID', userID);
    alert('Login successful!');
    router.push('/home'); // Redirect to home page after successful login
  } catch (error) {
    console.error('Error logging in:', error);
    alert('Login failed. Please check your credentials and try again.');
  }
};
</script>

<template>
  <div style="width: 100vw; display:flex; align-items: center; justify-content: center;">
  <v-card id="logincard" title="Welcome to LearnTogether!" color="#2a2a2a" style="color: #f2f2f2">
    <v-text-field v-model="username" variant="solo" placeholder="Username" bg-color="#97cbcb"></v-text-field>
    <v-text-field v-model="password" variant="solo" placeholder="Password" type="password" bg-color="#97cbcb"></v-text-field>
    <v-btn @click="requestLogin" color="#97cbcb">Log in</v-btn>
  </v-card>
  </div>
</template>

<style scoped>
#logincard {
  padding: 30px;
}
.custom-center {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  width: 100vw;
}

</style>