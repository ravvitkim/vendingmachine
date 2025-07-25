package service;

import db.DBconn;
import dto.UserDto;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserService implements  CRUDInterface{
    Connection conn = DBconn.getConnection();
    PreparedStatement psmt = null;
    String sql;

    public int insertData(UserDto dto) {
        try {
            sql =  "INSERT INTO user(id, password, name, phoneNumber, cardNumber, recharge) ";
            sql = sql + "VALUES(?, ?, ?, ?, ?, ?)";

            psmt = conn.prepareStatement(sql);
            // ?각 자리를 Mapping 해 준다.
            psmt.setString(1, dto.getId());
            psmt.setString(2, dto.getPassword());
            psmt.setString(3, dto.getName());
            psmt.setString(4, dto.getPhoneNumber());
            psmt.setString(5, dto.getCardNumber());
            psmt.setString(6, dto.getRecharge());


            //쿼리 실행하기
            int result = psmt.executeUpdate();
            psmt.close();
            return result;


        }catch (SQLException e){
            System.out.println(e.toString());
        }
        return 0;
    }

    @Override
    public int updateData(UserDto dto) {
        int result = 0;
        try {
            sql = "UPDATE user SET";
            sql = sql + " id = ?,";
            sql = sql + " password = ?,";
            sql = sql + " name = ?,";
            sql = sql + " phoneNumber = ?,";
            sql = sql + " cardNumber = ?";
            sql = sql + " WHERE u_id = ?";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, dto.getId());
            psmt.setString(2,dto.getPassword());
            psmt.setString(3, dto.getName());
            psmt.setString(4, dto.getPhoneNumber());
            psmt.setString(5, dto.getCardNumber());
            psmt.setInt(6,dto.getU_id());
            result = psmt.executeUpdate();
            psmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public int deleteData(int u_id) {
        int result = 0;
        try {
            sql = "DELETE FROM user WHERE u_id = ?";
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1,u_id);
            result = psmt.executeUpdate();
            psmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return  result;
    }


    @Override
    public List<UserDto> getListAll() {
        // DB에서 Select한 결과를 담을 리스트 선언
        List<UserDto> dtoList = new ArrayList<>();
        ResultSet rs = null;  //object라서 비워둠
        try {
            sql = "SELECT * FROM user";
            psmt = conn.prepareStatement(sql);
            rs = psmt.executeQuery();

            //ResultSet 에 들어온 레코드들을 하나씩 뽑아서
            //DtoList에 담는다.
            while (rs.next()){
                UserDto dto = new UserDto();
                dto.setU_id(rs.getInt("u_id"));
                dto.setId(rs.getString("id"));
                dto.setPassword(rs.getString("password"));
                dto.setPhoneNumber(rs.getString("phoneNumber"));
                dto.setCardNumber(rs.getString("cardNumber"));
                dto.setRecharge(rs.getString("recharge"));
                //날짜가 null인지 확인 후 처리
                if (rs.getTimestamp("signUpDate") != null){
                    dto.setSignUpDate(rs.getTimestamp("signUpDate").toLocalDateTime());
                }else {
                    dto.setSignUpDate(null);
                }
                //리스트에 담기
                dtoList.add(dto);
            }
//            //잘 들어왔는지 확인
//            dtoList.stream().forEach(x-> System.out.println(x));
            rs.close();
            psmt.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }

        return dtoList;
    }



    public UserDto findById(int id) {
        //id를 받아서 해당 레코드를 읽어오는
        ResultSet rs = null;
        try {
            sql = "SELECT u_id,id,password,name,phoneNumber,cardNumber,recharge FROM user WHERE u_id = ? ";
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1,id);
            rs = psmt.executeQuery();
            //레코드 셋의 자료를 while로 순회하면서 읽는다
            while (rs.next()) {
                UserDto dto = new UserDto();
                dto.setU_id(rs.getInt("u_id"));
                dto.setId(rs.getString("id"));
                dto.setPassword(rs.getString("password"));
                dto.setPhoneNumber(rs.getString("phoneNumber"));
                dto.setCardNumber(rs.getString("cardNumber"));
                dto.setRecharge(rs.getString("recharge"));
                return dto;
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<UserDto> searchList(String keyword) {
        ResultSet rs = null;
        List<UserDto> dtoList = new ArrayList<>();
        try {
//            sql = "SELECT id,name,age,address,phone FROM telBook WHERE name LIKE(name,'[가-힝]') " ;
            sql = "SELECT u_id,id,password,name,phoneNumber,cardNumber,recharge,signUpDate ";
            sql = sql + "FROM user ";
            sql = sql + "WHERE name LIKE ? ";
            sql = sql + "ORDER BY name DESC ";
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
                dto.setRecharge(rs.getString("recharge"));
                dtoList.add(dto);
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return dtoList;

    }


//    public boolean isUsernameAvailable(String id) {
//        try {
//            sql = "SELECT id FROM user WHERE id = ?";
//            psmt = conn.prepareStatement(sql);
//            psmt.setString(1, id);
//            ResultSet rs = psmt.executeQuery();
//
//            boolean isAvailable = !rs.next(); // true면 사용 가능
//            rs.close();
//            psmt.close();
//            return isAvailable;
//
//        } catch (SQLException e) {
//            System.out.println("[Error] isUsernameAvailable: " + e.getMessage());
//        }
//        return false;
//    }



}
