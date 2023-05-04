<template>
  <div>
    <el-container>
      <el-aside>
        <el-card style="background:white;min-height: 600px;width: 250px">
          热门博主
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
  name: "hot"
}
</script>

<style scoped>

</style>
