<template>
  <div>
    <div class="logo">
      <b>U<span>p</span>d<span>a</span>te</b>
    </div>
    <div id="loginform">
      <div id="inputlbl">
        <div class="inputsnlables">
          <div class="id_container">
            <label>아이디</label>
            <input
              type="text"
              v-model="tmpUser.u_id"
              required
              style="margin-left: 40px"
              readonly
            />
          </div>
        </div>
        <div class="inputsnlables">
          <label>비밀번호</label
          ><input
            type="text"
            v-model="tmpUser.pw"
            style="margin-right: 40px"
            required
          />
        </div>
        <div class="inputsnlables">
          <label>성명</label
          ><input
            type="text"
            v-model="tmpUser.name"
            style="margin-right: 40px"
            required
          />
        </div>
        <div class="inputsnlables">
          <label>닉네임</label
          ><input
            type="text"
            v-model="tmpUser.nickname"
            style="margin-right: 40px"
          />
        </div>
        <div class="inputsnlables">
          <label>이메일</label
          ><input
            type="email"
            v-model="tmpUser.email"
            style="margin-right: 40px"
            required
          />
        </div>

        <div class="inputsnlables">
          <label>전화번호</label
          ><input
            type="tel"
            pattern="[0-9]{3}-[0-9]{3,4}-[0-9]{4}"
            v-model="tmpUser.phone_no"
            style="margin-right: 40px"
          />
        </div>
        <div class="inputsnlables">
          <label>프로필 사진 <br />변경</label>

          <input
            type="file"
            enctype="multipart/form-data"
            style="color: black; margin-right: 40px"
          />
        </div>
      </div>
      <div class="btn_group">
        <input
          type="button"
          @click.prevent="updateUser"
          value="회원정보 수정"
          style="font-size: 20px"
        />
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import axios from "@/util/http-common.js";

export default {
  data() {
    return {
      tmpUser: {},
    };
  },
  computed: {
    ...mapState(["myUser"]),
  },
  created() {
    this.tmpUser = { ...this.myUser };
    this.tmpUser.phone_no = this.parsePhoneNo(this.tmpUser.phone_no);
  },
  watch: {
    myUser(newVal) {
      // console.log(newVal);
      this.tmpUser = { ...newVal };
      this.tmpUser.phone_no = this.parsePhoneNo(this.tmpUser.phone_no);
    },
  },
  methods: {
    updateUser() {
      console.log(this.tmpUser);
      this.tmpUser.phone_no = this.tmpUser.phone_no.split("-").join("");

      axios
        .put(`userApi/update`, null, { params: this.tmpUser })
        .then(() => {
          this.$store.commit("SET_MY_USER", this.tmpUser);
          this.$store.commit("SET_CUR_USER", this.tmpUser);
        })
        .then(() => this.$router.push({ name: "UserViewMain" }));
    },
    parsePhoneNo(phone_no) {
      // console.log(this.myUser.phone_no);
      if (!phone_no) {
        return;
      }
      if (phone_no.length == 10) {
        return (
          phone_no.slice(0, 3) +
          "-" +
          phone_no.slice(3, 6) +
          "-" +
          phone_no.slice(6)
        );
      } else if (phone_no.length == 11) {
        return (
          phone_no.slice(0, 3) +
          "-" +
          phone_no.slice(3, 7) +
          "-" +
          phone_no.slice(7)
        );
      }
    },
  },
};
</script>

<style scoped>
/* 회원 수정 폼  */
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

/* sign CSS */
@import url(//fonts.googleapis.com/css?family=Vibur);
html,
body {
  height: 100%;
}
body {
  background: #112 url(//images.weserv.nl/?url=i.imgur.com/6QJjYMe.jpg) center
    no-repeat;
  background-attachment: fixed;
  background-size: cover;
  height: 100vh;
  margin: 0;
}

.logo {
  padding: 50px;
  text-align: center;
  width: 65%;
  height: 250px;
  margin: auto;
  /* position: absolute; */
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  user-select: none;
}

.logo b {
  font: 400 10vh "Vibur";
  color: #fee;
  text-shadow: 0 -40px 100px, 0 0 2px, 0 0 1em #ff4444, 0 0 0.5em #ff4444,
    0 0 0.1em #ff4444, 0 10px 3px #000;
}
.logo b span {
  animation: blink linear infinite 2s;
}
.logo b span:nth-of-type(2) {
  animation: blink linear infinite 3s;
}
@keyframes blink {
  78% {
    color: inherit;
    text-shadow: inherit;
  }
  79% {
    color: #333;
  }
  80% {
    text-shadow: none;
  }
  81% {
    color: inherit;
    text-shadow: inherit;
  }
  82% {
    color: #333;
    text-shadow: none;
  }
  83% {
    color: inherit;
    text-shadow: inherit;
  }
  92% {
    color: #333;
    text-shadow: none;
  }
  92.5% {
    color: inherit;
    text-shadow: inherit;
  }
}
</style>
