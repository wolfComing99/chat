<template>
  <div>
    <el-container style="">
      <el-header style="background: white">
        <!-- 首页图标 -->
        <el-image
        style="width: 120px; height: 50px; position:absolute;left:2%"
        :src="icon"
        @click="gohome"
        fit="fill"></el-image>
        <!-- 搜索框 -->
        <el-autocomplete
          style="width: 400px;position:absolute;left: 45%;margin-top: 10px"
          prefix-icon="el-icon-search"
          v-model="search"
          :fetch-suggestions="querySearchAsync"
          @change="searched"
          placeholder="请输入内容"
          @select="handleSelect"
        ></el-autocomplete>
        <el-button type="primary" round style="width:150px;position:absolute;right:11%;margin-top: 10px" icon="el-icon-edit">写文章</el-button>

        <el-menu :default-active="activeIndex" 
                 class="el-menu-demo" 
                 mode="horizontal" 
                 @select="handleSelect" 
                 router="true"
                 style="position:absolute;left:15%;">
          <el-menu-item index="/home" class="sub-menu"><i class="el-icon-s-home">首页</i></el-menu-item>
          <el-menu-item index="/hot" class="sub-menu"><i class="el-icon-trophy">热门</i></el-menu-item>
          <el-menu-item index="/classify" class="sub-menu"><i class="el-icon-s-management">分区</i></el-menu-item>
          </el-menu>
        <!-- <el-link icon="el-icon-s-home" style="position:absolute;left:15%;height: 60px;width: 80px" href="/home">首页</el-link>
        <el-link icon="el-icon-trophy" style="position:absolute;left: 23%;height: 60px;width: 80px" href="/hot">热门</el-link>
        <el-link icon="el-icon-s-management" style="position:absolute;left: 30%;height: 60px;width: 80px" href="/classify">分区</el-link> -->

<!--        通知-->
        <el-dropdown style="font-size: 25px;position:absolute;left: 90%;margin-top: 15px">
          <i class="el-icon-message-solid" >
            <el-badge :value="message.length" class="item"></el-badge>
          </i>
          <el-dropdown-menu slot="dropdown">
            <div v-for="msg in message" :key="msg.messageId">
              <el-dropdown-item>{{msg.messageId}} : {{ msg.content}}</el-dropdown-item>
            </div>
          </el-dropdown-menu>
        </el-dropdown>
        

        <el-button type="text" @click="drawer = true">
          <el-avatar :src="avatar"  style="position:absolute;left: 95%;margin-top: -7px;"></el-avatar>
        </el-button>

      </el-header>

      <el-container style="position:absolute;left: 10%;top: 85px;width: 80%">
        <router-view/>
      </el-container>

    </el-container>

    <el-drawer
      title="我的好友"
      :visible.sync="drawer"
      direction="ltr"
      :with-header="false"
      size="20%"
      class="friend"
    >
    <friend></friend>
    <el-button icon="el-icon-user" type="success" class="profile" @click="profile">个人信息</el-button>
    <el-button icon="el-icon-switch-button" type="danger" class="logout" @click="logout">退出登入</el-button>
    </el-drawer>

  </div>
</template>

<script>

import Friend from "../components/Friend";
import LoginPassword from "../components/LoginPassword";
import LoginVerification from "../components/LoginVerification";
import axios from 'axios';
export default {
  name: "home",
  components: {LoginPassword,LoginVerification, Friend},
  data() {
    return {
      icon: require("../assets/wordlogo.jpg"),
      //显示好友列表
      drawer: false,
      //显示登入框
      dialogFormVisible: false,
      //用户头像
      avatar: null,
      //消息
      message:[],
      //搜索关键词
      search:"",
      activeIndex:"/home"
    };
  },
  methods: {
    gohome(){
      this.$router.push("/home")
    },
    logout(){
      console.log('logout....')
      var _this = this
        this.$axios.get('/logout').then(resp => {
          if (resp.data.code === 200) {
            _this.$store.commit('logout')
            _this.$router.replace('/login')
          }
        })
    },
    profile(){
      console.log("profile")
      this.$router.push("/user/home")
    },
    websocketonmessage(){

    }
  },
  beforeMount() {
    this.global.websock.onmessage=this.websocketonmessage
  },
  created(){
    this.avatar= JSON.parse(localStorage.getItem('user')).avatar
    let uid=JSON.parse(localStorage.getItem('user')).uid
    let self=this
    console.log("created............");
    axios.get("/message/getNotRead?uid="+uid).then(res=>{
      if(res.data.code===200){
        self.message=res.data.result;
        console.log(self.message);
      }else {
        console.log(res.data.message)
      }
    })
  }
}
</script>

<style scoped>
.friend::-webkit-scrollbar {
  display: none;
  position:relative;
  height:auto;
  min-height:100%;
}
.logout {
  position:fixed;
  bottom:10px;
  left: 150px;
}
.profile {
  position:fixed;
  bottom:10px;
  left: 30px;
}
.sub-menu{
  width: 100px;
}
</style>
