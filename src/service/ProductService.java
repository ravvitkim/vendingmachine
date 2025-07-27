package service;

import db.DBconn;
import dto.ProductDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductService {

    private Connection conn = DBconn.getConnection();
    private PreparedStatement psmt;
    private ResultSet rs;
    private String sql;

    // 상품 등록
    public int insertProduct(ProductDto dto) {
        sql = "INSERT INTO product(p_name, price, stock, status) VALUES (?, ?, ?, 1)";
        try {
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, dto.getP_name());
            psmt.setInt(2, dto.getPrice());
            psmt.setInt(3, dto.getStock());

            return psmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("insertProduct 오류: " + e.getMessage());
        } finally {
            close();
        }
        return 0;
    }

    // 상품 전체 목록 조회
    public List<ProductDto> getAllProducts() {
        List<ProductDto> list = new ArrayList<>();
        sql = "SELECT * FROM product WHERE status = 1";


        try {
            psmt = conn.prepareStatement(sql);
            rs = psmt.executeQuery();

            while (rs.next()) {
                ProductDto dto = new ProductDto();
                dto.setP_id(rs.getInt("p_id"));
                dto.setP_name(rs.getString("p_name"));
                dto.setPrice(rs.getInt("price"));
                dto.setStock(rs.getInt("stock"));
                dto.setStatus(rs.getInt("status"));
                list.add(dto);
            }
        } catch (SQLException e) {
            System.out.println("getAllProducts 오류: " + e.getMessage());
        } finally {
            close();
        }

        return list;
    }

    // 상품 ID로 조회
    public ProductDto getProductById(int p_id) {
        ProductDto dto = null;
        sql = "SELECT * FROM product WHERE p_id = ?";

        try {
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, p_id);
            rs = psmt.executeQuery();

            if (rs.next()) {
                dto = new ProductDto();
                dto.setP_id(rs.getInt("p_id"));
                dto.setP_name(rs.getString("p_name"));
                dto.setPrice(rs.getInt("price"));
                dto.setStock(rs.getInt("stock"));
            }
        } catch (SQLException e) {
            System.out.println("getProductById 오류: " + e.getMessage());
        } finally {
            close();
        }

        return dto;
    }

    // 상품 수정
    public int updateProduct(ProductDto dto) {
        sql = "UPDATE product SET p_name = ?, price = ?, stock = ? WHERE p_id = ?";
        try {
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, dto.getP_name());
            psmt.setInt(2, dto.getPrice());
            psmt.setInt(3, dto.getStock());
            psmt.setInt(4, dto.getP_id());

            return psmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("updateProduct 오류: " + e.getMessage());
        } finally {
            close();
        }
        return 0;
    }

    // 상품 삭제
    public int deleteProduct(int p_id) {
        sql = "UPDATE product SET status = 0 WHERE p_id = ?";
        try {
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, p_id);

            return psmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("deleteProduct 오류: " + e.getMessage());
        } finally {
            close();
        }
        return 0;
    }

    // DB 자원 정리
    private void close() {
        try {
            if (rs != null) rs.close();
            if (psmt != null) psmt.close();
        } catch (SQLException e) {
            System.out.println("자원 정리 오류: " + e.getMessage());
        }
    }
}
