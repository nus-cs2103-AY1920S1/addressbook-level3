package seedu.address.model.password.analyser.match;

import seedu.address.model.password.Password;

/**
 * Represents a unique match found by a unique analyser.
 */
public class UniqueMatch extends BaseMatch {
    private Password password;

    public UniqueMatch(int startIndex, int endIndex, String token, Password password) {
        super(startIndex, endIndex, token);
        this.password = password;
    }

    @Override
    public String toString() {
        return super.toString() + "Type : Unique Match\n" + "Account : " + this.password;
    }
}
