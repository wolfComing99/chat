<template>
  <div>

<!--    <el-card style="width: 300px;height: 780px">-->
    <el-card shadow="never">
      <!-- 用户信息-->
      <div>
        <div>
          <el-avatar :size="50" :src="user.avatar"></el-avatar>
          <h3 style="display: inline-block">{{user.nickname}}</h3>
        </div>
        <el-input
          placeholder="搜索"
          prefix-icon="el-icon-search"
          v-model="keyword"
          style="width: 260px;height: 20px">
        </el-input>
      </div>

      <!-- 好友-->
      <div style="margin-top: 10px">
        <!-- 分组-->
        <!-- <el-collapse v-for="(item) in group" :key="item.id" class="group">
          <el-collapse-item>
            <template slot="title">
              <h4>{{ item.name }}</h4>
            </template>
            
            <div v-for="(friend) in item.friends" class="friend" @click="chat(friend.id)">
              <el-avatar :size="30" :src="friend.head"></el-avatar>
              <div style="display: inline-block">
                <h3 style="height: 10px">{{ friend.name }}</h3>
              </div>
            </div>
          </el-collapse-item>
        </el-collapse> -->

        <!-- 无分组 -->
        <div v-for="(friend) in friends" class="friend" :key="friend.uid" @click="chat(friend.uid)">
              <el-avatar :size="30" :src="friend.avatar"></el-avatar>
              <i v-if="friend.online" class="dotClass" style="background-color: #67C23A"></i>
              <i v-else class="dotClass" style="background-color: rgb(173,173,173)"></i>
              <div style="display: inline-block">
                <h3 style="height: 10px">{{ friend.nickname }}</h3>
              </div>
            </div>
      </div>
    </el-card>
<!--    <el-dialog :visible.sync="chatting" width="820px">-->
<!--      <Chat></Chat>-->
<!--    </el-dialog>-->
  </div>
</template>

<script>
import axios from 'axios';
import Chat from "./Chat";

export default {
  name: "friend",
  components: {Chat},
  data() {
    return {
      user:{
        avatar:null,
        nickname:null
      },
      keyword:'',
      chatting:false,
      friends:null,
      /*
      group: [{
        name: 'g1',
        friends: [{
          id: '100001',
          name: '100001',
          head: ''
        },{
          id: '100002',
          name: '100002',
          head: ''
        }]
      }, {
        name: 'g2',
        friends: [{
          id: '1',
          name: '123',
          head: ''
        }, {
          id: '2',
          name: '123',
          head: ''
        }, {
          id: '3',
          name: '123',
          head: ''
        }]
      }],
      */
    }
  },
  methods: {
    chat(uid) {
      //新窗口打开
      const routeData = this.$router.resolve('/chat/'+uid);
      window.open(routeData.href, '_blank');
      // this.$router.push('/chat/'+id)
      // this.chatting=true

    }
  },
  created(){
    this.user= JSON.parse(localStorage.getItem('user'))
    var self=this
    axios.get("/friend/getAll",{
           params: {
             uid:self.user.uid
           }}).then(res=>{
            if(res.data.code===200){
              console.log(res.data.result)
              self.friends=res.data.result
              console.log(self.friends);
              this.$forceUpdate()
            }else{
              self.$message.error(res.data.message);
            }
            
      
    })
  }
}
</script>

<style scoped>
/* .friend {

} */

.friend:hover {
  background: rgb(242, 242, 242);
}

/* .group {

} */

.group:hover {
  background: rgb(242, 242, 242);
}
.dotClass {
  width: 10px;
  height: 10px;
  border-radius: 50%;
  display: inline-block;
  margin-right: 5px;
}
</style>
