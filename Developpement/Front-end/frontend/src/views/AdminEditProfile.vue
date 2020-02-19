<template>
  <v-card
    color="transparent"
    outlined
    height="90%"
    class="d-flex flex-column justify-center align-center"
  >
    <v-card-title class="headline py-6">Gérer un profil</v-card-title>

    <SelectedStaff />

    <v-card width="80%" color="transparent" outlined class="d-flex justify-space-around">
      <v-card width="30%" color="transparent" class="d-flex flex-column align-center title pt-5">
        <p class="text-center">Informations générales</p>

        <v-card width="80%" color="transparent" outlined>
          <v-form ref="generalForm">
            <v-text-field
              label="Prénom"
              outlined
              v-model="form.lastName"
              :rules="$rules('Last name')"
            />

            <v-text-field label="Nom" outlined v-model="form.name" :rules="$rules('Name')" />

            <v-text-field
              outlined
              label="Date de naissance"
              v-model="form.birthday"
              :rules="$rules('Birthday')"
            />

            <v-text-field
              label="Numéro de carte d'identé"
              outlined
              v-model="form.idendityCardNumber"
            />
          </v-form>
        </v-card>

        <v-card-actions class="mt-0 pt-0">
          <v-btn color="#2c96fa" class="white--text" @click="onGeneralInformationsButtonClick">
            Modifier
            <v-icon right>mdi-account-edit</v-icon>
          </v-btn>
        </v-card-actions>
      </v-card>

      <v-card width="30%" color="transparent" class="d-flex flex-column align-center title pt-5">
        <p class="text-center">Informations de contact</p>

        <v-card width="80%" color="transparent" outlined>
          <v-form ref="contactForm">
            <v-text-field
              label="Téléphone portable"
              outlined
              v-model="form.phoneLandline"
              :rules="$rules('Phone landline')"
            />

            <v-text-field
              label="Addresse"
              outlined
              v-model="form.address"
              :rules="$rules('Address')"
            />

            <v-text-field
              outlined
              label="Adresse mail"
              v-model="form.email"
              :rules="$rules('Email')"
            />
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
          <v-form ref="accountForm">
            <v-text-field
              label="Nouveau mot de passe"
              outlined
              v-model="form.newPwd"
              :rules="$rules('New password')"
            />

            <v-text-field
              outlined
              label="Ressaisissez un mot de passe"
              v-model="form.newPwdAgain"
              :rules="$rules('Same passord')"
            />
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
import SelectedStaff from "@/components/All/SelectedStaff.vue";

export default {
  name: "AdminEditProfile",
  components: { SelectedStaff },
  computed: {
    form() {
      return {
        lastName: this.selectedStaff.lastName,
        name: this.selectedStaff.name,
        idendityCardNumber: this.selectedStaff.idendityCardNumber,
        nationality: this.selectedStaff.nationality,
        birthday: this.selectedStaff.birthday,
        address: this.selectedStaff.address,
        phone: this.selectedStaff.phone,
        email: this.selectedStaff.email,
        newPwd: this.selectedStaff.newPwd,
        newPwdAgain: this.selectedStaff.newPwdAgain
      };
    },
    ...getters
  },
  created() {
    this.fetchProfiles();
    this.fetchPersonnalInformations();
  },
  data() {
    return {
      staffTypes: ["Médecin", "Dentiste", "Chirurgien"]
    };
  },
  methods: {
    fetchProfiles() {
      this.$request(
        "GET",
        "/profile/all",
        this.form,
        "Les profils ont été chargés !",
        response => (this.staffTypes = response),
        "Echec du chargement des profils !",
        () => {}
      );
    },
    fetchPersonnalInformations() {
      this.$request(
        "GET",
        "/staff/print/all",
        this.form,
        // {
        //   staffId
        // }
        "Vos informations personnelles ont été chargées !",
        response => (this.staffTypes = response),
        "Aucune information personnelle n’a pu être affichée !",
        () => {}
      );
    },
    onGeneralInformationsButtonClick() {
      if (this.$refs.generalForm.validate()) {
        this.$request(
          "PUT",
          "/staff/general",
          this.form,
          "Les informations générales ont bien été modifiées !",
          () => {},
          "Les informations générales n'ont pas pu être modifiées !",
          () => {}
        );
      }
    },
    onContactButtonClick() {
      if (this.$refs.contactForm.validate()) {
        this.$request(
          "GET",
          "/staff/contact",
          this.form,
          "Les informations de contact ont bien été modifiées !",
          () => {},
          "Les informations de contact n'ont pas pu être modifiées !",
          () => {}
        );
      }
    },
    onPwdButtonClick() {
      if (this.$refs.accountForm.validate()) {
        this.$request(
          "GET",
          "/staff/pwd",
          this.form,
          "Les informations administratives ont bien été modifiées !",
          () => {},
          "Les informations administratives n'ont pas pu être modifiées !",
          () => {}
        );
      }
    }
  }
};
</script>

<style></style>
