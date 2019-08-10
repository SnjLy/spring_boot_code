package com.yehao.boot.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author : LiuYong
 * Created by bosszhipin on 2019-08-09.
 * @Package: com.yehao.boot
 * @Description:
 * @function:
 */
@ConfigurationProperties(prefix = "config")
@Configuration
@Data
public class CommonConfig {

    private CanalConfig canal = new CanalConfig();
    private RedisConfig redis = new RedisConfig();
    private KafkaConfig kafka = new KafkaConfig();


    @Setter
    @Getter
    public class CanalConfig{
        private String host;
        private int port=11111;
        private String userName;
        private String password;
    }


    @Setter
    @Getter
    public class RedisConfig{

    }

    @Setter
    @Getter
    public class KafkaConfig{

    }
}
