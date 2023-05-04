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

      <el-form-item label="验证码" prop="code">
        <div>
          <el-input v-model="form.code" placeholder="输入验证码" style="width: 140px"/>
          <el-button v-show="sendAuthCode" @click="getAuthCode">获取验证码</el-button>
          <span v-show="!sendAuthCode">{{ auth_time }}秒后重新发送</span>
        </div>
      </el-form-item>
      <el-form-item style="margin-left: 30px">
        <el-button style="background: rgb(64,158,255);width: 130px;color: white" @click="submitForm">登录/注册</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "LoginVerification",
  data() {
    return {
      form: {
        username: "",
        code: ""
      },
      rules: {
        username: [
          {required: true, message: "请输入邮箱", trigger: "blur"},
          {
            type: 'string',
            message: '邮箱格式不正确',
            trigger: 'blur',
            transform(value) {
              if (!/^\w+((-\w+)|(\.\w+))*@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/.test(value)) {
                return true
              } else {
              }
            }
          },
        ],
        code: [{required: true, message: "请输入验证码", trigger: "blur"}],
      },
      code: "",
      auth_time: 0,
      sendAuthCode: true,
    }
  },
  methods: {
    submitForm() {
      const self = this;
      this.$refs.form.validate((valid) => {
        if (valid) {
          axios.post("/loginByVerification", self.form).then(function (res) {
            if (res.data.code === 200) {
              console.log("success")
              self.$store.commit('login',res.data.result)
              self.$message({
                message: '登入成功',
                type: 'success'
              });
              self.$router.push('/home')
            } else {
              self.$message({
                type: 'error',
                message: res.data.message
              });
            }
          });
        } else {
          console.log("error submit!!!");
          return false;
        }
      });
    },
    getAuthCode() {
      const self = this;
      axios.get("/sendCode?email="+self.form.username).then(function (res) {
        console.log(res.data.message)
        self.$message({
                message: '验证码已发送，请注意查收',
                type: 'success'
              });
      });
      this.sendAuthCode = false;
      //设置倒计时秒
      this.auth_time = 60;
      var auth_timetimer = setInterval(() => {
        this.auth_time--;
        if (this.auth_time <= 0) {
          this.sendAuthCode = true;
          clearInterval(auth_timetimer);
        }
      }, 1000);
    },
  }
}
</script>

<style scoped>

</style>
