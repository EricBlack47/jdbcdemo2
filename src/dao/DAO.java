package dao;


import annotation.Id;
import annotation.Table;
import util.JDBCUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
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
            if(param!=null)
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

    public <T>List<T> query(Class<T> cClass,String sql,Object...param){
        List<Map<String,Object>> mapList=query(sql,param);
        if (mapList.size()<=0) return null;
        List<T> olist=new ArrayList<>();
        for (Map<String,Object> row:mapList){
            try {
                T bean=cClass.newInstance();
                for (Map.Entry entry:row.entrySet()){
                    String key= (String) entry.getKey();
                    Object value=entry.getValue();
                    Field field=cClass.getDeclaredField(key);
                    field.setAccessible(true);
                    field.set(bean,value);
                }
                olist.add(bean);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return olist;
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


    public <T> T get(Class<T> tClass,Object id){
        Annotation an=tClass.getAnnotation(Table.class);
        Field[] fields=tClass.getDeclaredFields();
        String customerID=null;
        for (Field field:fields){
            Annotation annotation=field.getAnnotation(Id.class);
            if (annotation != null) {
                customerID=field.getName();
            }
            if (customerID==null){
                System.out.println("没有设置主键");
                return null;
            }
            String sql="select * from "+(((Table) an).name()+" where "+customerID+"=?");
            List<T> result=query(tClass,sql,id);
            System.out.println("sql=>"+sql);
            if (result!=null&&result.size()>0)
                return result.get(0);
        }
        return null;
    }

}
