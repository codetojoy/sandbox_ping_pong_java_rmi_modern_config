package net.codetojoy.client.config;

import net.codetojoy.common.PingService;
import net.codetojoy.common.PongService;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ClientConfiguration {
    private final AnnotationConfigApplicationContext context;
    
    public ClientConfiguration() {
        context = new AnnotationConfigApplicationContext(Config.class);        
    }

    // client
    public PingService getPingService() throws Exception {
        return context.getBean(Config.BEAN_PING_SERVICE, PingService.class); 
    }

    // client
    public PongService getPongService() throws Exception {
        return context.getBean(Config.BEAN_PONG_SERVICE, PongService.class); 
    }
}
