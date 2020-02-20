<template>
  <v-card color="transparent" outlined height="90%" class="d-flex justify-center align-center">
    <DraftSelectionForm
      :form="form"
      :selectItems="selectItems"
      :drafts="drafts"
      @change="fetchDrafts"
      @selection="onDraftClick"
    />
  </v-card>
</template>

<script>
import { mutations } from "@/store.js";
import DraftSelectionForm from "@/components/All/DraftSelectionForm.vue";

export default {
  name: "DraftSelection",
  components: { DraftSelectionForm },

  created() {
    this.fetchSortItems();
    this.fetchDrafts();
  },

  data() {
    return {
      form: {
        search: "",
        columnName: "",
        paginationNumber: 0,
        paginationLength: 3
      },
      selectItems: [
        "Prénom",
        "Nom",
        "Date de naissance",
        "Ordonnances",
        "Radiologie",
        "Actes médicaux"
      ],
      drafts: [
        {
          lastName: "Ewen",
          name: "Bouquet",
          date: "02/02/2018",
          title: "Résultats de radio",
          type: "Radiologie",
          description: "Rien à signaler",
          icon: require("@/assets/logos/icons/actions/black/radiology.png"),
          file: {}
        },
        {
          lastName: "Lucas",
          name: "Billard",
          date: "07/02/2019",
          title: "Ordonnance",
          type: "Ordonnance",
          description: "Rien à signaler",
          icon: require("@/assets/logos/icons/actions/black/prescription.png"),
          file: {}
        },
        {
          lastName: "Vincent",
          name: "Buisset",
          date: "02/03/2018",
          title: "Résultats d'examens médicaux",
          type: "Examens",
          description: "Rien à signaler",
          icon: require("@/assets/logos/icons/actions/black/exam-results.png"),
          file: {}
        }
      ]
    };
  },
  methods: {
    ...mutations,
    fetchSortItems() {
      this.$request(
        "GET",
        "/draft/print/sort-items",
        {},
        // empty
        "Chargement de la liste de critère de tri effectuée !",
        response => (this.selectItems = response),
        // {
        //   sortColumnName,
        //   printableName
        // },
        "Impossible de charger la liste des critères de tri !",
        () => {}
      );
    },
    fetchDrafts() {
      this.$request(
        "GET",
        "/draft/print/all",
        this.form, //Type non modifiable
        // {
        //   staffId,
        //   patientId,
        //   sortColumnName,
        //   search,
        //   paginationNumber,
        //   paginationLength
        // },
        "Chargement des brouillons effectué !",
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
        () => {},
        // response => (this.drafts = response),
        "Aucun brouillé ne correspond à vos critères !",
        () => {}
      );
    },
    onDraftClick(i) {
      this.setSelectedDraft(this.drafts[i]);
      this.setSelectedPatient({
        name: this.drafts[i].lastName + " " + this.drafts[i].name
      });
      this.$router.push("/print-draft");
    }
  }
};
</script>