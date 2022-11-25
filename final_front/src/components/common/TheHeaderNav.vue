<template>
  <!-- sticky false 헤더바 따라서 내려오게 함 fixed="top"-->
  <b-navbar id="header_whole" toggleable type="lg">
    <!-- 홈 버튼-->
    <b-navbar-brand class="head1">
      <router-link :to="{ name: 'HomeView' }" id="home_btn">
        <div class="logo_h">
          <b>G<span>y</span>m<span>SS</span>a</b>
        </div>
      </router-link>
    </b-navbar-brand>

    <div class="head2">
      <!-- 이미지-->
      <div class="header_profile">
        <b-img
          class="img_css"
          :src="img_src"
          v-bind="mainProps"
          fluid
          alt="Fluid image"
        ></b-img>
      </div>
      <!-- 성함-->
      <div id="user_name">
        <span style="font-size: 20px">{{ this.myUser.name }}님 👋</span>
      </div>

      <!-- 로그아웃-->
      <div id="logout_btn">
        <button class="learn-more" @click="logout">
          <span class="circle" aria-hidden="true">
            <span class="icon arrow"></span>
          </span>
          <span class="button-text">LogOut</span>
        </button>
      </div>

      <!-- 햄버거 -->
      <div id="ham">
        <b-navbar-toggle target="navbar-toggle-collapse"></b-navbar-toggle>
        <b-collapse class="mt-2" id="navbar-toggle-collapse" is-nav>
          <b-navbar-nav class="ml-auto">
            <b-nav-item>
              <router-link
                class="hamburger"
                :to="{ name: 'UserViewMain', params: { id: this.myUser.u_id } }"
                >마이페이지</router-link
              >
            </b-nav-item>
            <b-nav-item>
              <router-link :to="{ name: 'VideoSearch' }" class="hamburger"
                >영상검색</router-link
              >
            </b-nav-item>
            <b-nav-item>
              <router-link
                :to="{ name: 'TimelineView', params: { id: this.myUser.u_id } }"
                class="hamburger"
                >타임라인</router-link
              >
            </b-nav-item>
            <b-nav-item>
              <router-link
                :to="{ name: 'MapViewMain', params: { id: this.myUser.u_id } }"
                class="hamburger"
                >지도</router-link
              >
            </b-nav-item>
          </b-navbar-nav>
        </b-collapse>
      </div>
    </div>
  </b-navbar>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "TheHeaderNav",
  computed: {
    ...mapState(["myUser"]),
  },
  data() {
    return {
      img_src: "",
      mainProps: {
        width: 100,
        height: 200,
      },
    };
  },
  watch: {
    myUser() {
      this.img_src =
        "http://localhost:9999/user_profile/" + this.myUser.profile;
    },
  },
  created() {
    this.img_src = "http://localhost:9999/user_profile/" + this.myUser.profile;
  },
  methods: {
    logout() {
      this.$store.commit("CLEAR_ALL");

      // JWT 토큰 지우기
      sessionStorage.removeItem("access-token");
      this.$router.push({ name: "LoginView" });
    },
  },
};
</script>

<style scoped>
.logo_h b {
  font: 200 5vh "Vibur";
  color: #bafcde;
  text-shadow: 0 -40px 100px, 0 0 2px, 0 0 1em #8bf4c5, 0 0 0.5em #8bf4c5,
    0 0 0.1em #8bf4c5, 0 10px 3px #000;
}
.logo_h b span {
  animation: blink linear infinite 2s;
}
.logo_h b span:nth-of-type(2) {
  animation: blink linear infinite 3s;
}

#user_name {
  margin: 0px 30px;
}

#ham {
  margin: 0px 60px 0px 30px;
}

.hamburger {
  color: green;
}

#header_whole {
  height: 100px;
  display: flex;
  justify-content: space-between;
  padding-left: 50px;
  /* background-color : gray; */
}

.head2 {
  width: 800px;
  display: flex;
  justify-content: flex-end;
  align-items: center;
}

.img_css {
  width: 50px;
  height: 50px;
  border-radius: 30px;
}
/*
.header {
  display: flex;
  justify-content: space-between;
  padding: 20px;
  background-color: rgb(182, 45, 45);
} */

#navbar-toggle-collapse {
  color: red;
}

/* 로그아웃 버튼 CSS */
@import url("https://fonts.googleapis.com/css?family=Mukta:700");
* {
  box-sizing: border-box;
}
*::before,
*::after {
  box-sizing: border-box;
}

body {
  font-family: "Mukta", sans-serif;
  font-size: 1rem;
  line-height: 1.5;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0;
  min-height: 100vh;
  background: #f3f8fa;
}

button {
  position: relative;
  display: inline-block;
  cursor: pointer;
  outline: none;
  border: 0;
  vertical-align: middle;
  text-decoration: none;
  background: transparent;
  padding: 0;
  font-size: inherit;
  font-family: inherit;
}
button.learn-more {
  width: 12rem;
  height: auto;
}
button.learn-more .circle {
  transition: all 0.45s cubic-bezier(0.65, 0, 0.076, 1);
  position: relative;
  display: block;
  margin: 0;
  width: 3rem;
  height: 3rem;
  background: #e7b6c6;
  border-radius: 1.625rem;
}
button.learn-more .circle .icon {
  transition: all 0.45s cubic-bezier(0.65, 0, 0.076, 1);
  position: absolute;
  top: 0;
  bottom: 0;
  margin: auto;
  background: #fff;
}
button.learn-more .circle .icon.arrow {
  transition: all 0.45s cubic-bezier(0.65, 0, 0.076, 1);
  left: 0.625rem;
  width: 1.125rem;
  height: 0.125rem;
  background: none;
}
button.learn-more .circle .icon.arrow::before {
  position: absolute;
  content: "";
  top: -0.25rem;
  right: 0.0625rem;
  width: 0.625rem;
  height: 0.625rem;
  border-top: 0.125rem solid #fff;
  border-right: 0.125rem solid #fff;
  transform: rotate(45deg);
}
button.learn-more .button-text {
  transition: all 0.45s cubic-bezier(0.65, 0, 0.076, 1);
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  padding: 0.75rem 0;
  margin: 0 0 0 1.85rem;
  color: #fff;
  font-weight: 700;
  line-height: 1.6;
  text-align: center;
  text-transform: uppercase;
}
button:hover .circle {
  width: 100%;
}
button:hover .circle .icon.arrow {
  background: #fff;
  transform: translate(1rem, 0);
}
button:hover .button-text {
  color: #fff;
}

@supports (display: grid) {
  body {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    grid-gap: 0.625rem;
    grid-template-areas: ". main main ." ". main main .";
  }

  #logout_btn {
    grid-area: main;
    align-self: center;
    justify-self: center;
  }
}
</style>
