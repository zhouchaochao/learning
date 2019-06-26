package com.cc.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Title: DBOperate
 * Description: DBOperate
 * Date:  2018/7/9
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */
public class DBOperate {

    private static final Logger logger = LoggerFactory.getLogger(DBOperate.class);

    public static void main(String[] args) {

        String url = "jdbc:mysql://10.95.121.32:3306/chaochao?useUnicode=true&characterEncoding=UTF8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&autoReconnect=true&failOverReadOnly=false";
        String username = "root";
        String password = "123456";
        String driverClassName = "com.mysql.jdbc.Driver";

        String tableName = "t_user";

        Connection connection = DBUtil.getConnection(username, password, url, driverClassName);

        try {
            List<User> objects = new ArrayList<User>();
            for (int i = 0; i < 20; i++) {
                objects.add(new User(100 + i, "cc" + i, "p" + i, "phone" + i));
            }

            executeDelete(connection, tableName);

            executeInsert(connection, "insert into t_user (id,name,password,phone) values (?,?,?,?)", objects);
            executeSql(connection, "insert into t_user (id,name,password,phone) values (10,'cc1','pd','111'),(11,'cc1','pd','111')");

            executeUpdate(connection, "update t_user set name='updatevalue' where id<" + 20);

            int insertCount = executeCount(connection, tableName);
            logger.info("成功写入表记录数:" + insertCount);

            executeSelect(connection, "select id,name,password,phone from " + tableName);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            DBUtil.closeDBResources(null, connection);
        }

    }


    public static void executeInsert(Connection connection, String writeRecordSql, List<User> records)
            throws Exception {

        Date now = new Date(System.currentTimeMillis());

        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(writeRecordSql);

            for (User user : records) {
                preparedStatement.setLong(1, user.getId());
                preparedStatement.setString(2, user.getName());
                preparedStatement.setString(3, user.getPassword());
                preparedStatement.setString(4, user.getPhone());
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
            connection.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            DBUtil.closeDBResources(preparedStatement, null);
        }
    }


    public static void executeSql(Connection connection, String sql)
            throws Exception {

        Statement statement = null;

        try {
            statement = connection.createStatement();
            statement.execute(sql);
        } catch (Exception e) {
            throw e;
        } finally {
            DBUtil.closeDBResources(statement, null);
        }
    }


    public static void executeUpdate(Connection connection, String sql)
            throws Exception {


        PreparedStatement pstmt = connection.prepareStatement(sql);
        try {
            int updateCount = pstmt.executeUpdate();
            logger.info("更新数据条数 updateCount: " + updateCount);
        } catch (Exception e) {
            throw e;
        } finally {
            DBUtil.closeDBResources(pstmt, null);
        }
    }

    public static int executeSelect(Connection connection, String sql)
            throws Exception {

        //String sql =  + tableName;

        PreparedStatement statement = null;

        try {

            statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            int i = 0;

            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String password = rs.getString("password");
                String phone = rs.getString("phone");
                logger.info("查询获得" + new User(id, name, password, phone));
                i++;
            }

            return i;

        } catch (Exception e) {
            throw e;
        } finally {
            DBUtil.closeDBResources(statement, null);
        }
    }


    /**
     * 返回全表记录数
     *
     * @param connection
     * @param tableName
     * @return
     * @throws Exception
     */
    public static int executeCount(Connection connection, String tableName)
            throws Exception {

        String countSql = "select count(*) AS totalCount from " + tableName;

        PreparedStatement statement = null;

        try {

            statement = connection.prepareStatement(countSql);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                int count = rs.getInt("totalCount");
                return count;
            }

            return 0;

        } catch (Exception e) {
            throw e;
        } finally {
            DBUtil.closeDBResources(statement, null);
        }
    }


    /**
     * 删除表全部数据
     *
     * @param connection
     * @param tableName
     * @throws Exception
     */
    public static int executeDelete(Connection connection, String tableName)
            throws Exception {

        String sql = "delete from " + tableName;

        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(sql);
            int count = statement.executeUpdate();//.execute(sql);
            logger.info("删除记录数：" + count);

            return count;

        } catch (Exception e) {
            throw e;
        } finally {
            DBUtil.closeDBResources(statement, null);
        }
    }
}
