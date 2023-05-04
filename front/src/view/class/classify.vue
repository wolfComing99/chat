<template>
  <div>
    <el-container>
      <el-aside>
        <el-card style="background:white;min-height: 600px;width: 250px">
          分区
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
  name: "classify"
}
</script>

<style scoped>

</style>
