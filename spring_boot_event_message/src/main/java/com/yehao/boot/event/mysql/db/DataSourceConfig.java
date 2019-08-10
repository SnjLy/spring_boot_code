package com.yehao.boot.event.mysql.db;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author : LiuYong
 * @Package: com.yehao.boot.event.mysql.db
 * @Description:
 * @function:
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "config.mysql")
public class DataSourceConfig {

    private Long id;

    private Long indexId;

    private String ip;

    private String port = "3306";

    private String dbName;

    private String userName;

    private String password;

    private String tableName;

    private Date createTime;

    private Date updateTime;

    private String description;

    @Override
    public String toString() {
        return "DataSourceConfig{" +
                "id=" + id +
                ", indexId=" + indexId +
                ", ip='" + ip + '\'' +
                ", port='" + port + '\'' +
                ", dbName='" + dbName + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", tableName='" + tableName + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", description='" + description + '\'' +
                '}';
    }
}
