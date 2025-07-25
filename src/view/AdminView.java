package view;

import service.AdminService;

import java.util.Scanner;

//adminservice에서 진행

public class AdminView {
    AdminService adminService = new AdminService();
    public static Scanner sc = new Scanner(System.in);
    public void adminScreen(){
        while (true) {
            System.out.println("1.자판기관리 2.회원관리 3.판매관리");
            int choice = sc.nextInt();
            switch (choice){
                case 1 :
                    adminService.machineMg();
                    break;
                case 2:
                    adminService.userMg();
                case 3:
                    adminService.saleMg();
            }
        }
    }
}