<template>
  <div>
    <el-card class="card" style="min-height:150px">
      <el-upload
        class="avatar-uploader"
        action="http://localhost:8081/user/uploadAvatar"
        :show-file-list="false"
        :on-success="handleAvatarSuccess"
        :before-upload="beforeUpload"
        :data="{uid: user.uid,type: Object,required: true}"
        style="display:inline-block">
        <el-avatar :size="100" :src="user.avatar"></el-avatar>
      </el-upload>
      <span style="font-size:20px;font-weight:bold;">{{ user.nickname }}</span>
    </el-card>
    <el-card class="card" style="min-height:450px">
      <el-tabs v-model="activeName" @tab-click="handleClick" type="card">
      <el-tab-pane name="first">
        <span slot="label"><i class="el-icon-notebook-2"><span style="margin-left:4px">文章</span></i></span>
        <el-empty v-if="article" description="暂无文章"></el-empty>
      </el-tab-pane>
      <el-tab-pane name="second">
        <span slot="label"><i class="el-icon-collection"><span style="margin-left:4px">合集</span></i></span>
        <el-empty v-if="sets" description="暂无合集"></el-empty>
      </el-tab-pane>
      <el-tab-pane name="third">
        <span slot="label"><i class="el-icon-star-off"><span style="margin-left:4px">收藏</span></i></span>
        <el-empty v-if="collection" description="暂无收藏"></el-empty>
      </el-tab-pane>
    </el-tabs>
    </el-card>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "user_home",
  data(){
    return{
      user:{
         avatar: null
      },
      //默认labName
      activeName:'first',
      //文章
      article:[],
      //分类合集
      sets:[],
      //收藏列表
      collection:[]
    }
  },
  methods:{
    handleClick(tab, event) {
      if(tab._props.label==='文章'){
        
      }
    },
    beforeUpload(file) {
      const isJPG = file.type === "image/jpeg" || file.type == "image/png";
      const isLt2M = file.size / 1024 / 1024 < 3;
      if (!isJPG) {
        this.$message.error("上传头像图片只能是 JPG 或 PNG 格式!");
        return;
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 3MB!");
        return;
      }
      return isJPG && isLt2M;
    },
    handleAvatarSuccess(res) {
      var url=res.result
      axios.post('/user/updateAvatar',{uid:this.user.uid,avatar:url}).then(res0=>{
        if(res0.data.code===200){
          this.$message({
          showClose: true,
          message: '修改成功',
          type: 'success'
        })
        }else {
          console.log(res0.data.message)
        }
      })
      this.user.avatar = url
      console.log(this.user.avatar);
    }
  },
  created(){
    this.user= JSON.parse(localStorage.getItem('user'))
  }
}
</script>

<style scoped>
.card {
  width:1050px;
  margin-left: 20px
}
</style>
