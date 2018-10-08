package util;

import org.apache.commons.dbcp.BasicDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JDBCUtil {

    private static String driver;
    private static String url;
    private static String user;
    private static String password;
    private static Connection con;

    public JDBCUtil() {
    }

    static {
        InputStream in=JDBCUtil.class.getResourceAsStream("/db.properties");
        Properties properties=new Properties();
        try {
            properties.load(in);
            driver=properties.getProperty("driver");
            url=properties.getProperty("url");
            user=properties.getProperty("user");
            password=properties.getProperty("password");
            BasicDataSource ds=new BasicDataSource();
            ds.setDriverClassName(driver);
            ds.setUsername(user);
            ds.setPassword(password);
            ds.setUrl(url);
            ds.setInitialSize(10);
            ds.setMaxActive(20);
            ds.setMinIdle(5);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("读取配置文件错误");
        }
    }

    public static Connection getConnection(){
        BasicDataSource ds=new BasicDataSource();
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (con==null||con.isClosed())
                con=DriverManager.getConnection(url,user,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    public static void close(){
        close(null,null);
    }

    public static void close(Statement stm){
        close(null,stm);
    }

    public static void close(ResultSet rs, Statement stm){
        if (rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stm!=null){
            try {
                stm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
