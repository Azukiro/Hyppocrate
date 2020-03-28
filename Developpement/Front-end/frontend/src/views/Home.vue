<template>
  <div>
    <v-card color="transparent" outlined class="d-flex justify-space-around mt-5">
      <v-card
        color="#1F7087"
        class="d-flex justify-space-around mb-6"
        dark
        v-for="({link, title, label, width, height}, i) in items"
        :key="i"
        width="30%"
      >
        <v-card color="transparent" outlined>
          <v-avatar class="ma-3" :width="width" :height="height" tile>
            <v-img :src="link" />
          </v-avatar>
        </v-card>

        <v-card color="transparent" outlined>
          <v-card-title class="headline">{{ title }}</v-card-title>

          <v-card-subtitle class="mt-5">{{ label }}</v-card-subtitle>
        </v-card>
      </v-card>
    </v-card>

    <v-card color="#2c96fa" outlined>
      <p
        class="headline pt-4 pb-1 text-center white--text"
      >Hippocrate, l'application de gestion de dossiers médicaux</p>
    </v-card>

    <v-card color="#f6f3e4" class="pb-10" outlined>
      <p class="headline py-5 text-center">Les services proposés</p>

      <v-card color="transparent" class="d-flex justify-center mb-6" dark outlined>
        <v-card color="transparent" outlined>
          <v-avatar class="ma-3" width="450px" height="420px" tile>
            <v-img src="@/assets/logos/hippocrate/logo1.png" />
          </v-avatar>
        </v-card>

        <v-card
          color="transparent"
          outlined
          width="500px"
          style="flex-direction: column"
          class="d-flex justify-center align-center"
        >
          <v-list-item v-for="({link, title}, i) in listItems" :key="i">
            <v-avatar class="ma-3" width="125px" height="100px" tile>
              <v-img :src="link" />
            </v-avatar>

            <v-card-title class="headline black--text">{{ title }}</v-card-title>
          </v-list-item>
        </v-card>
      </v-card>
    </v-card>
  </div>
</template>

<script>
export default {
  name: "Home",

  data() {
    return {
      items: [
        {
          link: require("@/assets/pictures/doctor.png"),
          title: "Une gestion des dossiers médicaux",
          label:
            "Hippocrate est conçue dans le but de faire gagner du temps aux différents intervenants. \n En clair, les documents médicaux des patients sont enregistrés en ligne et chaque type d’utilisateur visualise les données qui le concernent.",
          width: "150px",
          height: "350px"
        },
        {
          link: require("@/assets/logos/icons/actions/white/time.png"),
          title: "Un accès n'importe où, n'importe quand",
          label:
            "Hippocrate simplifie donc la gestion des données médicales et leur partage en faisant en sorte que chaque personne concernée trouve rapidement l’information qu’il cherche.",
          width: "150px",
          height: "150px"
        }
      ],

      listItems: [
        {
          link: require("@/assets/logos/icons/list-items/1.png"),
          title: "Visualiser un DMP en respectant le secret médical."
        },
        {
          link: require("@/assets/logos/icons/list-items/2.png"),
          title: " Permettre une saisie efficace des informations."
        },
        {
          link: require("@/assets/logos/icons/list-items/3.png"),
          title: "Gérer un partage sécurisé des données."
        }
      ]
    };
  }
};
</script>
