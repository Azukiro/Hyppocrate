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
          <NodeSelection :form="form" :selectUnit="true" />
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
import NodeSelection from "@/components/All/NodeSelection.vue";

export default {
  name: "AffectPatient",

  components: { SelectedPatient, NodeSelection },

  created() {
    this.fetchAffectedStaff();
  },

  data() {
    return {
      patient: {
        lastName: "Ewen",
        name: "Bouquet"
      },
      form: {
        isLaboratory: false,
        hospitalId: -1,
        poleId: -1,
        sectorId: -1,
        staffId: -1
      },
      affectedStaff: []
    };
  },
  computed: {
    ...getters
  },
  methods: {
    onPatientAffectation() {
      if (this.$refs.form.validate()) {
        this.$request(
          "POST",
          "/patient/affect/staff",
          {
            nodeId: this.$getNodeId(this.form),
            staffId: this.form.staffId,
            patientId: this.selectedPatient.patientId
          },
          // {
          //   nodeId
          //   staffId
          //   patientId
          // },
          "L'affectation a été effectuée !",
          response => {
            this.staff = response;
            this.fetchAffectedStaff();
          },
          //  empty
          "Echec de l'affectation !",
          () => {}
        );
      }
    },
    onPatientDeleteAffectation(staffId) {
      this.$request(
        "DELETE",
        "/staff/assignment/delete",
        {
          patientId: this.selectedPatient.patientId,
          staffId
        },
        "L'affectation a bien été supprimée !",
        () => {
          this.fetchAffectedStaff();
        },
        //  empty
        "Echec de la suppression de l'affectation !",
        () => {}
      );
    },
    fetchAffectedStaff() {
      this.$request(
        "GET",
        "/patient/print/staff",
        {
          patientId: this.selectedPatient.patientId
        },
        "Le personnel affecté a bien été chargé !",
        response => {
          this.affectedStaff = response;
        },
        //  empty
        "Echec du chargement du personnel affecté !",
        () => {}
      );
      this.affectedStaff = getters.addStaffInformations(this.affectedStaff);
    },
    fileAdded(event) {
      return event;
    }
  }
};
</script>