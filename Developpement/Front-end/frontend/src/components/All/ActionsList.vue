<template>
  <v-card color="transparent" outlined height="100%" class="d-flex justify-center align-center">
    <v-card
      color="transparent"
      outlined
      class="d-flex flex-column justify-space-around align-space-around"
    >
      <v-list-item v-for="({ name, link, icon }, i) in actions" :key="i" :to="link">
        <v-avatar class="ma-3" width="90px" height="90px" tile>
          <v-img :src="icon" />
        </v-avatar>

        <v-card-title class="headline black--text">{{ name }}</v-card-title>
      </v-list-item>
    </v-card>
  </v-card>
</template>

<script>
export default {
  name: "ActionsList",
  props: {
    actions: {
      type: Array,
      required: true
    }
  }
};
</script>
