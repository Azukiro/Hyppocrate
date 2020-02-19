<template>
  <v-snackbar :value="true" :color="color" :timeout="timeout" :top="true">
    {{ text }}
    <v-btn color="white" @click="onClick" text>{{ buttonText }}</v-btn>
  </v-snackbar>
</template>

<script>
export default {
  name: "MessageSnackbar",
  props: ["color", "text", "buttonText", "timeout"],
  methods: {
    onClick() {
      this.$emit("close");
    }
  }
};
</script>