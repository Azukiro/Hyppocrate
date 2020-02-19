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
import { mutations } from "@/store.js";

export default {
  name: "PrintDmp",
  components: { PrintDmpForm },
  created() {
    this.fetchSortItems();
    this.fetchActs();
  },
  data() {
    return {
      form: {
        search: "",
        columnName: "",
        paginationNumber: 0,
        paginationLength: 4
      },
      selectItems: ["Actes médicaux", "Ordonnances", "Cardiologies", "IRM"],
      acts: [
        {
          icon: require("@/assets/logos/icons/actions/black/exam-results.png"),
          title: "Résultats d'examen",
          type: "Examen médical",
          date: "01/10/2019",
          hospital: "Hôpital Bicêtre",
          pole: "Département 36B",
          sector: "Secteur 12",
          staffLastName: "Ewen",
          staffName: "Bouquet",
          staffType: "Médecin généraliste",
          description: "Rien à noter"
        },
        {
          icon: require("@/assets/logos/icons/actions/black/prescription.png"),
          title: "Ordonnance",
          type: "Ordonnance",
          date: "01/11/2019",
          hospital: "Hôpital Bicêtre",
          pole: "Département 51",
          sector: "Secteur 9",
          staffLastName: "Ewen",
          staffName: "Bouquet",
          staffType: "Médecin généraliste",
          description: "Rien à noter"
        },
        {
          icon: require("@/assets/logos/icons/actions/black/radiology.png"),
          title: "Résultats de radio",
          type: "Radiologie",
          date: "17/01/2020",
          hospital: "Hôpital Bicêtre",
          pole: "Département 89A",
          sector: "Secteur 10",
          staffLastName: "Ewen",
          staffName: "Bouquet",
          staffType: "Médecin généraliste",
          description: "Rien à noter"
        }
      ]
    };
  },
  /*
    /dmp/print/types/sort-items/
    ""
    typeId,
    typeName
  */
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
        this.form,
        // {
        //   patientId,
        //   sortColumnName,
        //   search,
        //   paginationNumber,
        //   paginationLength
        // },
        "Chargement des actes du DMP effectué !",
        // response => (this.acts = response),
        () => {},
        "Impossible de charger les actes du DMP !",
        () => {}
        // {
        //   actId,
        //   type,
        //   title,
        //   staffId,
        //   staffTypeId, // Ajout get icon + descriptif type en string
        //   staffEmail, // Ajout popup contact
        //   staffPhoneNumber, // Au niveau de print act
        //   description,
        //   date,
        //   link,
        //   staffFirstName,
        //   staffLastName
        // }
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
