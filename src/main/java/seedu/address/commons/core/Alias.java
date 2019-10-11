package seedu.address.commons.core;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Pattern;


/**
 * Represents a user defined alias which can be used at the start of input.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Alias implements Serializable {

    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Alias names can only consist of alphanumeric characters";
    public static final String MESSAGE_INPUT_CONSTRAINTS =
            "Alias inputs must contain at least 1 non-whitespace character";
    public static final String NAME_VALIDATION_REGEX = "\\p{Alnum}+";
    public static final String INPUT_VALIDATION_REGEX = "[\\s\\S]*\\S[\\s\\S]*";


    private final String aliasName;
    private final String input;

    public Alias(String aliasName, String input) {
        requireAllNonNull(aliasName, input);
        checkArgument(isValidAliasName(aliasName), MESSAGE_NAME_CONSTRAINTS);
        checkArgument(isValidInput(input), MESSAGE_INPUT_CONSTRAINTS);
        this.aliasName = aliasName;
        this.input = input;
    }

    public String getAliasName() {
        return aliasName;
    }

    public String getInput() {
        return input;
    }

    @Override
    public int hashCode() {
        return Objects.hash(aliasName, input);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Alias)) {
            return false;
        }

        Alias other = (Alias) obj;
        return aliasName.equals(other.aliasName)
                && input.equals(other.input);
    }

    public static boolean isValidAliasName(String aliasName) {
        return Pattern.matches(NAME_VALIDATION_REGEX, aliasName);
    }


    public static boolean isValidInput(String input) {
        return Pattern.matches(INPUT_VALIDATION_REGEX, input);
    }

    @Override
    public String toString() {
        return "Alias: " + aliasName + " Input: " + input;
    }

}
