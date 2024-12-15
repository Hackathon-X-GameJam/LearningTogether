<script setup lang="ts">
import { onMounted, ref } from 'vue';
import GroupContainer from "@/components/GroupContainer.vue";
import FlatPickr from 'vue-flatpickr-component';
import 'flatpickr/dist/flatpickr.css';
import axios from 'axios';

const valueStart = ref(0);
const valueEnd = ref(0);
const groupName = ref(getRandomName());
const subject = ref('');

const createGroup = async () => {
  console.log('valueStart:', localStorage.getItem('userID'));
  const groupData = {
    groupName: groupName.value,
    subject: subject.value,
    startTime: new Date("1.1.1970 "+ valueStart.value).getTime(),
    endTime: new Date("1.1.1970 "+ valueEnd.value).getTime(),
    owner: localStorage.getItem('userID'),
    place: "TBA",
    maxSize: 11,
    members: [],
  };

  try {
    await axios.post('http://localhost:8080/learningGroup', groupData, {
  headers: {
    'Authorization': `Bearer ${localStorage.getItem('sessionToken')}`
  }
});
    alert('Group created successfully');
  } catch (error) {
    console.error('Error creating group:', error);
    alert('Failed to create group');
  }
};

const subjects = ref([]);

onMounted(async () => {
  try {
    const response = await axios.get('http://localhost:8080/subjects');
    console.log('Subjects:', response.data);
    subjects.value = response.data;
  } catch (error) {
    console.error('Error fetching subjects:', error);
  }
});

const itemProps = (item:any) => {
  return {
    title: item.subjectName,
    subtitle: item.prof,
  }
}

</script>



<script lang="ts">
function getRandomName(): string {
  const adjectives = ['happy', 'sad', 'angry', 'sleepy', 'hungry', 'thirsty', 'bored', 'excited', 'tired', 'sick'];
  const nouns = ['dog', 'cat', 'bird', 'fish', 'rabbit', 'hamster', 'turtle', 'snake', 'lizard', 'frog'];
  const randomAdjective = adjectives[Math.floor(Math.random() * adjectives.length)];
  const randomNoun = nouns[Math.floor(Math.random() * nouns.length)];
  return `${randomAdjective}-${randomNoun}`;
}

export default {
  data () {
    return {
      valueStart: 0,
      valueEnd: 0,
      date: null, // v-model binds to this variable
      config: {
        enableTime: true,
        noCalendar: true,
        dateFormat: "H:i",
        time_24hr: true,
        minuteIncrement: 15,
        defaultHour: 0,
        defaultMinute: 0,
        defaultDate: new Date("1.1.1970"),
      },
    }
  },
  name: "DatePicker",
  components: {
    FlatPickr,
  },
}
</script>

<template>
  <div style="width: 100vw; display:flex; align-items: center; justify-content: center;">
  <v-card id="tileform" style="background-color: #2a2a2a; padding-top: 25px;">
    <v-text-field bg-color=#97cbcb label="Group name" variant="solo" v-model="groupName"/>
    <v-select bg-color=#97cbcb label="Subject" variant="solo" v-model="subject" :item-props="itemProps" :items="subjects"></v-select>
    <div style="color: azure">Starting at...</div>
    <flat-pickr
        style="background-color: #97cbcb"
        v-model="valueStart"
        :config="config"
        id="datepicker"
        class="custom-flatpickr"
    />
    <div style="color: azure">Ending at...</div>
    <flat-pickr
        style="background-color: #97cbcb"
        v-model="valueEnd"
        :config="config"
        id="datepicker"
        class="custom-flatpickr"
    />
    <br>
    <br>
    <v-btn @click="createGroup" color=#97cbcb>Create study session</v-btn>

  </v-card>
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
  width: 100%;
  position: fixed; /* Fix the top bar at the top */
  top: 0; /* Position it at the top */
  left: 0; /* Position it at the left */
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px; /* Add some padding for better appearance */
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); /* Add a shadow for better visibility */
}

#toolbar{
  top: 100px;
  left: 0;
  width: 100%;
  position: fixed;
  padding: 20px;
  background-color: darkcyan;
}

#tileform {
  padding-top: 10px;
  padding-left: 30px;
  padding-right: 30px;
  padding-bottom: 30px;
  width: 1000px;
  align-self: center;
}

.custom-flatpickr {
  border: 2px solid #4CAF50; /* Green border */
  border-radius: 5px;       /* Rounded corners */
  padding: 10px;            /* Spacing inside the input */
  font-size: 16px;          /* Adjust font size */
}

.custom-flatpickr:focus {
  border-color: #2196F3;    /* Change border color on focus */
  outline: none;            /* Remove default focus outline */
  box-shadow: 0 0 5px rgba(33, 150, 243, 0.5); /* Add a glow effect */
}

</style>



