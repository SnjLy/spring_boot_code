package com.yehao.boot.event.mysql.db;

import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : LiuYong
 * @Package: com.yehao.boot.event.mysql.db
 * @Description:
 * @function:
 */
@Service
public class DBSchemaService {

    public List<DBSchema> getMetaDataFromDB(DataSourceConfig dataSource) throws SQLException {
        if (null == dataSource) {
            dataSource = new DataSourceConfig();
        }
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;


        try {
            conn = JdbcUtil.getConnection(
                    "jdbc:mysql://" + dataSource.getIp() + ":" + dataSource.getPort() + "/" + dataSource.getDbName()
                            + "?useUnicode=true&characterEncoding=utf-8&tinyInt1isBit=false",
                    dataSource.getUserName(), dataSource.getPassword());
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from " + dataSource.getDbName() + "." + dataSource.getTableName() + " LIMIT 0,0");
            ResultSetMetaData rsmd = rs.getMetaData();

            int columnCount = rsmd.getColumnCount();
            List<DBSchema> list = new ArrayList<>(columnCount);

            for (int i = 0; i < columnCount; i++) {
                list.add(new DBSchema(rsmd.getColumnName(i + 1), SqlTypeUtil.getTypeByValue(rsmd.getColumnType(i + 1))));
            }
            return list;
        } finally {
            JdbcUtil.free(conn, stmt, rs);
        }
    }
}
