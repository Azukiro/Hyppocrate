import Vue from "vue";
import Router from "vue-router";
import Home from "./views/Home.vue";
import { getters } from "./store.js";

Vue.use(Router);

const router = new Router({
  mode: "history",
  base: process.env.BASE_URL,
  routes: [
    {
      path: "/",
      name: "home",
      component: Home
    },

    //LOGIN

    {
      path: "/login",
      name: "login",
      component: () => import("./views/Login.vue"),
      meta: {
        mustBeAuthenticated: false,
        mustHaveAccessTo: false
      }
    },

    {
      // OLD
      path: "/reset-pwd",
      name: "reset-pwd",
      component: () => import("./views/Reset.vue"),
      meta: {
        mustBeAuthenticated: false,
        mustHaveAccessTo: false
      }
    },

    //CONNECTED

    {
      path: "/actions/:route?",
      name: "actions",
      component: () => import("./views/Actions.vue"),
      meta: {
        mustBeAuthenticated: true,
        mustHaveAccessTo: false
      }
    },
    {
      path: "/select-patient",
      name: "select-patient",
      component: () => import("./views/PatientSelection.vue"),
      meta: {
        mustBeAuthenticated: true,
        mustHaveAccessTo: true
      }
    },
    {
      path: "/print-dmp",
      name: "print-dmp",
      component: () => import("./views/PrintDmp.vue"),
      meta: {
        mustBeAuthenticated: true,
        mustHaveAccessTo: true
      }
    },
    {
      path: "/print-act",
      name: "print-act",
      component: () => import("./views/PrintAct.vue"),
      meta: {
        mustBeAuthenticated: true,
        mustHaveAccessTo: true
      }
    },
    {
      path: "/create-act",
      name: "create-act",
      component: () => import("./views/CreateAct.vue"),
      meta: {
        mustBeAuthenticated: true,
        mustHaveAccessTo: true
      }
    },
    {
      path: "/select-draft",
      name: "select-draft",
      component: () => import("./views/DraftSelection.vue"),
      meta: {
        mustBeAuthenticated: true,
        mustHaveAccessTo: true
      }
    },
    {
      path: "/print-draft",
      name: "print-draft",
      component: () => import("./views/PrintDraft.vue"),
      meta: {
        mustBeAuthenticated: true,
        mustHaveAccessTo: true
      }
    },
    {
      // STANDBY
      path: "/edit-profile",
      name: "edit-profile",
      component: () => import("./views/EditProfile.vue"),
      meta: {
        mustBeAuthenticated: true,
        mustHaveAccessTo: true
      }
    },
    {
      // OLD
      path: "/create-patient",
      name: "create-patient",
      component: () => import("./views/CreatePatient.vue"),
      meta: {
        mustBeAuthenticated: true,
        mustHaveAccessTo: true
      }
    },
    {
      path: "/affect-patient",
      name: "affect-patient",
      component: () => import("./views/AffectPatient.vue"),
      meta: {
        mustBeAuthenticated: true,
        mustHaveAccessTo: true
      }
    },
    {
      // OLD
      path: "/create-staff",
      name: "create-staff",
      component: () => import("./views/CreateStaff.vue"),
      meta: {
        mustBeAuthenticated: true,
        mustHaveAccessTo: true
      }
    },
    {
      path: "/select-staff",
      name: "select-staff",
      component: () => import("./views/StaffSelection.vue"),
      meta: {
        mustBeAuthenticated: true,
        mustHaveAccessTo: true
      }
    },
    {
      path: "/structurate-aphp",
      name: "structurate-aphp",
      component: () => import("./views/StructurateAPHP.vue"),
      meta: {
        mustBeAuthenticated: true,
        mustHaveAccessTo: true
      }
    },
    {
      path: "/create-hospital",
      name: "create-hospital",
      component: () => import("./views/CreateHospital.vue"),
      meta: {
        mustBeAuthenticated: true,
        mustHaveAccessTo: true
      }
    },
    {
      path: "/create-sector",
      name: "create-sector",
      component: () => import("./views/CreateSector.vue"),
      meta: {
        mustBeAuthenticated: true,
        mustHaveAccessTo: true
      }
    },
    {
      path: "/create-pole",
      name: "create-pole",
      component: () => import("./views/CreatePole.vue"),
      meta: {
        mustBeAuthenticated: true,
        mustHaveAccessTo: true
      }
    },
    {
      path: "/affect-staff",
      name: "affect-staff",
      component: () => import("./views/AffectStaff.vue"),
      meta: {
        mustBeAuthenticated: true,
        mustHaveAccessTo: true
      }
    },
    {
      //Standby
      path: "/admin-edit-profile",
      name: "admin-edit-profile",
      component: () => import("./views/AdminEditProfile.vue"),
      meta: {
        mustBeAuthenticated: true,
        mustHaveAccessTo: true
      }
    },
    {
      //Standby
      path: "/manage-affectations",
      name: "manage-affectations",
      component: () => import("./views/ManageAffectations.vue"),
      meta: {
        mustBeAuthenticated: true,
        mustHaveAccessTo: true
      }
    },
    {
      //OLD
      path: "/calendar",
      name: "calendar",
      component: () => import("./views/Calendar.vue"),
      meta: {
        mustBeAuthenticated: true,
        mustHaveAccessTo: true
      }
    },
    {
      path: "*",
      name: "permission-denied",
      component: () => import("./views/PermissionDenied.vue"),
      meta: {
        mustBeAuthenticated: false,
        mustHaveAccessTo: false
      }
    }
  ]
});

router.beforeEach((to, from, next) => {
  if (to.meta.mustBeAuthenticated === true && !getters.isAuthenticated()) {
    if (from.matched.length === 0) {
      next("/");
      return;
    }

    next("/permission-denied");
    return;
  }

  if (to.meta.mustHaveAccessTo === true && !getters.canAccessToPage(to.path)) {
    next("/permission-denied");
    return;
  }
  next();
});

// router.afterEach((_to, from) => {
//   globals.lastPage = from.path;
// });

export default router;
