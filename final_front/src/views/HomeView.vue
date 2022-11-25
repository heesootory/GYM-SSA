<template>
  <div class="home">
    <the-header-nav></the-header-nav>
    <!-- <h2>HomeView</h2> -->
    <router-view></router-view>
  </div>
</template>

<script>
import TheHeaderNav from "../components/common/TheHeaderNav.vue";
import axios from "@/util/http-common.js";

export default {
  name: "HomeView",
  components: {
    TheHeaderNav,
  },
  methods: {
    parseJWT(token) {
      var base64Url = token.split(".")[1];
      var base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
      var jsonPayload = decodeURIComponent(
        window
          .atob(base64)
          .split("")
          .map((c) => "%" + ("00" + c.charCodeAt(0).toString(16)).slice(-2))
          .join("")
      );
      return JSON.parse(jsonPayload);
    },
  },
  created() {
    // sessionStorage.setItem(
    //   "access-token",
    //   "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJteU1lc3NhZ2UiOiJXVEZGRkZGIEJST09PT08ifQ.Tf3M9Y8v6vzhDENFgucVt4c9KN1GQn0avqBEpMR_FRQ"
    // );
    // 모든 새로고침에서 store정보가 삭제되기 때문에
    // 이 페이지에서 유저 정보를 다시 요청(토큰 기반)
    const token = sessionStorage.getItem("access-token");
    if (token) {
      const payload = this.parseJWT(token);
      console.log("토큰에 있는 내정보");
      console.log(payload);
      axios
        .get(`userApi/detail`, { params: { id: payload.id } })
        .then((res) => {
          this.$store.commit("SET_MY_USER", res.data);
          this.$store.state.isHomeLoaded = true;
        });
    }
    // else {
    // this.$router.push({ name: "LoginView" });
    // }
  },
};
</script>

<style scoped></style>
