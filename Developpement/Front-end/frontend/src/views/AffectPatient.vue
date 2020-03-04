<template>
  <v-card
    color="transparent"
    outlined
    class="d-flex flex-column justify-space-around align-center py-5"
  >
    <SelectedPatient />

    <v-card outlined width="40%" class="d-flex flex-column justify-center align-center my-5">
      <v-card-title class="headline text-center">Affecter le patient à du personnel</v-card-title>

      <v-card color="transparent" outlined width="70%">
        <v-form ref="form">
          <v-select
            v-model="form.hospitalId"
            :items="hospitals"
            item-text="hospitalName"
            item-value="hospitalId"
            label="Hôpital"
            outlined
            :rules="$rules('Hospital name')"
            @change="onHospitalSelectionChange"
          />

          <v-select
            v-model="form.poleId"
            :items="poles"
            item-text="poleName"
            item-value="poleId"
            label="Pôle"
            outlined
            :rules="$rules('Pole name')"
            @change="onPoleSelectionChange"
          />

          <v-select
            v-model="form.sectorId"
            :items="sectors"
            item-text="sectorName"
            item-value="sectorId"
            label="Secteur"
            outlined
            :rules="$rules('Sector name')"
            @change="onSectorSelectionChange"
          />

          <v-select
            v-model="form.staffId"
            :items="staff"
            item-text="staffFullName"
            item-value="staffId"
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
          v-for="({ staffFullName, staffId, staffIcon }, i) in affectedStaff"
          :key="i"
        >
          <v-avatar class="ma-3" width="60px" height="60px" tile>
            <v-img :src="staffIcon" />
          </v-avatar>

          <v-card-title class="headline black--text">{{ staffFullName }}</v-card-title>

          <v-card-actions>
            <v-btn icon large color="black" @click="onPatientDeleteAffectation(staffId)">
              <v-icon>mdi-account-multiple-remove</v-icon>
            </v-btn>
          </v-card-actions>
        </v-list-item>
      </v-card>
    </v-card>
  </v-card>
</template>

<script>
import { getters } from "@/store.js";
import SelectedPatient from "@/components/All/SelectedPatient.vue";

export default {
  name: "AffectPatient",

  components: { SelectedPatient },

  created() {
    this.fetchHospitals();
    this.fetchPoles();
    this.fetchSectors();
    this.fetchStaff();
    this.fetchAffectedStaff();
  },

  data() {
    return {
      patient: {
        lastName: "Ewen",
        name: "Bouquet"
      },
      form: {
        hospitalId: -1,
        poleId: -1,
        sectorId: -1,
        staffId: -1
      },
      staff: [
        {
          staffId: 0,
          staffFullName: "SFN1 SLN1",
          staffType: 0
        },
        {
          staffId: 1,
          staffFullName: "SFN2 SLN2",
          staffType: 1
        },
        {
          staffId: 2,
          staffFullName: "SFN2 SLN2",
          staffType: 2
        }
      ],
      hospitals: [
        {
          hospitalId: 0,
          hospitalName: "H1",
          hospitalLeaderId: 0,
          hospitalLeaderFirstName: "HL1 HFN1"
        },
        {
          hospitalId: 1,
          hospitalName: "H2",
          hospitalLeaderId: 1,
          hospitalLeaderFirstName: "HL2 HFN2"
        }
      ],
      poles: [
        {
          poleId: 2,
          poleName: "P1",
          poleLeaderId: 2,
          poleLeaderFirstName: "PL1 PFN1"
        },
        {
          poleId: 3,
          poleName: "P2",
          poleLeaderId: 3,
          poleLeaderFirstName: "PL2 PFN2"
        }
      ],
      sectors: [
        {
          sectorId: 4,
          sectorName: "S1",
          sectorLeaderId: 4,
          sectorLeaderFirstName: "SL1 SFN1"
        },
        {
          sectorId: 5,
          sectorName: "S2",
          sectorLeaderId: 5,
          sectorLeaderFirstName: "SL2 SFN2"
        }
      ],
      affectedStaff: [
        {
          staffId: 0,
          staffFullName: "SFN1 SLN1",
          staffType: 0
        },
        {
          staffId: 1,
          staffFullName: "SFN2 SLN2",
          staffType: 1
        },
        {
          staffId: 2,
          staffFullName: "SFN2 SLN2",
          staffType: 2
        }
      ]
    };
  },
  computed: {
    ...getters
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
        // { hospitalId, hospitalName, hospitalLeaderId, hospitalLeaderFirstName, hospitalLeaderLastName }
        "Echec lors du chargement des hôpitaux !",
        () => {}
      );
    },
    fetchPoles() {
      this.$request(
        "GET",
        "/infrastructures/poles",
        {
          hospitalId: this.form.hospitalId
        },
        // { hospitalId }
        "Les pôles ont été chargés !",
        // { poleId, poleName, poleLeaderId, poleLeaderFirstName, poleLeaderLastName }
        response => (this.poles = response),
        "Echec lors du chargement des pôles !",
        () => {}
      );
    },
    fetchSectors() {
      this.$request(
        "GET",
        "/infrastructures/sector",
        {
          poleId: this.form.poleId
        },
        // { poleId }
        "Les secteur ont été chargés !",
        response => (this.sectors = response),
        // { sectorId, sectorName, sectorLeaderId, sectorLeaderFirstName, sectorLeaderLastName }
        "Echec lors du chargement des secteurs !",
        () => {}
      );
    },
    fetchStaff() {
      this.$request(
        "GET",
        "/staff/print/unit",
        { nodeId: this.getNodeId(this.form) },
        // { nodeId }
        "Les secteur ont été chargés !",
        response => (this.saff = response),
        // {
        //  staffId
        //  staffFullName
        //  staffType
        // }
        "Echec lors du chargement des secteurs !",
        () => {}
      );
    },
    onPatientAffectation() {
      if (this.$refs.form.validate()) {
        this.$request(
          "POST",
          "/patient/affect/staff",
          {
            nodeId: this.getNodeId(this.form),
            staffId: this.form.staffId,
            patientId: this.selectedPatient.id
          },
          // {
          //   nodeId
          //   staffId
          //   patientId
          // },
          "L'affectation a été effectuée !",
          response => (this.saff = response),
          //  empty
          "Echec de l'affectation !",
          () => {}
        );
      }
    },
    onPatientDeleteAffectation(staffId) {
      this.$request(
        "DELETE",
        "/staff/infos/assignment",
        {
          userId: this.selectedPatient.id,
          staffId
        },
        "L'affectation a bien été supprimée !",
        () => this.$emit("update"),
        //  empty
        "Echec de la suppression de l'affectation !",
        () => {}
      );
    },
    fetchAffectedStaff() {
      this.affectedStaff = getters.addStaffInformations(this.affectedStaff);
      //FORGOT...
    },
    deleteAffectation() {
      //FORGOT...
    },
    getNodeId({ hospitalId, poleId, sectorId }) {
      if (0 <= sectorId) return sectorId;
      if (0 <= poleId) return poleId;
      if (0 <= hospitalId) return hospitalId;
      return -1;
    },
    fileAdded(event) {
      return event;
    }
  }
};
</script>