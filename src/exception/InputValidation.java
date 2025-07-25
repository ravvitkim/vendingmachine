package exception;

import java.util.regex.Pattern;

public class InputValidation {


    public void idCheck(String id) throws MyException {
        // 아이디가 4~12자의 영문자와 숫자로만 이루어졌는지 확인
        if (!Pattern.matches("^[a-zA-Z0-9]{4,12}$", id)) {
            throw new MyException("아이디는 영문과 숫자만 포함하며 4~12자여야 합니다.");
        }

        // 이미 사용 중인 아이디인지 확인
        if (isIdTaken(id)) {
            throw new MyException("이미 사용 중인 아이디입니다.");
        }
    }

    private boolean isIdTaken(String id) {
        return "".equals(id);
    }

    public void passwordCheck(String password) throws MyException {
        if (!password.matches("^[a-zA-Z0-9]$")) {
            throw new MyException("비밀번호는 영문과 숫자 4~12자여야 합니다.");
        }
    }

    public void nameCheck(String name) throws MyException {
        boolean check = Pattern.matches("^[ㄱ-ㅎ가-힣]*$", name); //정규표현식, * 반복한다는뜻
        if (!check) {
            throw new MyException("이름은 한글로 입력하세요");
        }
    }

    public void phoneNumberCheck(String phoneNumber) throws MyException {
        boolean check = Pattern.matches("^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", phoneNumber);
        if (!check) {
            throw new MyException("✔휴대폰 입력방식은 xxx-xxxx-xxxx입니다");

        }
    }

    public void cardNumberCheck(String cardNumber) throws MyException {
        boolean check = Pattern.matches("((5[1-5]\\d{14})|(4\\d{12}(\\d{3})?)|(3[47]\\d{13})|(6011\\d{12})|((30[0-5]|3[68]\\d)\\d{11}))",cardNumber);
        if (!check) {
            throw new MyException("✔카드번호 입력방식은 xxxx-xxxx-xxxx-xxxx 입니다");
        }
    }


    public void rechargeCheck(String recharge) throws MyException {
        int number = 0;
        try {
            number = Integer.parseInt(recharge);
        } catch (NumberFormatException e) {
            throw new MyException("숫자 형식이 아닙니다.");
        }

        boolean check = (number % 1000) == 0;
        if (!check) {
            throw new MyException("1000 단위 숫자를 입력하세요.");
        }
    }

    //카드번호 충전금액 1000원단위
    // 중복안되는 것 아이디 휴대폰번호 카드번호

    //q1 중복처리 inputvalidation에서 처리?
    //q2 카드번호를 입력후 db에 저장,








}
