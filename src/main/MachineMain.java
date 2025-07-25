package main;

import view.AdminView;
import view.UserView;

import java.util.Scanner;

public class MachineMain {
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        UserView userView = new UserView();
        AdminView adminView = new AdminView();


        while (true) {
            System.out.println("1.회원가입 2.로그인");
            int num = sc.nextInt();
            switch (num){
                case 0 :
                    System.out.println("관리자 비밀번호 입력 : ");
                    String password = sc.next();
                    if (password.equals("1111")) {
                        adminView.adminScreen();
                    } else {
                        continue;
                    }
                    break;
                case 1 :
                    userView.signUp();
                    userView.menu();
                    break;
                case 2 :
                    if (userView.signIn()) {
                        userView.menu();
                    } else {
                        System.out.println("로그인 실패 : 아이디 또는 비밀번호가 틀렸습니다.");
                    }
                    break;

            }
        }

    }
}
