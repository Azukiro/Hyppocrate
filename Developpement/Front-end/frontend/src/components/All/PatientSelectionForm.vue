<template>
  <v-card color="transparent" outlined width="90%">
    <div>
      <p class="headline py-5 text-center black--text">Choix du dossier médical</p>

      <div class="d-flex justify-center align-center">
        <v-card
          width="50%"
          color="transparent"
          outlined
          class="d-flex justify-space-around align-center"
        >
          <div width="40%">
            <v-text-field @change="fetch" label="Rechercher" outlined v-model="form.search"></v-text-field>
          </div>

          <div width="40%">
            <v-select
              @change="fetch"
              v-model="form.sortColumnName"
              :items="selectItems"
              item-text="printableName"
              item-value="sortColumnName"
              label="Trier par"
              outlined
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
          v-for="({ firstName, lastName, birthdayDate }, i) in patients"
          :key="i"
          @click="onPatientSelection(i)"
          class="onAction"
        >
          <v-avatar class="ma-3" width="90px" height="90px" tile>
            <v-img src="@/assets/logos/icons/types/black/patient.png" />
          </v-avatar>

          <v-card-title
            class="headline black--text"
          >{{ firstName }} {{ lastName }}, {{ birthdayDate }}</v-card-title>
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
export default {
  firstName: "PatientSelectionForm",

  props: ["form", "selectItems", "patients", "hasNext"],

  computed: {
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
    onPatientSelection(i) {
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
