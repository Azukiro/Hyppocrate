<template>
  <ActionsList :actions="actions" />
</template>

<script>
import ActionsList from "@/components/All/ActionsList.vue";
import { getters } from "@/store.js";

export default {
  name: "Actions",

  components: {
    ActionsList
  },

  computed: {
    ...getters,
    route() {
      return this.$route.params.route;
    },
    actions() {
      return getters.staff_actions(`/${this.route}`);
    }
  }
};
</script>
