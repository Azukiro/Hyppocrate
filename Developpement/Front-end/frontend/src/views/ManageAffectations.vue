<template>
  <v-card
    color="transparent"
    outlined
    height="90%"
    class="d-flex flex-column justify-center align-center"
  >
    <v-card-title class="headline py-6">Gérer les affectations</v-card-title>
    <SelectedStaff />
    <v-card color="transparent" outlined width="30%">
      <APHPStructure
        :structure="structure"
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
import { mutations } from "@/store.js";

export default {
  name: "ManageAffectations",
  components: { APHPStructure, SelectedStaff },
  created() {
    this.fetchAffectations();
  },
  data() {
    return {
      structure: [
        {
          hospitalName: "Hôpital 1",
          poles: [
            {
              poleName: "Pole 1",
              sectors: [
                {
                  sectorName: "Secteur 1"
                },
                {
                  sectorName: "Secteur 2"
                },
                {
                  sectorName: "Secteur 3"
                }
              ]
            },
            {
              poleName: "Pole 2",
              sectors: [
                {
                  sectorName: "Secteur 4"
                },
                {
                  sectorName: "Secteur 5"
                }
              ]
            }
          ]
        },
        {
          hospitalName: "Hôpital 2",
          poles: [
            {
              poleName: "Pole 3"
            },
            {
              poleName: "Pole 4",
              sectors: [
                {
                  sectorName: "Secteur 6"
                }
              ]
            }
          ]
        }
      ]
    };
  },
  methods: {
    ...mutations,
    fetchAffectations() {
      this.$request(
        "GET",
        "/staff/infos/assignment",
        {},
        // {
        //   staffId
        // },
        "Les affectations du personnel sélectionné ont été chargées !",
        response => (this.staffTypes = response),
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