import Vue from "vue";

export const globals = Vue.observable({
  user: {},

  snacks: [],

  selectedPatient: {},

  selectedStaff: {},

  selectedAct: {},

  selectedDraft: {},

  selectedUnit: {},

  staff: [
    {
      name: "Médecin",
      icon: require("@/assets/logos/icons/types/white/doctor.png"),
      actions: [
        {
          name: "Gérer un dossier médical",
          icon: require("@/assets/logos/icons/actions/black/dmp.png"),
          link: "/select-patient",
          actions: [
            {
              name: "Consulter le dossier médical",
              icon: require("@/assets/logos/icons/actions/black/dmp.png"),
              link: "/print-dmp",
              actions: [
                {
                  name: "Consulter l'acte médical",
                  icon: require("@/assets/logos/icons/actions/black/dmp.png"),
                  link: "/print-act"
                }
              ]
            },
            {
              name: "Publier un acte médical",
              icon: require("@/assets/logos/icons/actions/black/download-file.png"),
              link: "/create-act"
            }
          ]
        },
        {
          name: "Gérer un brouillon",
          icon: require("@/assets/logos/icons/actions/black/download-file.png"),
          link: "/select-draft",
          actions: [
            {
              name: "Consulter un brouillon",
              icon: require("@/assets/logos/icons/actions/black/download-file.png"),
              link: "/print-draft"
            }
          ]
        },
        {
          name: "Modifier mon profil",
          icon: require("@/assets/logos/icons/actions/black/manage-profile.png"),
          link: "/edit-profile"
        },
        {
          name: "Consulter mon agenda",
          icon: require("@/assets/logos/icons/actions/black/calendar.png"),
          link: "/calendar"
        }
      ]
    },
    {
      name: "Infirmier",
      icon: require("@/assets/logos/icons/types/white/nurse.png"),
      actions: [
        {
          name: "Gérer un dossier médical",
          icon: require("@/assets/logos/icons/actions/black/dmp.png"),
          link: "/select-patient",
          actions: [
            {
              name: "Consulter le dossier médical",
              icon: require("@/assets/logos/icons/actions/black/dmp.png"),
              link: "/print-dmp",
              actions: [
                {
                  name: "Consulter l'acte médical",
                  icon: require("@/assets/logos/icons/actions/black/dmp.png"),
                  link: "/print-act"
                }
              ]
            }
          ]
        },
        {
          name: "Modifier mon profil",
          icon: require("@/assets/logos/icons/actions/black/manage-profile.png"),
          link: "/edit-profile"
        },
        {
          name: "Consulter mon agenda",
          icon: require("@/assets/logos/icons/actions/black/calendar.png"),
          link: "/calendar"
        }
      ]
    },
    {
      name: "Personnel de laboratoire",
      icon: require("@/assets/logos/icons/types/white/laboratoryStaff.png"),
      actions: [
        {
          name: "Gérer un dossier médical",
          icon: require("@/assets/logos/icons/actions/black/dmp.png"),
          link: "/select-patient",
          actions: [
            {
              name: "Consulter le dossier médical",
              icon: require("@/assets/logos/icons/actions/black/dmp.png"),
              link: "/print-dmp",
              actions: [
                {
                  name: "Consulter l'acte médical",
                  icon: require("@/assets/logos/icons/actions/black/dmp.png"),
                  link: "/print-act"
                }
              ]
            }
          ]
        },
        {
          name: "Modifier mon profil",
          icon: require("@/assets/logos/icons/actions/black/manage-profile.png"),
          link: "/edit-profile"
        },
        {
          name: "Consulter mon agenda",
          icon: require("@/assets/logos/icons/actions/black/calendar.png"),
          link: "/calendar"
        }
      ]
    },
    {
      name: "Secrétaire",
      icon: require("@/assets/logos/icons/types/white/secretary.png"),
      actions: [
        {
          name: "Créer un patient",
          icon: require("@/assets/logos/icons/actions/black/manage-profile.png"),
          link: "/create-patient"
        },
        {
          name: "Gérer un patient",
          icon: require("@/assets/logos/icons/actions/black/dmp.png"),
          link: "/select-patient",
          actions: [
            {
              name: "Affecter au personnel",
              icon: require("@/assets/logos/icons/actions/black/profile-edit.png"),
              link: "/affect-patient"
            }
          ]
        },
        {
          name: "Modifier mon profil",
          icon: require("@/assets/logos/icons/actions/black/manage-profile.png"),
          link: "/edit-profile"
        },
        {
          name: "Consulter mon agenda",
          icon: require("@/assets/logos/icons/actions/black/calendar.png"),
          link: "/calendar"
        }
      ]
    },
    {
      name: "Data administrateur",
      icon: require("@/assets/logos/icons/types/white/dataAdministrator.png"),
      actions: [
        {
          name: "Créer du personnel",
          icon: require("@/assets/logos/icons/types/black/doctor.png"),
          link: "/create-staff"
        },
        {
          name: "Gérer le personnel",
          icon: require("@/assets/logos/icons/actions/black/profile-edit.png"),
          link: "/select-staff",
          actions: [
            {
              name: "Editer le profil",
              icon: require("@/assets/logos/icons/actions/black/profile-edit.png"),
              link: "/admin-edit-profile"
            },
            {
              name: "Gérer les affectations",
              icon: require("@/assets/logos/icons/actions/black/structure-hierarchy.png"),
              link: "/manage-affectations"
            }
          ]
        },
        {
          name: "Structurer l'APHP",
          icon: require("@/assets/logos/icons/actions/black/structure-hierarchy.png"),
          link: "/structurate-aphp",
          actions: [
            {
              name: "Créer un hôpital",
              icon: require("@/assets/logos/icons/actions/black/appointment.png"),
              link: "/create-hospital"
            },
            {
              name: "Créer un pôle",
              icon: require("@/assets/logos/icons/actions/black/appointment.png"),
              link: "/create-sector"
            },
            {
              name: "Créer un service",
              icon: require("@/assets/logos/icons/actions/black/appointment.png"),
              link: "/create-pole"
            },
            {
              name: "Affecter le personnel",
              icon: require("@/assets/logos/icons/actions/black/appointment.png"),
              link: "/affect-staff"
            }
          ]
        },
        {
          name: "Modifier mon profil",
          icon: require("@/assets/logos/icons/actions/black/manage-profile.png"),
          link: "/edit-profile"
        },
        {
          name: "Consulter mon agenda",
          icon: require("@/assets/logos/icons/actions/black/calendar.png"),
          link: "/calendar"
        }
      ]
    }
  ],

  actTypes: [
    {
      name: "Ordonnance",
      icon: require("@/assets/logos/icons/actions/black/prescription.png")
    },
    {
      name: "Rapport d'analyse",
      icon: require("@/assets/logos/icons/actions/black/exam-results.png")
    },
    {
      name: "Resultat d'examens",
      icon: require("@/assets/logos/icons/actions/black/exam-results.png")
    },
    {
      name: "Radiologie",
      icon: require("@/assets/logos/icons/actions/black/radiology.png")
    },
    {
      name: "Compte rendu",
      icon: require("@/assets/logos/icons/actions/black/exam-results.png")
    },
    {
      name: "Autre",
      icon: require("@/assets/logos/icons/actions/black/exam-results.png")
    }
  ]
});

