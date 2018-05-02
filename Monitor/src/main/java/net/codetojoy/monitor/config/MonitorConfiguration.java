package net.codetojoy.monitor.config;

import net.codetojoy.common.PingService;
import net.codetojoy.common.PongService;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import org.springframework.remoting.rmi.RmiProxyFactoryBean;

public class MonitorConfiguration {
    private final AnnotationConfigApplicationContext context;
    
    public MonitorConfiguration() {
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
