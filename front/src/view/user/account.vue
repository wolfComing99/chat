<template>
  <div>
    <el-container>
      <el-main>
        <div>
          <el-card style="min-height: 600px;width: 600px;margin-left: 20px;margin-top: -20px">
            <div style="color: rgb(64,158,255)">账号安全</div>
            <el-divider></el-divider>
            <div style="position: relative;">
              <h1>密码</h1>
              <span v-if="user.userPassword">已设置</span>
              <span v-else="user.userPassword">未设置</span>
              <el-button type="text" class="modefy" @click="modifyPassword = !modifyPassword">
                <p v-show="!modifyPassword">编辑</p>
                <p v-show="modifyPassword">收起</p>
              </el-button>
              <div v-if="modifyPassword" style="position: relative;left: 100px">
                <el-form ref="password" :rules="passwordRules" :model="password" style="margin-top: 10px">
                  <el-form-item prop="first">
                    <el-input placeholder="请输入新密码" v-model="password.first" show-password
                              style="width: 300px"></el-input>
                  </el-form-item>
                  <el-form-item prop="second">
                    <el-input placeholder="请重新输入新密码" v-model="password.second" show-password
                              style="width: 300px"></el-input>
                  </el-form-item>
                  <el-form-item>
                    <el-button type="primary" @click="submitPassword" style="width: 300px">提交</el-button>
                  </el-form-item>
                </el-form>
              </div>
            </div>
            <div style="position: relative;">
              <h1>绑定手机</h1>
              <span v-if="user.userPhone">{{ user.userPhone }}</span>
              <span v-else="user.userPhone">未绑定</span>
              <el-button type="text" class="modefy" @click="modifyPhone = !modifyPhone">
                <p v-show="!modifyPhone">编辑</p>
                <p v-show="modifyPhone">收起</p>
              </el-button>
              <div v-if="modifyPhone" style="position: relative;left: 100px">
                <el-form ref="phone" :rules="phoneRules" :model="phone" style="margin-top: 10px">
                  <el-form-item prop="userPhone">
                    <el-input placeholder="请输入手机号" v-model.number="phone.userPhone" style="width: 300px"></el-input>
                  </el-form-item>
                  <el-form-item>
                    <el-input placeholder="请输入验证码" v-model="phone.verification" style="width: 150px"></el-input>
                    <el-button type="primary" style="margin-left: 30px">获取验证码</el-button>
                  </el-form-item>
                  <el-form-item>
                    <el-button type="primary" @click="submitPhone" style="width: 300px">提交</el-button>
                  </el-form-item>
                </el-form>
              </div>
            </div>
            <div style="position: relative;">
              <h1>绑定邮箱</h1>
              <span v-if="user.userEmail">{{ user.userEmail }}</span>
              <span v-else="user.userEmail">未绑定</span>
              <el-button type="text" class="modefy" @click="modifyEmail = !modifyEmail">
                <p v-show="!modifyEmail">编辑</p>
                <p v-show="modifyEmail">收起</p>
              </el-button>
              <div v-if="modifyEmail" style="position: relative;left: 100px">
                <el-form ref="email" :rules="emailRules" :model="email" style="margin-top: 10px">
                  <el-form-item prop="userEmail">
                    <el-input placeholder="请输入邮箱" v-model="email.userEmail" style="width: 300px"></el-input>
                  </el-form-item>
                  <el-form-item>
                    <el-input placeholder="请输入验证码" v-model="email.verification" style="width: 150px"></el-input>
                    <el-button type="primary" style="margin-left: 30px">获取验证码</el-button>
                  </el-form-item>
                  <el-form-item>
                    <el-button type="primary" @click="submitEmail" style="width: 300px">提交</el-button>
                  </el-form-item>
                </el-form>
              </div>
            </div>
          </el-card>
        </div>
      </el-main>
      <el-aside width="300px">
        <el-card style="min-height: 600px;">
          <h2>常见问题</h2>
          <h1>账号与密码</h1>
          <p><el-link :underline="false">1.为什么要进行二次验证？</el-link></p>
          <p><el-link :underline="false">2.二次验证的手机/邮箱不可用？</el-link></p>
          <p><el-link :underline="false">3.忘记密码怎么办？</el-link></p>
          <p><el-link :underline="false">4.手机丢失，手机号不可用怎么办？</el-link></p>
          <p><el-link :underline="false">5.我要绑定的手机号提示我已经注册了，怎么办？</el-link></p>
          <h1>其他问题</h1>
          <p><el-link :underline="false">6.网站出现了问题？</el-link></p>
          <p><el-link :underline="false">7.帐号如何注销？</el-link></p>
        </el-card>
      </el-aside>
    </el-container>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "account",
  data() {
    let validatePass = (rule, value, callback) => {
      if (value !== this.password.first) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      user: {
        userEmail: '',
        userPhone: '',
        userPassword: ''
      },
      modifyPassword: false,
      modifyPhone: false,
      modifyEmail: false,
      password: {
        first: '',
        second: '',
      },
      passwordRules: {
        first: [
          {required: true, message: "请输入密码", trigger: "blur"},
          {
            pattern: /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$/,
            message: "密码长度为6-20位，可以为数字、字母",
            trigger: "blur"
          },
        ],
        second: [
          {required: true, message: "请再次输入输入密码", trigger: "blur"},
          {
            pattern: /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$/,
            message: "密码长度为6-20位，可以为数字、字母",
            trigger: "blur"
          },
          {validator: validatePass, trigger: "blur"}
        ],
      },
      phone: {
        userPhone: '',
        verification: '',
      },
      phoneRules: {
        userPhone: [
          {required: true, message: "请输入手机号", trigger: "blur"},
          {
            pattern: /^[1][3,4,5,6,7,8,9][0-9]{9}$/,
            message: "请输入正确的11位手机号码",
            trigger: "blur"
          }],
      },
      email: {
        userEmail: '',
        verification: ''
      },
      emailRules: {
        userEmail: [
          {required: true, message: "请输入邮箱", trigger: "blur"},
          {
            pattern: /^\w+((-\w+)|(\.\w+))*@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/,
            message: "请输入正确的邮箱",
            trigger: "blur"
          }
        ]
      }
    }
  },
  methods: {
    submitPassword() {
      const self = this;
      this.$refs.password.validate((valid) => {
        if (valid) {
          let userId = localStorage.getItem("userId")
          const formData = new FormData
          formData.append("userId" ,userId)
          formData.append('userNewPassword',self.password.second)
          axios.post('/personalHome/updateUserPassword',formData).then(res=>{
            self.$message({
              message: res.data.msg,
              type: 'success'
            });
          })
        } else {
          console.log("error submit!!!");
          return false;
        }
      })
    },
    submitPhone() {
      const self = this;
      this.$refs.phone.validate((valid) => {
        if (valid) {
          let userId = localStorage.getItem("userId")
          const formData = new FormData
          formData.append("userId" ,userId)
          formData.append('userNewPhone',self.phone.userPhone)
          axios.post('/personalHome/updateUserPhone',formData).then(res=>{
            self.$message({
              message: res.data.msg,
              type: 'success'
            });
          })
        } else {
          console.log("error submit!!!");
          return false;
        }
      })
    },
    submitEmail() {
      const self = this;
      this.$refs.email.validate((valid) => {
        if (valid) {
          let userId = localStorage.getItem("userId")
          const formData = new FormData
          formData.append("userId" ,userId)
          formData.append('userNewEmail',self.email.userEmail)
          axios.post('/personalHome/updateUserEmail',formData).then(res=>{
            self.$message({
              message: res.data.msg,
              type: 'success'
            });
          })
        } else {
          console.log("error submit!!!");
          return false;
        }
      })
    },
  },
  created() {
    let self=this
    self.user.userId=localStorage.getItem('userId')
    let userId = localStorage.getItem("userId")
    const formData = new FormData
    formData.append("userId" ,userId)
    axios.post('/personalHome/getUserPasswordPhoneEmail',formData).then(res=>{
      if(res.data.msg=='成功'){
        self.user=res.data.data
      }else {
        console.log(res.data.msg)
      }
    })

  }
}
</script>

<style scoped>
.modefy {
  position: absolute;
  right: 40px;
  top: 5px;
}
</style>
