package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconn {
    private static Connection dbConn;

    public static Connection getConnection() {
        if (dbConn == null) {
            try {
                String dbDriver = "com.mysql.cj.jdbc.Driver";
                String dbUrl = "jdbc:mysql://localhost:3306/vmachine";
                String dbUser = "root";
                String dbPassword = "1111";
                Class.forName(dbDriver);
                dbConn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
                // 연결 성공한 경우
                System.out.println("DB 연결 성공");
            } catch (ClassNotFoundException e) {
                System.out.println("DB 연결 실패_1");
                e.printStackTrace();
            } catch (SQLException e) {
                System.out.println("DB 연결 실패_2");
                e.printStackTrace();
            }
        }
        return dbConn;
    }

    // DB연결 종료하기
    public static void close() {
        try {
            if (dbConn != null) {
                // 연결되어 있다면
                dbConn.close();
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        dbConn = null;
    }

}
