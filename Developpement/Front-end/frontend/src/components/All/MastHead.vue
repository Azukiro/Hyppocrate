<template>
  <div>
    <v-toolbar color="#142d41">
      <router-link tag="div" to="/" class="d-flex align-center justify-center px-5 py-2 onAction">
        <v-card color="transparent" outlined>
          <v-img height="40px" width="40px" src="@/assets/logos/hippocrate/logo2.png" />
        </v-card>

        <v-toolbar-title class="pl-3 white--text">Hippocrate</v-toolbar-title>
      </router-link>

      <v-spacer></v-spacer>

      <template></template>

      <template>
        <router-link tag="div" to="/login" v-if="!isAuthenticated">
          <v-btn color="#2c96fa" class="ma-2 white--text">
            Login
            <v-icon right>mdi-account-circle</v-icon>
          </v-btn>
        </router-link>

        <router-link tag="div" to="/actions" class="px-6 py-2 onAction" v-else>
          <ConnectedStaff />
        </router-link>
      </template>
    </v-toolbar>
  </div>
</template>

<script>
import { getters } from "@/store.js";
import ConnectedStaff from "@/components/All/ConnectedStaff.vue";

export default {
  name: "MastHead",
  components: { ConnectedStaff },
  computed: {
    ...getters
  }
};
</script>

<style>
.onAction:hover {
  background-color: rgba(255, 255, 255, 0.1);
  cursor: pointer;
}
</style>