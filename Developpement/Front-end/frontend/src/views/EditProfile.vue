<template>
  <v-card
    color="transparent"
    height="100%"
    outlined
    class="d-flex flex-column justify-space-around align-center"
  >
    <v-card-title class="headline py-6">Mon profil</v-card-title>

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
              label="Ancien mot de passe"
              outlined
              v-model="form.oldPwd"
              :rules="$rules('Old password')"
            ></v-text-field>

            <v-text-field
              label="Nouveau mot de passe"
              outlined
              v-model="form.newPwd"
              :rules="$rules('New password')"
            ></v-text-field>

            <v-text-field
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

    <v-card width="30%" color="#2c96fa" outlined class="pa-2 mt-4">
      <div class="d-flex justify-center align-center">
        <v-avatar class="ma-3" width="150px" height="150px" tile>
          <v-img src="@/assets/logos/icons/types/black/patient.png" />
        </v-avatar>

        <v-card color="transparent" outlined>
          <v-card-title class="headline white--text">{{ form.lastName }} {{ form.name }}</v-card-title>

          <v-card-text class="headline-2 white--text">
            <ul>
              <li class="py-1">Carte d'identité : {{ form.idendityCardNumber }}</li>
              <li class="py-1">Nationalité : {{ form.nationality }}</li>
              <li class="py-1">Naissance : {{ form.birthday }}</li>
            </ul>
          </v-card-text>
        </v-card>
      </div>
    </v-card>
  </v-card>
</template>

<script>
export default {
  name: "EditProfile",

  created() {
    this.fetchPersonnalInformations();
  },

  data() {
    return {
      form: {
        lastName: "Ewen",
        name: "Bouquet",
        idendityCardNumber: "0123456789",
        nationality: "Français",
        birthday: "22/11/2000",
        address: "",
        phone: "",
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
        "/staff/print/all",
        this.form,
        // {
        //   staffId
        // }
        "Vos informations personnelles ont été chargées !",
        // response => (this.form = response),
        () => {},
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
          // {
          //   staffId,
          //   phoneNumber,
          //   address,
          //   email
          // },
          {},
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
          {},
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