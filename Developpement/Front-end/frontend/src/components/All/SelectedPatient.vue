<template>
  <div class="d-flex justify-center align-center">
    <v-card-actions>
      <v-img width="60px" height="60px" src="@/assets/logos/icons/types/black/patient.png" />
    </v-card-actions>
    <v-card-title class="headline-2">{{ firstName }} {{ lastName }}</v-card-title>
  </div>
</template>

<script>
import { getters } from "@/store.js";
export default {
  firstName: "SelectedPatient",
  computed: {
    ...getters,
    firstName() {
      return this.selectedPatient.firstName;
    },
    lastName() {
      return this.selectedPatient.lastName;
    }
  }
};
</script>