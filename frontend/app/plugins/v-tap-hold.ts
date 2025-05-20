import { defineNuxtPlugin } from "#app";
import vTapHold from "~/directives/v-tap-hold";

export default defineNuxtPlugin((nuxtApp) => {
	nuxtApp.vueApp.directive("tap-hold", vTapHold);
});
