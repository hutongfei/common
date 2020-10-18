websocket 一对一模式



```java
    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        stompEndpointRegistry
                .addEndpoint("/webSocket")  //端点名称
                //.setHandshakeHandler() 握手处理，主要是连接的时候认证获取其他数据验证等
                //.addInterceptors() 拦截处理，和http拦截类似
                .setAllowedOrigins("*") //跨域
                .withSockJS(); //使用sockJS
    }
    
        @Override
    //配置消息代理(Message Broker)
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //点对点应配置一个/user消息代理，广播式应配置一个/topic消息代理
        registry.enableSimpleBroker("/topic","/user");
        //点对点使用的订阅前缀（客户端订阅路径上会体现出来），不设置的话，默认也是/user/
        registry.setUserDestinationPrefix("/user");
    }
```



一对一推送 api

```java
//     3.1参数一：指定客户端接收的用户标识（一般用用户ID）
//    3.2参数二：客户端监听指定通道时，设定的访问服务器的URL（客户端访问URL跟广播有些许不同）
//    3.3参数三：向目标发送消息体（实体、字符串等等）
        this.template.convertAndSendToUser(user.getUserId()+"","/queue/getResponse",new Date());
```

 前端

```java
  this.stompClient.connect({}, (frame) => {
	  console.log("connection 成功 .....")
    this.stompClient.subscribe('/user/' + this.id + '/queue/getResponse', (val) => {
	  console.log("resultresultresultresultresultresultresultresultresultresultresultresult")
	  this.msg = val.body
    });	
   
```
