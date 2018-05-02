package net.codetojoy.server.config;

import net.codetojoy.common.PongService;
import net.codetojoy.server.PongServiceImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Configuration;

import org.springframework.remoting.rmi.RmiRegistryFactoryBean;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import org.springframework.remoting.rmi.RmiServiceExporter;

@Configuration
public class Config {

    protected static final String BEAN_PING_SERVICE = "pingService";
    protected static final String BEAN_PONG_SERVICE_EXPORTER = "pongServiceExporter";
        
    @Bean
    public RmiRegistryFactoryBean myRegistry() {
        RmiRegistryFactoryBean result = new RmiRegistryFactoryBean();

        result.setHost("127.0.0.1");
        result.setPort(2020);
        
        return result;
    }

    @Bean
    @Lazy
    public RmiProxyFactoryBean pingService() {
        RmiProxyFactoryBean pingService = new RmiProxyFactoryBean();

        pingService.setServiceUrl("rmi://localhost:2020/pingServiceEndpoint");
        pingService.setServiceInterface(net.codetojoy.common.PingService.class);
        pingService.setRefreshStubOnConnectFailure(true);

        return pingService;
    }

    @Bean
    @Lazy
    public PongService pongService() {
        return new PongServiceImpl();
    }

    @Bean
    @Lazy
    public RmiServiceExporter pongServiceExporter() throws Exception {
        RmiServiceExporter result = new RmiServiceExporter();

        result.setServiceName("pongServiceEndpoint");
        result.setService(pongService());
        result.setServiceInterface(net.codetojoy.common.PongService.class);
        result.setRegistry(myRegistry().getObject());

        return result;
    }
}
