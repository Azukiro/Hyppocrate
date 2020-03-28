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
import { getters } from "@/store.js";

export default {
  name: "CreateHospital",
  data() {
    return {
      form: {
        hospitalName: ""
      }
    };
  },
  computed: {
    ...getters
  },
  methods: {
    create() {
      if (this.$refs.form.validate()) {
        this.$request(
          "POST",
          "/infrastructure/unit",
          {
            fatherId: -1,
            name: this.form.hospitalName,
            staffLeaderId: this.user.id
          },
          // {
          //   fatherId: -1
          //   name,
          //   staffLeaderId
          // }
          "L'hospital a été créé avec succès !",
          () => {},
          //  empty
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