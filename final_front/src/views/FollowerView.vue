<template>
  <div>
    <router-view></router-view>
  </div>
</template>

<script>
import axios from "@/util/http-common.js";
import { mapState } from "vuex";

export default {
  name: "FollowerView",
  data() {
    return {};
  },
  computed: {
    ...mapState(["myUser"]),
  },
  created() {
    // 로그인 확인
    // if (!sessionStorage.getItem("access-token")) {
    //   alert("로그아웃 되었습니다 - 로그인 페이지로 이동합니다.");
    //   this.$router.push("/login");
    // } else {
    //   this.getUserDetail();
    // }
    //모든 회원 불러오기
    console.log(this.myUser);
    axios({
      method: "GET",
      url: "userApi/all",
    }).then((res) => {
      this.$store.commit("SET_ALLUSER", res.data);
    });
    //팔로워 목록 조회
    axios({
      method: "GET",
      url: "followApi/",
      params: { from_user: this.myUser.u_id },
    }).then((res) => {
      // console.log(res.data);
      this.$store.commit("SET_MYFOLLOWER", res.data);
    });
  },
  methods: {
    allUser() {
      this.$router.push({ name: "FollowerViewMain" });
    },
    follower() {
      this.$router.push({ name: "MyFollowerView" });
    },
  },
};
</script>

<style scoped></style>
