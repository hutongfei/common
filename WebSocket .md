**WebSocket 入门**



后台

```java

@Component
@ServerEndpoint("/server/{userId}")
public class WebScoketServer {
    private Session session;
    private static CopyOnWriteArraySet<WebScoketServer> webSockets =new CopyOnWriteArraySet<>();
    private static Map<String,Session> sessionPool = new HashMap<String, Session>();
    static Logger logger = LoggerFactory.getLogger(WebScoketServer.class);

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        this.session = session;
        if (sessionPool.get(userId) != null) {
            sessionPool.remove(userId);
        }
        webSockets.add(this);
        sessionPool.put(userId, session);
        logger.info("当前用户连接"+ userId );
        // 发送数据
    }

    @OnClose
    public void  onClose() {

        webSockets.remove(this);
        logger.info("当前退出用户数量" + webSockets.size() );
    }

    @OnMessage
    public void onMessage(Session session,String message) throws Exception{
        logger.info("******8" + message);
        radio(message);
    }

    /**
     * 广播消息
     * @param message
     * @throws Exception
     */
    public static void radio(String message) throws Exception{
        logger.info("*********广播**********");
        for (WebScoketServer webSocket : webSockets) {
            webSocket.session.getBasicRemote().sendText(message);
        }
    }

    /**
     * 单点消息发送 （发送文本）
     * @param message
     * @param userId
     * @throws Exception
     */
    public static void singleMessage(String message,String userId) throws Exception{
        logger.info(userId + "********消息为****" + message);
        if (sessionPool.get(userId) != null) {
            sessionPool.get(userId).getBasicRemote().sendText(message);
        }
        radio(userId + "同学发送的********消息为****" + message);
    }

    /**
     * 单点消息发送 （对象）
     */
    public void singleObject(Object object, String userId) throws Exception {
        logger.info(userId + "********消息为****" + object.toString());
        if (sessionPool.get(userId) != null) {
            sessionPool.get(userId).getBasicRemote().sendObject(object);
        }
    }
}
	
```



配置

```java

@Configuration  
public class WebSocketConfig {
    @Bean  
    public ServerEndpointExporter serverEndpointExporter() {  
        return new ServerEndpointExporter();  
    }  
  
} 
```

依赖

```java
 <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-websocket</artifactId>
        </dependency>
```

前端

```java
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>websocket通讯</title>
</head>
<script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.js"></script>
<script>
    var socket;
    function openSocket() {
        if(typeof(WebSocket) == "undefined") {
            console.log("您的浏览器不支持WebSocket");
        }else{
            console.log("您的浏览器支持WebSocket");
            //实现化WebSocket对象，指定要连接的服务器地址与端口  建立连接
            //等同于socket = new WebSocket("ws://localhost:8888/xxxx/im/25");
            //var socketUrl="${request.contextPath}/im/"+$("#userId").val();
            var socketUrl="http://localhost:8080/server/"+$("#userId").val();
            socketUrl=socketUrl.replace("https","ws").replace("http","ws");
            console.log(socketUrl);
            if(socket!=null){
                socket.close();
                socket=null;
            }
            socket = new WebSocket(socketUrl);
            //打开事件
            socket.onopen = function() {
                console.log("websocket已打开");
                //socket.send("这是来自客户端的消息" + location.href + new Date());
            };
            //获得消息事件
            socket.onmessage = function(msg) {
                console.log(msg.data);
                //发现消息进入    开始处理前端触发逻辑
            };
            //关闭事件
            socket.onclose = function() {
                console.log("websocket已关闭");
            };
            //发生了错误事件
            socket.onerror = function() {
                console.log("websocket发生了错误");
            }
        }
    }
    function sendMessage() {
        if(typeof(WebSocket) == "undefined") {
            console.log("您的浏览器不支持WebSocket");
        }else {
            console.log("您的浏览器支持WebSocket");
            console.log('{"toUserId":"'+$("#toUserId").val()+'","contentText":"'+$("#contentText").val()+'"}');
            socket.send('{"toUserId":"'+$("#toUserId").val()+'","contentText":"'+$("#contentText").val()+'"}');
        }
    }
</script>
<body>
	【userId】：<input id="userId" name="userId" type="text" value="10">
	<br>
		【toUserId】：<input id="toUserId" name="toUserId" type="text" value="20">
		<br>
	【toUserId】：<input id="contentText" name="contentText" type="text" value="hello websocket">
	<br>
		【操作】：<button onclick="openSocket()">开启socket1 <button>
		<br>
		【操作】：<button onclick="sendMessage()">发送消息<button>
		<br>
	
</body>
</html>

```

