<template>
  <v-card color="transparent" outlined height="100%" class="d-flex justify-center align-center">
    <v-card outlined width="40%" class="d-flex flex-column justify-center align-center">
      <v-card-title class="headline text-center">Créer un pôle</v-card-title>

      <v-card color="transparent" outlined width="70%">
        <v-form ref="form">
          <v-select
            label="Nom de l'hôpital"
            :items="hospitals"
            item-text="hospitalName"
            item-value="hospitalId"
            outlined
            v-model="form.hospitalId"
            :rules="$rules('Hospital name')"
          ></v-select>

          <v-text-field
            label="Nom du pôle"
            outlined
            v-model="form.poleName"
            :rules="$rules('Pole name')"
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
  name: "CreateSector",
  computed: {
    ...getters,
    form() {
      return this.selectedUnit;
    }
  },
  created() {
    this.fetchHospitals();
  },
  data() {
    return {
      hospitals: []
    };
  },
  methods: {
    fetchHospitals() {
      this.$request(
        "GET",
        "/infrastructure/hospital",
        {},
        // empty
        "Les hôpitaux ont été chargés !",
        response => (this.hospitals = response),
        // { hospitalId, hospitalName, hospitalLeaderId, hospitalLeaderFirstName, hospitalLeaderLastName }
        "Echec lors du chargement des hôpitaux !",
        () => {}
      );
    },
    create() {
      if (this.$refs.form.validate()) {
        this.$request(
          "POST",
          "/infrastructure/unit",
          {
            fatherId: this.form.hospitalId,
            name: this.form.poleName,
            staffLeaderId: this.user.id
          },
          // {
          //   fatherId: (hospitalId)
          //   name,
          //   staffLeaderId
          // }
          "Le secteur a été créé avec succès !",
          response => (this.staffTypes = response),
          //  empty
          "Echec de la création de lu secteur !",
          () => {}
        );
      }
    }
  }
};
</script>

<style>
</style>