<template>
  <div>
    <!-- <select id="sort" v-model="sortCriteria">
      <option>----</option>
      <option>제목</option>
      <option>조회수</option>
      <option>등록일</option>
      <option>좋아요수</option>
    </select> -->
    <p>정렬기준</p>
    <div>
      <input type="radio" name="radiobtn" id="정렬해제" />
      <label for="정렬해제">default <span></span> </label>

      <input
        type="radio"
        name="radiobtn"
        id="제목"
        @click="sortCriteria = '제목'"
      />
      <label for="제목">제목 <span></span> </label>

      <input
        type="radio"
        name="radiobtn"
        id="조회수"
        @click="sortCriteria = '조회수'"
      />
      <label for="조회수">조회수 <span></span> </label>

      <input
        type="radio"
        name="radiobtn"
        id="등록일"
        @click="sortCriteria = '등록일'"
      />
      <label for="등록일">등록일 <span></span> </label>

      <input
        type="radio"
        name="radiobtn"
        id="좋아요수"
        @click="sortCriteria = '좋아요수'"
      />
      <label for="좋아요수">좋아요수 <span></span> </label>
    </div>

    <!-- <div>
      <button @click="sortCriteria = '제목'">제목</button>
      <button @click="sortCriteria = '조회수'">조회수</button>
      <button @click="sortCriteria = '등록일'">등록일</button>
      <button @click="sortCriteria = '좋아요수'">좋아요수</button>
    </div> -->

    <div class="container container__content--flow">
      <span class="videosection" v-for="video in curVideos" :key="video.id">
        <search-video-result-row :video="video"></search-video-result-row>
      </span>
    </div>
    <!-- pagination -->
    <div class="d-flex justify-content-center">
      <b-pagination
        v-model="curPage"
        :total-rows="totalRows"
        :per-page="PAGE_SIZE"
        aria-controls="my-table"
      >
      </b-pagination>
    </div>
  </div>
</template>

<script>
import SearchVideoResultRow from "@/components/video/SearchVideoResultRow.vue";
import { mapState } from "vuex";

export default {
  computed: {
    ...mapState(["videos"]),
    totalRows() {
      return this.videos.length;
    },
  },
  components: {
    SearchVideoResultRow,
  },
  data() {
    return {
      sortCriteria: "",
      curPage: 1,
      curVideos: [],
      PAGE_SIZE: 9,
    };
  },
  watch: {
    sortCriteria(newVal) {
      this.$store.commit("SORT_VIDEOS", newVal);
    },
    curPage(newVal) {
      this.curVideos = this.videos.slice(
        (newVal - 1) * this.PAGE_SIZE,
        newVal * this.PAGE_SIZE
      );
    },
    videos() {
      this.curPage = 1; // 검색마다 1페이지로 갱신
      this.curVideos = this.videos.slice(
        (this.curPage - 1) * this.PAGE_SIZE,
        this.curPage * this.PAGE_SIZE
      );
    },
  },
  created() {
    this.curVideos = this.videos.slice(
      (this.curPage - 1) * this.PAGE_SIZE,
      this.curPage * this.PAGE_SIZE
    );
  },
};
</script>

<style scoped>
/*  radioBtn css  */
body {
  font-family: Arial, sans-serif;
  position: relative;
  background: #40464b;
  height: 100%;
  padding: 40px 30%;
  margin: 0;
}

input[type="radio"] {
  display: none;
}

input[type="radio"] + label {
  color: #f2f2f2;
  font-family: Arial, sans-serif;
}

input[type="radio"] + label span {
  display: inline-block;
  width: 19px;
  height: 19px;
  margin: -2px 10px 0 0;
  vertical-align: middle;
  background: url(https://s3-us-west-2.amazonaws.com/s.cdpn.io/210284/check_radio_sheet.png) -38px
    top no-repeat;
  cursor: pointer;
}

input[type="radio"]:checked + label span {
  background: url(https://s3-us-west-2.amazonaws.com/s.cdpn.io/210284/check_radio_sheet.png) -57px
    top no-repeat;
}

.videosection {
  display: flex;
  justify-content: space-evenly;
}

*,
*::before,
*::after {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

img {
  width: 100%;
  object-fit: cover;
  padding: 0.35em;
  border-radius: 0.6rem;
  min-height: 10rem;
}

body {
  background: linear-gradient(45deg, #f8e8b5, #e2c4ed);
}

.container {
  display: flex;
  justify-content: center;
  max-width: clamp(30rem, 100% - 2rem, 60rem);
  width: max(22.5rem, 100%);
  min-height: 100vh;
  margin-inline: auto;
}

.container__content--flow {
  padding-block: 1rem;
  display: flex;
  flex-wrap: wrap;
  gap: 1rem;
}

.container__content--flow > * {
  flex-grow: 1;
  margin: 0 auto;
  flex-basis: calc((30rem - 100%) * 999);
  min-width: 15rem;
  max-width: calc(100% / 1.5);
}

.card {
  display: flex;
  flex-direction: column;
  background-color: #fff;
  border-radius: 0.5em;
  box-shadow: 0 0.125em 0.25em #0004;
  transition: all 400ms ease;
  position: relative;
}

.card::before {
  content: "";
  position: absolute;
  inset: 0;
  opacity: 0;
  border-radius: inherit;
  transition: opacity 300ms ease;
  box-shadow: 0 0.125em 0.25em #0003, 0 0.65em 0.85em #0003,
    0 0.96em 1.35em #0002;
}

.card:hover::before {
  opacity: 1;
}

.card h2 {
  padding-block: 1rem;
  text-align: center;
  color: #3871a0;
  font-weight: bolder;
  font-size: 1.37rem;
}

.card > p {
  padding-block-end: 0.5rem;
  margin-inline: 0.5em;
  text-align: center;
}

.card:nth-child(odd) {
  flex: 1 1 25%;
}

.container__content--flow:hover .card:not(:hover) {
  background-color: #fff6;
  box-shadow: none;
  opacity: 0.8;
}

.card:hover {
  scale: 1.05;
}

@media (max-width: 830px) {
  .card:nth-child(odd) {
    flex: 1 1;
  }
}
</style>
