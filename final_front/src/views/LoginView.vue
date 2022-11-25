<template>
  <div>
    <div class="body"></div>
    <div class="grad"></div>
    <div class="logo">
      <b>Login<span></span><span></span></b>
    </div>
    <div class="header">
      <div>GYM<span> SSA</span></div>
    </div>
    <br />
    <div class="login">
      <form @submit.prevent="login">
        <input
          autocomplete="off"
          type="text"
          placeholder="id"
          v-model="u_id"
          id="id"
          required
        /><br />
        <input
          type="password"
          placeholder="password"
          v-model="pw"
          id="pw"
          required
        /><br />
        <br />
        <b-button variant="outline-secondary" type="submit">로그인</b-button>
        <b-button variant="outline-secondary" type="submit" @click="register"
          >회원가입</b-button
        >
      </form>
    </div>
  </div>
</template>

<script>
import axios from "@/util/http-common.js";

export default {
  name: "LoginView",
  data() {
    return {
      u_id: "",
      pw: "",
    };
  },
  methods: {
    login() {
      console.log(this.u_id + " " + this.pw);
      axios
        .get(`userApi/login`, { params: { id: this.u_id, pw: this.pw } })
        .then((res) => {
          if (res.data.message === "fail") {
            alert("등록된 아이디가 없습니다.");
            throw new Error("등록된 아이디가 없습니다.");
          } else if (res.data.message === "wrong") {
            alert("비밀번호가 맞지 않습니다.");
            throw new Error("비밀번호가 맞지 않습니다.");
          }
          if (!res.data["access-token"]) {
            console.log(res);
            return;
          }
          sessionStorage.setItem("access-token", res.data["access-token"]);

          return axios.get(`userApi/detail`, {
            params: {
              id: this.u_id,
            },
          });
        })
        .then((res) => {
          this.$store.commit("SET_MY_USER", res.data);
          // this.myUser = res.data;
          this.$router.push({ name: "HomeView" });
        })
        .catch((err) => {
          console.log(err);
        });
    },
    register() {
      this.$router.push({ name: "RegisterView" });
    },
  },
  created() {
    if (sessionStorage.getItem("access-token")) {
      this.$router.push({ name: "HomeView" });
    }
  },
};
</script>

<style scoped>
@import url(https://fonts.googleapis.com/css?family=Exo:100,200,400);
@import url(https://fonts.googleapis.com/css?family=Source+Sans+Pro:700,400,300);

body {
  margin: 0;
  padding: 0;
  background: #fff;

  color: #fff;
  font-family: Arial;
  font-size: 12px;
}

.body {
  position: absolute;
  top: -20px;
  left: -20px;
  right: -40px;
  bottom: -40px;
  width: auto;
  height: auto;
  /* background-image: url(https://post-phinf.pstatic.net/MjAxNzEwMTBfNTAg/MDAxNTA3NTkzNTAyMzYx.okX06szYWQXWnVygDhMLU2OTAb41FFczAAR8vHvd8kUg.WCSlKhqQ5qGOe6s953BUXG9iq4S67oSTUJXRQ5bgrycg.JPEG/mug_obj_201710100858228067.jpg?type=w1080); */
  background-size: cover;
  /* -webkit-filter: blur(5px); */
  z-index: 0;
}

.grad {
  position: absolute;
  top: -20px;
  left: -20px;
  right: -40px;
  bottom: -40px;
  width: auto;
  height: auto;
  background: -webkit-gradient(
    linear,
    left top,
    left bottom,
    color-stop(0%, rgba(0, 0, 0, 0)),
    color-stop(100%, rgba(0, 0, 0, 0.65))
  ); /* Chrome,Safari4+ */
  z-index: 1;
  opacity: 0.7;
}

.header {
  position: absolute;
  top: calc(50% - 35px);
  left: calc(50% - 255px);
  z-index: 2;
}

.header div {
  float: left;
  color: #fff;
  font-family: "Exo", sans-serif;
  font-size: 35px;
  font-weight: 200;
}

.header div span {
  color: #e29eb4 !important;
}

.login {
  position: absolute;
  top: calc(50% - 75px);
  left: calc(50% - 50px);
  height: 150px;
  width: 350px;
  padding: 10px;
  z-index: 2;
}

.login input[type="text"] {
  width: 250px;
  height: 30px;
  background: transparent;
  border: 1px solid rgba(255, 255, 255, 0.6);
  border-radius: 2px;
  color: #fff;
  font-family: "Exo", sans-serif;
  font-size: 16px;
  font-weight: 400;
  padding: 4px;
}

.login input[type="password"] {
  width: 250px;
  height: 30px;
  background: transparent;
  border: 1px solid rgba(255, 255, 255, 0.6);
  border-radius: 2px;
  color: #fff;
  font-family: "Exo", sans-serif;
  font-size: 16px;
  font-weight: 400;
  padding: 4px;
  margin-top: 10px;
}

.login input[type="button"] {
  width: 260px;
  height: 35px;
  background: #fff;
  border: 1px solid #fff;
  cursor: pointer;
  border-radius: 2px;
  color: #a18d6c;
  font-family: "Exo", sans-serif;
  font-size: 16px;
  font-weight: 400;
  padding: 6px;
  margin-top: 10px;
}

.login input[type="button"]:hover {
  opacity: 0.8;
}

.login input[type="button"]:active {
  opacity: 0.6;
}

.login input[type="text"]:focus {
  outline: none;
  border: 1px solid rgba(255, 255, 255, 0.9);
}

.login input[type="password"]:focus {
  outline: none;
  border: 1px solid rgba(255, 255, 255, 0.9);
}

.login input[type="button"]:focus {
  outline: none;
}

::-webkit-input-placeholder {
  color: rgba(255, 255, 255, 0.6);
}

::-moz-input-placeholder {
  color: rgba(255, 255, 255, 0.6);
}
</style>
