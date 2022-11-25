import Vue from "vue";
import VueRouter from "vue-router";
import HomeView from "../views/HomeView.vue";
import MainContentView from "../views/MainContentView.vue";
import LoginView from "../views/LoginView.vue";
import RegisterView from "../views/RegisterView.vue";
import VideoSearchView from "../views/VideoSearchView.vue";
import UserView from "../views/UserView.vue";
import TimelineView from "../views/TimelineView.vue";
import FollowerView from "../views/FollowerView.vue";

import VideoSearch from "../components/video/VideoSearch.vue";
import VideoDetail from "../components/video/VideoDetail.vue";
import UserViewMain from "../components/user/UserViewMain.vue";
import UserViewUpdate from "../components/user/UserViewUpdate.vue";
import TimelineViewInput from "../components/timeline/TimelineViewInput";

import FollowerViewMain from "../components/follow/FollowerViewMain";
import MyFollowerView from "../components/follow/MyFollowerView";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    redirect: "/login",
  },
  {
    path: "/login",
    name: "LoginView",
    component: LoginView,
  },
  {
    path: "/register",
    name: "RegisterView",
    component: RegisterView,
  },
  {
    path: "/home",
    name: "HomeView",
    component: HomeView,
    redirect: "/home/maincontent",
    children: [
      {
        path: "maincontent",
        name: "MainContentView",
        component: MainContentView,
      },
      {
        path: "videosearch",
        name: "VideoSearchView",
        component: VideoSearchView,
        redirect: "/home/videosearch/search",
        children: [
          {
            path: "search",
            name: "VideoSearch",
            component: VideoSearch,
          },
          {
            path: "detail/:id",
            name: "VideoDetail",
            component: VideoDetail,
          },
        ],
      },
      {
        path: "user/:id",
        name: "UserView",
        component: UserView,
        children: [
          {
            path: "",
            name: "UserViewMain",
            component: UserViewMain,
          },
          {
            path: "update",
            name: "UserViewUpdate",
            component: UserViewUpdate,
          },
        ],
      },
      {
        path: "timeline/:id",
        name: "TimelineView",
        component: TimelineView,
        children: [
          {
            path: "",
            name: "TimelineViewInput",
            component: TimelineViewInput,
          },
        ],
      },
      {
        path: "follower/:id",
        name: "FollowerView",
        component: FollowerView,
        children: [
          {
            path: "",
            name: "FollowerViewMain",
            component: FollowerViewMain,
          },
          {
            path: "follower",
            name: "MyFollowerView",
            component: MyFollowerView,
          },
        ],
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
