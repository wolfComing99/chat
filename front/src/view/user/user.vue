<template>
  <div>
    <el-container>
      <el-aside style="width: 200px;align-content: center">
        <el-card style="min-height: 600px">
          <el-link class="asideline" :underline="false" href="/user/home">个人主页</el-link>
          <el-link class="asideline" :underline="false" href="/user/profile">我的信息</el-link>
          <el-link class="asideline" :underline="false" href="/user/account">账号安全</el-link>
        </el-card>
      </el-aside>
      <el-container>
        <router-view/>
      </el-container>
    </el-container>
  </div>
</template>

<script>

export default {
  name: "user"

}
</script>

<style scoped>
.asideline{
  width: 160px;
  height: 100px;
}

</style>
