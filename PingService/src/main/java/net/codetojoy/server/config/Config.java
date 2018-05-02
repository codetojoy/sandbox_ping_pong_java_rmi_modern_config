package net.codetojoy.server.config;

import net.codetojoy.common.PingService;
import net.codetojoy.server.PingServiceImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Configuration;

import org.springframework.remoting.rmi.RmiRegistryFactoryBean;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import org.springframework.remoting.rmi.RmiServiceExporter;

@Configuration
class Config {

    protected static final String BEAN_PING_SERVICE_EXPORTER = "pingServiceExporter";
    protected static final String BEAN_PONG_SERVICE = "pongService";
        
    @Bean
    public RmiRegistryFactoryBean myRegistry() {
        RmiRegistryFactoryBean result = new RmiRegistryFactoryBean();

        result.setHost("127.0.0.1");
        result.setPort(2020);
        
        return result;
    }

    @Bean
    @Lazy
    public RmiProxyFactoryBean pongService() {
        RmiProxyFactoryBean pongService = new RmiProxyFactoryBean();

        pongService.setServiceUrl("rmi://localhost:2020/pongServiceEndpoint");
        pongService.setServiceInterface(net.codetojoy.common.PongService.class);
        pongService.setRefreshStubOnConnectFailure(true);

        return pongService;
    }

    @Bean
    @Lazy
    public PingService pingService() {
        return new PingServiceImpl();
    }

    @Bean
    @Lazy
    public RmiServiceExporter pingServiceExporter() throws Exception {
        RmiServiceExporter result = new RmiServiceExporter();

        result.setServiceName("pingServiceEndpoint");
        result.setService(pingService());
        result.setServiceInterface(net.codetojoy.common.PingService.class);
        result.setRegistry(myRegistry().getObject());

        return result;
    }
}
