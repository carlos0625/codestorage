package cn.edu.ncu.liuqing.banksavingsystem.connectDB;
import cn.edu.ncu.liuqing.banksavingsystem.tools.RandomPassword;

import java.sql.*;

public class OperationsForDB {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "lq990625";
    private static final String URL = "jdbc:mysql://localhost:3306/BankSavingSystem";
    public static short maxCno;

    /**
     * 建立数据库连接
     * @param
     * @return
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 关闭数据库连接
     * @param connection
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
