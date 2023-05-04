<template>
  <div>
    <el-container>
      <el-aside width="38%">

      </el-aside>
      <el-main style="text-align: center;">
        <div class="font"><i>欢迎使用wolf</i></div>
        <div class="login">
          <el-tabs type="border-card">
            <el-tab-pane label="密码登录">
              <LoginPassword/>
            </el-tab-pane>
            <el-tab-pane label="验证码登录">
              <LoginVerification/>
            </el-tab-pane>
          </el-tabs>
        </div>

      </el-main>
    </el-container>
  </div>
</template>

<script>
import LoginPassword from "../components/LoginPassword";
import LoginVerification from "../components/LoginVerification";
export default {
  name: "login",
  components: {LoginPassword,LoginVerification}
}
</script>

<style scoped>
.el-aside {
  background-color: #D3DCE6;
  color: #333;
  text-align: center;
  height: 750px;
}

.el-main {
  background-color: #E9EEF3;
  color: #333;
  text-align: center;
  height: 750px;
}
.login {
  width: 450px;
  height: 400px;
  display: inline-block;
  margin-top: 50px;
}
.font {
  font-size: 60px;
  text-shadow: 5px 5px 5px black;
  color: rgb(64,158,255);
  margin-top: 50px;
}
</style>
