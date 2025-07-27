package view;

import dto.UserDto;
import exception.InputValidation;
import exception.MyException;
import service.AdminService;
import service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class UserView {
    private Scanner sc = new Scanner(System.in);
    private UserService userService = new UserService();
    private InputValidation validation = new InputValidation();
    private Random rand = new Random();

    // 회원가입 메서드
    public void signUp() {
        System.out.println("회원가입 페이지");

        String id = "";
        while (true) {
            try {
                System.out.print("아이디를 입력하세요: ");
                id = sc.next();
                validation.idCheck(id);
                break;
            } catch (MyException e) {
                System.out.println(e.getMessage());
            }
        }

        String password = "";
        while (true) {
            try {
                System.out.print("비밀번호를 입력하세요: ");
                password = sc.next();
                validation.passwordCheck(password);
                break;
            } catch (MyException e) {
                System.out.println(e.getMessage());
            }
        }

        String name = "";
        while (true) {
            try {
                System.out.print("이름을 입력하세요: ");
                name = sc.next();
                validation.nameCheck(name);
                break;
            } catch (MyException e) {
                System.out.println(e.getMessage());
            }
        }

        String phone = "";
        while (true) {
            try {
                System.out.print("전화번호를 입력하세요 (ex: 010-1234-5678): ");
                phone = sc.next();
                validation.phoneNumberCheck(phone);
                break;
            } catch (MyException e) {
                System.out.println(e.getMessage());
            }
        }

        String cardNumber = "";
        while (true) {
            try {
                cardNumber = randomCardNumber();
                System.out.println("생성된 카드번호: " + cardNumber);
                validation.cardNumberCheck(cardNumber);
                if (isCardNumberTaken(cardNumber)) {
                    System.out.println("중복된 카드번호입니다. 다시 생성합니다.");
                    continue;
                }
                break;
            } catch (MyException e) {
                System.out.println(e.getMessage());
            }
        }

        int recharge = 0;
        while (true) {
            try {
                System.out.print("카드에 충전 할 금액을 입력하세요 (1000원 단위로 가능): ");
                recharge = sc.nextInt();
                validation.rechargeCheck(recharge);
                break;
            } catch (MyException e) {
                System.out.println(e.getMessage());
            }
        }

        UserDto dto = new UserDto();
        dto.setId(id);
        dto.setPassword(password);
        dto.setName(name);
        dto.setPhoneNumber(phone);
        dto.setCardNumber(cardNumber);
        dto.setRecharge(recharge);


        int result = userService.insertData(dto);
        if (result > 0) {
            System.out.println("정상적으로 회원가입 되었습니다.");
        } else {
            System.out.println("회원가입에 실패했습니다.");
        }
    }

    // 카드번호 중복 확인
    private boolean isCardNumberTaken(String cardNumber) {
        return userService.isCardNumberExist(cardNumber);
    }


    // 카드번호 랜덤 생성
    private String randomCardNumber() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int group = rand.nextInt(9000) + 1000;
            sb.append(group);
            if (i < 3) sb.append("-");
        }
        return sb.toString();
    }


    public boolean signIn() {
        System.out.println("로그인 페이지");
        System.out.print("ID : ");
        String id = sc.next();
        System.out.print("PW : ");
        String pw = sc.next();

        UserDto user = userService.findByIdAndPassword(id, pw);
        if (user != null) {
            System.out.println("로그인 성공!");
            return true;
        }
        System.out.println("로그인 실패!");
        return false;

    }

    // 메뉴 선택 메서드
    public void menu() {
        while (true) {
            System.out.println("\n메뉴 선택 페이지");
            System.out.println("1. 메뉴 선택 2.잔액 조회 3. 카드 충전 0. 종료");
            int num = sc.nextInt();

            switch (num) {
                case 1:
                    break;
                case 2:
                    checkBalance();
                    break;
                case 3:
                    rechargeMenu();
                    break;
                case 0:
                    System.out.println("프로그램 종료");
                    return;
            }
        }
    }

    // 카드 잔액 조회 기능
    private void checkBalance() {
        System.out.print("카드번호를 입력하세요: ");
        String cardNumber = sc.next();

        UserDto user = findUserByCardNumber(cardNumber);
        if (user == null) {
            System.out.println("등록되지 않은 카드번호입니다.");
            return;
        }
        System.out.println("현재 잔액: " + user.getRecharge() + "원");
    }

    // 카드 충전 기능 - 잔액 누적 처리
    private void rechargeMenu() {
        System.out.print("카드번호를 입력하세요: ");
        String cardNumber = sc.next();
        UserDto user = userService.findByCardNumber(cardNumber);
        if (user == null) {
            System.out.println("등록되지 않은 카드번호입니다.");
            return;
        }

        int amount = 0;
        while (true) {
            try {
                System.out.print("충전할 금액을 입력하세요 (1000원 단위): ");
                amount = sc.nextInt();
                validation.rechargeCheck(amount);
                break;
            } catch (MyException e) {
                System.out.println(e.getMessage());
            }
        }
        int newRecharge = user.getRecharge() + amount;
        user.setRecharge(newRecharge);

        int result = userService.updateRecharge(user);
        if (result > 0) {
            System.out.println("충전 완료! 현재 잔액: " + newRecharge + "원");
        } else {
            System.out.println("충전 실패");
        }
    }


    // 카드번호로 UserDto 찾기
    private UserDto findUserByCardNumber(String cardNumber) {
        return userService.findByCardNumber(cardNumber);
    }

}
