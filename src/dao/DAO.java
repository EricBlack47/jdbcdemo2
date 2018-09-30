package dao;

import jdk.nashorn.api.tree.ReturnTree;
import util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DAO {

    public void update(String sql,Object...args){
        Connection con= JDBCUtil.getConnection();
        try {
            System.out.println("sql=>"+sql);
            System.out.println("param=>");
            PreparedStatement pstm=con.prepareStatement(sql);
            for (int i=0;i<args.length;i++){
                pstm.setObject(i+1,args[i]);
                System.out.print("\t"+args[i]);
            }
            pstm.executeUpdate();
            JDBCUtil.close(pstm);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Map<String,Object>> query(String sql,Object...param){
        Connection con=JDBCUtil.getConnection();
        try {
            PreparedStatement pstm=con.prepareStatement(sql);
            for (int i=0;i<param.length;i++){
                pstm.setObject(i+1,param[i]);
            }
            ResultSet rs=pstm.executeQuery();
            return resultSetToMap(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    private List<Map<String,Object>> resultSetToMap(ResultSet rs){
        List<String> names=getColumnName(rs);
        List<Map<String,Object>> result=new ArrayList<>();
        try {
            while (rs.next()){
                Map<String,Object> row=new HashMap<>();
                for (String n:names) {
                    Object values=rs.getObject(n);
                    row.put(n,values);
                }
                result.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    private List<String> getColumnName(ResultSet rs){
        List<String> names=new ArrayList<>();
        try {
            ResultSetMetaData rsmd=rs.getMetaData();
            for(int i=0;i<rsmd.getColumnCount();i++){
                names.add(rsmd.getColumnName(i+1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return names;
    }

}
