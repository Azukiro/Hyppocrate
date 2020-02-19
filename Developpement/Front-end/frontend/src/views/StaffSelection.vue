<template>
  <v-card color="transparent" outlined height="90%" class="d-flex justify-center align-center">
    <PatientSelectionForm
      :form="form"
      :selectItems="selectItems"
      :patients="staff"
      @change="fetchSortItems"
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
      staff: [
        {
          firstName: "Ewen",
          lastName: "Bouquet",
          birthday: "22/11/2000",
          icon: require("@/assets/logos/icons/types/black/doctor.png")
        },
        {
          firstName: "Lucas",
          lastName: "Billard",
          birthday: "10/01/2000",
          icon: require("@/assets/logos/icons/types/black/doctor.png")
        },
        {
          firstName: "Vincent",
          lastName: "Buisset",
          birthday: "24/05/2000",
          icon: require("@/assets/logos/icons/types/black/nurse.png")
        },
        {
          firstName: "Naoufal",
          lastName: "Arradi",
          birthday: "01/01/2000",
          icon: require("@/assets/logos/icons/types/black/doctor.png")
        }
      ]
    };
  },
  methods: {
    ...mutations,
    fetchSortItems() {
      this.$request(
        "GET",
        "/profile/all",
        {},
        "Les types de profils ont été chargés !",
        response => (this.selectItems = response),
        "Echec lors du chargement des profils !",
        () => {}
      );
    },
    fetchStaff() {
      this.$request(
        "GET",
        "/patient/search/all",
        this.form,
        "Le personnel a été chargé !",
        response => (this.selectItems = response),
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