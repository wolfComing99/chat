<template>
  <div>
    <el-card style="min-height: 600px;width: 1050px;margin-left: 20px">
      <div style="color: rgb(64,158,255)">我的信息</div>
      <el-divider></el-divider>
      <div style="margin-top: 40px;position: relative;left: 200px">
        <el-form label-position="left" label-width="80px" :model="user">
          <el-form-item label="UID">
            {{user.uid}}
          </el-form-item>
          <el-form-item label="昵称">
            <el-input v-model="user.nickname" style="width: 200px"></el-input>
          </el-form-item>
          <el-form-item label="邮箱">
            {{user.username}}
          </el-form-item>
          <el-form-item label="生日">
            <el-date-picker v-model="user.birthday" value-format="yyyy-MM-dd" type="date" placeholder="请选择生日">
           </el-date-picker>
            <!-- <el-input v-model="user.birthday" suffix-icon="el-icon-date" style="width: 200px"></el-input> -->
          </el-form-item>
          <el-form-item label="性别">
            <el-radio-group v-model="user.sex">
              <el-radio label="男"></el-radio>
              <el-radio label="女"></el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="职业">
            <el-select v-model="user.profession" placeholder="请选择你的职业" prop="profession">
              <el-option label="国家机关、党群组织、企业、事业单位负责人" value="国家机关、党群组织、企业、事业单位负责人"></el-option>
              <el-option label="专业技术人员" value="专业技术人员"></el-option>
              <el-option label="办事人员和有关人员" value="办事人员和有关人员"></el-option>
              <el-option label="商业、服务业人员" value="商业、服务业人员"></el-option>
              <el-option label="农、林、牧、渔、水利业生产人员" value="农、林、牧、渔、水利业生产人员"></el-option>
              <el-option label="生产、运输设备操作人员及有关人员" value="生产、运输设备操作人员及有关人员"></el-option>
              <el-option label="军人" value="军人"></el-option>
              <el-option label="不便分类的其他从业人员" value="不便分类的其他从业人员"></el-option>
              <el-option label="未知" value="未知"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="个人简介">
            <el-input v-model="user.introduction" style="width: 500px" type="textarea" rows="3"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="save">保存修改</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "profile",
  data(){
    return{
      user:{
        uid:null,
        nickname:null,
        username:null,
        birthday:null,
        sex:null,
        profession:null,
        introduction:null
      }
    }
  },
  methods:{
    save(){
      let self=this
      console.log(self.user)
      axios.post('/user/updateUser',self.user).then(res=>{
        if(res.data.code===200){
          window.localStorage.setItem('user',JSON.stringify(self.user))
          self.$message({
            message: '修改成功',
            type: 'success'
          });
        } else{
          self.$message.error(res.data.message);
        }
      })
    }
  },
  created() {
    this.user=JSON.parse(window.localStorage.getItem('user'))
    console.log(this.user);
  }
}
</script>

<style scoped>

</style>
