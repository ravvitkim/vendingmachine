package service;

import dto.ProductDto;
import dto.UserDto;
import exception.InputValidation;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AdminService {

    private Scanner sc = new Scanner(System.in);
    private UserService userService = new UserService();
    private ProductService productService = new ProductService();
    private OrderService orderService = new OrderService();

    public void machineMg() {
        //자판기 입력, 수정, 삭제, 조회
        while (true) {
            System.out.println("1.메뉴등록 2.메뉴수정 3.메뉴삭제 4.메뉴조회 0.종료");
            int num = sc.nextInt();
            switch (num) {
                case 1:
                    MenuInsertView();
                    break;
                case 2:
                    MenuUpdateView();
                    break;
                case 3:
                    MenuDeleteView();
                    break;
                case 4:
                    MenuFindAllView();
                    break;
                case 0:
                    return;

            }
        }


    }

    private void MenuFindAllView() {
        System.out.println("메뉴조회");
        List<ProductDto> productList = productService.getAllProducts();

        if (productList.isEmpty()) {
            System.out.println("등록된 메뉴가 없습니다.");
            return;
        }
        System.out.println("ID\t메뉴명\t가격\t재고");
        for (ProductDto product : productList) {
            System.out.println(
                    product.getP_id() + "\t" +
                            product.getP_name() + "\t" +
                            product.getPrice() + "\t" +
                            product.getStock()
            );
        }
    }

    private void MenuDeleteView() {
        System.out.println("메뉴 삭제 페이지");

        System.out.print("삭제할 메뉴 ID 입력: ");
        int p_id = sc.nextInt();

        int result = productService.deleteProduct(p_id);
        if (result > 0) {
            System.out.println("메뉴가 정상적으로 삭제되었습니다.");
        } else {
            System.out.println("삭제 실패");
        }
    }

    private void MenuUpdateView() {
        System.out.println("메뉴 수정 페이지 ");

        System.out.print("수정할 메뉴의 ID: ");
        int id = sc.nextInt();

        ProductDto product = productService.getProductById(id);
        if (product == null) {
            System.out.println("해당 ID의 메뉴가 존재하지 않습니다.");
            return;
        }

        System.out.print("새 이름 (" + product.getP_name() + "): ");
        String name = sc.next();

        System.out.print("새 가격 (" + product.getPrice() + "): ");
        int price = sc.nextInt();

        System.out.print("새 재고 (" + product.getStock() + "): ");
        int stock = sc.nextInt();

        product.setP_name(name);
        product.setPrice(price);
        product.setStock(stock);

        int result = productService.updateProduct(product);
        if (result > 0) {
            System.out.println("수정이 완료되었습니다.");
        } else {
            System.out.println("수정 실패!");
        }
    }

    private void MenuInsertView() {
        System.out.println("메뉴등록페이지");

        System.out.println("추가할 메뉴의 이름 : ");
        String name = sc.next();
        System.out.println(name + "의 가격 : ");
        int price = sc.nextInt();
        System.out.println(name +"의 재고 : ");
        int stock = sc.nextInt();
        ProductDto dto = new ProductDto();

        dto.setP_name(name);
        dto.setPrice(price);
        dto.setStock(stock);

        // 상품 등록
        int result = productService.insertProduct(dto);

        if (result > 0) {
            System.out.println("정상적으로 저장되었습니다.");

        } else {
            System.out.println("메뉴 등록에 실패했습니다.");

        }
    }


    public void userMg() {
        //관리자는 회원 정보를 조회, 검색 (1.조회 2. 검색 3.삭제 4.입력)
        while (true) {
            System.out.println("1.회원조회 2.회원검색 3.회원삭제 4.회원정보입력 0.종료");
            int num = sc.nextInt();
            switch (num) {
                case 1:
                    UserFindAllView();
                    break;
                case 2:
                    UserSearchView();
                    break;
                case 3:
                    UserDeleteView();
                    break;
                case 4:
                    UserUpdateView();
                    break;
                case 0:
                    return;

            }
        }


    }


    public void saleMg() {
        while (true){
            System.out.println("1. 제품별 판매현황  2. 회원별 판매현황");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    List<Map<String, Object>> productSales = orderService.getProductSales();
                    System.out.println("제품명\t판매수량\t판매금액");
                    for (Map<String, Object> map : productSales) {
                        System.out.printf("%s\t%d\t%d\n",
                                map.get("p_name"),
                                map.get("sales_count"),
                                map.get("sales_amount") == null ? 0 : map.get("sales_amount"));
                    }
                    break;

                case 2:
                    List<Map<String, Object>> userSales = orderService.getUserSales();
                    System.out.println("회원아이디\t회원명\t구매금액\t충전잔액");
                    for (Map<String, Object> map : userSales) {
                        System.out.printf("%s\t%s\t%d\t%d\n",
                                map.get("id"),
                                map.get("name"),
                                map.get("total_purchase"),
                                map.get("recharge"));
                    }
                    break;

            }
        }
    }



    public void UserFindAllView() {
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
                    ", signUpDate=" + signUpDate;
            System.out.println(output);
        }
    }

    public void UserSearchView() {

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

    public void UserDeleteView() {
        System.out.println("========회원정보 삭제======");
        System.out.println("삭제할 ID를 입력하세요");
        int deleteId = sc.nextInt();
        //삭제 요청 후 결과를 int 타입으로 받기
        int result = userService.deleteData(deleteId);
        //result 값이 양수면 성공, 그렇지 않으면 실패
        if (result > 0) {
            System.out.println("정상적으로 삭제되었습니다");
        } else {
            System.out.println("삭제되지 않았습니다.");
            System.out.println("관리자에게 문의하세요");
        }
    }

    public void UserUpdateView() {
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
            } else {
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
            } else {
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
            } else {
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
            } else {
                yesOrNo = false;
            }
        }
        int result = userService.updateData(oldDto);
        if(result >0)

        {
            System.out.println("수정완료");
        } else

        {
            System.out.println("수정실패");
        }

    }


}