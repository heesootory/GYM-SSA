import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    API_URL: "http://localhost:9999/api",
    curUser: {},
    myUser: {},
    myUserFollowers: [],
    videos: [],
    video: {},
    videoFavoriteUsers: [],
    comments: [],
    comment: {},
    timeList: [],
    allUser: [],
    myFollower: [],
    isHomeLoaded: false,
    isTimelineViewLoaded: false,
  },
  getters: {},
  mutations: {
    SET_MYFOLLOWER(state, payload) {
      state.myFollower = payload;
    },
    SET_ALLUSER(state, payload) {
      state.allUser = payload;
    },
    SET_TIMELIST(state, payload) {
      state.timeList = payload;
    },
    SET_CUR_USER(state, payload) {
      state.curUser = payload;
    },
    SET_MY_USER(state, payload) {
      state.myUser = payload;
    },
    SET_MY_USER_FOLLOWERS(state, payload) {
      state.myUserFollowers = payload;
    },
    ADD_FOLLOWER(state) {
      state.myUserFollowers.push(state.curUser);
    },
    DELETE_FOLLOWER(state) {
      state.myUserFollowers = state.myUserFollowers.filter(
        (f) => f.u_id !== state.curUser.u_id
      );
    },
    CLEAR_ALL(state) {
      state.curUser = {};
      state.myUser = {};
      state.myUserFollowers = [];
      state.videos = [];
      state.video = {};
      state.comments = [];
    },
    SORT_VIDEOS(state, payload) {
      switch (payload) {
        case "제목":
          console.log(payload);
          state.videos.sort((a, b) => a.title.localeCompare(b.title));
          break;
        case "조회수":
          state.videos.sort((b, a) => a.view_cnt - b.view_cnt);
          break;
        case "등록일":
          state.videos.sort((b, a) => a.reg_date.localeCompare(b.reg_date));
          break;
        case "좋아요수":
          state.videos.sort((b, a) => a.favorite_cnt - b.favorite_cnt);
          break;
      }
    },
    FAVORITE(state) {
      state.videoFavoriteUsers.push(state.myUser);
    },
    UNFAVORITE(state) {
      state.videoFavoriteUsers = state.videoFavoriteUsers.filter(
        (user) => user.u_id !== state.myUser.u_id
      );
    },
    SET_COMMENTS(state, payload) {
      state.comments = payload;
    },
    DELETE_COMMENT(state, payload) {
      state.comments = state.comments.filter((c) => c.c_id !== payload);
    },
    SET_VIDEOS(state, payload) {
      state.videos = payload;
    },
    SET_VIDEO(state, payload) {
      state.video = payload;
    },
    SET_VIDEO_FAVORITE_USERS(state, payload) {
      state.videoFavoriteUsers = payload;
    },
  },
  actions: {},
  modules: {},
});
