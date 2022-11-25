<template>
  <div>
    <div class="logo" v-if="curUser.u_id === myUser.u_id">
      <b>M<span>y</span>P<span>a</span>ge</b>
    </div>

    <!-- <h3 v-if="curUser.u_id === myUser.u_id">마이 페이지</h3> -->
    <h3 v-else>유저 메인페이지</h3>
    <div class="user_detail">
      <div class="user_profile">
        <b-img class="rounded-5" :src="img_src" v-bind="mainProps"></b-img>
      </div>
      <div class="user_info">
        <table>
          <tr>
            <th>ID</th>
            <td>{{ curUser.u_id }}</td>
          </tr>
          <tr>
            <th>NAME</th>
            <td>{{ curUser.name }}</td>
          </tr>
          <tr>
            <th>NICK NAME</th>
            <td>{{ curUser.nickname }}</td>
          </tr>
          <tr>
            <th>EMAIL</th>
            <td>{{ curUser.email }}</td>
          </tr>
          <tr>
            <th>GENDER</th>
            <td>{{ curUser.gender }}</td>
          </tr>
          <tr>
            <th>TEL</th>
            <td>{{ curUser.phone_no | parsePhoneNo }}</td>
          </tr>
        </table>
      </div>
    </div>
    <router-link
      id="btn-twtr1"
      v-if="curUser.u_id === myUser.u_id"
      :to="{ name: 'UserViewUpdate' }"
      >회원정보 수정</router-link
    >
    <button v-else-if="checkIfFollow()" @click="unfollow">팔로우 취소</button>
    <button v-else @click="follow">팔로우</button>

    <b-button
      id="btn-twtr2"
      v-if="curUser.u_id === myUser.u_id"
      @click="deleteUser"
    >
      회원탈퇴
    </b-button>
  </div>
</template>

<script>
import { mapState } from "vuex";
import axios from "@/util/http-common.js";

export default {
  computed: {
    ...mapState(["curUser", "myUser", "myUserFollowers"]),
  },
  data() {
    return {
      img_src: "",
      mainProps: {
        width: 400,
        height: 300,
      },
      modalShow: false,
    };
  },
  filters: {
    parsePhoneNo(phone_no) {
      if (!phone_no) {
        return;
      } else if (phone_no.length == 10) {
        return (
          phone_no.substring(0, 3) +
          "-" +
          phone_no.substring(3, 6) +
          "-" +
          phone_no.substring(6)
        );
      } else if (phone_no.length == 11) {
        return (
          phone_no.substring(0, 3) +
          "-" +
          phone_no.substring(3, 7) +
          "-" +
          phone_no.substring(7)
        );
      }
    },
  },
  watch: {
    myUser() {
      this.img_src =
        "http://localhost:9999/user_profile/" + this.myUser.profile;
    },
  },
  created() {
    this.img_src = "http://localhost:9999/user_profile/" + this.myUser.profile;
  },
  methods: {
    follow() {
      axios
        .post(`followApi/`, null, {
          params: {
            from_user: this.myUser.u_id,
            to_user: this.curUser.u_id,
          },
        })
        .then(() => this.$store.commit("ADD_FOLLOWER"));
    },
    unfollow() {
      axios
        .delete(`followApi/`, {
          params: {
            from_user: this.myUser.u_id,
            to_user: this.curUser.u_id,
          },
        })
        .then(() => this.$store.commit("DELETE_FOLLOWER"));
    },
    checkIfFollow() {
      for (let f of this.myUserFollowers) {
        if (f.u_id === this.curUser.u_id) return true;
      }
      return false;
    },
    deleteUser() {
      axios
        .delete(`userApi/delete`, { params: { id: this.myUser.u_id } })
        .then(() => {
          this.$store.commit("CLEAR_ALL");
          // 세션정보 지우기 : 로그아웃
          sessionStorage.removeItem("access-token");
        })
        .then(() => this.$router.push("/"));
    },
  },
};
</script>

<style>
.user_detail {
  display: flex;
  flex-direction: row;
  justify-content: center;
  font-size: 30px;
}
.user_profile {
  margin-right: 100px;
}

.user_info {
  margin-left: 100px;
}
/* .modal-class {
  width: 500px;
} */

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

/* follow me @nodws */
#btn-twtr1 {
  clear: both;
  color: #fff;
  border: 2px solid;
  border-radius: 3px;
  text-align: center;
  text-decoration: none;
  display: block;
  font-family: sans-serif;
  font-size: 14px;
  width: 13em;
  padding: 5px 10px;
  font-weight: 600;
  position: absolute;
  bottom: 20px;
  left: 0;
  right: 200px;
  margin: 0 auto;
  background: rgba(0, 0, 0);
  box-shadow: 0 0 0px 3px rgba(0, 0, 0, 0.2);
  opacity: 0.4;
}

#btn-twtr2 {
  clear: both;
  color: #fff;
  border: 2px solid;
  border-radius: 3px;
  text-align: center;
  text-decoration: none;
  display: block;
  font-family: sans-serif;
  font-size: 14px;
  width: 13em;
  padding: 5px 10px;
  font-weight: 600;
  position: absolute;
  bottom: 20px;
  left: 200px;
  right: 0;
  margin: 0 auto;
  background: rgba(0, 0, 0, 0.2);
  box-shadow: 0 0 0px 3px rgba(0, 0, 0, 0.2);
  opacity: 0.4;
}

#btn-twtr:hover {
  color: #fff;
  opacity: 1;
}
</style>