export const getters = {
  isAuthenticated() {
    return (
      globals.user !== undefined &&
      globals.user.id !== undefined &&
      globals.user.id !== -1 &&
      globals.user.staff_id !== undefined &&
      globals.user.staff_id !== -1
    );
  },
  canAccessToPage: link => {
    let staff_id = getters.staff_id();
    if (staff_id == -1) return false;
    return canAccessToPage_Rec(getters.initial_actions(), link);
  },
  user: () => globals.user,
  snacks: () => globals.snacks,
  selectedPatient: () => globals.selectedPatient,
  selectedStaff: () => globals.selectedStaff,
  selectedAct: () => globals.selectedAct,
  selectedUnit: () => globals.selectedUnit,
  selectedDraft: () => globals.selectedDraft,
  staff_id: () =>
    globals.user !== undefined &&
    0 <= globals.user.staff_id &&
    globals.user.staff_id < globals.staff.length
      ? getters.user().staff_id
      : -1,
  initial_actions: () => {
    let staff_id = getters.staff_id();
    return staff_id === -1 ? [] : globals.staff[staff_id].actions;
  },
  staff_actions: link => {
    let actions = getters.initial_actions();
    if (actions.length == 0) return [];
    let res = getActions(actions, link);
    return res.length == 0 ? actions : res;
  },
  actTypes: () =>
    globals.actTypes.map(({ name }, i) => {
      return {
        typeName: name,
        typeId: i
      };
    }),
  actType_name: id => globals.actTypes[id].name,
  actType_icon: id => globals.actTypes[id].icon,
  staffType_name: id => globals.staff[id].name,
  staffType_icon: id => globals.staff[id].icon,
  addActInformations: acts =>
    acts.map(act => {
      return {
        ...act,

        actIcon: getters.actType_icon(act.actTypeId),
        actTypeName: getters.actType_name(act.actTypeId),

        staffIcon: getters.staffType_icon(act.staffTypeId),
        staffTypeName: getters.staffType_name(act.staffTypeId)
      };
    })
};

