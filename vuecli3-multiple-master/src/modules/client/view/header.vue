<template>
  <div>
    <button class="user-profile__subscribe-button" type="button" @click="prompt">按鈕</button>

    <v-header></v-header>
    <router-link :to="{name:'home'}">Home</router-link>
    <router-link :to="{name:'userinfo'}">User Info</router-link>
    <router-link :to="{name:'test'}">test</router-link>

    <a href @click.prevent="logout">Logout</a>

    <el-dropdown trigger="click" @command="handleCommand">
      <span class="el-dropdown-link">
        多國語系
        <i class="el-icon-arrow-down el-icon--right"></i>
      </span>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item command="cn">chines</el-dropdown-item>
        <el-dropdown-item command="en">english</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>
<script>
import { removeToken } from "../../../api//cookiesex";
import header from "../../../components/test.vue";
var ws = null;
export default {
  components: {
    "v-header": header
  },

  mounted: function() {
    //this.testa();
  },
  methods: {
    logout() {
      //localStorage.removeItem("token");
      //removeToken();

      this.getRequest("/removeSession").then(resp => {
        //const items = resp.data.data.items;
        this.$message({
          message: "你已經登出",
          type: "success"
        });
      });
      this.$router.push("/login");
    },
    handleCommand(command) {
      this.$message("change lang " + command);
      if (command == "en") this.$i18n.locale = "en";
      else this.$i18n.locale = "cn";
    },
    WebSocketConn() {
      if (ws != null && ws.readyState == 1) {
        this.testnotic("已经在线");
        return;
      }
      var tmp = this;
      if ("WebSocket" in window) {
        // Let us open a web socket
        ws = new WebSocket("ws://localhost:6699/sredis");

        ws.onopen = function() {
          tmp.testnotic("成功进入聊天室");
          console.log("成功进入聊天室");
        };

        ws.onmessage = function(event) {
          tmp.testnotic(event.data);
          console.log(event.data);
        };

        ws.onclose = function() {
          // websocket is closed.
          tmp.testnotic("已经和服务器断开");
          console.log("已经和服务器断开");
        };

        ws.onerror = function(event) {
          tmp.testnotic("error " + event.data);
          console.log("error " + event.data);
        };
      } else {
        // The browser doesn't support WebSocket
        alert("WebSocket NOT supported by your Browser!");
      }
    },
    SendMsg() {
      if (ws != null && ws.readyState == 1) {
        var msg = document.getElementById("msgtext").value;
        ws.send(msg);
      } else {
        this.testnotic("请先进入聊天室");
      }
    },

    WebSocketClose() {
      if (ws != null && ws.readyState == 1) {
        ws.close();
        this.testnotic("发送断开服务器请求");
      } else {
        this.testnotic("当前没有连接服务器");
      }
    },

    testnotic(text) {
      this.notifyMe(text);
      // var li = document.createElement('li');
      // li.appendChild(document.createTextNode(text));
      // document.getElementById('log').appendChild(li);
      return false;
    },

    testa() {
      this.WebSocketConn();

      // const PUBLIC_KEY = 'BCDthgEHnL_wlsG2xW6S-v_LGMmQ_miXzG0V3aLNEeA66nnwh_oXbhNNWNTg0XfHEAYTDa7PboiaU0SLWXkMxIE'
      // const NOTIFICATION_USABILITY = 'serviceWorker' in navigator && 'PushManager' in window
      // /**
      //  * initialization function
      //  */
      // const init = () => {
      //   const subscribeButton = document
      //           .querySelector('.user-profile__subscribe-button')
      //   if (!NOTIFICATION_USABILITY) {
      //     console.log('Service Worker and Push is supported')
      //     // Disable subscribe button when user device is not support
      //     subscribeButton.innerHTML = '未支援訂閱'
      //     subscribeButton.disabled = true
      //   } else {
      //     navigator
      //             .serviceWorker
      //             .register('sw.js')
      //             .then((registration) => {
      //               console.log('Service Worker is registered')
      //               return getSubscription(registration)
      //             })
      //             .then(subscription => {
      //               const isSubscribed = subscription !== null
      //               if (isSubscribed) {
      //                 saveSubscription(subscription)
      //                 subscribeButton.innerHTML = '已訂閱'
      //                 subscribeButton.disabled = true
      //               }
      //             })
      //             .catch(error => {
      //               console.error('Service Worker Error', error)
      //             })
      //   }
      // }
      // const getSubscription = (registration) => {
      //   return registration
      //           .pushManager
      //           .getSubscription()
      // }
      // const API_HOST = 'https://webnoticee.firebaseio.com/'
      // const saveSubscription = (subscription) => {
      //   const options = {
      //     method: 'PUT',
      //     body: JSON.stringify({
      //       fingerprint: localStorage.getItem('fingerprint'),
      //       subscription
      //     }),
      //     headers: new Headers({
      //       'Content-Type': 'Application/json'
      //     })
      //   }
      //   return fetch(`${API_HOST}/subscriptions`, options)
      //           .then(response => response.json())
      // }
      // const subscribe = () => {
      //   // TODO: click event handler
      // }
      // init()
    },
    prompt() {
      document.addEventListener("DOMContentLoaded", function() {
        if (!Notification) {
          alert(
            "Desktop notifications not available in your browser. Try Chromium."
          );
          return;
        }

        if (Notification.permission !== "granted")
          Notification.requestPermission();
      });

      //this.notifyMe("現在你已經");
      this.testa();
    },
    notifyMe(text) {
      console.log(text);
      if (Notification.permission !== "granted")
        Notification.requestPermission();
      else {
        var notification = new Notification("Notification title", {
          icon: "http://cdn.sstatic.net/stackexchange/img/logos/so/so-icon.png",
          body: text
        });

        notification.onclick = function() {
          window.open("https://stackoverflow.com/a/13328397/1269037");
        };
      }
    }
  }
};
</script>

