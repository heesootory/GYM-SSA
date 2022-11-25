<template>
  <div>
    <section class="page_whole intro" ref="this_page">
      <div class="container">
        <h1>
          Vertical Timeline <br />
          &darr;
        </h1>
      </div>
    </section>
    <!-- 모달창-->
    <div id="modal-container" ref="modal_con">
      <div class="modal-background">
        <div class="modal">
          <div class="modal_content">
            <div class="modal_btn_head">
              <div class="modal_btns">
                <div id="exit">
                  <b-button @click="fade_out">수정</b-button>
                </div>
                <div id="exit">
                  <b-button @click="fade_out">삭제</b-button>
                </div>
              </div>
              <div id="exit">
                <b-button @click="fade_out">X</b-button>
              </div>
            </div>
            <!-- 모달 정보 -->
            <h2>{{ select_day_info.reg_date }}</h2>
            <p>{{ select_day_info.content }}</p>
            <p>{{ select_day_info.lesson }}</p>
            <div id="my_video">
              <span>📹 이날의 영상</span>
              <iframe id="modal_video" :src="video_src">
                <p>
                  현재 사용 중인 브라우저는 iframe 요소를 지원하지 않습니다!
                </p>
              </iframe>
            </div>
            <!-- 지도 -->
            <div>
              <container id="map2" style="width: 100%"></container>
            </div>
            <svg
              class="modal-svg"
              xmlns="http://www.w3.org/2000/svg"
              width="100%"
              height="100%"
              preserveAspectRatio="none"
            >
              <rect
                x="0"
                y="0"
                fill="none"
                width="200"
                height="100"
                rx="5"
                ry="5"
              ></rect>
            </svg>
          </div>
        </div>
      </div>
    </div>

    <!-- 타임라인-->
    <section v-if="isTimelineViewLoaded" class="timeline">
      <ul>
        <li class="buttons" v-for="(timeline, index) in timeList" :key="index">
          <div
            id="six"
            class="button"
            ref="uuse"
            style="cursor: pointer"
            @click="modal_act(), each_day(timeline.reg_date)"
          >
            <time> {{ timeline.reg_date | pharse_reg_date }}</time>
          </div>
        </li>
      </ul>
    </section>
  </div>
</template>

<script src="https://api.mapbox.com/mapbox-gl-js/v2.11.0/mapbox-gl.js"></script>
<script src="https://api.mapbox.com/mapbox-gl-js/plugins/mapbox-gl-directions/v4.1.0/mapbox-gl-directions.js"></script>

<script>
import { mapState } from "vuex";
import mapboxgl from "mapbox-gl";

