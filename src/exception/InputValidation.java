package exception;

import java.util.regex.Pattern;

public class InputValidation {

    public boolean idCheck(String id) throws MyException{
        if (!id.matches("^[a-zA-Z0-9]{4,12}$")) {
            throw new MyException("아이디는 영문과 숫자 4~12자여야 합니다.");
        }
        if (!idCheck(id)) {
            throw new MyException("이미 사용 중인 아이디입니다.");
        }
        return false;
    }





    public void nameCheck(String name) throws MyException {
        boolean check = Pattern.matches("^[ㄱ-ㅎ가-힣]*$", name); //정규표현식, * 반복한다는뜻
        if (!check) {
            throw new MyException("이름은 한글로 입력하세요");
        }
    }

    public void ageCheck(int age) throws MyException {
        if (age <0 || age > 120) {
            throw new MyException("나이는 0세부터 120세 까지예요");
        }
    }
    public void phoneNumberCheck(String phoneNumber) throws MyException {
        boolean check = Pattern.matches("^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", phoneNumber);
        if (!check) {
            throw new MyException("✔휴대폰 입력방식은 xxx-xxxx-xxxx입니다");

        }
    }



}
