<template>
  <v-card color="transparent" outlined height="100%" class="d-flex justify-center align-center">
    <v-card outlined width="40%" class="d-flex flex-column justify-center align-center">
      <v-card-title class="headline text-center">Créer un patient</v-card-title>

      <v-card color="transparent" outlined width="70%">
        <v-form ref="form">
          <v-text-field label="Nom" outlined v-model="form.firstName" :rules="$rules('First name')"></v-text-field>

          <v-text-field
            label="Prénom"
            outlined
            v-model="form.lastName"
            :rules="$rules('Last name')"
          ></v-text-field>

          <v-text-field
            label="Date de naissance"
            outlined
            type="date"
            v-model="form.birthdayDate"
            :rules="$rules('birthdayDate')"
          ></v-text-field>

          <v-text-field
            label="Numéro de téléphone"
            type="number"
            outlined
            v-model="form.phoneNumber"
            :rules="$rules('Phone number')"
          ></v-text-field>

          <v-text-field
            label="Numéro de sécurité sociale"
            type="number"
            outlined
            v-model="form.socialNumber"
            :rules="$rules('Social number')"
          ></v-text-field>

          <v-text-field
            label="Addresse mail"
            outlined
            v-model="form.email"
            :rules="$rules('Email')"
          ></v-text-field>

          <v-text-field label="Addresse" outlined v-model="form.address" :rules="$rules('Address')"></v-text-field>
        </v-form>
      </v-card>

      <v-card-actions>
        <v-btn color="#2c96fa" class="ma-2 white--text" @click="createPatient">
          Créer
          <v-icon right>mdi-clipboard-plus</v-icon>
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-card>
</template>

<script>
export default {
  name: "CreatePatient",

  data() {
    return {
      form: {
        firstName: "",
        lastName: "",
        birthdayDate: "",
        phoneNumber: "",
        socialNumber: "",
        email: "",
        address: "",
        file: {}
      }
    };
  },
  methods: {
    createPatient() {
      if (this.$refs.form.validate()) {
        this.$request(
          "POST",
          "/staff/new/patient",
          this.form,
          //{
          //   firstName,
          //   lastName,
          //   birthdayDate,
          //   phoneNumber,
          //   socialNumber,
          //   email,
          //   address
          // }
          "Le patient a bien été créé !",
          () => {},
          //  empty
          "Erreur lors de la création du patient !",
          () => {}
        );
      }
    }
  }
};
</script>