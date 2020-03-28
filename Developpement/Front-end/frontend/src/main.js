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
  function console_log(response) {
    console.log(type + " => " + url.href, response);
  }

  function requestError(response) {
    console_log(response);
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
    console_log(response);
    ok_action(response);

    mutations.addSnack({
      color: "green",
      text: ok_message,
      buttonText: "Ok",
      timeout: 5000
    });
    return response;
  }

  url = new URL("http://51.178.45.82:8080/Hyppocrate/api" + url);
  url.search = new URLSearchParams(params).toString();

  fetch(url, {
    mode: "cors",
    method: type
  })
    .then(response =>
      response.ok
        ? response.json().then(json => requestOk(json))
        : requestError(response)
    )
    .catch(() => requestError());
};

Vue.prototype.$downloadFile = (url, fileName) => {
  const link = document.createElement("a");
  link.href = url;
  link.setAttribute("download", fileName);
  document.body.appendChild(link);
  link.click();
};

Vue.prototype.$getNodeId = function({ sectorId, poleId, hospitalId }) {
  if (0 <= sectorId) return sectorId;
  if (0 <= poleId) return poleId;
  if (0 <= hospitalId) return hospitalId;
  return -1;
};

Vue.prototype.$rules = name => [v => !!v || v === 0 || name + " is required!"];

Vue.config.productionTip = false;

new Vue({
  router,
  vuetify,
  render: h => h(App)
}).$mount("#app");
