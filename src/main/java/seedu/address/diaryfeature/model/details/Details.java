package seedu.address.diaryfeature.model.details;

public class Details {
    private final Username userName ;
    private final Password password;

    public Details(Username user, Password pass) {
        userName = user;
        password = pass;
    }


    public boolean checkDetails(Details input) {
        if(userName.equalsSpecial(input.getUserName()) && password.equalsSpecial(input.getPassword())) {
            return true;
        } else {
            return false;
        }
    }

    public Username getUserName() {
        return userName;
    }

    public Password getPassword() {
        return password;
    }

    public String toString() {
        return "USER: " +  userName + "PASSWORD" + password;
    }



}
