<template>
  <div>
    <div class="logo">
      <b><span>H</span>o<span>m</span>e</b>
    </div>

    <div id="mainview" class="container">
      <div @click="moveYouTube">
        <the-main-card
          data-image="https://images.unsplash.com/photo-1479660656269-197ebb83b540?dpr=2&auto=compress,format&fit=crop&w=1199&h=798&q=80&cs=tinysrgb&crop="
        >
          <h1 slot="header">YouTube Video</h1>
          <p slot="content">관심 있는 운동 영상을 시청하세요</p>
        </the-main-card>
      </div>
      <div @click="movePlanner">
        <the-main-card
          data-image="https://images.unsplash.com/photo-1479659929431-4342107adfc1?dpr=2&auto=compress,format&fit=crop&w=1199&h=799&q=80&cs=tinysrgb&crop="
        >
          <h1 slot="header">My Planner</h1>
          <p slot="content">운동 기록</p>
        </the-main-card>
      </div>
      <div @click="moveMyPage">
        <the-main-card
          data-image="https://images.unsplash.com/photo-1479644025832-60dabb8be2a1?dpr=2&auto=compress,format&fit=crop&w=1199&h=799&q=80&cs=tinysrgb&crop="
        >
          <h1 slot="header">My Page</h1>
          <p slot="content">내 정보</p>
        </the-main-card>
      </div>
      <div @click="moveGymMap">
        <the-main-card
          data-image="https://images.unsplash.com/photo-1479621051492-5a6f9bd9e51a?dpr=2&auto=compress,format&fit=crop&w=1199&h=811&q=80&cs=tinysrgb&crop="
        >
          <h1 slot="header">Follower</h1>
          <p slot="content">내 팔로워</p>
        </the-main-card>
      </div>
    </div>
  </div>
</template>

<script>
import TheMainCard from "../components/common/TheMainCard.vue";
import { mapState } from "vuex";

export default {
  name: "MainView",
  components: {
    TheMainCard,
  },
  computed: {
    ...mapState(["myUser"]),
  },
  methods: {
    moveYouTube() {
      this.$router.push({
        name: "VideoSearchView",
      });
    },
    movePlanner() {
      this.$router.push({
        name: "TimelineViewInput",
        params: { id: this.myUser.u_id },
      });
    },
    moveMyPage() {
      this.$router.push({
        name: "UserViewMain",
        params: { id: this.myUser.u_id },
      });
    },
    moveGymMap() {
      this.$router.push({
        name: "FollowerViewMain",
        params: { id: this.myUser.u_id },
      });
    },
  },
};
</script>

<!-------------------------------------------------------------------------------------------------->

<style scoped>
p {
  line-height: 1.5em;
}

h1 + p,
p + p {
  margin-top: 10px;
}

body {
  margin: 40px 0;
  font-family: "Raleway";
  font-size: 14px;
  font-weight: 500;
  background-color: #bcaaa4;
  -webkit-font-smoothing: antialiased;
}
.container {
  padding: 40px 80px;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
}

#mainview {
  display: flex;
  justify-content: space-around;
}
</style>
