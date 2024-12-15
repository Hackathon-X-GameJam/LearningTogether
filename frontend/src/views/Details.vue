<template>
  <v-container class="py-5" style="width: 75vw; ">
    <v-card elevation="3">
      <v-card-title style="padding-top: 30px; margin-left: 10px">
        <v-row align="center" justify="space-between" class="w-100">
          <div class="text-h5" style="color: #97cbcb">   {{ studyGroup.groupName }}</div>

        </v-row>
      </v-card-title>

      <v-card-text>
        <v-row style="background-color: #2a2a2a;">
          <v-col cols="12" md="6">
            <v-list dense style="background-color: #2a2a2a;">
              <v-list-item>
                <v-list-item-content>

                </v-list-item-content>
              </v-list-item>

              <v-list-item>
                <v-list-item-content>
                  <v-list-item-title class="text-my-4">Owner</v-list-item-title>
                  <v-list-item-subtitle class="text-my-4">{{ studyGroup.owner }}</v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>

              <v-list-item>
                <v-list-item-content>
                  <v-list-item-title class="text-my-4">Subject</v-list-item-title>
                  <v-list-item-subtitle class="text-my-4">{{ studyGroup.subject.subjectName}}</v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>

              <v-list-item>
                <v-list-item-content>
                  <v-list-item-title class="text-my-4">Place</v-list-item-title>
                  <v-list-item-subtitle class="text-my-4">{{ studyGroup.place }}</v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>
            </v-list>
          </v-col>

          <v-col cols="12" md="6">
            <v-list dense style="background-color: #2a2a2a;">
              <v-list-item>
                <v-list-item-content>
                  <v-list-item-title class="text-my-4">Start Time</v-list-item-title>
                  <v-list-item-subtitle class="text-my-4">{{ formatTime(studyGroup.startTime) }}</v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>

              <v-list-item>
                <v-list-item-content>
                  <v-list-item-title class="text-my-4">End Time</v-list-item-title>
                  <v-list-item-subtitle class="text-my-4">{{ formatTime(studyGroup.endTime) }}</v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>

              <v-list-item>
                <v-list-item-content>
                  <v-list-item-title class="text-my-4">Max Size</v-list-item-title>
                  <v-list-item-subtitle class="text-my-4">{{ studyGroup.maxSize }}</v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>

              <v-list-item>
                <v-list-item-content>
                  <v-list-item-title class="text-my-4">Current Size</v-list-item-title>
                  <v-list-item-subtitle class="text-my-4">{{ currentSize }}</v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>
            </v-list>
          </v-col>
        </v-row>

        <v-divider class="my-4"></v-divider>

        <div class="text-h6" style="color: #97cbcb">Members</div>
        <v-row>
          <v-col
              v-for="(member, index) in studyGroup.members"
              :key="index"
              cols="12"
              md="4"
          >
            <v-chip outlined color="secondary">{{member.account.username}}</v-chip>
          </v-col>
          <v-col>
            <v-btn color=#97cbcb variant="flat" text @click="joinGroup" style="color: #2a2a2a">Join</v-btn>
          </v-col>
        </v-row>
      </v-card-text>
      <div style="height: 20px"/>
    </v-card>
  </v-container>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import axios from 'axios';

const props = defineProps<{
  id: String
}>();

const groupId = props.id;
const studyGroup = ref({
  groupName: "",
  owner: "",
  startTime: "",
  endTime: "",
  place: "",
  subject: "",
  maxSize: "",
  members: [],
});

const currentSize = computed(() => {
  try {
    return studyGroup.value.members.length;
  } catch (error) {
    console.error("Error fetching current size:", error);
    return 0;
  }
});

const joinGroup = async () => {
  try {
    const url = `http://localhost:8080/learningGroup/${groupId}/student/${localStorage.getItem('userID')}`;
    console.log("Joining study group at:", url);
    const response = await axios.post(url, {}, {
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('sessionToken')}`
      }
    });
    console.log("Join group response:", response.data);
    alert("You have joined the study group!");
    fetchStudyGroup();
  } catch (error) {
    console.error("Error joining study group:", error);
    alert("Failed to join the study group!");
  }
};

const fetchStudyGroup = async () => {
  try {
    const url = `http://localhost:8080/learningGroup/${groupId}`;
    console.log("Fetching study group data from:", url);
    const response = await axios.get(url);
    studyGroup.value = response.data;
    console.log("Study group data:", studyGroup.value);
  } catch (error) {
    console.error("Error fetching study group data:", error);
  }
};

const formatTime = (timestamp) => {
  const date = new Date(timestamp);
  return date.toLocaleString();
};

onMounted(() => {
  fetchStudyGroup();
});
</script>

<style scoped>
.v-card {
  max-width: 800px;
  margin: auto;
  background-color: #2a2a2a;
}
.v-container {
  width: 100%;
}
.text-my-4 {
  color:#97cbcb;
}
</style>