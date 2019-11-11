package seedu.address.diaryfeature.model.details;

public class Details {
    private final Username userName;
    private final Password password;

    /**
     * Construct a new set of user details
     * @param user
     * @param pass
     */
    public Details(Username user, Password pass) {
        userName = user;
        password = pass;
    }

    /**
     * Check if the 2 details are the same
     * @param input
     * @return boolean
     */
    public boolean checkDetails(Details input) {
        if (userName.equalsSpecial(input.getUserName()) && password.equalsSpecial(input.getPassword())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get this username
     * @return Username
     */
    public Username getUserName() {
        return userName;
    }

    /**
     * Get this password
     * @return Password
     */
    public Password getPassword() {
        return password;
    }

    public String toString() {
        return "USER: " + userName + "PASSWORD" + password;
    }


}
