package exception;

public class MyException extends Exception {

    public static final Long serialVersionUID = 1L;    //대문자 쓸떄 숫자 뒤에 앞글자 붙혀야함
    public MyException(){}; //디폴트 생성자

    public MyException(String message) {
        super(message);
    }

}
