<template>
  <v-card
    color="transparent"
    height="100%"
    outlined
    class="d-flex flex-column justify-space-around align-center"
  >
    <v-card outlined width="40%" class="d-flex flex-column justify-center align-center my-5">
      <v-card-title class="headline text-center">Affecter à un personnel médical</v-card-title>

      <v-card color="transparent" outlined width="70%">
        <v-form>
          <v-select
            v-model="form.staff"
            :items="staff"
            item-text="text"
            item-value="value"
            label="Personnel"
            outlined
          />

          <v-select v-model="form.hospital" :items="hospitals" label="Hôpital" outlined />

          <v-select v-model="form.pole" :items="poles" label="Pôle" outlined />

          <v-select v-model="form.sector" :items="sectors" label="Secteur" outlined />
        </v-form>
      </v-card>

      <v-card-actions>
        <v-btn color="#2c96fa" class="ma-2 white--text">
          Affecter
          <v-icon right>mdi-plus</v-icon>
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-card>
</template>

<script>
import { getters } from "@/store.js";

export default {
  name: "AffectStaff",

  computed: {
    ...getters,
    form() {
      return this.selectedUnit;
    }
  },

  data() {
    return {
      staff: [
        {
          text: "Jean Bourali - Médecin traitant",
          value: 0
        },
        {
          text: "Philippe Delacourt - Dentiste",
          value: 1
        },
        {
          text: "Jérôme Garcia - Cardiologue",
          value: 2
        }
      ],
      hospitals: ["H1", "H2", "H3", "H4"],
      poles: ["P1", "P2", "P3", "P4"],
      sectors: ["S1", "S2", "S3", "S4"]
    };
  },
  methods: {
    fetchHospitals() {
      this.$request(
        "GET",
        "/infrastructures/hospital",
        {},
        "Les hôpitaux ont été chargés !",
        response => (this.hospitals = response),
        "Echec lors du chargement des hôpitaux !",
        () => {}
      );
    },
    fetchSectors() {
      this.$request(
        "GET",
        "/infrastructures/sector",
        {},
        "Les secteurs ont été chargés !",
        response => (this.sectors = response),
        "Echec lors du chargement des secteurs !",
        () => {}
      );
    }
  }
};
</script>