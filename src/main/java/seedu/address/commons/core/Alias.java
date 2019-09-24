package seedu.address.commons.core;

import java.io.Serializable;
import java.util.Arrays;
import java.util.regex.Pattern;

import static seedu.address.commons.util.AppUtil.checkArgument;

public class Alias implements Serializable {

    public static final String MESSAGE_CONSTRAINTS =
            "Alias names can only consist of alphanumeric characters";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    private final String aliasName;
    private final String input;

    public Alias(String aliasName, String input) {
        checkArgument(isValidAliasName(aliasName), MESSAGE_CONSTRAINTS);
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
        return Arrays.hashCode(new String[]{aliasName, input});
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
        return getAliasName().equals(other.getAliasName())
                && getInput().equals(other.getInput());
    }

    public boolean isValidAliasName(String aliasName) {
        return Pattern.matches(VALIDATION_REGEX, aliasName);
    }

//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("AliasName : ").append(aliasName);
//        sb.append("\nInput : ").append(input);
//        return sb.toString();
//    }
}
