<template>
  <div>
    <router-view></router-view>
  </div>
</template>

<script>
import { mapState } from "vuex";
import axios from "@/util/http-common.js";
export default {
  computed: {
    ...mapState(["myUser", "curUser"]),
  },
  data() {
    return {
      curId: this.$route.params.id,
    };
  },
  watch: {
    $route() {
      this.getUserDetail();
    },
  },
  methods: {
    getUserDetail() {
      axios
        .get(`userApi/detail`, { params: { id: this.$route.params.id } })
        .then((res) => this.$store.commit("SET_CUR_USER", res.data));

      axios
        .get(`followApi/`, { params: { from_user: this.myUser.u_id } })
        .then((res) => this.$store.commit("SET_MY_USER_FOLLOWERS", res.data));
    },
  },
  created() {
    if (!sessionStorage.getItem("access-token")) {
      alert("로그아웃 되었습니다 - 로그인 페이지로 이동합니다.");
      this.$router.push("/login");
    } else {
      this.getUserDetail();
    }
  },
};
</script>

<style></style>
