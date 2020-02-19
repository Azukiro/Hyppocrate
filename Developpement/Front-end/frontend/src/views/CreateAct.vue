<template>
  <v-card color="transparent" outlined height="100%" class="d-flex justify-center align-center">
    <v-card outlined width="40%" class="d-flex flex-column justify-center align-center">
      <v-card-title class="headline text-center">Saisir un acte médical</v-card-title>

      <SelectedPatient />

      <v-card color="transparent" outlined width="70%">
        <v-form ref="form">
          <v-text-field label="Titre" outlined v-model="form.title" :rules="$rules('Title')" />

          <v-text-field label="Type" outlined v-model="form.type" :rules="$rules('Type')" />

          <v-textarea
            outlined
            label="Description"
            v-model="form.description"
            :rules="$rules('Description')"
          />

          <v-file-input label="Fichier" @blur="fileAdded" />
        </v-form>
      </v-card>

      <v-card-actions>
        <v-btn color="#2c96fa" class="ma-2 white--text" @click="saveAsDraft">
          Archiver
          <v-icon right>mdi-clipboard-plus</v-icon>
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

export default {
  name: "CreateAct",
  components: { SelectedPatient },
  data() {
    return {
      form: {
        title: "",
        type: "", // Ajout liste déroulante
        description: "",
        file: {}
      }
    };
  },
  methods: {
    saveAsDraft() {
      this.$request(
        "POST",
        "/medical-act/draft",
        this.form,
        // {
        //   nodeId: -1,
        //   staffId,
        //   patientId,
        //   title,
        //   type,
        //   description,
        //   file: "chemin fichier"
        // },
        "L’acte médical a été sauvegardé en tant que brouillon !",
        () => {},
        "L'archivage a échoué !",
        () => {}
      );
    },
    saveAsAct() {
      if (this.$refs.form.validate()) {
        this.$request(
          "POST",
          "/medical-act/publish",
          this.form,
          // {
          //   nodeId: -1,
          //   staffId,
          //   patientId,
          //   title,
          //   type,
          //   description,
          //   file: "chemin fichier"
          // },
          "L’acte médical a été publié avec succès !",
          () => {},
          "La publication a échoué !",
          () => {}
        );
      }
    },
    fileAdded(event) {
      this.form.file = event;
    }
  }
};
</script>