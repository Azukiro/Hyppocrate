<template>
  <v-card color="transparent" outlined height="100%" class="d-flex justify-center align-center">
    <v-card outlined width="40%" class="d-flex flex-column justify-center align-center">
      <v-card-title class="headline text-center">Créer un secteur</v-card-title>

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
            @change="fetchPoles"
          ></v-select>

          <v-select
            label="Nom du pôle"
            outlined
            v-model="form.poleId"
            :rules="$rules('Pole name')"
            :items="poles"
            item-text="poleName"
            item-value="poleId"
          ></v-select>

          <v-text-field
            label="Nom du secteur"
            outlined
            v-model="form.sectorName"
            :rules="$rules('Sector name')"
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
  name: "CreatePole",
  computed: {
    ...getters,
    form() {
      return this.selectedUnit;
    }
  },
  created() {
    this.fetchHospitals();
    this.fetchPoles();
  },
  data() {
    return {
      hospitals: [],
      poles: [] // Ajouter type labo
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
    fetchPoles() {
      this.$request(
        "GET",
        "/infrastructure/pole",
        {
          nodeId: this.form.hospitalId
        },
        // { hospitalId }
        "Les pôles ont été chargés !",
        // { poleId, poleName, poleLeaderId, poleLeaderFirstName, poleLeaderLastName }
        response => (this.poles = response),
        "Echec lors du chargement des pôles !",
        () => {}
      );
    },
    create() {
      if (this.$refs.form.validate()) {
        this.$request(
          "POST",
          "/infrastructure/unit",
          {
            fatherId: this.form.poleId,
            name: this.form.poleName,
            staffLeaderId: this.user.id
          },
          // {
          //   fatherId: (poleId)
          //   name,
          //   staffLeaderId,
          //   isLaboratory: (boolean)
          // }
          "Le pôle a été créé avec succès !",
          //  empty
          response => (this.staffTypes = response),
          "Echec de la création du pôle !",
          () => {}
        );
      }
    }
  }
};
</script>

<style>
</style>