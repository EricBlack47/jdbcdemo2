package util;

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
            Class.forName(driver);
            con= DriverManager.getConnection(url,user,password);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("读取配置文件错误");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("无法加载驱动");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("连接失败");
        }
    }

    public static Connection getConnection(){
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