export default {
  name: "TimelineViewMain",
  computed: {
    ...mapState(["timeList", "isTimelineViewLoaded"]),
  },
  data() {
    return {
      select_day_info: {},
      video_src: "",
      accessToken:
        "pk.eyJ1IjoieHhrbnU4OCIsImEiOiJjbGFrc3RoNm0wM2JjM3BvNmllaWs0aGt1In0.x3g8E5vRCiar6lFliJapmw",
      center: [127.3848, 36.3496],
      tree_loaded: false,
    };
  },
  filters: {
    pharse_reg_date(reg_date) {
      return reg_date.substring(0, 10);
    },
  },
  watch: {
    timeList() {
      if (this.timeList.length !== 0) {
        this.tree_loaded = true;
      }
    },
  },
  created() {},
  mounted() {
    // 타임라인 그리기
    draw_timeline();
  },
  methods: {
    // 날짜 버튼을 클릭시 -> 1.날짜에 해당하는 정보 찾고 -> 2.맵그리기
    each_day(day) {
      const base_url = "http://localhost:9999/user_video/";
      day = day.substring(0, 10);
      // 해당 날짜 찾기. -> store에 저장
      for (let i = 0; i < this.timeList.length; i++) {
        if (this.timeList[i].reg_date.substring(0, 10) === day) {
          console.log(this.timeList[i]);
          this.select_day_info = this.timeList[i];
          break;
        }
      }
      this.video_src = base_url + this.select_day_info.v_url;
      // 모달 안에 맵그리기
      this.createdMap();
    },
    // 모달 문법
    modal_act() {
      const btn_id = this.$refs.uuse[0].id;
      this.$refs.this_page.classList.add("modal-remove-scrolling");
      this.$refs.this_page.classList.add("modal-active");
      this.$refs.modal_con.removeAttribute("class");
      this.$refs.modal_con.setAttribute("class", btn_id);
    },
    fade_out() {
      this.$refs.modal_con.classList.add("out");
      document.body.classList.remove("modal-active");
      document.body.classList.remove("modal-remove-scrolling");
      this.$refs.modal_con.removeAttribute("class");
    },
    // 맵 만들기
    async createdMap() {
      mapboxgl.accessToken = this.accessToken;
      this.map2 = new mapboxgl.Map({
        container: "map2",

        style: "mapbox://styles/mapbox/streets-v12",
        center: this.center,
        zoom: 10,
      });

      // 경로를 감시하는 객체생성
      var directions = new MapboxDirections({
        accessToken: mapboxgl.accessToken,
        profile: "mapbox/walking",
        api: "https://api.mapbox.com/directions/v5/",
      });
      this.directions = directions;

      // 유저의 조깅 경로 입력하기
      this.directions.setOrigin([
        this.select_day_info.from_lng,
        this.select_day_info.from_lat,
      ]);
      this.directions.setDestination([
        this.select_day_info.to_lng,
        this.select_day_info.to_lat,
      ]);

      // 맵 그리기
      this.map2.addControl(directions, "top-left");
    },
  },
};
// -------------- 타임 라인 코드펜 -------------------
const draw_timeline = function () {
  "use strict";

  function isElementInViewport(el) {
    var rect = el.getBoundingClientRect();
    return (
      rect.top >= 0 &&
      rect.left >= 0 &&
      rect.bottom <=
        (window.innerHeight || document.documentElement.clientHeight) &&
      rect.right <= (window.innerWidth || document.documentElement.clientWidth)
    );
  }

  function callbackFunc() {
    // "새로고침 이슈마다 items배열도 다시 불러오기
    let items = document.querySelectorAll(".timeline li");
    for (var i = 0; i < items.length; i++) {
      if (isElementInViewport(items[i])) {
        items[i].classList.add("in-view");
      }
    }
  }

  // listen for events
  window.addEventListener("load", callbackFunc);
  window.addEventListener("resize", callbackFunc);
  window.addEventListener("scroll", callbackFunc);
};
</script>

<style scoped>
/* ------------------------ 맵 css --------------------------- */
#map2 {
  position: relative;
  top: 50px;
  left: -200px;
  right: 0;
  bottom: 0;
  width: 100%;
  height: 550px;
}
/* ----------------------- --------------------------- */

*,
*::before,
*::after {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font: normal 16px/1.5 "Helvetica Neue", sans-serif;
  background: #66788b;
  color: #fff;
  overflow-x: hidden;
  padding-bottom: 50px;
}

/* INTRO SECTION
–––––––––––––––––––––––––––––––––––––––––––––––––– */
#modal_video {
  width: 400px;
  height: 300px;
}

.intro {
  background: (0, 0, 0, 0);
  padding: 100px 0 20px 0;
}

.container {
  width: 90%;
  max-width: 1200px;
  margin: 0 auto;
  text-align: center;
}

h1 {
  font-size: 2.5rem;
}

/* TIMELINE
–––––––––––––––––––––––––––––––––––––––––––––––––– */

.timeline ul {
  background: (0, 0, 0, 0);
  padding: 50px 0;
  margin: 0px 0px 100px 0px;
}

