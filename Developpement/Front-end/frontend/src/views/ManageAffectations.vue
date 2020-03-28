<template>
  <v-card
    color="transparent"
    outlined
    height="90%"
    class="d-flex flex-column justify-center align-center"
  >
    <v-card-title class="headline py-6">Gérer les affectations</v-card-title>
    <SelectedStaff />
    <v-card-actions class="d-flex justify-space-around align-center">
      <v-btn text @click="addAffectation({})">Ajouter une affectation</v-btn>
    </v-card-actions>
    <v-card color="transparent" outlined width="30%">
      <v-checkbox v-model="displayLabo" @change="fetchAffectations" label="Laboratoire"></v-checkbox>
      <APHPStructure
        ref="APHPStructure"
        :infrastructureAddHandler="addAffectation"
        :infrastructureDeleteHandler="suppressAffectation"
        :staffDeleteHandler="suppressAffectation"
        :poleAddButton="false"
        @update="fetchAffectations"
      />
    </v-card>
  </v-card>
</template>

<script>
import APHPStructure from "@/components/All/APHPStructure.vue";
import SelectedStaff from "@/components/All/SelectedStaff.vue";

import { getters, mutations } from "@/store.js";

export default {
  name: "ManageAffectations",
  components: { APHPStructure, SelectedStaff },
  created() {
    this.fetchAffectations();
  },
  data() {
    return {
      displayLabo: false
    };
  },
  computed: {
    ...getters
  },
  methods: {
    ...mutations,
    fetchAffectations() {
      this.$request(
        "GET",
        "/staff/infos/assignment",
        {
          staffId: this.selectedStaff.staffId
        },
        // {
        //   staffId
        // },
        "Les affectations du personnel sélectionné ont été chargées !",
        response => {
          this.$refs.AHPHPStructure.$emit(
            "fetch",
            getters.addAPHPStructureInformations(response),
            this.displayLabo
          );
        },
        // [
        //   {
        //     hospitalId: 0,
        //     hospitalName: "Hôpital 1",
        //     poles: [
        //       {
        //         poleId: 1,
        //         poleName: "Pole 1",
        //         sectors: [
        //           {
        //             sectorId: 2,
        //             sectorName: "Secteur 1",
        //           }
        //         ],
        //         labos: [
        //           {
        //              laboId: 3,
        //              laboName: "Laboratoire 1"
        //            }
        //          ]
        //       }
        //     ]
        //   }
        // ],
        "Le personnel sélectionné ne possède aucune affectation !",
        () => {}
      );
    },
    addAffectation(data) {
      this.setSelectedUnit(data);
      this.$router.push("affect-staff");
    },
    suppressAffectation(data) {
      this.$request(
        "DELETE",
        "/staff/infos/assignment",
        {
          userId: this.selectedPatient.patientId,
          ...data
        },
        "L'affectation a bien été supprimée !",
        () => this.$emit("update"),
        //  empty
        "Echec de la suppression de l'affectation !",
        () => {}
      );
    }
  }
};
</script>