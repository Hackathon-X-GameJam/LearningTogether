<script setup lang="ts">
import { onMounted, ref, toRaw, watch } from 'vue';
import GroupContainer from "@/components/GroupContainer.vue";
import {useRouter} from "vue-router";

import axios from "axios";

const router = useRouter()

const toAddGroup = () => {
  // This is a vulnerable point, I don't care
  router.push('/addGroup');
};

let subjects = ref([]);
const searchString = ref("")
let filterObject = ref({
  groupName: null,
  owner: null,
  minStartTime: null,
  maxStartTime: null,
  minEndTime: null,
  maxEndTime: null,
  place: null,
  subject: [],
  freeSlots: null,
  members: null
})
const pickedSubjects = ref([])
let rawFilter = ref(filterObject)
let updateProp = ref(0)

onMounted(async () => {
  try {
    const response = await axios.get('http://localhost:8080/subjects');
    console.log('Subjects:', response.data);
    subjects.value = response.data;
  } catch (error) {
    console.error('Error fetching subjects:', error);
  }
});

onMounted(async () => {
  try {
    const response = await axios.get('http://localhost:8080/learningGroup/filter/empty');
    console.log('Filter:', response.data);
    filterObject.value = response.data
  } catch (error) {
    console.error('Error fetching subjects:', error);
  }
});

const filter = () => {
  rawFilter = filterObject._rawValue
  rawFilter.subject = []
  for (let i = 0; i < pickedSubjects._rawValue.length; i++) {
    rawFilter.subject.push(toRaw(pickedSubjects._rawValue[i]).id)
  }
}

watch(() => pickedSubjects, (newVal, oldVal) => {
  console.log("picked subjects:", newVal);
  updateProp += 1
  filter();
}, { deep: true }, // This enables deep watching
);

const itemProps = (item:any) => {
  return {
    title: item.subjectName,
    subtitle: item.prof,
  }
}
</script>

<template>
  <div id="toolbar">
    <v-row>
      <v-col cols="4"><v-combobox chips multiple id="searchtags" variant="solo" rounded="xl" placeholder="Enter tags..." :items="subjects" :item-props="itemProps" v-model="pickedSubjects" @update:model-value="filter">
      </v-combobox></v-col>
      <v-col cols="6"><v-text-field id="searchbar" variant="solo" rounded="xl" append-inner-icon="mdi-magnify" placeholder="Search..." v-model="filterObject.groupName" @update:model-value="filter"></v-text-field></v-col>
      <v-col cols="1"><v-btn @click="filter" icon="mdi-magnify" size="large"></v-btn></v-col>
      <v-col cols="1"><v-btn @click="toAddGroup" icon="mdi-plus" size="large"></v-btn></v-col>
    </v-row>
  </div>

  <div style="width: 100vw; display:flex; align-items: center; justify-content: center;">
    <group-container v-if="rawFilter" :raw-filter="rawFilter" :update-prop="updateProp"/>
  </div>
</template>

<style scoped>
#toolbar{
  display: flex;
  width: 100%;
  height: 80px;
  padding: 10px;
  background-color: darkcyan;
  align-content: flex-start;
}

#searchtags {
  padding: 10px;
  width: 50%;
}

#searchbar {
  padding: 10px;
  width:50%
}
</style>