<template>
  <v-card color="transparent" outlined height="90%" class="d-flex justify-center align-center">
    <PrintDmpForm
      :form="form"
      :selectItems="selectItems"
      :acts="acts"
      @change="fetchActs"
      @selection="onActClick"
    />
  </v-card>
</template>

<script>
import PrintDmpForm from "@/components/All/PrintDmpForm.vue";
import { getters, mutations } from "@/store.js";

export default {
  name: "PrintDmp",
  components: { PrintDmpForm },
  created() {
    // this.fetchSortItems();
    // this.fetchActs();
    this.acts = getters.addActInformations(this.acts);
  },

  computed: {
    ...getters,
    requestForm() {
      return {
        ...this.form,
        patientId: this.user.id
      };
    }
  },

  data() {
    return {
      form: {
        search: "",
        sortColumnName: "",
        paginationNumber: 1,
        paginationLength: 4
      },
      selectItems: [{ sortColumnName: "test", printableName: "test" }],
      acts: [
        {
          actId: 1,
          actTypeId: 0,
          title: "Résultats médicaux",
          staffId: 0,
          staffTypeId: 0,
          staffEmail: "Serge.gas@gmail.com",
          staffPhoneNumber: "01561309",
          description: "Rien à noter",
          date: "01/11/2019",
          link: "test/file.txt",
          staffFirstName: "Ewen",
          staffLastName: "Bouquet"
        },
        {
          actId: 2,
          actTypeId: 1,
          title: "Examen médical",
          staffId: 0,
          staffTypeId: 1,
          staffEmail: "Serge.gas@gmail.com",
          staffPhoneNumber: "01561309",
          description: "Rien à noter",
          date: "01/10/2019",
          link: "test/file.txt",
          staffFirstName: "Ewen",
          staffLastName: "Bouquet"
        },
        {
          actId: 2,
          actTypeId: 2,
          title: "Résultats de radio",
          staffId: 0,
          staffTypeId: 3,
          staffEmail: "Serge.gas@gmail.com",
          staffPhoneNumber: "01561309",
          description: "Rien à noter",
          date: "17/01/2020",
          link: "test/file.txt",
          staffFirstName: "Ewen",
          staffLastName: "Bouquet"
        }
      ]
    };
  },
  methods: {
    fetchSortItems() {
      this.$request(
        "GET",
        "/dmp/print/sort-items",
        {},
        // empty
        "Chargement de la liste de critère de tri effectuée !",
        response => (this.selectItems = response),
        // [{sortColumnName, printableName}]
        "Impossible de charger la liste des critères de tri !",
        () => {}
      );
    },
    fetchActs() {
      this.$request(
        "GET",
        "/dmp/print/all",
        this.requestForm,
        // {
        //   patientId,
        //   sortColumnName,
        //   search,
        //   paginationNumber,
        //   paginationLength
        // },
        "Chargement des actes du DMP effectué !",
        // response => (this.acts = response),
        response => {
          this.acts = response.map(act => {
            return act;
          });
        },
        "Impossible de charger les actes du DMP !",
        () => {}
        // [
        //   {
        //     actId,
        //     type,
        //     title,
        //     staffId,
        //     staffTypeId, // Ajout get icon + descriptif type en string
        //     staffEmail, // Ajout popup contact
        //     staffPhoneNumber, // Au niveau de print act
        //     description,
        //     date,
        //     link,
        //     staffFirstName,
        //     staffLastName,
        //     patientFirstName,
        //     patientLastName
        //   }
        // ]
      );
    },
    onActClick(i) {
      this.setSelectedAct(this.acts[i]);
      this.$router.push("/print-act");
    },
    ...mutations
  }
};
</script>
