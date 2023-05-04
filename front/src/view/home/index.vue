<template>
  <div>
    <el-container>
      <el-aside>
        <el-card style="background:white;min-height: 600px;width: 250px">
          我的关注
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
  name: "index"
}
</script>

<style scoped>

</style>
