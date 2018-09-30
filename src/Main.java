import dao.DAO;
import util.JDBCUtil;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
     Connection conn= JDBCUtil.getConnection();
        DAO dao=new DAO();
        /*
        String sql="insert into userinfo(customerID,customerName,PID,telephone,address) values(?,?,?,?,?)";
        dao.update(sql,5,"猪八戒","123","110","高老庄");*/

        //List<Map<String,Object>> user=dao.query("select * from userinfo");
        List<Map<String,Object>> user=dao.query("select * from userinfo where customerID=?",2);
        for (Map u:user){
            System.out.println(u);
        }

    }
}
