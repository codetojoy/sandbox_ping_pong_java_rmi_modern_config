package net.codetojoy.monitor.config;

import net.codetojoy.common.PingService;
import net.codetojoy.common.PongService;
import net.codetojoy.common.rmi.Constants;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Configuration;

import org.springframework.remoting.rmi.RmiProxyFactoryBean;

@Configuration
public class Config {

    protected static final String BEAN_PING_SERVICE = "pingService";
    protected static final String BEAN_PONG_SERVICE = "pongService";
        
    @Bean
    @Lazy
    public RmiProxyFactoryBean pingService() {
        RmiProxyFactoryBean pingService = new RmiProxyFactoryBean();

        pingService.setServiceUrl(Constants.PING_SERVICE_URI);
        pingService.setServiceInterface(net.codetojoy.common.PingService.class);
        pingService.setRefreshStubOnConnectFailure(true);

        return pingService;
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
}
