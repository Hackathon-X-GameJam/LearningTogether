<script setup lang="ts">
import {onMounted, ref} from 'vue'
import { useRouter } from 'vue-router'
import axios from "axios";

const props = defineProps<{
  groupName: string,
  startTime: any,
  endTime: any,
  courseOfStudy: any
  groupID: string
  owner: string
  member: string[]
}>()

const owner = ref('');

onMounted(async () => {
  try {
    const temp = await axios.get(`http://localhost:8080/students/${props.owner}`);
    owner.value = temp.data.account.username;
  } catch (error) {
    console.error('Error fetching owner:', error);
  }
});

const formatTime = (timestamp: string) => {
  try {
    const date = new Date(timestamp).toLocaleTimeString().split(':');
    return date[0] + ":" + date[1];
  } catch (e) {
    return "Invalid date";
  }
};

const router = useRouter()

const flexClass = ref('flex-container')
const flexClass2 = ref('flex-container2')
const titleClass = ref('title')
const infoClass = ref('info')
const mainElementClass = ref('main')

const openDetails = (id: string) => {
  router.push('/details/' + id);
};

const joinGroup = async () => {
  try {
    const url = `http://localhost:8080/learningGroup/${props.groupID}/student/${localStorage.getItem('userID')}`;
    console.log("Joining study group at:", url);
    const response = await axios.post(url, {}, {
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('sessionToken')}`
      }
    });
    console.log("Join group response:", response.data);
    router.push('/home');
  } catch (error) {
    console.error("Error joining study group:", error);
    alert("Failed to join the study group!");
  }
};
</script>

<template>
  <div class="main">
    <flex-container :class="flexClass">
      <div class="title">{{props.groupName}}</div>
      <div :class="infoClass">{{formatTime(props.startTime)}} - {{formatTime(props.endTime)}}</div>
    </flex-container>

    <div class="info">{{props.courseOfStudy.subjectName}}</div>
    <br>
    <div>
      <v-chip outlined color=#235555 style="color: azure">{{ owner }}</v-chip>
      <v-chip outlined color=#235555 style="color: azure">+ {{ props.member.length }}</v-chip>
    </div>
    <br>
    <flex-container :class="flexClass2">
      <v-btn @click="openDetails(props.groupID)" color=#97cbcb style="color: #2a2a2a">Details</v-btn>
      <v-btn @click="joinGroup" color=#97cbcb style="color: #2a2a2a">Join</v-btn>
    </flex-container>
  </div>
</template>

<style scoped>
flex-container {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  align-self: center;
}
flex-container2 {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  align-items: center;
  align-self: center;
}
.title {
  font-size: 1.5rem;
  font-weight: bold;
  color: azure;

}
.info {
  font-size: 1rem;
  color: azure;
}
.main {
  background-color: #2a2a2a;
  -webkit-box-shadow: 10px 10px 25px 0px rgba(0,0,0,0.75);
  -moz-box-shadow: 10px 10px 25px 0px rgba(0,0,0,0.75);
  box-shadow: 10px 10px 25px 0px rgba(0,0,0,0.75);
  width: 300px;
  padding-bottom: 5px;
  padding-left: 7px;
  padding-right: 7px;
  border-radius: 8px 8px 8px 8px;
  font-family: Calibri;
}
</style>