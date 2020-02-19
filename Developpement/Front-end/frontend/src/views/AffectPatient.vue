<template>
  <v-card color="transparent" outlined class="d-flex flex-column justify-space-around align-center">
    <div class="d-flex justify-center align-center my-5">
      <v-card-actions>
        <v-img width="70px" height="70px" src="@/assets/logos/icons/types/black/patient.png" />
      </v-card-actions>
      <v-card-title class="headline-2">{{ patient.lastName }} {{ patient.name }}</v-card-title>
    </div>

    <v-card outlined width="40%" class="d-flex flex-column justify-center align-center my-5">
      <v-card-title class="headline text-center">Affecter à un personnel médical</v-card-title>

      <v-card color="transparent" outlined width="70%">
        <v-form ref="form">
          <v-select
            v-model="form.hospital"
            :items="hospitals"
            label="Hôpital"
            outlined
            :rules="$rules('Hospital name')"
            @change="onHospitalSelectionChange"
          />

          <v-select
            v-model="form.pole"
            :items="poles"
            label="Pôle"
            outlined
            :rules="$rules('Pole name')"
            @change="onPoleSelectionChange"
          />

          <v-select
            v-model="form.sector"
            :items="sectors"
            label="Secteur"
            outlined
            :rules="$rules('Sector name')"
            @change="onSectorSelectionChange"
          />

          <v-select
            v-model="form.staff"
            :items="staff"
            item-text="text"
            item-value="value"
            label="Personnel"
            outlined
            :rules="$rules('Staff name')"
          />
        </v-form>
      </v-card>

      <v-card-actions>
        <v-btn color="#2c96fa" class="ma-2 white--text" @click="onPatientAffectation">
          Affecter
          <v-icon right>mdi-clipboard-plus</v-icon>
        </v-btn>
      </v-card-actions>
    </v-card>

    <v-card outlined width="40%" class="d-flex flex-column justify-center align-center my-5">
      <v-card-title class="headline text-center">Personnel médical affecté</v-card-title>

      <v-card
        max-width="1000px"
        color="transparent"
        outlined
        class="d-flex flex-column justify-space-around align-start"
      >
        <v-list-item
          style="width: 100%;"
          class="d-flex justify-space-between"
          v-for="({text, value, icon}, i) in affectedStaff"
          :key="i"
        >
          <v-avatar class="ma-3" width="60px" height="60px" tile>
            <v-img :src="icon" />
          </v-avatar>

          <v-card-title class="headline black--text">{{ text }}</v-card-title>

          <v-card-actions>
            <v-btn icon large color="black">
              <v-icon>mdi-account-multiple-remove</v-icon>
            </v-btn>
          </v-card-actions>
        </v-list-item>
      </v-card>
    </v-card>
  </v-card>
</template>

<script>
export default {
  name: "AffectPatient",

  created() {
    this.fetchHospitals();
    this.fetchPoles();
    this.fetchSectors();
    this.fetchStaff();
  },

  data() {
    return {
      patient: {
        lastName: "Ewen",
        name: "Bouquet"
      },
      form: {
        hospital: "",
        pole: "",
        sector: "",
        staff: ""
      },
      staff: [
        {
          text: "Jean Bourali - Médecin traitant",
          value: 0,
          icon: require("@/assets/logos/icons/types/black/doctor.png")
        },
        {
          text: "Philippe Delacourt - Dentiste",
          value: 1,
          icon: require("@/assets/logos/icons/types/black/laboratory-staff.png")
        },
        {
          text: "Jérôme Garcia - Cardiologue",
          value: 2,
          icon: require("@/assets/logos/icons/types/black/doctor.png")
        }
      ],
      hospitals: ["H1", "H2", "H3", "H4"],
      poles: ["P1", "P2", "P3", "P4"],
      sectors: ["S1", "S2", "S3", "S4"],
      affectedStaff: [
        {
          text: "Jean Bourali - Médecin traitant",
          value: 0,
          icon: require("@/assets/logos/icons/types/black/doctor.png")
        },
        {
          text: "Philippe Delacourt - Dentiste",
          value: 1,
          icon: require("@/assets/logos/icons/types/black/doctor.png")
        }
      ]
    };
  },
  methods: {
    onHospitalSelectionChange() {
      this.fetchPoles();
      this.fetchSectors();
      this.fetchStaff();
    },
    onPoleSelectionChange() {
      this.fetchSectors();
      this.fetchStaff();
    },
    onSectorSelectionChange() {
      this.fetchStaff();
    },
    fetchHospitals() {
      this.$request(
        "GET",
        "/infrastructures/hospital",
        {},
        // empty
        "Les hôpitaux ont été chargés !",
        response => (this.hospitals = response),
        // { hospitalId, hospitalName }
        "Echec lors du chargement des hôpitaux !",
        () => {}
      );
    },
    fetchPoles() {
      this.$request(
        "GET",
        "/infrastructures/poles",
        this.form,
        // { hospitalId }
        "Les pôles ont été chargés !",
        // { poleId, poleName }
        response => (this.poles = response),
        "Echec lors du chargement des pôles !",
        () => {}
      );
    },
    fetchSector() {
      this.$request(
        "GET",
        "/infrastructures/sector",
        this.form,
        // { poleId }
        "Les secteur ont été chargés !",
        response => (this.sectors = response),
        // { sectorId, sectorName }
        "Echec lors du chargement des secteur !",
        () => {}
      );
    },
    fetchStaff() {
      this.$request(
        "GET",
        "staff/print/unit",
        this.form,
        // { nodeId }
        "Les secteur ont été chargés !",
        response => (this.saff = response),
        // {
        //  staffName
        //  staffId
        //  staffType
        // }
        "Echec lors du chargement des secteur !",
        () => {}
      );
    },
    onPatientAffectation() {
      if (this.$refs.form.validate()) {
        this.$request(
          "POST",
          "/...",
          this.form,
          "L'affectation a été effectuée !",
          response => (this.saff = response),
          "Echec de l'affectation !",
          () => {}
        );
      }
    },
    fileAdded(event) {
      return event;
    }
  }
};
</script>