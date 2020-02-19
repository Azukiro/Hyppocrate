<template>
  <v-app>
    <MastHead />

    <v-content>
      <router-view />
      <message-snackbar
        v-for="({ color, text, buttonText, timeout }, i) in snacks"
        :key="i"
        :color="color"
        :text="text"
        :buttonText="buttonText"
        :timeout="timeout"
        @close="onSnackClose(i)"
      />
    </v-content>

    <MastFoot />
  </v-app>
</template>

<script>
import { getters, mutations } from "@/store.js";
import MastHead from "./components/All/MastHead";
import MastFoot from "./components/All/MastFoot";
import MessageSnackbar from "@/components/All/MessageSnackbar.vue";

export default {
  name: "App",

  components: {
    MastHead,
    MastFoot,
    MessageSnackbar
  },

  computed: {
    ...getters
  },

  methods: {
    ...mutations,
    onSnackClose(i) {
      this.removeSnack(this.snacks[i]);
    }
  }
};
</script>
