package com.demo;

 
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
 
@Configuration
@EnableRedisHttpSession//开启spring session支持
public class RedisSessionConfig {
}
