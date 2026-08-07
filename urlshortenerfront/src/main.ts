import { createApp } from "vue";
import App from "./App.vue";
import PrimeVue from "primevue/config";
import Aura from "@primeuix/themes/aura";
import CrudLayout from "./layout/CrudLayout.vue";

// 2. Load PrimeIcons (This file still exists in v4)
import "primeicons/primeicons.css";

// 3. Load PrimeFlex Utility Library
import "primeflex/primeflex.css";
import { ToastService } from "primevue";
createApp(App)
  .use(PrimeVue, {
    theme: {
      preset: Aura,
    },
  })
  .use(ToastService)
  .component("CrudLayout", CrudLayout)
  .mount("#app");
