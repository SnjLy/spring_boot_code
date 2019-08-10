package com.yehao.boot.event.mysql.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author : LiuYong
 * @Package: com.yehao.boot.event.mysql.db
 * @Description:
 * @function:
 */
@Setter
@Getter
@AllArgsConstructor
public class DBSchema {
    private String dbFieldName;
    private String dbFieldType;

    @Override
    public String toString() {
        return "DBSchema{" +
                "dbFieldName='" + dbFieldName + '\'' +
                ", dbFieldType='" + dbFieldType + '\'' +
                '}';
    }
}
