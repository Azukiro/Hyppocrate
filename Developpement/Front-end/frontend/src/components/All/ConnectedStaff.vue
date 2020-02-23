<template>
  <div class="d-flex justify-center align-center">
    <v-card-actions>
      <v-img width="50px" height="50px" :src="icon" />
    </v-card-actions>
    <v-card-title class="headline-2 ml-1 pa-0 white--text">{{ firstName }} {{ lastName }}</v-card-title>
  </div>
</template>

<script>
import { getters } from "@/store.js";
export default {
  name: "ConnectedStaff",
  computed: {
    ...getters,
    lastName() {
      return this.user.lastName;
    },
    firstName() {
      return this.user.firstName;
    },
    icon() {
      return this.user.icon;
    }
  }
};
</script>