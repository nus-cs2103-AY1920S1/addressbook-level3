package seedu.address.model.password.analyser.match;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;


/**
 * Represents a match found by an analyser which contains the start and end index as well as the match token.
 */
public abstract class BaseMatch implements Match {

    private static final String MESSAGE_INVALID_TOKEN = "start index cannot be greater than end index.";
    private String token;
    private int startIndex;
    private int endIndex;

    /**
     * Constructs a basic {@code Match}.
     *
     * @param startIndex the start index in the {@code PasswordValue} which the match was found.
     * @param endIndex the end index in the {@code PasswordValue} which the match was found.
     * @param token the string in the {@code PasswordValue} which the match was found.
     */
    public BaseMatch(int startIndex, int endIndex, String token) {
        requireNonNull(token);
        checkArgument(isValidMatch(startIndex, endIndex, token), MESSAGE_INVALID_TOKEN);
        this.token = token;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public String getToken() {
        return token;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    /**
     * Returns true if start index greater than end index, and token is not empty string.
     */
    protected Boolean isValidMatch(int startIndex, int endIndex, String token) {
        return startIndex < endIndex && !token.isEmpty();
    }

    @Override
    public String toString() {
        return MESSAGE_INIT
                + "Token: " + this.token + "\n"
                + "Start Index: " + this.startIndex + "\n"
                + "End Index: " + this.endIndex + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BaseMatch baseMatch = (BaseMatch) o;
        return startIndex == baseMatch.startIndex
                && endIndex == baseMatch.endIndex
                && token.equals(baseMatch.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, startIndex, endIndex);
    }

}
