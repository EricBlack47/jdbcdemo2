import dao.DAO;
import entiy.Deposit;
import entiy.User;
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
        /*List<Map<String,Object>> user=dao.query("select * from userinfo where customerID=?",2);
        for (Map u:user){
            System.out.println(u);
        }*/
       /* List<User> user=dao.query(User.class,"select * from userinfo");
        for (User u:user){
            System.out.println(u);
            }
        List<Deposit> deposits=dao.query(Deposit.class,"select * from deposit");
        for (Deposit d:deposits
        ) {
            System.out.println(d);
    }*/
/*

       User u=dao.get(User.class,1);
       System.out.println(u);
*/

  }
}
