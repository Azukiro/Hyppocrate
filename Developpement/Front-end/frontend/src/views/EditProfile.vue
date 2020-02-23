<template>
  <v-card
    color="transparent"
    height="90%"
    outlined
    class="d-flex flex-column justify-center align-center"
  >
    <v-card-title class="headline py-6 mb-5">Mon profil</v-card-title>

    <v-card width="80%" color="transparent" outlined class="d-flex justify-space-around">
      <v-card width="30%" color="transparent" class="d-flex flex-column align-center title pt-5">
        <p class="text-center">Informations de contact</p>

        <v-card width="80%" color="transparent" outlined>
          <v-form ref="contactForm">
            <v-text-field
              label="Téléphone portable"
              outlined
              v-model="form.phone"
              :rules="$rules('Phone')"
            ></v-text-field>

            <v-text-field
              label="Adresse"
              outlined
              v-model="form.address"
              :rules="$rules('Address')"
            ></v-text-field>

            <v-text-field
              outlined
              label="Adresse mail"
              v-model="form.email"
              class="mb-0 pb-0"
              :rules="$rules('Email')"
            ></v-text-field>
          </v-form>
        </v-card>

        <v-card-actions class="mt-0 pt-0">
          <v-btn color="#2c96fa" class="white--text" @click="onContactButtonClick">
            Modifier
            <v-icon right>mdi-account-edit</v-icon>
          </v-btn>
        </v-card-actions>
      </v-card>

      <v-card width="30%" color="transparent" class="d-flex flex-column align-center title pt-5">
        <p class="text-center">Compte & Administration</p>

        <v-card width="80%" color="transparent" outlined>
          <v-form ref="pwdForm">
            <v-text-field
              type="password"
              label="Ancien mot de passe"
              outlined
              v-model="form.oldPwd"
              :rules="$rules('Old password')"
            ></v-text-field>

            <v-text-field
              type="password"
              label="Nouveau mot de passe"
              outlined
              v-model="form.newPwd"
              :rules="$rules('New password')"
            ></v-text-field>

            <v-text-field
              type="password"
              outlined
              label="Ressaisissez un mot de passe"
              v-model="form.newPwdAgain"
              :rules="$rules('Same password')"
            ></v-text-field>
          </v-form>
        </v-card>
        <v-card-actions class="mt-0 pt-0">
          <v-btn color="#2c96fa" class="white--text" @click="onPwdButtonClick">
            Modifier
            <v-icon right>mdi-account-edit</v-icon>
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-card>
  </v-card>
</template>

<script>
import { getters } from "@/store.js";

export default {
  name: "EditProfile",

  created() {
    this.fetchPersonnalInformations();
  },

  computed: {
    ...getters
  },

  data() {
    return {
      form: {
        firstName: "Ewen",
        lastName: "Bouquet",
        birthdayDate: "22/11/2000",
        phone: "",
        address: "",
        email: "",
        oldPwd: "",
        newPwd: "",
        newPwdAgain: ""
      }
    };
  },
  methods: {
    fetchPersonnalInformations() {
      this.$request(
        "GET",
        "/staff/all",
        {
          patientId: this.user.id
        },
        // {
        //   patientId
        // }
        "Vos informations personnelles ont été chargées !",
        response =>
          (this.form = {
            ...response,
            oldPwd: "",
            newPwd: "",
            newPwdAgain: ""
          }),
        // {
        //   firstName,
        //   lastName,
        //   birthdayDate,
        //   phone,
        //   address,
        //   email
        // }
        "Aucune information personnelle n’a pu être affichée !",
        () => {}
      );
    },
    onContactButtonClick() {
      if (this.$refs.contactForm.validate()) {
        this.$request(
          "GET",
          "/staff/contact",
          {
            id: this.form.id,
            phone: this.form.phone,
            address: this.form.address,
            email: this.form.email
          },
          // {
          //   staffId,
          //   phone,
          //   address,
          //   email
          // },
          "Vos informations de contact ont bien été modifiées !",
          () => {},
          // empty
          "Erreur lors de la modification des informations de contact !",
          () => {}
        );
      }
    },
    onPwdButtonClick() {
      if (this.$refs.pwdForm.validate()) {
        this.$request(
          "GET",
          "/staff/pwd",
          {
            id: this.form.id,
            oldPwd: this.form.oldPwd,
            newPwd: this.form.newPwd,
            newPwdAgain: this.form.newPwdAgain
          },
          // {
          //   staffId,
          //   oldPwd,
          //   newPwd,
          //   newPwdAgain
          // },
          "Le mot de passe a bien été modifié !",
          () => {},
          // boolean
          "Erreur lors de la modification du mot de passe !",
          () => {}
        );
      }
    }
  }
};
</script>

<style>
</style>