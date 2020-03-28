<template>
  <div>
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

    <v-checkbox v-model="form.isLaboratory" @change="onPoleSelectionChange" label="Laboratoire"></v-checkbox>

    <v-select
      v-model="form.sectorId"
      :items="sectors"
      :item-text="form.isLaboratory ? 'laboName' : 'sectorName'"
      item-value="sectorId"
      :label="form.isLaboratory ? 'Laboratoire' : 'Secteur'"
      outlined
      :rules="$rules('Sector name')"
      @change="onSectorSelectionChange"
    />

    <v-select
      v-if="selectUnit"
      v-model="form.staffId"
      :items="staff"
      item-text="staffFullName"
      item-value="staffId"
      label="Personnel"
      outlined
      :rules="$rules('Staff name')"
    />
  </div>
</template>

<script>
export default {
  name: "NodeSelection",

  props: ["form", "selectUnit"],

  data() {
    return {
      staff: [],
      hospitals: [],
      poles: [],
      sectors: []
    };
  },

  created() {
    this.fetchHospitals();
    this.fetchPoles();
    this.fetchSectors();
    this.onSectorSelectionChange();
  },

  methods: {
    onHospitalSelectionChange() {
      this.fetchPoles();
      this.fetchSectors();
      this.onSectorSelectionChange();
      this.form.poleId = -1;
      this.form.sectorId = -1;
    },
    onPoleSelectionChange() {
      this.fetchSectors();
      this.onSectorSelectionChange();
      this.form.sectorId = -1;
    },
    onSectorSelectionChange() {
      if (this.selectUnit) {
        this.fetchStaff();
        this.form.staffId = -1;
      }
    },
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
        response => (this.poles = response),
        // { poleId, poleName, poleLeaderId, poleLeaderFirstName, poleLeaderLastName }
        "Echec lors du chargement des pôles !",
        () => {}
      );
    },
    fetchSectors() {
      this.$request(
        "GET",
        "/infrastructure/sector",
        {
          nodeId: this.form.poleId,
          isLaboratory: this.form.isLaboratory
        },
        // { poleId }
        "Les secteurs ont été chargés !",
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
        {
          nodeId: this.$getNodeId(this.form)
        },
        "Le personnel ont été chargés !",
        response => (this.staff = response),
        // {
        //  staffId
        //  staffFullName
        //  staffType
        // }
        "Echec lors du chargement du personnel !",
        () => {}
      );
    }
  }
};
</script>

<style>
</style>