<template>
  <v-card color="transparent" outlined height="90%" class="d-flex justify-center align-center">
    <v-card width="30%" class="pb-5 d-flex flex-column justify-center align-center">
      <p class="headline py-5 text-center">Login</p>

      <v-card
        color="transparent"
        outlined
        class="d-flex flex-column justify-space-around align-center mt-10"
      >
        <v-text-field label="Email" outlined v-model="form.email" />

        <v-text-field label="Password" outlined v-model="form.pwd" type="password" />

        <div>
          <v-btn color="#2c96fa" class="ma-2 white--text" @click="connect">
            Login
            <v-icon right>mdi-account-check</v-icon>
          </v-btn>

          <router-link to="/reset-pwd">
            <v-btn color="red" class="ma-2 white--text">
              Forgot
              <v-icon right>mdi-account-question</v-icon>
            </v-btn>
          </router-link>
        </div>
      </v-card>
    </v-card>
  </v-card>
</template>

<script>
import { getters, mutations } from "@/store.js";

export default {
  name: "Login",

  components: {},

  computed: {
    ...getters
  },

  data() {
    return {
      form: {
        email: "",
        pwd: ""
      },
      retry: 5
    };
  },

  methods: {
    ...mutations,
    connect() {
      if (this.retry <= 0) {
        this.addSnack({
          color: "red",
          text: "Nombre maximal d'authentifications dépassé !",
          buttonText: "Ok",
          timeout: 5000
        });
      } else {
        this.sendRequest();
      }
    },

    sendRequest() {
      this.$request(
        "GET",
        "/connection/login",
        {
          email: this.form.email,
          pwd: this.form.pwd
        },
        "Bienvenue " + this.user.firstName + " " + this.user.lastName + " !",
        response => this.setUser(response),
        "Echec de l'authentification, il vous reste " +
          --this.retry +
          " essais !",
        () => {}
      );
    }
  }
};
</script>
