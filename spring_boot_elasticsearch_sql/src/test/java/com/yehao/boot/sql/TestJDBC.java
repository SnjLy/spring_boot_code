package com.yehao.boot.sql;

import org.elasticsearch.xpack.sql.jdbc.jdbcx.JdbcDataSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.sql.*;
import java.util.Properties;

/**
 * com.yehao.boot.sql
 *
 * @author: SNJly
 * @date: 2019-07-19
 */
@RunWith(JUnit4.class)
public class TestJDBC {

    public static String  driver = "org.elasticsearch.xpack.sql.jdbc.jdbc.JdbcDriver";
    public static final String INDEX_NAME = "bank";

    public static final String ES_ADDRESS_9200 = "106.13.44.20:9200";
    public static final String ES_ADDRESS_9300 = "106.13.44.20:9300";


    @Before
    //启动数据源
    public void initDBSource(){
//		SQLUtil.startPool("es",//ES数据源名称 for 6.3.x
//				"org.elasticsearch.xpack.sql.jdbc.jdbc.JdbcDriver",//ES jdbc驱动
//				"jdbc:es://http://192.168.137.1:9200/timezone=UTC&page.size=250",//es链接串
//				"elastic","changeme",//es x-pack账号和口令
//				"SHOW tables 'dbclob111%'" //数据源连接校验sql
//		);
//		SQLUtil.startPool("es",//ES数据源名称
//				"org.elasticsearch.xpack.sql.jdbc.EsDriver",//ES jdbc驱动
//				"jdbc:es://http://192.168.137.1:9200/timezone=UTC&page.size=250",//es链接串
//				"elastic","changeme",//es x-pack账号和口令
//				null,//"false",
//				null,// "READ_UNCOMMITTED",
//				"SHOW tables 'demo'", //数据源连接校验sql
//				 "es_jndi",
//				10,
//				10,
//				20,
//				true,
//				false,
//				null, true, false,10000,"es7","com.frameworkset.sqlexecutor.DBElasticsearch7"
//		);
    }


    public static Properties initProperties(){
        Properties properties = new Properties();
        properties.put("user", "elastic");
        properties.put("password", "elasticsearch");
        return properties;
    }


    @Test
    public void testJDBC() throws Exception {
        String address = "jdbc:es://http://" + ES_ADDRESS_9200;
        Properties connectionProperties = initProperties();
        try  {
            Connection connection = DriverManager.getConnection(address, connectionProperties);
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(
                    "SELECT firstname, account_number FROM bank ORDER BY account_number DESC LIMIT 5");
            while(results.next()){
                System.out.println(results.getString("firstname"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @Test
    public void testDataSource(){
        JdbcDataSource dataSource = new JdbcDataSource();
        String address = "jdbc:es://http://" + ES_ADDRESS_9200;
        dataSource.setUrl(address);
        Properties connectionProperties = initProperties();
        dataSource.setProperties(connectionProperties);

        try{
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(
                    "   SELECT * FROM bank  ORDER BY city DESC  LIMIT 10");
            System.out.println("rows：" + results.getRow());
            while(results.next()){
                convertResult(results);
            }

            ResultSet results2 = statement.executeQuery(
                    "SELECT * FROM bank where age > 18 LIMIT 10");
            System.out.println("rows：" + results2.getRow());
            while(results2.next()){
                convertResult(results2);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }


    private void convertResult(ResultSet rs) throws Exception{
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            System.out.println("tableName:" + metaData.getTableName(i) + "  ColumnName:" + metaData.getColumnName(i) +  "  ColumnTypeName:"+ metaData.getColumnTypeName(i));
            System.out.println("isReadOnly:" + metaData.isReadOnly(i) +"  isWriteable:" + metaData.isWritable(i) + "  isNullable:" + metaData.isNullable(i));

        }

    }



}
