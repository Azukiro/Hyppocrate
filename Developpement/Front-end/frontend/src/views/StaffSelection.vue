<template>
  <v-card color="transparent" outlined class="d-flex justify-center align-center">
    <PatientSelectionForm
      title="Choix du personnel"
      :hasNext="hasNext"
      :form="form"
      :selectItems="selectItems"
      :patients="staff"
      @change="fetchStaff"
      @selection="onStaffClick"
    />
  </v-card>
</template>

<script>
import { mutations } from "@/store.js";
import PatientSelectionForm from "@/components/All/PatientSelectionForm.vue";

export default {
  name: "PatientSelection",
  components: { PatientSelectionForm },
  created() {
    this.fetchSortItems();
    this.fetchStaff();
  },
  data() {
    return {
      form: {
        search: "",
        columnName: "",
        paginationNumber: 1,
        paginationLength: 4
      },
      hasNext: false,
      selectItems: [],
      staff: []
    };
  },
  methods: {
    ...mutations,
    fetchSortItems() {
      this.$request(
        "GET",
        "/profile/all",
        {},
        // empty
        "Les types de profils ont été chargés !",
        response => (this.selectItems = response),
        // [{sortColumnName, printableName}]
        "Echec lors du chargement des profils !",
        () => {}
      );
    },
    fetchStaff() {
      this.$request(
        "GET",
        "/staff/search/all",
        this.form,
        // {
        //   sortColumnName,
        //   search,
        //   paginationNumber,
        //   paginationLength
        // },
        "Le personnel a été chargé !",
        response => {
          this.hasNext = response.hasNext;
          this.staff = response.result;
        },
        // {
        //   staffId,
        //   staffType
        //   lastName,
        //   firstName,
        //   birthdayDate
        // }
        "Aucun personnel ne correspond à vos critères !",
        () => {}
      );
    },
    onStaffClick(i) {
      this.setSelectedStaff(this.staff[i]);
      this.$router.push("/actions/select-staff");
    }
  }
};
</script>