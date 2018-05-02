package net.codetojoy.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Configuration;

import org.springframework.remoting.rmi.RmiRegistryFactoryBean;

@Configuration
class Config {

    protected static final String BEAN_MY_REGISTRY = "myRegistry";
        
    @Bean
    public RmiRegistryFactoryBean myRegistry() {
        RmiRegistryFactoryBean result = new RmiRegistryFactoryBean();

        result.setPort(2020);
        result.setAlwaysCreate(true);
        
        return result;
    }
}
