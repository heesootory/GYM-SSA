<template>
  <div id="whole">
    <div id="blocking"></div>
    <form>
      <div id="input_form">
        <div id="today">
          <h2>{{ year + "/" + month + "/" + date }}</h2>
        </div>

        <div>
          <h2>🤸 Workout Record</h2>
          <div>
            <b-form-textarea
              id="textarea-rows"
              placeholder="오늘 한 운동을 기록하세요."
              rows="8"
              v-model="text1"
            ></b-form-textarea>
          </div>
        </div>
        <div>
          <h2>💪 point to fix</h2>
          <div>
            <b-form-textarea
              id="textarea-rows"
              placeholder="교정할 점을 기록하세요."
              rows="4"
              v-model="text2"
            ></b-form-textarea>
          </div>
        </div>

        <div>
          <h3>📹 오늘의 영상</h3>
          <br />
          <div id="video_upload">
            <label for="video" />
            <input
              id="video"
              type="file"
              @change="onFileSelected"
              enctype="multipart/form-data"
            />
          </div>
        </div>

        <div id="jogging">
          <h2>👟 오늘의 조깅 기록</h2>
          <div class="parent">
            <container id="map1" style="width: 100%"></container>
          </div>
        </div>
        <!-- <b-button @click="pos_check">확인용</b-button> -->
      </div>
      <button @click="regist_list">제출</button>
    </form>
  </div>
</template>

<script src="https://api.mapbox.com/mapbox-gl-js/v2.11.0/mapbox-gl.js"></script>

<script src="https://api.mapbox.com/mapbox-gl-js/plugins/mapbox-gl-directions/v4.1.0/mapbox-gl-directions.js"></script>

<script>
import { mapState } from "vuex";
import axios from "@/util/http-common.js";
import mapboxgl from "mapbox-gl";

export default {
  name: "TimelineViewInput",
  computed: {
    ...mapState(["myUser"]),
  },
  data() {
    return {
      // 날짜
      today: "",
      year: "",
      month: "",
      date: "",
      // 운동내용 기록
      text1: "",
      text2: "",
      selectedFile: null, // 영상 선택
      inputToday: "",
      from_lat: "",
      from_lng: "",
      to_lat: "",
      to_lng: "",

      accessToken:
        "pk.eyJ1IjoieHhrbnU4OCIsImEiOiJjbGFrc3RoNm0wM2JjM3BvNmllaWs0aGt1In0.x3g8E5vRCiar6lFliJapmw",
      center: [127.3848, 36.3496], // 첫 화면 좌표 위치
      directions: {},
    };
  },
  created() {
    this.today = new Date();
    this.year = this.today.getFullYear(); // 년도
    this.month = this.today.getMonth() + 1; // 월
    this.date = this.today.getDate(); // 날짜
    this.inputToday = `${this.year}/${this.month}/${this.date}`;
    console.log(this.inputToday);
  },
  mounted() {
    this.createdMap();
  },
  methods: {
    // 위치 확인용
    pos_check() {
      console.log(this.directions.getOrigin());
      console.log(this.directions.getOrigin().geometry.coordinates[0]);
      console.log(this.directions.getOrigin().geometry.coordinates[1]);
      console.log(this.directions.getDestination().geometry.coordinates[0]);
      console.log(this.directions.getDestination().geometry.coordinates[1]);
    },
    // 업로드 한 파일을 이 컴포넌트 데이터에 저장
    onFileSelected(event) {
      this.selectedFile = event.target.files[0];
    },
    // 운동기록을 form data를 만든뒤 -> 서버로 전송
    regist_list() {
      const formData = new FormData();
      formData.append("Videofile", this.selectedFile);
      formData.append("u_id", this.myUser.u_id);
      formData.append("reg_date", this.inputToday);
      formData.append("content", this.text1);
      formData.append("lesson", this.text2);
      formData.append(
        "from_lat",
        this.directions.getOrigin().geometry.coordinates[1]
      );
      formData.append(
        "from_lng",
        this.directions.getOrigin().geometry.coordinates[0]
      );
      formData.append(
        "to_lat",
        this.directions.getDestination().geometry.coordinates[1]
      );
      formData.append(
        "to_lng",
        this.directions.getDestination().geometry.coordinates[0]
      );
      axios({
        headers: {
          "Content-Type": "multipart/form-data",
        },
        method: "POST",
        url: "timelineApi/",
        data: formData,
      }).then((res) => {
        console.log(res);
      });
    },

    // 맵 만들기
    async createdMap() {
      mapboxgl.accessToken = this.accessToken;
      this.map1 = new mapboxgl.Map({
        container: "map1",

        style: "mapbox://styles/mapbox/streets-v12",
        center: this.center,
        zoom: 13,
      });

      // 경로 그리기
      var directions = new MapboxDirections({
        accessToken: mapboxgl.accessToken,
        profile: "mapbox/walking",
        unit: "metric",
        controls: { instructions: false },
        api: "https://api.mapbox.com/directions/v5/",
      });
      this.map1.addControl(directions, "top-left");
      this.directions = directions;
      console.log("입력폼의 맵");
    },
  },
};
</script>

<style scoped>
#today {
  margin: 0px 0px 100px 0px;
}

#textarea-rows {
  color: rgba(255, 255, 255, 1);
  opacity: 0.7;
  background-color: #218e75;
  border-radius: 10px;
  margin: 50px 0px;
}

#video_upload {
  height: 100px;
  width: 350px;
  opacity: 0.7;
  background-color: #218e75;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
}

#whole {
  margin-top: 100px;
  /* background-color: yellow; */
  width: 500px;
}

#input_form {
  width: 600px;
  /* position: fixed; */
}

.parent {
  width: 100%;
  height: 400px;
  /* background-color: red; */
}

#map1 {
  position: relative;
  top: 50px;
  left: -200px;
  right: 0;
  bottom: 0;
  width: 100%;
  height: 550px;
}

#jogging {
  margin-top: 50px;
}
</style>
