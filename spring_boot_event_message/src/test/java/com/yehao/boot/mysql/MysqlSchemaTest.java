package com.yehao.boot.mysql;

import com.yehao.boot.event.mysql.db.DBSchema;
import com.yehao.boot.event.mysql.db.DBSchemaService;
import com.yehao.boot.event.mysql.db.DataSourceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;
import java.util.List;

/**
 * @author : LiuYong
 * Created by Ly on 2019-08-10.
 * @Package: com.yehao.boot.mysql
 * @Description:
 * @function:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MysqlSchemaTest {


    @Autowired
    private DataSourceConfig dataSourceConfig;
    @Autowired
    private DBSchemaService dbSchemaService;


    @Test
    public void testMysqlSchema() {
        try {
            List<DBSchema> metaDataFromDB = dbSchemaService.getMetaDataFromDB(dataSourceConfig);
            metaDataFromDB.forEach(System.out::println);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
