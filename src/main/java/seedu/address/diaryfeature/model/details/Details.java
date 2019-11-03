package seedu.address.diaryfeature.model;

public class Details {
    private final String password;
    private final String userName ;

    public Details(String user, String pass) {
        userName = user;
        password = pass;
    }


    public boolean checkDetails(String user, String pass) {
        if(user.equals(userName) && password.equals(pass)) {
            return true;
        } else {
            return false;
        }
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String toString() {
        return "USER: " +  userName + "PASSWORD" + password;
    }



}
