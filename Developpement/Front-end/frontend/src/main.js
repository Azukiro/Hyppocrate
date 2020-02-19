import Vue from "vue";
import "./plugins/vuetify";
import App from "./App.vue";
import router from "./router";
import vuetify from "./plugins/vuetify";
import { mutations } from "@/store.js";

Vue.prototype.$request = function(
  type,
  url,
  params,
  ok_message,
  ok_action,
  error_message,
  error_action
) {
  function requestError(response) {
    error_action(response);
    mutations.addSnack({
      color: "red",
      text: error_message,
      buttonText: "Ok",
      timeout: 5000
    });
    return {};
  }

  function requestOk(response) {
    ok_action(response);
    console.log(response);
    mutations.addSnack({
      color: "green",
      text: ok_message,
      buttonText: "Ok",
      timeout: 5000
    });
    return response;
  }

  fetch("http://51.178.45.82:8080/Hyppocrate/api" + url, {
    mode: "cors",
    method: type,
    params
  })
    .then(response =>
      !response.ok ? requestError(response) : requestOk(response)
    )
    .catch(response => requestError(response));
};

Vue.prototype.$downloadFile = (url, fileName) => {
  const link = document.createElement("a");
  link.href = url;
  link.setAttribute("download", fileName);
  document.body.appendChild(link);
  link.click();
};

Vue.prototype.$rules = name => [v => !!v || name + " is required!"];

Vue.config.productionTip = false;

new Vue({
  router,
  vuetify,
  render: h => h(App)
}).$mount("#app");
