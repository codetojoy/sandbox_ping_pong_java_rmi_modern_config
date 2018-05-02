package net.codetojoy.server.config;

import net.codetojoy.common.PingService;
import net.codetojoy.common.rmi.Constants;
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

        result.setHost(Constants.HOST);
        result.setPort(Constants.PORT);
        
        return result;
    }

    @Bean
    @Lazy
    public RmiProxyFactoryBean pongService() {
        RmiProxyFactoryBean pongService = new RmiProxyFactoryBean();

        pongService.setServiceUrl(Constants.PONG_SERVICE_URI);
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

        result.setServiceName(Constants.PING_SERVICE);
        result.setService(pingService());
        result.setServiceInterface(net.codetojoy.common.PingService.class);
        result.setRegistry(myRegistry().getObject());

        return result;
    }
}
