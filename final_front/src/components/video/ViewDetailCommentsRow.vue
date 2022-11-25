<template>
  <div>
    <div class="comment_whole">
      <div class="com">
        <div class="text">
          <div>{{ comment.nickname }}</div>

          <textarea
            class="comment"
            rows="3"
            v-model="tmpContent"
            :readonly="isReadonly"
          ></textarea>
        </div>
        <div>&#128197; 등록일 : {{ comment.reg_date }}</div>
      </div>
      <div class="btn" v-if="myUser.u_id === comment.u_id">
        <b-button @click="updateComment">수정</b-button>
        <b-button @click="deleteComment">삭제</b-button>
      </div>
    </div>
    <hr />
  </div>
</template>

<script>
import { mapState } from "vuex";
import axios from "@/util/http-common.js";

export default {
  name: "ViewDetailCommentsRow",
  props: {
    comment: Object,
  },
  data() {
    return {
      tmpContent: "",
      isReadonly: true,
    };
  },
  methods: {
    updateComment() {
      if (this.isReadonly) {
        this.isReadonly = false;
        return;
      }
      this.isReadonly = true;
      axios
        .put(`commentApi/`, null, {
          params: {
            c_id: this.comment.c_id,
            content: this.tmpContent,
          },
        })
        .then(() => {
          console.log(this.video);
          axios
            .get(`commentApi/`, { params: { v_id: this.video.v_id } })
            .then((res) => this.$store.commit("SET_COMMENTS", res.data));
        });
    },
    deleteComment() {
      axios
        .delete(`commentApi/${this.comment.c_id}`)
        .then(() => this.$store.commit("DELETE_COMMENT", this.comment.c_id));
    },
  },
  computed: {
    ...mapState(["myUser", "video"]),
  },
  created() {
    this.tmpContent = this.comment.content;
  },
};
</script>

<style scoped>
.comment_whole {
  display: flex;
  justify-content: space-between;
}
textarea {
  border: 2px solid red;
}
textarea[readonly] {
  border: 1px solid black;
}
.comment {
  margin-left: 20px;
  border: 1;
  width: 400px;
  padding: 3px 0;
  box-shadow: none;
  outline: 0;
  background: transparent;
  color: rgb(216, 204, 204);
  resize: none;
}
textarea::placeholder {
  color: #fff;
}
.text {
  display: flex;
  text-align: center;
}
hr {
  background: pink;
  height: 1.5px;
  border: 0;
}
.btn {
  text-align: right;
}
</style>
