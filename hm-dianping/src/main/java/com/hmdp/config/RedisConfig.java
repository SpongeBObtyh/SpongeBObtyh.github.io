package com.hmdp.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author:SpongeBOb
 * @Date:2023/1/12
 * @Description:配置Redisson客户端
 * @Version:java_15
 */
@Configuration
public class RedisConfig {
    @Bean
    public RedissonClient redissonClient(){
        //加载配置类
        Config config = new Config();
        //添加redis地址，这里添加单点的地址，也可以使用config.useClusterServers()添加集群地址
        config.useSingleServer().setAddress("redis://192.168.10.102:6379").setPassword("092176");
        //创建客户端
        return Redisson.create(config);
    }
}
