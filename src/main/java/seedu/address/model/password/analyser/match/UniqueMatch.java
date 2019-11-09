package seedu.address.model.password.analyser.match;

import java.util.Objects;

import seedu.address.model.password.Password;

/**
 * Represents a {@code Match} which was found  by {@code UniqueAnalyser}.
 */
public class UniqueMatch extends BaseMatch {
    private Password password;

    /**
     * Constructs a {@code UniqueMatch}
     *
     * @param startIndex the start index in the {@code PasswordValue} which the match was found.
     * @param endIndex the end index in the {@code PasswordValue} which the match was found.
     * @param token the string in the {@code PasswordValue} which the match was found.
     * @param password the password found that was the same.
     */
    public UniqueMatch(int startIndex, int endIndex, String token, Password password) {
        super(startIndex, endIndex, token);
        this.password = password;
    }

    @Override
    public String toString() {
        return super.toString() + "Type : Unique Match\n" + "Account : " + this.password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UniqueMatch that = (UniqueMatch) o;
        return password.equals(that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), password);
    }
}
