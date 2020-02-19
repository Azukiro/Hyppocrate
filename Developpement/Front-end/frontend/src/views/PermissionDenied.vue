<template>
  <v-card color="transparent" outlined height="90%" class="d-flex justify-center align-center">
    <v-card
      color="transparent"
      class="d-flex flex-column justify-space-around align-space-around pa-5"
    >
      <p class="title text-center">Vous n'avez pas accès à cette page.</p>
      <v-card-actions class="d-flex justify-center align-center">
        <v-btn color="#2c96fa" class="black--text" to="/">Acceuil</v-btn>
      </v-card-actions>
    </v-card>
  </v-card>
</template>

<script>
export default {
  name: "PermissionDenied"
};
</script>

<style>
</style>