.timeline ul li {
  list-style-type: none;
  position: relative;
  width: 5px;
  margin: 0 auto;
  padding-top: 40px;
  background: #82959b;
  opacity: 0.7;
}

.timeline ul li::after {
  content: "";
  position: absolute;
  left: 50%;
  bottom: 0;
  transform: translateX(-50%);
  width: 30px;
  height: 30px;
  border-radius: 50%;
  background: inherit;
  z-index: 1;
}

/* 각 타임라인의 크기 */
.timeline ul li:nth-child(odd) div {
  position: relative;
  bottom: 0;
  width: 200px;
  padding: 15px;
  background: #218e75;
  opacity: 0.5;
  border-radius: 100px 40px 100px 35px;
}
.timeline ul li:nth-child(even) div {
  position: relative;
  bottom: 0;
  width: 200px;
  padding: 15px;
  background: #218e75;
  opacity: 0.5;
  border-radius: 35px 100px 40px 100px;
}

.timeline ul li div::before {
  content: "";
  position: absolute;
  bottom: 7px;
  width: 0;
  height: 0;
  border-style: solid;
}

.timeline ul li:nth-child(odd) div {
  left: 45px;
}

.timeline ul li:nth-child(odd) div::before {
  left: -15px;
  border-width: 8px 16px 8px 0;
  width: 20px;
  border-color: transparent #218e75 transparent transparent;
}

/* 가져오는 정도 조절 */
.timeline ul li:nth-child(even) div {
  left: -239px;
}

.timeline ul li:nth-child(even) div::before {
  right: -15px;
  border-width: 8px 0 8px 16px;
  width: 20px;
  border-color: transparent transparent transparent #218e75;
}

time {
  display: block;
  font-size: 1.2rem;
  font-weight: bold;
  margin-bottom: 8px;
  color: #ed87a7;
}

/* EFFECTS
–––––––––––––––––––––––––––––––––––––––––––––––––– */

.timeline ul li::after {
  transition: background 0.5s ease-in-out;
}

.timeline ul li.in-view::after {
  background: #82959b;
  opacity: 0.8;
}

.timeline ul li div {
  visibility: hidden;
  opacity: 0;
  transition: all 0.5s ease-in-out;
}

/* 이동하는 애니메이션 */
.timeline ul li:nth-child(odd) div {
  transform: translate3d(200px, 0, 0);
}

.timeline ul li:nth-child(even) div {
  transform: translate3d(-200px, 0, 0);
}

.timeline ul li.in-view div {
  transform: none;
  visibility: visible;
  opacity: 1;
}

/* GENERAL MEDIA QUERIES
–––––––––––––––––––––––––––––––––––––––––––––––––– */

@media screen and (max-width: 900px) {
  .timeline ul li div {
    width: 250px;
  }
  .timeline ul li:nth-child(even) div {
    left: -289px;
    /*250+45-6*/
  }
}

@media screen and (max-width: 600px) {
  .timeline ul li {
    margin-left: 20px;
  }
  .timeline ul li div {
    width: calc(100vw - 91px);
  }
  .timeline ul li:nth-child(even) div {
    left: 45px;
  }
  .timeline ul li:nth-child(even) div::before {
    left: -15px;
    border-width: 8px 16px 8px 0;
    border-color: transparent #f45b69 transparent transparent;
  }
}

/* EXTRA/CLIP PATH STYLES
–––––––––––––––––––––––––––––––––––––––––––––––––– */
.timeline-clippy ul li::after {
  width: 40px;
  height: 40px;
  border-radius: 0;
}

.timeline-rhombus ul li::after {
  clip-path: polygon(50% 0%, 100% 50%, 50% 100%, 0% 50%);
}

.timeline-rhombus ul li div::before {
  bottom: 12px;
}

.timeline-star ul li::after {
  clip-path: polygon(
    50% 0%,
    61% 35%,
    98% 35%,
    68% 57%,
    79% 91%,
    50% 70%,
    21% 91%,
    32% 57%,
    2% 35%,
    39% 35%
  );
}

