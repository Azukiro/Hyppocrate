<template>
  <div class="d-flex flex-column justify-space-around align-center my-5">
    <v-card color="transparent" outlined width="50%" class="my-5">
      <v-card-actions class="d-flex justify-space-around align-center">
        <v-btn text to="/create-hospital">Ajouter un hôpital</v-btn>
        <v-btn text to="/create-pole">Saisir un pôle</v-btn>
        <v-btn text to="/create-sector">Créer un secteur</v-btn>
      </v-card-actions>
    </v-card>

    <v-card color="transparent" outlined width="50%">
      <v-checkbox v-model="displayLabo" @change="fetchStructure" label="Laboratoire"></v-checkbox>
      <APHPStructure
        ref="AHPHPStructure"
        :infrastructureAddHandler="onInfrastructureCreate"
        :infrastructureDeleteHandler="onInfrastructureDelete"
        :staffDeleteHandler="onAffectationDelete"
        :poleAddButton="true"
        @update="fetchStructure"
      />
    </v-card>
  </div>
</template>

<script>
import APHPStructure from "@/components/All/APHPStructure.vue";
import { getters, mutations } from "@/store.js";

export default {
  name: "StructurateAPHP",
  components: { APHPStructure },
  created() {
    this.fetchStructure();
  },
  data() {
    return {
      displayLabo: false
    };
  },
  methods: {
    ...mutations,
    fetchStructure() {
      this.$request(
        "GET",
        "/infrastructure/all",
        {},
        // empty
        "L'infrastructure de l'APHP a été chargée !",
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
        // response => (this.structure = response),
        "Erreur lors du chargement de l'infrastructure de l'APHP !",
        () => {}
      );
    },
    onInfrastructureCreate(data) {
      this.setSelectedUnit(data);
      this.$router.push(data.link);
    },
    onInfrastructureDelete({ nodeId }) {
      this.$request(
        "DELETE",
        "/infrastructure/delete",
        {
          nodeId
        },
        // {
        //   nodeId
        // },
        "L'infrastructure a bien été supprimée !",
        () => this.$emit("update"),
        //  empty
        "Echec de la suppression de l'infrastructure !",
        () => {}
      );
    },
    onAffectationDelete(data) {
      this.$request(
        "DELETE",
        "/staff/infos/assignment",
        data,
        // {
        //   nodeId
        //   staffId
        // },
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
