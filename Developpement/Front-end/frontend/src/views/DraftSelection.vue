<template>
  <v-card color="transparent" outlined height="90%" class="d-flex justify-center align-center">
    <DraftSelectionForm
      :hasNext="hasNext"
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
      hasNext: false,
      form: {
        search: "",
        sortColumnName: "",
        paginationNumber: 1,
        paginationLength: 3
      },
      selectItems: [],
      drafts: []
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
        //     staffType, // Ajout get icon + descriptif type en string
        //     staffEmail, // Ajout popup contact
        //     staffPhoneNumber, // Au niveau de print act
        //     description,
        //     date,
        //     link,
        //     staffFirstName,
        //     staffName,
        //     patientFirstName,
        //     patientLastName
        //   }
        // ]
        response => {
          this.drafts = getters.addActInformations(response.result);
          this.hasNext = response.hasNext;
        },
        "Aucun brouillé ne correspond à vos critères !",
        () => {}
      );
    },
    onDraftClick(i) {
      this.setSelectedDraft(this.drafts[i]);
      console.log({
        firstName: this.drafts[i].patientFirstName,
        lastName: this.drafts[i].patientName
      });
      this.setSelectedPatient({
        firstName: this.drafts[i].patientFirstName,
        lastName: this.drafts[i].patientName
      });
      this.$router.push("/print-draft");
    }
  }
};
</script>