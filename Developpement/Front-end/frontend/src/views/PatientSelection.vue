<template>
  <v-card color="transparent" outlined height="90%" class="d-flex justify-center align-center mt-5">
    <PatientSelectionForm
      title="Choix du dossier médical"
      :hasNext="hasNext"
      :form="form"
      :selectItems="selectItems"
      :patients="patients"
      @change="fetchRecords"
      @selection="onRecordClick"
    />
  </v-card>
</template>

<script>
import { mutations } from "@/store.js";
import PatientSelectionForm from "@/components/All/PatientSelectionForm.vue";

export default {
  firstName: "PatientSelection",
  components: { PatientSelectionForm },

  created() {
    this.fetchSortItems();
    this.fetchRecords();
  },
  data() {
    return {
      hasNext: false,
      form: {
        search: "",
        sortColumnName: "",
        paginationNumber: 1,
        paginationLength: 4
      },
      selectItems: [],
      patients: []
    };
  },
  methods: {
    fetchSortItems() {
      this.$request(
        "GET",
        "/patient/search/sort-items",
        {},
        "Chargement de la liste de critère de tri effectuée !",
        response => (this.selectItems = response),
        // [{sortColumnName, printableName}]
        "Impossible de charger la liste des critères de tri !",
        () => {}
      );
    },
    fetchRecords() {
      this.$request(
        "GET",
        "/patient/search/all",
        this.form,
        // {
        //   sortColumnName,
        //   form.search,
        //   form.paginationNumber,
        //   paginationLength: this.form.paginationLength
        // },
        "Chargement des DMP effectué !",
        // {
        //   id,
        //   lastName,
        //   firstName,
        //   birthdayDate
        // }
        response => {
          this.patients = response.result;
          this.hasNext = response.hasNext;
        },
        "Aucun DMP ne correspond à vos critères !",
        () => {}
      );
    },
    onRecordClick(i) {
      this.setSelectedPatient(this.patients[i]);
      this.$router.push("/actions/select-patient");
    },
    ...mutations
  }
};
</script>
