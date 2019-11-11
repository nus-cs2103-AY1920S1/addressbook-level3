package seedu.address.model.policy;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Policy's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class PolicyName {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";
    public static final String DATA_TYPE = "NAME";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String policyName;

    /**
     * Constructs a {@code PolicyName}.
     *
     * @param name A valid policy name.
     */
    public PolicyName(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        policyName = formatName(name);
    }

    /**
     * Formats the policy name.
     * @param name name
     * @return
     */
    private String formatName(String name) {
        String[] nameSplitBySpace = name.toLowerCase().split(" ");
        StringBuilder formattedName = new StringBuilder();
        for (int i = 0; i < nameSplitBySpace.length; i++) {
            String firstLetter = nameSplitBySpace[i].substring(0, 1);
            String remainingLetters = nameSplitBySpace[i].substring(1);
            formattedName.append(firstLetter.toUpperCase() + remainingLetters);
            formattedName.append(" ");
        }
        return formattedName.toString().trim();
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return policyName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PolicyName // instanceof handles nulls
                && policyName.equals(((PolicyName) other).policyName)); // state check
    }

    @Override
    public int hashCode() {
        return policyName.hashCode();
    }

}
