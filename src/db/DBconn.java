package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    // ResultSet 닫기
    public static void close(ResultSet rs) {
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // PreparedStatement 닫기
    public static void close(PreparedStatement psmt) {
        try {
            if (psmt != null) psmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Connection 닫기 (필요시)
    public static void close(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 기존에 사용하던 Connection 닫기 (옵션)
    public static void close() {
        try {
            if (dbConn != null && !dbConn.isClosed()) {
                dbConn.close();
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        dbConn = null;
    }
}
