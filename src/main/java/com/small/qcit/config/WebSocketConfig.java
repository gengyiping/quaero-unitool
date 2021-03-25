package com.small.qcit.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

import com.small.qcit.constant.StompConstant;
import com.small.qcit.interceptor.WebSocketInterceptor;


/**
 * @title WebSocketConfig
 * @Description 描述 websocket的配置类 开启 ServerPoint
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
//	 @Autowired
//     private SimpMessagingTemplate messagingTemplate;
	@Resource
    private WebSocketInterceptor webSocketInterceptor;
    /**
     * 注册stomp端点，,并映射指定的url，主要是起到连接作用
     *
     * @param stompEndpointRegistry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
    	//注册一个STOMP的endpoint,并指定使用SockJS协议
    	stompEndpointRegistry
                .addEndpoint(StompConstant.STOMP_ENDPOINT)  //端点名称
                //.setHandshakeHandler() 握手处理，主要是连接的时候认证获取其他数据验证等
                //.addInterceptors() //拦截处理，和http拦截类似
                .setAllowedOrigins("*") //跨域
                .withSockJS(); //使用sockJS
    	//stompEndpointRegistry.addEndpoint("/queueServer").withSockJS();//注册两个STOMP的endpoint，分别用于广播和点对点

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
    	//点对点应配置一个/user消息代理，广播式应配置一个/topic消息代理,群发（mass），单独（alone）
        registry.enableSimpleBroker(StompConstant.STOMP_TOPIC,StompConstant.STOMP_USER,"/mass","/alone");
    	//registry.enableSimpleBroker(StompConstant.STOMP_TOPIC, StompConstant.STOMP_USER);
    	//设置客户端前缀 即@MessageMapping
        registry.setApplicationDestinationPrefixes("/app");
        //点对点发送前缀,不设置的话，默认也是/user/
        //registry.setUserDestinationPrefix("/user");
    }
    
    /**
     * 注册消息拦截器
     *
     * @param registration
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(webSocketInterceptor);
    }
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
      registration.setMessageSizeLimit(500 * 1024 * 1024);
      registration.setSendBufferSizeLimit(1024 * 1024 * 1024);
      registration.setSendTimeLimit(200000);
     }

     /*将客户端渠道拦截器加入spring ioc容器*/
//    @Bean
//    public UserInterceptor createUserInterceptor() {
//        //return new UserInterceptor();
//    }


//   
    /**
     * WebSocket Error 处理
     *
     * @return WebSocket Error 处理器
     */
//    @Bean
//    public StompSubProtocolErrorHandler webSocketHandler() {
//        return new WebSocketErrorHandler();
//    }
    /**
     * 推送日志到/topic/pullLogger
     */
//    @PostConstruct
//    public void pushLogger()
//    {
//        ExecutorService executorService = Executors.newFixedThreadPool(4);
//        Runnable fileLog = new Runnable()
//        {
//            @Override
//            public void run()
//            {
//                while (true)
//                {
//                    try
//                    {
//                        String log = LoggerQueue.getInstance().poll().toString();
//                        if (log != null)
//                        {
//                            if (messagingTemplate != null)
//                                messagingTemplate.convertAndSend("/topic/pullFileLogger", log);
//                        }
//                    }
//                    catch (Exception e)
//                    {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        };
//        executorService.submit(fileLog);
//        executorService.submit(fileLog);
//    }

}

