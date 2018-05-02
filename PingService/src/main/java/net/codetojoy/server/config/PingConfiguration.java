package net.codetojoy.server.config;

import net.codetojoy.common.PongService;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import org.springframework.remoting.rmi.RmiServiceExporter;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

public class PingConfiguration {
    private final AnnotationConfigApplicationContext context;
    
    public PingConfiguration() {
        context = new AnnotationConfigApplicationContext(Config.class);        
    }

    // server
    public RmiServiceExporter getPingServiceExporter() {
        return context.getBean(Config.BEAN_PING_SERVICE_EXPORTER, RmiServiceExporter.class); 
    }

    // client
    public PongService getPongService() throws Exception {
        return context.getBean(Config.BEAN_PONG_SERVICE, PongService.class); 
    }
}
