<template>
  <v-card color="transparent" outlined height="90%" class="d-flex justify-center align-center">
    <PrintDmpForm
      :form="form"
      :selectItems="selectItems"
      :acts="acts"
      @change="fetchActs"
      @selection="onActClick"
    />
  </v-card>
</template>

<script>
import PrintDmpForm from "@/components/All/PrintDmpForm.vue";
import { getters, mutations } from "@/store.js";

export default {
  name: "PrintDmp",
  components: { PrintDmpForm },
  created() {
    this.fetchSortItems();
    this.fetchActs();
    this.acts = getters.addActInformations(this.acts);
  },

  computed: {
    ...getters,
    requestForm() {
      return {
        ...this.form,
        patientId: this.selectedPatient.patientId
      };
    }
  },

  data() {
    return {
      form: {
        search: "",
        sortColumnName: "",
        paginationNumber: 1,
        paginationLength: 4
      },
      selectItems: [],
      acts: []
    };
  },
  methods: {
    fetchSortItems() {
      this.$request(
        "GET",
        "/dmp/print/sort-items",
        {},
        // empty
        "Chargement de la liste de critère de tri effectuée !",
        response => (this.selectItems = response),
        // [{sortColumnName, printableName}]
        "Impossible de charger la liste des critères de tri !",
        () => {}
      );
    },
    fetchActs() {
      this.$request(
        "GET",
        "/dmp/print/all",
        this.requestForm,
        // {
        //   patientId,
        //   sortColumnName,
        //   search,
        //   paginationNumber,
        //   paginationLength
        // },
        "Chargement des actes du DMP effectué !",
        response => (this.acts = response),
        "Impossible de charger les actes du DMP !",
        () => {}
        // [
        //   {
        //     actId,
        //     actType,
        //     type,
        //     title,
        //     staffId,
        //     staffType, // Ajout get icon + descriptif type en string #TODO#
        //     staffEmail, // Ajout popup contact
        //     staffPhoneNumber, // Au niveau de print act
        //     description,
        //     date,
        //     link,
        //     staffFirstName,
        //     staffLastName,
        //     patientFirstName,
        //     patientLastName
        //   }
        // ]
      );
    },
    onActClick(i) {
      this.setSelectedAct(this.acts[i]);
      this.$router.push("/print-act");
    },
    ...mutations
  }
};
</script>
