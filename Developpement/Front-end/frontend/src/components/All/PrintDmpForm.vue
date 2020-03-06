<template>
  <v-card color="transparent" outlined width="90%">
    <div>
      <p class="headline pt-5 text-center black--text">Consulter le dossier médical</p>

      <SelectedPatient />

      <div class="d-flex justify-center align-center">
        <v-card
          width="50%"
          color="transparent"
          outlined
          class="d-flex justify-space-around align-center"
        >
          <div width="40%">
            <v-text-field label="Rechercher" outlined v-model="form.search" @change="fetch"></v-text-field>
          </div>

          <div width="40%">
            <v-select
              v-model="form.sortColumnName"
              :items="selectItems"
              item-text="printableName"
              item-value="sortColumnName"
              label="Trier par"
              outlined
              @change="fetch"
            ></v-select>
          </div>
        </v-card>
      </div>
    </div>

    <div class="d-flex flex-row justify-space-around align-center">
      <v-card color="transparent" outlined width="20%" class="d-flex justify-end">
        <v-btn icon color="black" @click="onPaginate_Left" :disabled="paginateLeftDisabled">
          <v-icon large>mdi-arrow-left-bold</v-icon>
        </v-btn>
      </v-card>

      <v-card
        color="transparent"
        outlined
        class="d-flex flex-column justify-space-around align-start"
      >
        <v-list-item
          style="width: 100%;"
          v-for="({ actIcon, actTypeName, title, staffFirstName, staffLastName, staffTypeName, actType, date }, i) in acts"
          :key="i"
          @click="onSelection(i)"
          class="onAction"
        >
          <v-avatar class="ma-3" width="90px" height="90px" tile>
            <v-img :src="actIcon" />
          </v-avatar>

          <v-card color="transparent" outlined>
            <v-card-title class="headline black--text">{{ title }}</v-card-title>

            <v-card-text class="headline-2 black--text">
              <ul>
                <li>{{ staffFirstName }} {{ staffLastName }}, {{ staffTypeName }}</li>
                <li>{{ actTypeName }}</li>
                <li>Fait le {{ date }}</li>
              </ul>
            </v-card-text>
          </v-card>
        </v-list-item>
      </v-card>

      <v-card color="transparent" outlined width="20%">
        <v-btn icon color="black" @click="onPaginate_Right" :disabled="paginateRightDisabled">
          <v-icon large>mdi-arrow-right-bold</v-icon>
        </v-btn>
      </v-card>
    </div>
  </v-card>
</template>

<script>
import { getters } from "@/store.js";
import SelectedPatient from "@/components/All/SelectedPatient.vue";

export default {
  name: "PrintDmp",
  components: { SelectedPatient },
  props: ["form", "selectItems", "acts", "hasNext"],
  computed: {
    ...getters,
    paginateLeftDisabled() {
      return this.form.paginationNumber <= 1;
    },
    paginateRightDisabled() {
      return !this.hasNext;
    }
  },
  methods: {
    fetch() {
      this.$emit("change");
    },
    onSelection(i) {
      this.$emit("selection", i);
    },
    onPaginate_Left() {
      this.form.paginationNumber -= this.paginateLeftDisabled ? 0 : 1;
      this.fetch();
    },
    onPaginate_Right() {
      this.form.paginationNumber += this.paginateRightDisabled ? 0 : 1;
      this.fetch();
    }
  }
};
</script>

<style>
.onAction:hover {
  background-color: rgba(255, 255, 255, 0.1);
  cursor: pointer;
}
</style>