export const mutations = {
  setUser: val => {
    globals.user = val;
    globals.user.icon =
      0 <= globals.user.staff_id && globals.user.staff_id < globals.staff.length
        ? globals.staff[globals.user.staff_id].icon
        : require("@/assets/logos/icons/types/white/undefined.png");
  },
  addSnack: val => globals.snacks.push(val),
  removeSnack: val =>
    (globals.snacks = globals.snacks.filter(value => value != val)),
  setSelectedPatient: val => (globals.selectedPatient = val),
  setSelectedStaff: val => (globals.selectedStaff = val),
  setSelectedAct: val => (globals.selectedAct = val),
  setSelectedDraft: val => (globals.selectedDraft = val),
  setSelectedUnit: val => (globals.selectedUnit = val)
};

// /
//     /select-patient
//         /print-dmp
//             /print-act
//         /create-act
//         /select-draft
//             /print-draft
//     /edit-profile
//     /calendar

// /
//     /select-patient
//         /print-dmp
//             /print-act
//         /create-act
//         /select-draft
//             /print-draft
//     /edit-profile
//     /calendar

// /
//     /select-patient
//         /print-dmp
//             /print-act
//                 /create-act
//     /edit-profile
//     /calendar

// /
//     /select-patient
//         /create-patient
//         /affect-patient
//         /print-dmp
//             /print-act
//     /edit-profile
//     /calendar

// /
//     /select-patient
//         /create-staff
//             /select-staff
//                 /admin-edit-profile
//     /structurate-aphp
//         /create-hospital
//         /create-sector
//         /create-pole
//         /affect-staff
//     /edit-profile
//     /calendar

function canAccessToPage_Rec(actions, searchLink) {
  for (const { link, actions: subactions } of actions) {
    if (link === searchLink) return true;
    if (subactions && canAccessToPage_Rec(subactions, searchLink)) return true;
  }
  return false;
}

function getActions(actions, searchLink) {
  for (const { link, actions: subactions } of actions) {
    if (link === searchLink)
      return subactions !== undefined ? subactions : actions;
    if (subactions) {
      const res = getActions(subactions, searchLink);
      if (res.length !== 0) return res;
    }
  }
  return [];
}

mutations.setUser({
  id: 0,
  firstName: "Eric",
  lastName: "Robert",
  staff_id: 0
});

/*
  Types :
  
  0 : Ordonnance
  1 : Rapport d'analyse
  2 : Resultat d'examens
  3 : Radiologie
  4 : Compte rendu
  5 : Autre
*/
