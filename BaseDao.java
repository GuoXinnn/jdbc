package com.hp.demo.utils;



import java.sql.*;

public class BaseDao {
    private static Connection connection =null;
    private static PreparedStatement preparedStatement =null;
    private static ResultSet resultSet =null;
    //静态代码块，再类加载的时候运行，只会运行一次
    static{
        try {
            //开启驱动
            Class.forName("com.mysql.jdbc.Driver");
//            //建立连接
//            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/school", "root", "root");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    //建立连接
    public static Connection getConn(){
        //建立连接
        try {
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/school", "root", "root");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }
    //查询
    public  static ResultSet query(String sql,Object[] objects) throws SQLException {
        getConn();
        //接受sql语句
      preparedStatement = connection.prepareStatement(sql);
        //先判断objects是不是空值
        if (objects != null) {
            for (int i = 0; i < objects.length; i++) {
                preparedStatement.setObject(i+1,objects[i]);
            }
        }
        //执行sql语句，返回结果集
        resultSet = preparedStatement.executeQuery();
        return resultSet;
    }
    //增删改
    public static int update(String sql,Object[] objects){
        getConn();
        int count=0;//记录增删改的行数
        try {
            //接受sql语句
             preparedStatement = connection.prepareStatement(sql);
            if (objects != null) {
                for (int i = 0; i < objects.length; i++) {
                    preparedStatement.setObject(i+1,objects[i]);
                }
            }
            //执行sql语句，并返回影响数据库行数
            count = preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
      //  close();
        return count;
    }
    //释放资源
    public static void close(){
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}

