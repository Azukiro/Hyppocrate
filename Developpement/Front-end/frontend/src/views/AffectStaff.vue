<template>
  <v-card
    color="transparent"
    height="100%"
    outlined
    class="d-flex flex-column justify-space-around align-center"
  >
    <v-card outlined width="40%" class="d-flex flex-column justify-center align-center my-5">
      <v-card-title class="headline text-center">Affecter le personnel</v-card-title>

      <SelectedStaff />

      <v-card color="transparent" outlined width="70%">
        <v-form>
          <NodeSelection :form="form" :selectUnit="false" />
        </v-form>
      </v-card>

      <v-card-actions>
        <v-btn color="#2c96fa" class="ma-2 white--text" @click="onStaffAffectation">
          Affecter
          <v-icon right>mdi-plus</v-icon>
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-card>
</template>

<script>
import NodeSelection from "@/components/All/NodeSelection.vue";
import SelectedStaff from "@/components/All/SelectedStaff.vue";

import { getters } from "@/store.js";

export default {
  name: "AffectStaff",

  components: { NodeSelection, SelectedStaff },

  computed: {
    ...getters,
    form() {
      return this.selectedUnit;
    }
  },
  methods: {
    onStaffAffectation() {
      this.$request(
        "POST",
        "/staff/affect",
        {
          staffId: this.selectedStaff.staffId,
          nodeId: this.$getNodeId(this.form)
        },
        "L'affectation a bien été crée !",
        () => {},
        //  empty
        "Echec de l'affectation !",
        () => {}
      );
    }
  }
};
</script>