<template>
  <div style="position: relative">
    <el-card style="width: 800px;height: 600px;margin: auto;position: relative">
      <div style="height: 30px;border-bottom: solid rgb(218,217,217) 1px">
        <div class="font" v-if="friend">{{ friend.nickname }}</div>
      </div>
      <div class="infinite-list-wrapper o1" style="overflow:auto;height: 340px;" id="chatContainer">
        <ul
          class="list"
          style="padding: 10px"
          v-infinite-scroll="load"
          infinite-scroll-disabled="disabled"
          ref="list"
          >
          <div v-for="msg in message" :key="msg.messageId" class="list-item" id="gundong">
            <div v-if="msg.sendId===user.uid" class="self-all-box">
              <div class="chatBox1">
                {{msg.content}}
              </div>
              <el-avatar :size="30" :src="user.avatar" class="self-head"></el-avatar>
            </div>
            <div v-else class="all-box">
              <el-avatar :size="30" :src="friend.avatar"></el-avatar>
              <div class="chatBox2">
                {{msg.content}}
              </div>
            </div>
          </div>
        </ul>
        <!-- <p v-if="loading">加载中...</p>
        <p v-if="noMore">没有更多了</p> -->
      </div>

      <div style="border-top: solid rgb(218,217,217) 2px">
        <el-input type="textarea" rows="7" class="input" v-model="content"
                  @keydown.native="handleKeyCode"></el-input>
        <el-button type="primary" size="mini"
                   style="position: relative;left: 670px;width: 80px"
                   @click="send">发送
        </el-button>
      </div>
    </el-card>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  name: "Chat",
  data() {
    return{
      loading: false,
      // limit: {maxRows: 6, minRows: 5},
      friend: {},
      user: {},
      message:[],
      content:'',
    }
  },
  methods: {
    handleKeyCode(e) {
      if (!e.shiftKey && e.keyCode == 13) {
        e.cancelBubble = true; //ie阻止冒泡行为
        e.stopPropagation();//Firefox阻止冒泡行为
        e.preventDefault(); //取消事件的默认动作*换行
        //以下处理发送消息代码
        this.send()
      }
    },
    send() {
      if (this.content !== '' && this.content !== null && this.content !== undefined) {
        let msg={
          sendId: this.user.uid,
          receiveId: this.friend.uid,
          content:this.content
        }
        this.message.push(msg)
        this.content = ''
        this.global.websock.send(JSON.stringify(msg))
      }
    },
    websocketonmessage(e){
      console.log(JSON.stringify(e));
      console.log(e.data)
      const data = JSON.parse(e.data);
      console.log(data)
      this.message.push(data)
    },
    load() {
      this.count += 2
    }
  },
  computed: {
    noMore() {
      return this.count >= 20
    },
    disabled() {
      return this.loading || this.noMore
    }
  },
  beforeMount() {
    this.global.websock.onmessage=this.websocketonmessage
  },
  watch: {
    message() {
      this.$nextTick(() => {
        let container = this.$el.querySelector("#chatContainer");
        container.scrollTop = container.scrollHeight;
      })
    }
  },
  created() {
    this.user= JSON.parse(localStorage.getItem('user'))
    let uid = this.$route.params.uid
    let self=this
    axios.get("/user/getByUid?uid="+uid).then(res=>{
      if(res.data.code===200){
          self.friend=res.data.result
          this.$forceUpdate()
          console.log(self.friend);
          axios.get("/message/getMessage",{params:{uid:self.user.uid,friendId:self.friend.uid}}).then(res=>{
            if(res.data.code===200){
              self.message=res.data.result;
            }else{
              console.log(res.data.message);
            }
          })
        }else {
          console.log(res.data.message)
        }
    })
  }
}
</script>

<style scoped>
.font {
  font-family: 'lucida Grande', Tahoma, Arial, Helvetica, 宋体, sans-serif;
  font-size: 20px;
  line-height: 21px;
  color: #454545;
  margin-left: 20px;
}

.input >>> .el-textarea__inner {
  border: 0;
  resize: none; /* 这个是去掉 textarea 下面拉伸的那个标志，如下图 */
}

.chatBox2 {
  position: relative;
  margin-left: 10px;
  padding: 5px 8px;
  word-break: break-all;
  background: rgb(229, 229, 229);
  /*border: 1px solid #989898;*/
  border-radius: 5px;
  max-width: 400px;
  min-height: 20px;
  display: inline-block;
}

.chatBox1 {
  position: relative;
  margin-right: 10px;
  padding: 5px 8px;
  word-break: break-all;
  background: rgb(18, 183, 245);
  /*border: 1px solid #989898;*/
  border-radius: 5px;
  max-width: 400px;
  min-height: 20px;
  color: #f0f0f0;
  display: inline-block;
}
.self-head{
  float: top;
}
.self-all-box {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 10px;
}

.all-box {
  display: flex;
  margin-bottom: 10px;
}

.o1::-webkit-scrollbar {
  display: none;
}

</style>
