
<template>
  <div>
    <router-link :to="{name:'h1'}">h1</router-link>
    <router-link :to="{name:'h2'}">h2</router-link>

    <h1>test</h1>
    <router-view name="nav2" style="float:left;width:50%;background-color:#ccc;height:300px;"></router-view>
    <!-- <router-view name="nav2" style="float:left;width:50%;background-color:#ccc;height:300px;"></router-view> -->
  </div>
</template>