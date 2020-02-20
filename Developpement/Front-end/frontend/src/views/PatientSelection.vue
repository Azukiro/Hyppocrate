<template>
  <v-card color="transparent" outlined height="90%" class="d-flex justify-center align-center">
    <PatientSelectionForm
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
      form: {
        search: "",
        columnName: "",
        paginationNumber: 0,
        paginationLength: 4
      },
      selectItems: ["Prénom", "Nom", "Date de naissance"],
      patients: [
        {
          id: 0,
          lastName: "Ewen",
          firstName: "Bouquet",
          birthdayDate: "22/11/2000"
        },
        {
          id: 1,
          lastName: "Lucas",
          firstName: "Billard",
          birthdayDate: "10/01/2000"
        },
        {
          id: 2,
          lastName: "Vincent",
          firstName: "Buisset",
          birthdayDate: "24/05/2000"
        },
        {
          id: 3,
          lastName: "Naoufal",
          firstName: "Arradi",
          birthdayDate: "01/01/2000"
        }
      ]
    };
  },
  methods: {
    fetchSortItems() {
      this.$request(
        "GET",
        "/patient/search/sort-items",
        {},
        // empty
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
        //   search,
        //   paginationNumber,
        //   paginationLength
        // },
        "Chargement des DMP effectué !",
        // {
        //   id,
        //   lastName,
        //   firstName,
        //   birthdayDate
        // }
        () => {},
        // response => (this.patients = response),
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
