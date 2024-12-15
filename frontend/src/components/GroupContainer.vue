<script setup lang="ts">
import { ref, onMounted, watch, defineProps, toRaw } from 'vue';
import GroupElement from './GroupElement.vue';
import axios from 'axios';

// Define props using defineProps
const props = defineProps({
  rawFilter: String,
  updateProp: Number,
});

// Reactive reference for group elements
const groupElements = ref([]);

// Watch for changes in the rawFilter prop
watch(() => props.rawFilter, (newVal, oldVal) => {
  console.log("rawFilter changed:", newVal);
  getFilteredResults();
}, { deep: true }, // This enables deep watching
);

watch(() => props.updateProp, (newVal, oldVal) => {
  console.log("test")
  getFilteredResults();
}, { deep: true }, // This enables deep watching
);

// Fetch group elements on mounted
onMounted(async () => {
  try {
    const response = await axios.get('http://localhost:8080/learningGroup');
    console.log('Group elements:', response.data);
    groupElements.value = response.data;
  } catch (error) {
    console.error('Error fetching group elements:', error);
  }
});

const getFilteredResults = async () => {
  try {
    console.log(toRaw(props.rawFilter))
    const response = await axios.post('http://localhost:8080/learningGroup/filter', toRaw(props.rawFilter), {
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('sessionToken')}`
      }
    });
    console.log('Group elements:', response.data);
    groupElements.value = response.data;
  } catch (error) {
    console.error('Error fetching group elements:', error);
  }
}
</script>

<template>
  <div class="group-container">
    <GroupElement
      v-for="element in groupElements"
      :key="element.groupName"
      :groupName="element.groupName"
      :startTime="element.startTime"
      :endTime="element.endTime"
      :courseOfStudy="element.subject"
      :groupID="element.id"
      :owner="element.owner"
      :member="element.members"
    />
  </div>
</template>

<style scoped>
.group-container {
  display: flex;
  flex-wrap: wrap;
  gap: 25px;
  align-items: center;
  justify-content: center;
  padding: 0px;
  padding-top: 200px;
}
</style>
