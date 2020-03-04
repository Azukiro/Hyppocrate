<template>
  <v-card color="transparent" outlined height="100%" class="d-flex justify-center align-center">
    <v-card outlined width="40%" class="d-flex flex-column justify-center align-center">
      <v-card-title class="headline text-center">Saisir un acte médical</v-card-title>

      <SelectedPatient />

      <v-card color="transparent" outlined width="70%">
        <v-form ref="form">
          <v-select
            v-model="form.typeId"
            :items="actTypes"
            item-text="typeName"
            item-value="typeId"
            label="Type"
            outlined
            :rules="$rules('Type')"
          ></v-select>

          <v-text-field label="Titre" outlined v-model="form.title" :rules="$rules('Title')" />

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
import { getters } from "@/store.js";

export default {
  name: "CreateAct",
  components: { SelectedPatient },
  computed: {
    ...getters,
    actTypes() {
      return getters.actTypes();
    },
    requestForm() {
      return {
        staffId: this.user.id,
        patientId: this.selectedPatient.patientId,
        ...this.form
      };
    }
  },
  data() {
    return {
      form: {
        title: "",
        typeId: "", // Ajout liste déroulante
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
        this.requestForm,
        // {
        //   staffId,
        //   patientId,
        //   title,
        //   typeId,
        //   description,
        //   file: "chemin fichier 1|chemin fichier 2"
        // },
        "L’acte médical a été sauvegardé en tant que brouillon !",
        () => {},
        //  empty
        "L'archivage a échoué !",
        () => {}
      );
    },
    saveAsAct() {
      if (this.$refs.form.validate()) {
        this.$request(
          "POST",
          "/medical-act/publish",
          this.requestForm,
          // {
          //   staffId,
          //   patientId,
          //   title,
          //   typeId,
          //   description,
          //   file: "chemin fichier 1|chemin fichier 2"
          // },
          "L’acte médical a été publié avec succès !",
          () => {},
          //  empty
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