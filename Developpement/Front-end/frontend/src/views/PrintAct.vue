<template>
  <v-card color="transparent" outlined height="90%" class="d-flex justify-center align-center">
    <v-card width="30%" color="#0c0c2" outlined class="d-flex flex-column align-center pa-5">
      <v-card-title class="mb-5">{{ title }}</v-card-title>

      <v-card-text class="text-center">{{ description }}</v-card-text>

      <v-list-item @click="$downloadFile(fileLink, fileName)">
        <div class="d-flex align-center">
          <v-card-actions>
            <v-img
              width="50px"
              height="50px"
              src="@/assets/logos/icons/actions/black/download-file.png"
            />
          </v-card-actions>
          <v-card-subtitle>
            {{ type }}, le {{ date }}.
            <br />Fait par
            <b>{{ staffFullName }}, {{ staffType }}</b>
          </v-card-subtitle>
        </div>
      </v-list-item>
    </v-card>
  </v-card>
</template>

<script>
import { getters } from "@/store.js";

export default {
  name: "PrintAct",
  computed: {
    staffFullName() {
      return this.selectedAct.staffLastName + " " + this.selectedAct.staffName;
    },
    staffType() {
      return this.selectedAct.staffType;
    },
    title() {
      return this.selectedAct.title;
    },
    type() {
      return this.selectedAct.type;
    },
    description() {
      return this.selectedAct.description;
    },
    date() {
      return this.selectedAct.date;
    },
    fileLink() {
      // return require("@/assets/logos/hippocrate/APHP.png");
      // Gérer la liste de fichiers ...
      return require("@/assets/documents/" + this.selectedAct.link);
    },
    fileName() {
      return this.selectedAct.fileName;
    },
    ...getters
  }
};
</script>