package service;

import db.DBconn;
import dto.UserDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService implements  CRUDInterface{
    Connection conn = DBconn.getConnection();
    PreparedStatement psmt = null;
    String sql;

    public int insertData(UserDto dto) {
        System.out.println("[UserService.InsertData]");

        try {
            sql =  "INSERT INTO user(id, password, name, phoneNumber, recharge,cardNumber) ";
            sql = sql + "VALUES(?, ?, ?, ?, ?, ?)";

            psmt = conn.prepareStatement(sql);
            // ?각 자리를 Mapping 해 준다.
            psmt.setString(1, dto.getId());
            psmt.setString(2, dto.getPassword());
            psmt.setString(3, dto.getName());
            psmt.setString(4, dto.getPhoneNumber());
            psmt.setString(5, dto.getRecharge());
            psmt.setString(6, dto.getCardNumber());


            //쿼리 실행하기
            int result = psmt.executeUpdate();
            psmt.close();
            return result;


        }catch (SQLException e){
            System.out.println(e.toString());
        }
        return 0;
    }

    public boolean isUsernameAvailable(String id) {
        try {
            sql = "SELECT id FROM user WHERE id = ?";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, id);
            ResultSet rs = psmt.executeQuery();

            boolean isAvailable = !rs.next(); // true면 사용 가능
            rs.close();
            psmt.close();
            return isAvailable;

        } catch (SQLException e) {
            System.out.println("[Error] isUsernameAvailable: " + e.getMessage());
        }
        return false;
    }



}
