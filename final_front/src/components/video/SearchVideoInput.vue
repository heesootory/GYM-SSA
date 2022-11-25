<template>
  <div>
    <form>
      <!-- 간격 조절용 -->
      <div class="check_container1">
        <input
          type="checkbox"
          id="전신"
          name="cc"
          value="전신"
          v-model="category"
        />
        <label for="전신">전신 <span></span></label>
        <input
          type="checkbox"
          id="가슴"
          name="cc"
          value="가슴"
          v-model="category"
        />
        <label for="가슴">가슴 <span></span></label>
        <input
          type="checkbox"
          id="상체"
          name="cc"
          value="상체"
          v-model="category"
        />
        <label for="상체">상체 <span></span></label>
        <input
          type="checkbox"
          id="어깨"
          name="cc"
          value="어깨"
          v-model="category"
        />
        <label for="어깨">어깨 <span></span></label>
        <input
          type="checkbox"
          id="등"
          name="cc"
          value="등"
          v-model="category"
        />
        <label for="등">등 <span></span></label>
        <p />
      </div>
      <!-- 간격 조절용 -->
      <div class="check_container2">
        <input
          type="checkbox"
          id="하체"
          name="cc"
          value="하체"
          v-model="category"
        />
        <label for="하체">하체 <span></span></label>
        <input
          type="checkbox"
          id="복근"
          name="cc"
          value="복근"
          v-model="category"
        />
        <label for="복근">복근 <span></span></label>
        <input
          type="checkbox"
          id="이두"
          name="cc"
          value="이두"
          v-model="category"
        />
        <label for="이두">이두 <span></span></label>
        <input
          type="checkbox"
          id="삼두"
          name="cc"
          value="삼두"
          v-model="category"
        />
        <label for="삼두">삼두 <span></span></label>
        <input
          type="checkbox"
          id="팔뚝"
          name="cc"
          value="팔뚝"
          v-model="category"
        />
        <label for="팔뚝">팔뚝 <span></span></label>
      </div>
      <br />
      <!--  여기까지 체크박스   -->

      <div class="input_container">
        <select v-model="range" name="select">
          <option>전체</option>
          <option>제목</option>
          <option>채널명</option>
        </select>
        <!-- 여기까지 셀렉트 박스 -->

        <input type="text" class="search_input" v-model="keyword" />
        <b-button @click.prevent="searchVideo">검색</b-button>
      </div>
    </form>
  </div>
</template>

<script>
import { mapState } from "vuex";
import axios from "@/util/http-common.js";

export default {
  data() {
    return {
      range: "전체",
      category: [],
      keyword: "",
    };
  },
  methods: {
    searchVideo() {
      axios
        .get(
          `videoApi/video?${this.category
            .map((n) => `category=${n}`)
            .join("&")}`,
          {
            params: {
              keyword: this.keyword,
              range: this.range,
            },
          }
        )
        .then((res) => this.$store.commit("SET_VIDEOS", res.data));
    },
  },
  computed: {
    ...mapState(["videos"]),
  },
};
</script>

<style scoped>
/*  checkbox css  */
body {
  font-family: Arial, sans-serif;
  position: relative;
  background: #40464b;
  height: 100%;
  padding: 40px 30%;
  margin: 0;
}

input[type="checkbox"] {
  display: none;
}

input[type="checkbox"] + label {
  color: #f2f2f2;
}

input[type="checkbox"] + label span {
  display: inline-block;
  width: 19px;
  height: 19px;
  margin: -2px 10px 0 0;
  vertical-align: middle;
  background: url(https://s3-us-west-2.amazonaws.com/s.cdpn.io/210284/check_radio_sheet.png)
    left top no-repeat;
  cursor: pointer;
}

input[type="checkbox"]:checked + label span {
  background: url(https://s3-us-west-2.amazonaws.com/s.cdpn.io/210284/check_radio_sheet.png) -19px
    top no-repeat;
}

/* 셀렉트 박스 */
select {
  background: #6c757d;
  box-sizing: border-box;
  color: #fff;
  width: 80px;
  height: 38.21px;
  padding: 4px;
  font-size: 14px;
  border-radius: 6px;
}

option {
  padding: 4px;
  font-size: 14px;
  color: #fff;
  background: #6c757d;
}

input {
  width: 400px;
  height: 38.21px;
  font-size: 15px;
  border: 0;
  border-radius: 4px;
  outline: none;
  color: #fff;
  padding-left: 10px;
  background-color: #6c757d;
}

.search_input {
  margin: 0 1px 0 1px;
}

/* 검색창 모음  */
.input_container {
}
</style>
