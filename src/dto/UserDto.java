package dto;


public class UserDto extends CommonField{
    private int u_id;
    private String id;
    private String password;
    private String name;
    private String phoneNumber;
    private String recharge;
    private String cardNumber;
   

    @Override
    public String toString() {
        return "UserDto{" +
                "u_id='" + u_id + '\'' +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", recharge='" + recharge + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", signUpDate='" + getSignUpDate() +
                '}';
    }


    public int getU_id() {
        return u_id;
    }

    public void setU_id(int u_id) {
        this.u_id = u_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRecharge() {
        return recharge;
    }

    public void setRecharge(String recharge) {
        this.recharge = recharge;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
