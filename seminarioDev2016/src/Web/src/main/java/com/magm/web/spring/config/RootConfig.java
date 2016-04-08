package com.magm.web.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = { "com.magm.web" })
@Import({ WebConfig.class, PersistenceConfig.class, Beans.class })
public class RootConfig {

}
