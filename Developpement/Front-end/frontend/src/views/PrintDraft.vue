<template>
  <v-card color="transparent" outlined height="90%" class="d-flex justify-center align-center">
    <v-card outlined width="40%" class="d-flex flex-column justify-center align-center">
      <v-card-title class="headline text-center">Mon brouillon</v-card-title>

      <SelectedPatient />

      <v-card color="transparent" outlined width="70%">
        <v-form ref="form">
          <v-text-field label="Titre" outlined v-model="form.title" :rules="$rules('Title')"></v-text-field>

          <v-text-field label="Type" outlined v-model="form.type" :rules="$rules('Type')"></v-text-field>

          <v-textarea
            outlined
            label="Description"
            v-model="form.description"
            :rules="$rules('Description')"
          ></v-textarea>

          <v-file-input label="Fichier" @blur="fileAdded"></v-file-input>
        </v-form>
      </v-card>

      <v-card-actions>
        <v-btn color="red" class="ma-2 white--text" @click="deleteDraft">
          Supprimer
          <v-icon right>mdi-trash-can-outline</v-icon>
        </v-btn>

        <v-btn color="#2c96fa" class="ma-2 white--text" @click="saveAsDraft">
          Enregistrer
          <v-icon right>mdi-clipboard-text</v-icon>
        </v-btn>

        <v-btn color="green" class="ma-2 white--text" @click="saveAsAct">
          Publier
          <v-icon right>mdi-account-alert</v-icon>
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-card>
</template>

<script>
import SelectedPatient from "@/components/All/SelectedPatient.vue";
import { getters, mutations } from "@/store.js";

export default {
  name: "PrintDraft",
  components: { SelectedPatient },
  computed: {
    form() {
      return {
        title: this.selectedDraft.title,
        type: this.selectedDraft.type,
        description: this.selectedDraft.description,
        file: this.selectedDraft.file
      };
    },
    ...getters
  },
  data() {
    return {
      file: {}
    };
  },
  methods: {
    ...mutations,
    saveAsDraft() {
      this.$request(
        "POST",
        "/draft/actions/publish",
        this.form,
        // {
        //   patientId,
        //   title,
        //   description,
        //   draftId,
        //   fileName
        // },
        "Le brouillon a été publié avec succès !",
        () => {},
        "Echec de la publication du brouillon !",
        () => {}
      );
    },
    saveAsAct() {
      if (this.$refs.form.validate()) {
        this.$request(
          "POST",
          "/draft/actions/update",
          this.form,
          // {
          //   patientId,
          //   title,
          //   description,
          //   draftId,
          //   fileName
          // },
          "L’acte médical a été sauvegardé en tant que brouillon !",
          () => {},
          "Echec de création du brouillon !",
          () => {}
        );
      }
    },
    deleteDraft() {
      this.$request(
        "DELETE",
        "/draft/actions/delete",
        this.form,
        // {
        //   draftId
        // },
        "Le brouillon a été supprimé avec succès !",
        () => {},
        "Echec de suppression du brouillon !",
        () => {}
      );
    },
    fileAdded(event) {
      this.file = event;
    }
  }
};
</script>