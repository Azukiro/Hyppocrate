<template>
  <v-card color="transparent" outlined class="d-flex justify-center align-center my-5">
    <v-card outlined width="40%" class="d-flex flex-column justify-center align-center">
      <v-card-title class="headline text-center">Créer du personnel</v-card-title>

      <v-card color="transparent" outlined width="70%">
        <v-form ref="form">
          <v-select
            v-model="form.type"
            :items="staffTypes"
            item-text="printableName"
            item-value="sortColumnName"
            label="Type"
            outlined
            :rules="$rules('Type')"
          />

          <v-text-field
            label="Prénom"
            outlined
            v-model="form.firstName"
            :rules="$rules('First name')"
          />

          <v-text-field label="Nom" outlined v-model="form.lastName" :rules="$rules('Last name')" />

          <v-text-field
            label="Date de naissance"
            type="date"
            outlined
            v-model="form.birthdayDate"
            :rules="$rules('birthdayDate')"
          />

          <v-text-field
            label="Téléphone portable"
            outlined
            v-model="form.phoneLandline"
            :rules="$rules('Phone landliner')"
          />

          <v-text-field
            label="Addresse"
            outlined
            v-model="form.address"
            :rules="$rules('Address')"
          />

          <v-text-field
            label="Adresse mail"
            outlined
            v-model="form.email"
            :rules="$rules('Email')"
          />

          <v-text-field
            label="Numéro de sécurité sociale"
            outlined
            v-model="form.socialNumber"
            :rules="$rules('Social number')"
          ></v-text-field>
        </v-form>
      </v-card>

      <v-card-actions>
        <v-btn color="#2c96fa" class="ma-2 white--text" @click="createStaff">
          Créer
          <v-icon right>mdi-account-plus</v-icon>
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-card>
</template>

<script>
export default {
  name: "CreateStaff",

  created() {
    this.fetchProfiles();
  },
  data() {
    return {
      form: {
        type: "",
        lastName: "",
        firstName: "",
        birthdayDate: "",
        address: "",
        phoneLandline: "",
        email: "",
        socialNumber: ""
      },
      staffTypes: [
        "Médecin traitant",
        "Dentiste",
        "Cardiologue",
        "Personnel de laboratoire"
      ]
    };
  },
  methods: {
    fetchProfiles() {
      this.$request(
        "GET",
        "/profile/all",
        {},
        //  empty
        "Les types de profils ont été chargés !",
        // {
        //   sortColumnName,
        //   printableName
        // },
        response => (this.staffTypes = response),
        "Echec lors du chargement des profils !",
        () => {}
      );
    },
    createStaff() {
      if (this.$refs.form.validate()) {
        this.$request(
          "POST",
          "/staff/create",
          {
            ...this.form
          },
          // {
          //   actType,
          //   firstName,
          //   lastName,
          //   birthdayDate,
          //   address,
          //   phone,
          //   email,
          //   socialNumber
          // },
          "Le personnel a bien été créé !",
          () => {},
          //  empty
          "Erreur lors de la création du personnel !",
          () => {}
        );
      }
    }
  }
};
</script>