<template>
  <v-card color="transparent" outlined height="100%" class="d-flex justify-center align-center">
    <v-card outlined width="40%" class="d-flex flex-column justify-center align-center">
      <v-card-title class="headline text-center">Créer un hôpital</v-card-title>

      <v-card color="transparent" outlined width="70%">
        <v-form ref="form">
          <v-text-field
            label="Nom de l'hôpital"
            outlined
            v-model="form.hospitalName"
            :rules="$rules('Hospital name')"
          ></v-text-field>

          <v-text-field
            label="Emplacement"
            outlined
            v-model="form.localisation"
            :rules="$rules('Localisation')"
          ></v-text-field>
        </v-form>
      </v-card>

      <v-card-actions>
        <v-btn color="green" class="ma-2 white--text" @click="create">
          Créer
          <v-icon right>mdi-plus</v-icon>
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-card>
</template>

<script>
export default {
  name: "CreateHospital",
  data() {
    return {
      form: {
        hospitalName: "",
        localisation: ""
      }
    };
  },
  methods: {
    create() {
      if (this.$refs.form.validate()) {
        this.$request(
          "POST",
          "/infrastructures/unit",
          this.form,
          "L'hospital a été créé avec succès !",
          response => (this.staffTypes = response),
          "Echec de la création de l'hospital !",
          () => {}
        );
      }
    }
  }
};
</script>

<style>
</style>