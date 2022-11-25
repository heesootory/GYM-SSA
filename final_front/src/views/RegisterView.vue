<template>
  <div class="form_main">
    <div class="logo">
      <b>SIGN UP<span></span><span></span></b>
    </div>
    <div id="loginform">
      <div id="inputlbl">
        <div class="inputsnlables">
          <div class="id_container">
            <label>아이디</label>
            <input
              type="text"
              placeholder="아이디"
              v-model="user.u_id"
              required
              style="margin-left: 40px"
            />
            <div>
              <b-button
                pill
                variant="outline-secondary"
                @click="checkDuplicate"
                :class="{ ok: isOk }"
                style="font-size: 12px"
              >
                {{ duplicateMsg }}
              </b-button>
            </div>
          </div>
        </div>
        <div class="inputsnlables">
          <label>비밀번호</label
          ><input
            type="text"
            placeholder="비밀번호"
            v-model="user.pw"
            style="margin-right: 40px"
            required
          />
        </div>
        <div class="inputsnlables">
          <label>성명</label
          ><input
            type="text"
            placeholder="성명"
            v-model="user.name"
            style="margin-right: 40px"
            required
          />
        </div>
        <div class="inputsnlables">
          <label>이메일</label
          ><input
            type="email"
            placeholder="이메일"
            v-model="user.email"
            style="margin-right: 40px"
            required
          />
        </div>
        <div class="inputsnlables">
          <div class="gender">
            <div class="gender_child">
              <label for="M" value="남">남</label>
              <input
                type="radio"
                v-model="user.gender"
                name="gender"
                value="M"
                id="M"
              />
            </div>
            <div class="gender_child">
              <label for="F" value="여">여</label>
              <input
                type="radio"
                v-model="user.gender"
                name="gender"
                value="F"
                id="F"
              />
            </div>
          </div>
        </div>
        <div class="inputsnlables">
          <label>전화번호</label
          ><input
            type="tel"
            pattern="[0-9]{3}-[0-9]{3,4}-[0-9]{4}"
            v-model="user.phone_no"
            placeholder="예 : 010-123(4)-5678"
            style="margin-right: 40px"
          />
        </div>
        <div class="inputsnlables">
          <label>닉네임</label
          ><input
            type="text"
            placeholder="닉네임"
            v-model="user.nickname"
            style="margin-right: 40px"
          />
        </div>
        <div class="inputsnlables">
          <label>프로필 사진 <br />업로드</label>

          <input
            type="file"
            @change="onFileSelected"
            enctype="multipart/form-data"
            style="color: black; margin-right: 40px"
          />
        </div>
      </div>
      <div class="btn_group">
        <input
          type="button"
          @click.prevent="registUser"
          value="회원가입"
          style="font-size: 20px"
        />
        <input
          type="button"
          @click="toLogin"
          value="홈으로"
          style="font-size: 20px"
        />
      </div>
    </div>
  </div>
</template>

<script>
import axios from "@/util/http-common.js";

export default {
  name: "RegisterView",
  data() {
    return {
      user: {
        u_id: "",
        pw: "",
        name: "",
        email: "",
        gender: "M",
        phone_no: "",
        nickname: "",
        profile: "",
      },
      selectedFile: null, // 프로필 이미지 선택
      isOk: false,
    };
  },
  methods: {
    // 파일 선택 메서드
    onFileSelected(event) {
      console.log(event.target.files[0]);
      this.selectedFile = event.target.files[0];
    },
    // 회원가입 요청
    registUser() {
      console.log(" 가입");
      if (!this.isOk) {
        alert("아이디 중복확인을 완료해 주세요.");
        return;
      }
      this.user.phone_no = this.user.phone_no.split("-").join("");

      // form 데이터 생성
      const formData = new FormData();
      formData.append("Imagefile", this.selectedFile); // 프로필 사진
      // User 정보
      formData.append("u_id", this.user.u_id);
      formData.append("pw", this.user.pw);
      formData.append("name", this.user.name);
      formData.append("email", this.user.email);
      formData.append("gender", this.user.gender);
      formData.append("phone_no", this.user.phone_no);
      formData.append("nickname", this.user.nickname);

      axios({
        headers: {
          "Content-Type": "multipart/form-data",
        },
        method: "POST",
        url: "userApi/regist",
        data: formData,
      })
        .then(() => this.$router.push({ name: "LoginView" }))
        .catch((err) => {
          console.log(err.response.data);
        });
    },
    checkDuplicate() {
      axios({
        url: "userApi/check",
        method: "GET",
        params: { id: this.user.u_id },
      }).then((res) => {
        console.log(res);
        if (res.data === "fail") {
          alert("이미 사용중인 아이디입니다.");
          this.isOk = false;
        } else {
          alert("사용 가능한 아이디입니다.");
          this.isOk = true;
        }
      });
    },
    toLogin() {
      this.$router.push({ name: "LoginView" });
    },
  },
  created() {
    if (sessionStorage.getItem("access-token")) {
      this.$router.push({ name: "HomeView" });
    }
  },
  computed: {
    duplicateMsg() {
      return this.isOk ? "중복확인 완료" : "중복확인";
    },
  },
};
</script>

<style scoped>
.gender_child {
  display: flex;
}
.ok {
  background-color: green;
}

table {
  margin: 0 auto;
}

.form_main {
  margin-top: 70px;
}

/* 로그인 폼 CSS */
input:focus {
  outline: 0;
}

::-webkit-input-placeholder {
  font-size: 1em;
}

:-moz-placeholder {
  font-size: 1em;
}

::-moz-placeholder {
  font-size: 1em;
}

body {
  background: url(http://blogs.sap.com/innovation/files/2013/09/274651_h_ergb_s_gl.jpg)
    no-repeat 0 0;
  background-size: cover;
  font-family: Verdana;
  font-size: 0.9em;
}

h1 {
  font-size: 1.6em;
  color: #fff;
  text-align: center;
  font-weight: normal;
}

#loginform {
  width: 500px;
  height: auto;
  margin: 1% auto;
}

#inputlbl {
  padding: 0px 0px 0px 20px;
  background: rgba(255, 255, 255, 0.8);
  border-radius: 5px;
}

#loginform .inputsnlables:last-child {
  border-bottom: none;
}

.inputsnlables {
  overflow: auto;
  border-bottom: 1px solid #dedede;
}

label {
  display: block;
  font-size: 0.9em;
  color: #080808;
  padding: 20px 0px;
  width: 100px;
  float: left;
  font-weight: bold;
}

input {
  border: none;
  padding: 20px 0px;
  background: transparent;
  width: 230px;
  font-size: 0.9em;
}

#M,
#F {
  width: 20px;
}

/* input[type="radio"] {
  font-size: 5px;
} */

input[type="button"] {
  background: #8b8689;
  text-align: center;
  width: 100%;
  border-radius: 5px;
  font-size: 1em;
  color: #fff;
  margin-top: 10px;
  cursor: pointer;
  -webkit-transition: 0.5s ease;
  -moz-transition: 0.5s ease;
  transition: 0.5s ease;
}

input[type="button"]:hover {
  background: #e29eb4;
  -webkit-transition: 0.5s ease;
  -moz-transition: 0.5s ease;
  transition: 0.5s ease;
}

.id_container {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.gender {
  display: flex;
}

.btn_group {
  display: flex;
  justify-content: space-between;
}
</style>
