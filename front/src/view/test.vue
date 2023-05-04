<template>
  <div v-if="friend">
      {{friend.nickname}}
  </div>
</template>

<script>
import axios from 'axios';
export default {
    
  name: "test",
  data() {
    return{
      content:'',
      friend:null,
      user:null
    }
  },
  created(){
    let self=this
    console.log("121212121212121212121212121212");
    axios.get("/user/getByUid?uid=2").then(res=>{
      if(res.data.code===200){
          self.friend=res.data.result
          this.$forceUpdate()
          console.log(self.friend);
        }else {
          console.log(res.data.message)
        }
    })
  }
}
</script>

<style>

</style>
