​												**WebSocket高级**



基于vue js前端

```vue
<script>
import SockJS from 'sockjs-client'
import Stomp from 'webstomp-client'
export default {
  data() {
    return {
      list1: [],
      stompClient: null,
	  msg:'',
	  timer: ''

    }
  },
  mounted() {
     this.initWebSocket()
  },
  created() {
	 
  },
  methods: {
    senMessage() {
		var name  = Math.random()
      this.stompClient.send('/app/hello/hutf/100',JSON.stringify({name:'张三','age':'33'}),JSON.stringify({ 'name': 'test','age':'22' }))
    },
    initWebSocket() {
      this.connection()
      // 需要有一个失败重连得到问题
	  let that= this;
	  // 断开重连机制,尝试发送消息,捕获异常发生时重连
	  // this.timer = setInterval(() => {
		 //  try {
			//   that.stompClient.send("test");
		 //  } catch (err) {
			//   console.log("断线了: " + err);
			//   that.connection();
		 //  }
	  // }, 5000);
    },
    connection() {
      // 更换that指针
      const socket = new SockJS('http://localhost:8080/webSocket')
      this.stompClient = Stomp.over(socket)
      this.stompClient.connect({}, (frame) => {
		  console.log("connection 成功 .....")
		  debugger
        console.log(frame)
        this.stompClient.subscribe('/topic/getResponse', (val) => {
          // this.list1 = JSON.parse(val.body)
		  debugger
          console.log('订阅成功........')
			//下面会报错，应该是json的问题，但是数据可以接收到
          console.log(val.body)
		  this.msg = JSON.parse(val.body)
        });
		this.stompClient.send('/app/hello/hutf/100',JSON.stringify({}),JSON.stringify({ 'name': 'test','age':'22' }))
      }, (error) => {
		 console.log(error) 
	  });
      // 回调函数 3 end
    },
	disconnection() {
		if(this.stompClient !== null) {
			this.stompClient.disconnect()
		}
	}
  }
}
</script>
```

基于spring boot 后台

1，处理跨域

```java
@Configuration
public class CorsConfig {

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //允许任何域名访问
        corsConfiguration.addAllowedOrigin("*");
        //允许任何header访问
        corsConfiguration.addAllowedHeader("*");
        //允许任何方法访问
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }
}
```

2，配置

```
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    /**
     * 注册stomp端点，主要是起到连接作用
     *
     * @param stompEndpointRegistry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        stompEndpointRegistry
                .addEndpoint("/webSocket")  //端点名称
                //.setHandshakeHandler() 握手处理，主要是连接的时候认证获取其他数据验证等
                //.addInterceptors() 拦截处理，和http拦截类似
                .setAllowedOrigins("*") //跨域
                .withSockJS(); //使用sockJS

    }

    /**
     * 注册相关服务
     *
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //这里使用的是内存模式，生产环境可以使用rabbitmq或者其他mq。
        //这里注册两个，主要是目的是将广播和队列分开。
        //registry.enableStompBrokerRelay().setRelayHost().setRelayPort() 其他方式
        registry.enableSimpleBroker("/topic");
        //设置客户端前缀 即@MessageMapping
        registry.setApplicationDestinationPrefixes("/app");
        //点对点发送前缀
        registry.setUserDestinationPrefix("/user");
    }
    }
```

3，控制器

```java
    @Autowired
    public SimpMessagingTemplate template;
    
        @MessageMapping("/hello/{name}/{age}")
    public void sub(String ss, @DestinationVariable String name,@DestinationVariable String age,  SimpMessageHeaderAccessor accessor, Map<String,Object> principal) throws InterruptedException {
        System.out.println(ss.toString());
        Thread.sleep(1000);
        List<Users> list = new ArrayList<>();
        Users u = null;
        for (int i = 0; i < 10; i++) {
            u = new Users();
            u.setAge(i+"");
            u.setName(UUID.randomUUID().toString());
            list.add(u);
        }
        String s = UUID.randomUUID().toString();
        template.convertAndSend("/topic/getResponse", list);
    }
```

