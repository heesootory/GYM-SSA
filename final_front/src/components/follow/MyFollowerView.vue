<template>
  <div>
    <div class="logo">
      <b>Fo<span>l</span>l<span>ow</span>er</b>
    </div>
    <b-button id="move_btn">
      <router-link id="to_allUser" :to="{ name: 'FollowerViewMain' }"
        >모든 유저 보기</router-link
      >
    </b-button>
    <div id="follow_page">
      <div class="all_user">
        <div
          class="user_list"
          v-for="(follower, index) in myFollower"
          :key="index"
          style="cursor: pointer"
          @click="move_user_timeline(follower.u_id)"
        >
          <b-img
            class="profile_img"
            :src="`http://localhost:9999/user_profile/${follower.profile}`"
          ></b-img>
          <div class="info">
            <div>{{ follower.nickname }}</div>
            <div>{{ follower.name }}</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "MyFollowerView",
  computed: {
    ...mapState(["myFollower"]),
  },
  created() {
    console.log(this.myFollower);
  },
  methods: {
    move_user_timeline(u_id) {
      this.$router.push({
        name: "TimelineView",
        params: { id: u_id },
      });
    },
  },
};
</script>

<style scoped>
/* #to_allUser {
} */

#move_btn {
  background-color: #e7b6c6;
  color: white;
}

a {
  text-decoration: none;
  color: white;
}

#follow_page {
  display: flex;
  justify-content: center;
}
.all_user {
  margin: 50px;
  width: 1000px;
  display: flex;
  flex-wrap: wrap;
}
.user_list {
  margin: 20px;
}
.profile_img {
  border: 2px solid rosybrown;
  border-radius: 20px;
  width: 150px;
  height: 150px;
  /* background-color: rosybrown; */
}
</style>