.timeline-heptagon ul li::after {
  clip-path: polygon(
    50% 0%,
    90% 20%,
    100% 60%,
    75% 100%,
    25% 100%,
    0% 60%,
    10% 20%
  );
}

.timeline-infinite ul li::after {
  animation: scaleAnimation 2s infinite;
}

@keyframes scaleAnimation {
  0% {
    transform: translateX(-50%) scale(1);
  }
  50% {
    transform: translateX(-50%) scale(1.25);
  }
  100% {
    transform: translateX(-50%) scale(1);
  }
}

/* FOOTER STYLES
–––––––––––––––––––––––––––––––––––––––––––––––––– */
.page-footer {
  position: fixed;
  right: 0;
  bottom: 20px;
  display: flex;
  align-items: center;
  padding: 5px;
  color: black;
  background: rgba(255, 255, 255, 0.65);
}

.page-footer a {
  display: flex;
  margin-left: 4px;
}

html.modal-active,
body.modal-active {
  overflow: hidden;
}

#modal-container {
  position: fixed;
  display: table;
  height: 100%;
  width: 100%;
  top: 0;
  left: 0;
  transform: scale(0);
  z-index: 6;
}

#modal-container.six {
  transform: scale(1);
}
#modal-container.six .modal-background {
  background: rgba(0, 0, 0, 0);
  animation: fadeIn 0.5s cubic-bezier(0.165, 0.84, 0.44, 1) forwards;
}

/* 모달창 크기 */
#modal-container.six .modal-background .modal {
  width: 600px;
  height: 700px;
  background-color: black;
  background-color: transparent;
  /* 진해지는 시간/ 나오기 까지 시간 */
  animation: modalFadeIn 1.5s 0.5s cubic-bezier(0.165, 0.84, 0.44, 1) forwards;
}
#modal-container.six .modal-background .modal h2,
#modal-container.six .modal-background .modal p {
  opacity: 0;
  position: relative;
  animation: modalContentFadeIn 0.5s 1s cubic-bezier(0.165, 0.84, 0.44, 1)
    forwards;
}
/* 모달창 등장할때 그리기 */
#modal-container.six .modal-background .modal .modal-svg rect {
  animation: sketchIn 0.5s 0.5s cubic-bezier(0.165, 0.84, 0.44, 1) forwards;
}
#modal-container.six.out {
  animation: quickScaleDown 0s 0.5s linear forwards;
}
#modal-container.six.out .modal-background {
  animation: fadeOut 0.5s cubic-bezier(0.165, 0.84, 0.44, 1) forwards;
}
#modal-container.six.out .modal-background .modal {
  animation: modalFadeOut 0.5s cubic-bezier(0.165, 0.84, 0.44, 1) forwards;
}
#modal-container.six.out .modal-background .modal h2,
#modal-container.six.out .modal-background .modal p {
  animation: modalContentFadeOut 0.5s cubic-bezier(0.165, 0.84, 0.44, 1)
    forwards;
}
#modal-container.six.out .modal-background .modal .modal-svg rect {
  animation: sketchOut 0.5s cubic-bezier(0.165, 0.84, 0.44, 1) forwards;
}

#modal-container .modal-background {
  display: table-cell;
  background: rgba(0, 0, 0, 0.8);
  text-align: center;
  vertical-align: middle;
}
#modal-container .modal-background .modal {
  background: #176b62;
  padding: 50px;
  display: inline-block;
  border-radius: 3px;
  font-weight: 300;
  position: relative;
}

#modal-container .modal-background .modal .modal_content #my_video {
  margin: 20px;
}

#modal-container .modal-background .modal .modal_btn_head {
  display: flex;
  justify-content: space-between;
  margin: 15px;
}

/* 수정, 삭제 */
#modal-container .modal-background .modal .modal_btn_head .modal_btns {
  display: flex;
}

