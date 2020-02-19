<template>
  <v-footer app class="font-weight-medium white--text" color="#142d41">
    <v-card color="transparent" outlined>
      <v-img height="40px" width="40px" src="@/assets/logos/hippocrate/logo2.png" />
    </v-card>

    <v-card color="transparent px-4" outlined>
      <v-img height="40px" width="200px" src="@/assets/logos/hippocrate/APHP.png" />
    </v-card>

    <v-spacer></v-spacer>

    <v-card
      class="px-3"
      color="transparent"
      outlined
      @click="$downloadFile('/UserDocumentation.pdf', 'Documentation.pdf')"
    >
      <v-img height="40px" width="40px" src="@/assets/logos/icons/actions/white/help.png" />
    </v-card>
  </v-footer>
</template>

<script>
export default {
  name: "MastFoot"
};
</script>