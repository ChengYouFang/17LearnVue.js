/**
*
* @author louie
* @date created in 2018-10-8 22:48
*/
<template>
  <div id="console" v-wechat-title="$route.meta.title">
    <img src="../../assets/logo.png">

    <p>{{$t('message.i1')}}</p>
    <router-view name="nav"></router-view>
    <router-view></router-view>
    <router-view name="test"></router-view>
  </div>
</template>

<script>
export default {
  name: "console"
};
</script>

<style scoped>
</style>



<style>
#console {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>