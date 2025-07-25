package dto;

import java.time.LocalDateTime;

public class CommonField {
    private LocalDateTime signUpDate;

    public LocalDateTime getSignUpDate() {
        return signUpDate;
    }

    public void setSignUpDate(LocalDateTime signUpDate) {
        this.signUpDate = signUpDate;
    }

}
