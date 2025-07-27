package service;

import db.DBconn;
import dto.UserDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static db.DBconn.close;

public class UserService implements CRUDInterface {
    Connection conn = DBconn.getConnection();
    PreparedStatement psmt = null;
    String sql;
    ResultSet rs = null;

    public int insertData(UserDto dto) {
        try {
            sql = "INSERT INTO user(id, password, name, phoneNumber, cardNumber, recharge, status) ";
            sql += "VALUES(?, ?, ?, ?, ?, ?, 1)"; // status 기본값 1

            psmt = conn.prepareStatement(sql);
            psmt.setString(1, dto.getId());
            psmt.setString(2, dto.getPassword());
            psmt.setString(3, dto.getName());
            psmt.setString(4, dto.getPhoneNumber());
            psmt.setString(5, dto.getCardNumber());
            psmt.setInt(6, dto.getRecharge());

            int result = psmt.executeUpdate();
            psmt.close();
            return result;

        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return 0;
    }

    // 🔄 회원 정보 전체 수정
    @Override
    public int updateData(UserDto dto) {
        int result = 0;
        try {
            sql = "UPDATE user SET ";
            sql += "id = ?, password = ?, name = ?, phoneNumber = ?, cardNumber = ? ";
            sql += "WHERE u_id = ? AND status = 1";

            psmt = conn.prepareStatement(sql);
            psmt.setString(1, dto.getId());
            psmt.setString(2, dto.getPassword());
            psmt.setString(3, dto.getName());
            psmt.setString(4, dto.getPhoneNumber());
            psmt.setString(5, dto.getCardNumber());
            psmt.setInt(6, dto.getU_id());
            result = psmt.executeUpdate();
            psmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public UserDto findByCardNumber(String cardNumber) {
        try {
            sql = "SELECT * FROM user WHERE cardNumber = ? AND status = 1";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, cardNumber);
            rs = psmt.executeQuery();

            if (rs.next()) {
                UserDto dto = new UserDto();
                dto.setU_id(rs.getInt("u_id"));
                dto.setId(rs.getString("id"));
                dto.setPassword(rs.getString("password"));
                dto.setName(rs.getString("name"));
                dto.setPhoneNumber(rs.getString("phoneNumber"));
                dto.setCardNumber(rs.getString("cardNumber"));
                dto.setRecharge(rs.getInt("recharge"));
                dto.setStatus(rs.getInt("status"));
                dto.setSignUpDate(rs.getTimestamp("signUpDate").toLocalDateTime());
                return dto;
            }

        } catch (SQLException e) {
            System.out.println("카드 조회 중 오류 발생: " + e.getMessage());
        } finally {
            close();
        }
        return null;
    }

    // ✅ 카드 충전 전용 메서드 (recharge 컬럼만 갱신)
    public int updateRecharge(UserDto dto) {
        int result = 0;
        try {
            sql = "UPDATE user SET recharge = ? WHERE u_id = ? AND status = 1";
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, dto.getRecharge());
            psmt.setInt(2, dto.getU_id());
            result = psmt.executeUpdate();
            psmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    // 논리 삭제
    public int deleteData(int u_id) {
        int result = 0;
        try {
            sql = "UPDATE user SET status = 0 WHERE u_id = ?";
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, u_id);
            result = psmt.executeUpdate();
            psmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public List<UserDto> getListAll() {
        List<UserDto> dtoList = new ArrayList<>();
        ResultSet rs = null;
        try {
            sql = "SELECT * FROM user WHERE status = 1";
            psmt = conn.prepareStatement(sql);
            rs = psmt.executeQuery();

            while (rs.next()) {
                UserDto dto = new UserDto();
                dto.setU_id(rs.getInt("u_id"));
                dto.setId(rs.getString("id"));
                dto.setPassword(rs.getString("password"));
                dto.setPhoneNumber(rs.getString("phoneNumber"));
                dto.setCardNumber(rs.getString("cardNumber"));
                dto.setRecharge(rs.getInt("recharge"));
                if (rs.getTimestamp("signUpDate") != null) {
                    dto.setSignUpDate(rs.getTimestamp("signUpDate").toLocalDateTime());
                } else {
                    dto.setSignUpDate(null);
                }
                dtoList.add(dto);
            }
            rs.close();
            psmt.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return dtoList;
    }

    public UserDto findById(int id) {
        ResultSet rs = null;
        try {
            sql = "SELECT u_id, id, password, name, phoneNumber, cardNumber, recharge ";
            sql += "FROM user WHERE u_id = ? AND status = 1";
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, id);
            rs = psmt.executeQuery();

            if (rs.next()) {
                UserDto dto = new UserDto();
                dto.setU_id(rs.getInt("u_id"));
                dto.setId(rs.getString("id"));
                dto.setPassword(rs.getString("password"));
                dto.setPhoneNumber(rs.getString("phoneNumber"));
                dto.setCardNumber(rs.getString("cardNumber"));
                dto.setRecharge(rs.getInt("recharge"));
                return dto;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    @Override
    public List<UserDto> searchList(String keyword) {
        ResultSet rs = null;
        List<UserDto> dtoList = new ArrayList<>();
        try {
            sql = "SELECT u_id, id, password, name, phoneNumber, cardNumber, recharge, signUpDate ";
            sql += "FROM user WHERE name LIKE ? AND status = 1 ORDER BY name DESC";

            psmt = conn.prepareStatement(sql);
            psmt.setString(1, "%" + keyword + "%");
            rs = psmt.executeQuery();

            while (rs.next()) {
                UserDto dto = new UserDto();
                dto.setU_id(rs.getInt("u_id"));
                dto.setId(rs.getString("id"));
                dto.setPassword(rs.getString("password"));
                dto.setPhoneNumber(rs.getString("phoneNumber"));
                dto.setCardNumber(rs.getString("cardNumber"));
                dto.setRecharge(rs.getInt("recharge"));
                dtoList.add(dto);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dtoList;
    }
}
