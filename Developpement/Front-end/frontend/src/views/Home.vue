<template>
  <v-app>
    <HomeHeader/>
  </v-app>
</template>

<script>
import { HomeHeader } from "../components/home/HomeHeader.vue";

export default {
  name: 'Home',

  components: {
    HomeHeader
  },
};
</script>
