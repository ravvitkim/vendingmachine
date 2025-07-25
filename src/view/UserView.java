package view;

import dto.UserDto;
import exception.InputValidation;
import exception.MyException;
import service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserView {
    private Scanner sc = new Scanner(System.in);
    private UserService userService = new UserService();
    private InputValidation validation = new InputValidation();
    private List<UserDto> userList = new ArrayList<>();


// 아이디 비번 이름 전화번호
// 회원가입 후 카드번호 충전금액
    public void signUp() {
        System.out.println("회원가입 페이지");

        boolean idOK = true;
        String id = "";
        while (idOK) {
            try {
                System.out.print("아이디를 입력하세요: ");
                id = sc.next();
                validation.idCheck(id);
                idOK = false;
            } catch (MyException e) {
                System.out.println(e.getMessage());
            }
        }

        boolean passwardOK = true;
        String passward = "";
        while (passwardOK) {
            try {
                System.out.println("비밀번호를 입력하세요: ");
                passward = sc.next();
                validation.passwordCheck(passward);
                passwardOK = false;
            } catch (MyException e) {
                System.out.println(e.getMessage());
            }
        }


        boolean nameOK = true;
        String name="";
        while (nameOK) {
            try {
                System.out.println("이름을 입력하세요: ");
                name = sc.next();
                validation.nameCheck(name);
                nameOK = false;
            } catch (MyException e) {
                System.out.println(e.getMessage());
            }
        }

        boolean phoneOK = true;
        String phone = "";

        while (phoneOK) {
            try {
                System.out.println("전화번호를 입력하세요: ");
                phone = sc.next();
                validation.phoneNumberCheck(phone);
                phoneOK = false;
            }catch (MyException e) {
                System.out.println(e.getMessage());
            }
        }

        boolean cardOK = true;
        String cardNumber = "";
        while (cardOK) {
            try {
                System.out.println("카드번호를 입력하세요(xxxx-xxxx-xxxx-xxxx):");
                cardNumber = sc.next();
                validation.cardNumberCheck(cardNumber);
                cardOK = false;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }


        boolean rechargeOK = true;
        String recharge = "";
        while (rechargeOK) {
            try {
                System.out.println("카드에 충전 할 금액을 입력하세요(1000원 단위로 가능):");
                recharge = sc.next();
                validation.rechargeCheck(recharge);
                rechargeOK = false;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }




        //입력 받은 후  빈 TelDto에 넣는다.
        //id를 제외한 정보 입력(id는 자동생성)
        UserDto dto = new UserDto();
        dto.setId(id);
        dto.setPassword(passward);
        dto.setName(name);
        dto.setPhoneNumber(phone);
        dto.setCardNumber(cardNumber);
        dto.setRecharge(recharge);


        //서비스에 insert 요청하기
        int result = userService.insertData(dto);
        //result > 0 : insert 성공, result = 0 : 실패
        if (result > 0) {
            System.out.println("정상적으로 입력되었습니다");
        }else {
            System.out.println("입력되지 않았습니다");
        }


    }




    public boolean signIn() {
        System.out.println("로그인 페이지");
        System.out.println("ID :");
        String id = sc.next();
        System.out.println("PW :");
        String pw = sc.next();

        for (UserDto user : userList) {
            if (user.getId().equals(id) && user.getPassword().equals(pw)) {
                System.out.println("로그인 성공!");
                return true;
            }
        }
        return false;
    }

    public void menu() {
        System.out.println("메뉴선택페이지");
    }
}


