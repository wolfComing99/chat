<template>
  <div>
    <el-form
      :model="form"
      status-icon
      :rules="rules"
      ref="form"
      label-width="100px"
      class="login-form"
      style="text-align: left"
    >
      <el-form-item label="账号" prop="username">
        <el-input v-model="form.username" style="width: 250px" placeholder="邮箱"></el-input>
      </el-form-item>

      <el-form-item label="密码" prop="password" >
        <el-input
          type="password"
          v-model="form.password"
          autocomplete="off"
          style="width: 250px"
          placeholder="密码"
        ></el-input>
      </el-form-item>
      <el-form-item style="margin-left: 30px">
        <el-button style="background: rgb(64,158,255);width: 130px;color: white" @click="submitForm">登录</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "LoginPassword",
  data() {
    return {
      form: {
        username: "",
        password: "",
      },
      rules: {
        username: [{required: true, message: "请输入账号", trigger: "blur"}],
        password: [{required: true, message: "请输入密码", trigger: "blur"}],
      },
    }
  },
  methods:{
    submitForm() {
      const self = this;
      console.log(this.$store.state)
      this.$refs.form.validate((valid) => {
        if (valid) {
          axios.post("/loginByPassword", self.form).then(function (res) {
            if (res.data.code === 200) {
              console.log("success")
              self.$store.commit('login',res.data.result)
              self.$message({
                message: '登入成功',
                type: 'success'
              });
              self.$router.push('/home')
            }else{
              self.$message.error(res.data.message);
              console.log(res.data.message)
            }
          });
        } else {
          console.log("error submit!!!");
          return false;
        }
      });
    },
  }
}
</script>

<style scoped>

</style>
