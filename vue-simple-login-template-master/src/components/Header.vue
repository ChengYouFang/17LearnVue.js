<template>
  <div>
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
import { removeToken } from "../api/cookiesex";
export default {
  methods: {
    logout() {
      //localStorage.removeItem("token");
      removeToken();
      this.$router.push("/login");
    },
    handleCommand(command) {
      this.$message("change lang " + command);
      if (command == "en") this.$i18n.locale = "en";
      else this.$i18n.locale = "cn";
    }
  }
};
</script>