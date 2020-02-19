<template>
  <v-card color="transparent" outlined height="90%" class="d-flex justify-center align-center">
    <v-card width="30%" class="pb-5 d-flex flex-column justify-center align-center">
      <p class="headline py-5 text-center">Reset Password</p>

      <v-card
        color="transparent"
        outlined
        class="d-flex flex-column justify-space-around align-center mt-10"
      >
        <v-text-field label="Email" outlined v-model="form.email"></v-text-field>

        <v-btn color="#2c96fa" class="ma-2 white--text" @click="resetCode">
          Reset
          <v-icon right>mdi-account-check</v-icon>
        </v-btn>
      </v-card>
    </v-card>
  </v-card>
</template>

<script>
export default {
  name: "Reset",

  data() {
    return {
      form: {
        email: ""
      }
    };
  },

  components: {},

  methods: {
    resetCode() {
      this.$request(
        "PUT",
        "/connection/forgot",
        {
          email: this.form.email
        },
        "Votre mot de passe vous a été envoyé par mail !",
        () => {
          this.$router.push("/login");
        },
        "Identifiants invalides !",
        () => {}
      );
    }
  }
};
</script>