/* 종료 */
#modal-container .modal-background .modal .modal_btn_head #exit {
  margin: 15px;
}

#modal-container .modal-background .modal h2 {
  font-size: 25px;
  line-height: 25px;
  margin: 15px;
}

#modal-container .modal-background .modal .modal_content {
  background: #176b62;
  font-size: 25px;
  line-height: 25px;
  margin-bottom: 15px;
  /* border-radius: 50px; */
  width: 100%;
  border: 1px solid #e7b6c6;
}

#modal-container .modal-background .modal p {
  /* background: #176b62; */
  font-size: 18px;
  line-height: 22px;
}

#modal-container .modal-background .modal div {
  background: #176b62;
}
/* 모달 그리기 z축 변경 */
#modal-container .modal-background .modal .modal-svg {
  position: absolute;
  top: 150px;
  left: 0;
  height: 100%;
  width: 100%;
  border-radius: 3px;
  z-index: -1;
}
#modal-container .modal-background .modal .modal-svg rect {
  stroke: #fff;
  stroke-width: 3px;
  stroke-dasharray: 778;
  stroke-dashoffset: 778;
}

.content {
  min-height: 100%;
  height: 100%;
  background: white;
  position: relative;
  z-index: 0;
}
.content h1 {
  padding: 75px 0 30px 0;
  text-align: center;
  font-size: 30px;
  line-height: 30px;
}
.content .buttons {
  max-width: 800px;
  margin: 0 auto;
  padding: 0;
  text-align: center;
}
.content .buttons .button {
  display: inline-block;
  text-align: center;
  padding: 10px 15px;
  margin: 10px;
  background: red;
  font-size: 18px;
  background-color: #efefef;
  border-radius: 8px;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.3);
  cursor: pointer;
}
.content .buttons .button:hover {
  color: white;
  background: #009bd5;
}

