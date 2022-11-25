<template>
  <div class="form">
    <h3 class="title">댓글</h3>
    <div class="text">
      <div class="comment_team1">
        <div>{{ myUser.nickname }}</div>
        <textarea
          class="comment"
          cols="50"
          rows="2"
          v-model="curComment"
          placeholder="댓글을 입력하세요"
        >
        </textarea>
      </div>
      <b-button class="registBtn" @click="registComment">등록</b-button>
    </div>
    <hr />
    <h3 style="text-align: left; margin-top: 50px">등록된 댓글</h3>
  </div>
</template>

<script>
import { mapState } from "vuex";
import axios from "@/util/http-common.js";

export default {
  name: "ViewDetailCommentsRegist",
  data() {
    return {
      curComment: "",
    };
  },
  methods: {
    registComment() {
      axios
        .post(`commentApi/`, null, {
          params: {
            u_id: this.myUser.u_id,
            v_id: this.video.v_id,
            content: this.curComment,
          },
        })
        .then(() => {
          axios
            .get(`commentApi/`, { params: { v_id: this.video.v_id } })
            .then((res) => this.$store.commit("SET_COMMENTS", res.data));
        })
        .then(() => (this.curComment = ""));
    },
  },
  computed: {
    ...mapState(["API_URL", "myUser", "video", "comments"]),
  },
};
</script>

<style scoped>
.comment_team1 {
  display: flex;
}
.title {
  text-align: left;
  margin-top: 50px;
}
button {
  text-align: right;
}
.comment {
  border: 0;
  width: 550px;
  padding: 3px 0;
  box-shadow: none;
  outline: 0;
  background: transparent;
  color: rgb(216, 204, 204);
  resize: none;
  margin: 0px 30px;
}
textarea::placeholder {
  color: #fff;
}
.text {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
hr {
  background: pink;
  height: 5px;
  border: 0;
}
</style>
