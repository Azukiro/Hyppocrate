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
import { getters, mutations } from "@/store.js";
import DraftSelectionForm from "@/components/All/DraftSelectionForm.vue";

export default {
  name: "DraftSelection",
  components: { DraftSelectionForm },

  created() {
    this.fetchSortItems();
    this.fetchDrafts();
    this.drafts = getters.addActInformations(this.drafts);
  },

  computed: {
    ...getters,
    requestForm() {
      return {
        ...this.form,
        staffId: this.user.id
      };
    }
  },

  data() {
    return {
      form: {
        search: "",
        sortColumnName: "",
        paginationNumber: 1,
        paginationLength: 3
      },
      selectItems: [],
      drafts: [
        {
          actId: 1,
          actTypeId: 0,
          title: "Résultats médicaux",
          staffId: 0,
          staffTypeId: 0,
          staffEmail: "Serge.gas@gmail.com",
          staffPhoneNumber: "01561309",
          description: "Rien à noter",
          date: "01/11/2019",
          link: "test/file.txt",
          staffFirstName: "Ewen",
          staffLastName: "Bouquet",
          patientFirstName: "Jean",
          patientLastName: "Dujardin"
        },
        {
          actId: 2,
          actTypeId: 1,
          title: "Examen médical",
          staffId: 0,
          staffTypeId: 1,
          staffEmail: "Serge.gas@gmail.com",
          staffPhoneNumber: "01561309",
          description: "Rien à noter",
          date: "01/10/2019",
          link: "test/file.txt",
          staffFirstName: "Ewen",
          staffLastName: "Bouquet",
          patientFirstName: "Jacques",
          patientLastName: "Dujardin"
        },
        {
          actId: 2,
          actTypeId: 2,
          title: "Résultats de radio",
          staffId: 0,
          staffTypeId: 3,
          staffEmail: "Serge.gas@gmail.com",
          staffPhoneNumber: "01561309",
          description: "Rien à noter",
          date: "17/01/2020",
          link: "test/file.txt",
          staffFirstName: "Ewen",
          staffLastName: "Bouquet",
          patientFirstName: "Gilles",
          patientLastName: "Dujardin"
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
        this.requestForm,
        // {
        //   staffId,
        //   sortColumnName,
        //   search,
        //   paginationNumber,
        //   paginationLength
        // },
        "Chargement des brouillons effectué !",
        // [
        //   {
        //     actId,
        //     type,
        //     title,
        //     staffId,
        //     staffTypeId, // Ajout get icon + descriptif type en string
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
        () => {},
        // response => (this.drafts = response),
        "Aucun brouillé ne correspond à vos critères !",
        () => {}
      );
    },
    onDraftClick(i) {
      this.setSelectedDraft(this.drafts[i]);
      this.setSelectedPatient({
        firstName: this.drafts[i].patientFirstName,
        lastName: this.drafts[i].patientLastName
      });
      this.$router.push("/print-draft");
    }
  }
};
</script>