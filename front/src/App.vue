<template>
    <router-view/>
</template>

<script>
export default {
  name: 'App',
  methods: {
    //-------------------------------------------------------
    initWebSocket(){
      if(typeof(WebSocket) === "undefined"){
        console.log("您的浏览器不支持WebSocket")
      }else{
        // let userId=localStorage.getItem('userId')
        const wsurl = 'ws://localhost:8081' + "/websocket/"+JSON.parse(localStorage.getItem('user')).uid;
        // 实例化 WebSocket
        this.websock = new WebSocket(wsurl);
        this.global.setWebsock(this.websock)
        // 监听 WebSocket 连接
        this.websock.onopen = this.websocketonopen;
        // 监听 WebSocket 错误信息
        this.websock.onerror = this.websocketonerror;
        // 监听 WebSocket 消息
        // this.websock.onmessage = this.websocketonmessage;
        this.websock.onclose = this.websocketclose;
        console.log('success')
      }
    },
    // 连接建立之后执行send方法发送数据
    websocketonopen(e){
      console.log("websocket连接成功")
       //let actions = {"test":"12345"};
       // this.websocketsend(JSON.stringify(actions));
    },
    // 连接建立失败重连
    websocketonerror(e){
      console.log("连接错误");
      this.initWebSocket();
    },
    // // 数据接收
    // websocketonmessage(e){
    //   const resdata = JSON.parse(e.data);
    //   // console.log(resdata);
    //   console.log(e.data)
    //   console.log(resdata)
    // },
    // // 数据发送
    // websocketsend(Data){
    //   this.websock.send(Data);
    // },
    // 关闭
    websocketclose(e){
      console.log('WebSocket 断开连接: '+e.code + ' ' + e.reason + ' ' + e.wasClean);
    },
  },
  //=================
  beforeMount() {
    if(localStorage.getItem("user")){
      this.initWebSocket()
    }
  },
  destroyed() {
    //离开路由之后断开 websocket 连接
    this.websock.close()
  },
}
</script>

<style>
body{
  background:#f0f0f0;
}
</style>
