package service;

import dto.UserDto;

import java.util.List;

public interface CRUDInterface {
    int insertData(UserDto dto);

    int updateData(UserDto dto);

    List<UserDto> getListAll();   //전체 찾기

    UserDto findById(int id); //한 개 데이터 찾기

    List<UserDto> searchList(String keyword);  //이름검색
}