@keyframes unfoldIn {
  0% {
    transform: scaleY(0.005) scaleX(0);
  }
  50% {
    transform: scaleY(0.005) scaleX(1);
  }
  100% {
    transform: scaleY(1) scaleX(1);
  }
}
@keyframes unfoldOut {
  0% {
    transform: scaleY(1) scaleX(1);
  }
  50% {
    transform: scaleY(0.005) scaleX(1);
  }
  100% {
    transform: scaleY(0.005) scaleX(0);
  }
}
@keyframes zoomIn {
  0% {
    transform: scale(0);
  }
  100% {
    transform: scale(1);
  }
}
@keyframes zoomOut {
  0% {
    transform: scale(1);
  }
  100% {
    transform: scale(0);
  }
}
@keyframes fadeIn {
  0% {
    background: rgba(0, 0, 0, 0);
  }
  100% {
    background: rgba(0, 0, 0, 0.7);
  }
}
@keyframes fadeOut {
  0% {
    background: rgba(0, 0, 0, 0.7);
  }
  100% {
    background: rgba(0, 0, 0, 0);
  }
}
@keyframes scaleUp {
  0% {
    transform: scale(0.8) translateY(1000px);
    opacity: 0;
  }
  100% {
    transform: scale(1) translateY(0px);
    opacity: 1;
  }
}
@keyframes scaleDown {
  0% {
    transform: scale(1) translateY(0px);
    opacity: 1;
  }
  100% {
    transform: scale(0.8) translateY(1000px);
    opacity: 0;
  }
}
@keyframes scaleBack {
  0% {
    transform: scale(1);
  }
  100% {
    transform: scale(0.85);
  }
}
@keyframes scaleForward {
  0% {
    transform: scale(0.85);
  }
  100% {
    transform: scale(1);
  }
}
@keyframes quickScaleDown {
  0% {
    transform: scale(1);
  }
  99.9% {
    transform: scale(1);
  }
  100% {
    transform: scale(0);
  }
}
@keyframes slideUpLarge {
  0% {
    transform: translateY(0%);
  }
  100% {
    transform: translateY(-100%);
  }
}
@keyframes slideDownLarge {
  0% {
    transform: translateY(-100%);
  }
  100% {
    transform: translateY(0%);
  }
}
@keyframes moveUp {
  0% {
    transform: translateY(150px);
  }
  100% {
    transform: translateY(0);
  }
}
@keyframes moveDown {
  0% {
    transform: translateY(0px);
  }
  100% {
    transform: translateY(150px);
  }
}
@keyframes blowUpContent {
  0% {
    transform: scale(1);
    opacity: 1;
  }
  99.9% {
    transform: scale(2);
    opacity: 0;
  }
  100% {
    transform: scale(0);
  }
}
@keyframes blowUpContentTwo {
  0% {
    transform: scale(2);
    opacity: 0;
  }
  100% {
    transform: scale(1);
    opacity: 1;
  }
}
@keyframes blowUpModal {
  0% {
    transform: scale(0);
  }
  100% {
    transform: scale(1);
  }
}
@keyframes blowUpModalTwo {
  0% {
    transform: scale(1);
    opacity: 1;
  }
  100% {
    transform: scale(0);
    opacity: 0;
  }
}
@keyframes roadRunnerIn {
  0% {
    transform: translateX(-1500px) skewX(30deg) scaleX(1.3);
  }
  70% {
    transform: translateX(30px) skewX(0deg) scaleX(0.9);
  }
  100% {
    transform: translateX(0px) skewX(0deg) scaleX(1);
  }
}
@keyframes roadRunnerOut {
  0% {
    transform: translateX(0px) skewX(0deg) scaleX(1);
  }
  30% {
    transform: translateX(-30px) skewX(-5deg) scaleX(0.9);
  }
  100% {
    transform: translateX(1500px) skewX(30deg) scaleX(1.3);
  }
}
@keyframes sketchIn {
  0% {
    stroke-dashoffset: 778;
  }
  100% {
    stroke-dashoffset: 0;
  }
}
@keyframes sketchOut {
  0% {
    stroke-dashoffset: 0;
  }
  100% {
    stroke-dashoffset: 778;
  }
}
@keyframes modalFadeIn {
  0% {
    background-color: transparent;
  }
  100% {
    background-color: white;
  }
}
@keyframes modalFadeOut {
  0% {
    background-color: white;
  }
  100% {
    background-color: transparent;
  }
}
@keyframes modalContentFadeIn {
  0% {
    opacity: 0;
    top: -20px;
  }
  100% {
    opacity: 1;
    top: 0;
  }
}
@keyframes modalContentFadeOut {
  0% {
    opacity: 1;
    top: 0px;
  }
  100% {
    opacity: 0;
    top: -20px;
  }
}
@keyframes bondJamesBond {
  0% {
    transform: translateX(1000px);
  }
  80% {
    transform: translateX(0px);
    border-radius: 75px;
    height: 75px;
    width: 75px;
  }
  90% {
    border-radius: 3px;
    height: 182px;
    width: 247px;
  }
  100% {
    border-radius: 3px;
    height: 162px;
    width: 227px;
  }
}
@keyframes killShot {
  0% {
    transform: translateY(0) rotate(0deg);
    opacity: 1;
  }
  100% {
    transform: translateY(300px) rotate(45deg);
    opacity: 0;
  }
}
@keyframes fadeToRed {
  0% {
    background-color: rgba(0, 0, 0, 0.6);
  }
  100% {
    background-color: rgba(255, 0, 0, 0.8);
  }
}
@keyframes slowFade {
  0% {
    opacity: 1;
  }
  99.9% {
    opacity: 0;
    transform: scale(1);
  }
  100% {
    transform: scale(0);
  }
}
.jq {
  position: relative;
  z-index: 9;
  padding: 30px;
}
</style>
