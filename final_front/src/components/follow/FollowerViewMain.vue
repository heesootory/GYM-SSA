<template>
  <div>
    <div class="logo">
      <b>Al<span>l</span> U<span>S</span>ER</b>
    </div>
    <b-button id="move_btn">
      <router-link id="to_myFollower" :to="{ name: 'MyFollowerView' }"
        >내 팔로워 보기</router-link
      >
    </b-button>

    <div id="follow_page">
      <div class="all_user">
        <div
          class="user_list"
          v-for="(user, index) in allUser"
          :key="index"
          style="cursor: pointer"
          @click="move_user_timeline(user.u_id)"
        >
          <b-img
            class="profile_img"
            :src="`http://localhost:9999/user_profile/${user.profile}`"
          ></b-img>
          <div class="info">
            <div>{{ user.nickname }}</div>
            <div>{{ user.name }}</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  computed: {
    ...mapState(["allUser"]),
  },
  name: "FollowerViewMain",
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
