<template>
  <div>
    <div class="video_container">
      <iframe
        class="iframe"
        :src="`https://www.youtube.com/embed/${video.v_id}`"
        frameborder="0"
        width="500px"
        height="300px"
      ></iframe>
      <div class="video_detail">
        <p class="title_icon" style="font-weight: bold">
          &#128249; 영상정보 &#128249;
        </p>
        <div class="video_title">{{ video.title }}</div>
        <p>채널명 : {{ video.v_writer }}</p>
        <p>등록일자 : {{ video.reg_date }}</p>
        <p>카테고리 : {{ video.category }}</p>
        <p>조회수 : {{ video.view_cnt }}</p>
        좋아요 : {{ videoFavoriteUsers.length }}

        <button class="heart_btn" v-if="checkIfFavorite()" @click="unfavorite">
          <p>&#128152;</p>
        </button>
        <button class="heart_btn" v-else @click="favorite">
          <p>&#129293;</p>
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import axios from "@/util/http-common.js";

export default {
  data() {
    return {};
  },
  computed: {
    ...mapState(["video", "myUser", "videoFavoriteUsers"]),
  },
  watch: {
    videoFavoriteUsers() {},
  },
  methods: {
    checkIfFavorite() {
      for (let user of this.videoFavoriteUsers) {
        if (user.u_id === this.myUser.u_id) return true;
      }
      return false;
    },
    favorite() {
      axios
        .post(`favoriteApi/favoriteUp`, null, {
          params: {
            u_id: this.myUser.u_id,
            v_id: this.video.v_id,
          },
        })
        .then(() => this.$store.commit("FAVORITE"));
    },
    unfavorite() {
      axios
        .post(`favoriteApi/favoriteDown`, null, {
          params: {
            u_id: this.myUser.u_id,
            v_id: this.video.v_id,
          },
        })
        .then(() => this.$store.commit("UNFAVORITE"));
    },
  },
};
</script>

<style>
.title_icon {
  font-size: 40px;
}
.video_container {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
}
.heart_btn {
  font-size: 20px;
  text-align: center;
  background-color: #070707;
  opacity: 0.8;
  height: 10px;
}
.iframe {
  max-width: 100%;
  max-height: 100%;
  margin-right: 30px;
  margin-left: 50px;
}
.video_title {
  font-weight: bold;
  font-size: 18px;
}
</style>
