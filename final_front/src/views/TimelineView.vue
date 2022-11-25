<template>
  <div id="timeline">
    <div class="logo">
      <b v-if="cur_id != myUser.u_id"
        ><span>{{ cur_id }}</span></b
      >
      <b>Ti<span>me</span>L<span>i</span>ne</b>
    </div>
    <div class="container">
      <timeline-view-main></timeline-view-main>
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import TimelineViewMain from "../components/timeline/TimelineViewMain.vue";
import axios from "@/util/http-common.js";
import { mapState } from "vuex";

export default {
  name: "TimelineView",
  components: {
    TimelineViewMain,
  },
  computed: {
    ...mapState(["myUser", "isHomeLoaded"]),
  },
  data() {
    return {
      cur_id: {},
    };
  },
  watch: {
    isHomeLoaded() {
      axios({
        method: "GET",
        url: "timelineApi/",
        params: { u_id: this.myUser.u_id },
      }).then((res) => {
        console.log("timeListView");
        console.log(res.data);
        this.$store.commit("SET_TIMELIST", res.data);
        this.$store.state.isTimelineViewLoaded = true;
      });
    },
  },
  created() {
    this.cur_id = this.$route.params.id;
    console.log("파라미터id : " + this.cur_id);
    console.log("로그인id : " + this.myUser.u_id);
    if (this.cur_id === this.myUser.id) {
      if (this.isHomeLoaded) {
        axios({
          method: "GET",
          url: "timelineApi/",
          params: { u_id: this.myUser.u_id },
        }).then((res) => {
          console.log("timeListView");
          // console.log(this.myUser);
          console.log(res.data);
          this.$store.commit("SET_TIMELIST", res.data);
          this.$store.state.isTimelineViewLoaded = true;
        });
      }
    } else {
      axios({
        method: "GET",
        url: "timelineApi/",
        params: { u_id: this.cur_id },
      }).then((res) => {
        console.log("timeListView");
        console.log(res.data);
        this.$store.commit("SET_TIMELIST", res.data);
        this.$store.state.isTimelineViewLoaded = true;
      });
    }
  },
};
</script>

<style scoped>
.container {
  display: flex;
  justify-content: space-around;
}
</style>
