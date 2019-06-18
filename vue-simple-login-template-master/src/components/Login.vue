<template>
  <div>
    <h1>Login Page</h1>
    <form @submit.prevent="login">
      <label>User Name</label>
      <input type="text" v-model="username" required>
      <br>
      <label>Password</label>
      <input type="password" v-model="password" required>
      <br>
      <button type="submit">Log In</button>
    </form>
  </div>
</template>
<script>
export default {
  data() {
    return {
      username: "admin",
      password: "111111"
    };
  },
  methods: {
    login() {
      // this.$message.error({ message: "请求超时!", type: "success" });
      //write login authencation logic here!
      this.postRequest("/httpMethod", {
        name: this.username,
        pwd: this.password
      }).then(resp => {
        if (resp.data.code == "20000") {
          localStorage.setItem("token", "ImLogin");
          this.$router.push("/");
        } else
          this.$message.error({
            message: "errrrrrrrrrrrrrrrrrrrrrror",
            type: "success"
          });
      });
      // if (this.userName == "abcd" && this.password == "1234") {
      //   localStorage.setItem("token", "ImLogin");
      //   this.$router.push("/");
      // } else {
      //   alert("login failed");
      // }
    },
    FetchUsers() {
      this.postRequest("/login", {
        username: this.username,
        password: this.password
      }).then(resp => {
        console.log("error");
      });
    }
  }
};
</script>