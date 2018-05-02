package net.codetojoy.server.config;

import net.codetojoy.common.PingService;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import org.springframework.remoting.rmi.RmiServiceExporter;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

public class PongConfiguration {
    private final AnnotationConfigApplicationContext context;
    
    public PongConfiguration() {
        context = new AnnotationConfigApplicationContext(Config.class);        
    }

    // server
    public RmiServiceExporter getPongServiceExporter() {
        return context.getBean(Config.BEAN_PONG_SERVICE_EXPORTER, RmiServiceExporter.class); 
    }

    // client
    public PingService getPingService() throws Exception {
        return context.getBean(Config.BEAN_PING_SERVICE, PingService.class); 
    }
}
