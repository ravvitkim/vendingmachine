package service;

import dto.UserDto;
import exception.InputValidation;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdminService {

    private Scanner sc = new Scanner(System.in);
    private UserService userService = new UserService();

    public void machineMg() {
        //자판기 입력, 수정, 삭제, 조회

    }

    public void userMg() {
        //관리자는 회원 정보를 조회, 검색 (1.조회 2. 검색 3.삭제 4.입력)
        System.out.println("1.회원조회 2.회원검색 3.회원삭제 4.회원정보입력");
        int num = sc.nextInt();
        switch (num) {
            case 1 :
                findAllView();
                break;
            case 2 :
                searchView();
                break;
            case 3 :
                deleteView();
                break;
            case 4:
                updateView();
                break;

        }


    }


    public void saleMg() {
        //ⓐ 관리자는 제품별 판매현황을 확인할 수 있습니다.
        //                 (제품명, 판매수량, 판매금액)
        //	ⓑ 관리자는 회원별 판매현황을 확인할 수 있습니다.
        //  	   (회원아이디, 회원명, 구매금액, 충전잔액)
    }


    public void findAllView() {
        List<UserDto> dtoList = new ArrayList<>();
        System.out.println("===회원 목록===");
        //서비스에 DB에서 리스트로 요청하기
        dtoList = userService.getListAll();
        //출력
//        dtoList.stream().forEach(x-> System.out.println(x));
        for (UserDto dto : dtoList) {
            String signUpDate;
            if (dto.getSignUpDate() != null) {
                signUpDate = dto.getSignUpDate()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            } else {
                signUpDate = "";
            }


            String output = "u_id=" + dto.getU_id() +
                    ", id=" + dto.getId() + '\'' +
                    ", password=" + dto.getPassword() + '\'' +
                    ", name='" + dto.getName() + '\'' +
                    ", phoneNumber='" + dto.getPhoneNumber() + '\'' +
                    ", recharge='" + dto.getRecharge() + '\'' +
                    ", signUpDate=" +  signUpDate;
            System.out.println(output);
        }
    }
    public void searchView() {

        System.out.println("===회원 검색===");
        System.out.println("이름으로 검색합니다.");
        System.out.println("이름 전체나 일부를 입력하세요");
        String keyword = sc.next();
        List<UserDto> dtoList = userService.searchList(keyword);
        if (dtoList.size() == 0) {
            System.out.println("찾는 데이터가 없습니다.");
        } else {
            dtoList.stream().forEach(x -> System.out.println(x));
        }

    }
    public void deleteView() {
        System.out.println("========회원정보 삭제======");
        System.out.println("삭제할 ID를 입력하세요");
        int deleteId = sc.nextInt();
        //삭제 요청 후 결과를 int 타입으로 받기
        int result = userService.deleteData(deleteId);
        //result 값이 양수면 성공, 그렇지 않으면 실패
        if (result > 0) {
            System.out.println("정상적으로 삭제되었습니다");
        }else {
            System.out.println("삭제되지 않았습니다.");
            System.out.println("관리자에게 문의하세요");
        }
    }
    public void updateView() {
        System.out.println("=======회원정보 수정========");
        System.out.println("수정할 ID를 입력하세요");
        int updateId = sc.nextInt();
        //수정할 데이터를 가져온다.
        UserDto oldDto = userService.findById(updateId);
        if (oldDto == null) {
            System.out.println("찾는 데이터가 없어요");
            return;
        }


            //수정작업진행
            boolean yesOrNo = true;


            // 이름 수정 처리
            while (yesOrNo) {
                System.out.println("수정 전 ID : " + oldDto.getName());
                System.out.println("수정할까요?(Y/N)");
                String strYesOrNo = sc.next();
                if (strYesOrNo.toUpperCase().equals("Y")) {
                    System.out.println("수정할 ID : ");
                    oldDto.setName(sc.next());
                    yesOrNo = false;
                }else {
                    yesOrNo = false;
                }
            }
            yesOrNo = true;

        // 비밀번호 수정처리
            while (yesOrNo) {
                System.out.println("수정 전 비밀번호 : " + oldDto.getPassword());
                System.out.println("수정할까요?(Y/N)");
                String strYesOrNo = sc.next();
                if (strYesOrNo.toUpperCase().equals("Y")) {
                    System.out.println("수정할 비밀번호 : ");
                    oldDto.setPassword(sc.next());
                    yesOrNo = false;
                }else {
                    yesOrNo = false;
                }
            }

            // 이름 수정 처리
            while (yesOrNo) {
                System.out.println("수정 전 이름: " + oldDto.getName());
                System.out.println("수정할까요?(Y/N)");
                String strYesOrNo = sc.next();
                if (strYesOrNo.toUpperCase().equals("Y")) {
                    System.out.println("수정할 주소 : ");
                    oldDto.setName(sc.next());
                    yesOrNo = false;
                }else {
                    yesOrNo = false;
                }
            }

            // 전화번호 수정 처리
            while (yesOrNo) {
                System.out.println("수정 전 전화번호 : " + oldDto.getPhoneNumber());
                System.out.println("수정할까요?(Y/N)");
                String strYesOrNo = sc.next();
                if (strYesOrNo.toUpperCase().equals("Y")) {
                    System.out.println("수정할 전번 : ");
                    oldDto.setPhoneNumber(sc.next());
                    yesOrNo = false;
                }else {
                    yesOrNo = false;
                }
            }

        }
        int result = userService.updateData(oldDto);
        if (result > 0) {
            System.out.println("수정완료");
        } else {
            System.out.println("수정실패");
        }
    }