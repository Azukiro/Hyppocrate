<template>
  <v-card color="transparent" outlined width="90%">
    <div>
      <p class="headline py-5 text-center black--text">Mes brouillons</p>

      <div class="d-flex justify-center align-center">
        <v-card
          width="50%"
          color="transparent"
          outlined
          class="d-flex justify-space-around align-center"
        >
          <div width="40%">
            <v-text-field @change="fetch" label="Rechercher" outlined v-model="form.search"></v-text-field>
          </div>

          <div width="40%">
            <v-select
              @change="fetch"
              v-model="form.sortColumnName"
              :items="selectItems"
              item-text="printableName"
              item-value="sortColumnName"
              label="Trier par"
              outlined
            ></v-select>
          </div>
        </v-card>
      </div>
    </div>

    <div class="d-flex flex-row justify-space-around align-center">
      <v-card color="transparent" outlined width="20%" class="d-flex justify-end">
        <v-btn icon color="black" @click="onPaginate_Left">
          <v-icon large>mdi-arrow-left-bold</v-icon>
        </v-btn>
      </v-card>

      <v-card
        max-width="1000px"
        color="transparent"
        outlined
        class="d-flex flex-column justify-space-around align-start"
      >
        <v-list-item
          style="width: 100%;"
          class="d-flex justify-space-between"
          v-for="({ staffFirstName, staffLastName, title, date, actIcon }, i) in drafts"
          :key="i"
          @click="onSelection(i)"
        >
          <v-card
            max-width="400px"
            color="transparent"
            outlined
            class="d-flex justify-start align-center"
          >
            <v-avatar class="ma-3" width="90px" height="90px" tile>
              <v-img src="@/assets/logos/icons/types/black/patient.png" />
            </v-avatar>

            <v-card-title class="headline black--text">
              {{ staffFirstName }}
              <br />
              {{ staffLastName }}
            </v-card-title>
          </v-card>
          <v-card
            max-width="400px"
            color="transparent"
            outlined
            class="d-flex justify-start align-center"
          >
            <v-card-title class="headline black--text text-right">
              {{ title }}
              <br />
              Fait le {{ date }}
            </v-card-title>

            <v-avatar class="ma-3" width="90px" height="90px" tile>
              <v-img :src="actIcon" />
            </v-avatar>
          </v-card>
        </v-list-item>
      </v-card>

      <v-card color="transparent" outlined width="20%">
        <v-btn icon color="black" @click="onPaginate_Right">
          <v-icon large>mdi-arrow-right-bold</v-icon>
        </v-btn>
      </v-card>
    </div>
  </v-card>
</template>

<script>
export default {
  name: "DraftSelectionForm",
  props: ["form", "selectItems", "drafts"],
  methods: {
    fetch() {
      this.$emit("change");
    },
    onSelection(i) {
      this.$emit("selection", i);
    },
    onPaginate_Left() {
      this.form.paginationNumber -=
        this.form.paginationNumber <= 1 ? 1 : this.form.paginationLength;
      this.fetch();
    },
    onPaginate_Right() {
      this.form.paginationNumber +=
        this.drafts.length < this.form.paginationLength
          ? 1
          : this.form.paginationLength;
      this.fetch();
    }
  }
};
</script>