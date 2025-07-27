package service;

import db.DBconn;
import dto.OrderDto;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class OrderService {
    private Connection conn = DBconn.getConnection();
    private PreparedStatement psmt;
    private ResultSet rs;
    private String sql;

    // 기존 메서드 생략...

    // 제품별 판매 현황 조회
    public List<Map<String, Object>> getProductSales() {
        List<Map<String, Object>> salesList = new ArrayList<>();
        try {
            // product 테이블과 orders 테이블을 조인하여 제품별 판매 수량과 판매금액을 집계
            sql = "SELECT p.p_name, COUNT(o.p_id) AS sales_count, SUM(o.price) AS sales_amount " +
                    "FROM product p LEFT JOIN orders o ON p.p_id = o.p_id " +
                    "GROUP BY p.p_id, p.p_name";
            psmt = conn.prepareStatement(sql);
            rs = psmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                map.put("p_name", rs.getString("p_name"));
                map.put("sales_count", rs.getInt("sales_count"));
                map.put("sales_amount", rs.getInt("sales_amount") == 0 ? 0 : rs.getInt("sales_amount"));
                salesList.add(map);
            }
        } catch (SQLException e) {
            System.out.println("제품별 판매현황 조회 오류: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); if (psmt != null) psmt.close(); } catch (SQLException ignored) {}
        }
        return salesList;
    }

    // 회원별 판매 현황 조회
    public List<Map<String, Object>> getUserSales() {
        List<Map<String, Object>> userSalesList = new ArrayList<>();
        try {
            // user 테이블과 orders 테이블을 조인하여 회원별 구매금액 합계와 충전 잔액 가져오기
            sql = "SELECT u.id, u.name, COALESCE(SUM(o.price), 0) AS total_purchase, u.recharge " +
                    "FROM user u LEFT JOIN orders o ON u.u_id = o.u_id " +
                    "GROUP BY u.u_id, u.id, u.name, u.recharge";
            psmt = conn.prepareStatement(sql);
            rs = psmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", rs.getString("id"));
                map.put("name", rs.getString("name"));
                map.put("total_purchase", rs.getInt("total_purchase"));
                map.put("recharge", rs.getInt("recharge"));
                userSalesList.add(map);
            }
        } catch (SQLException e) {
            System.out.println("회원별 판매현황 조회 오류: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); if (psmt != null) psmt.close(); } catch (SQLException ignored) {}
        }
        return userSalesList;
    }
}
