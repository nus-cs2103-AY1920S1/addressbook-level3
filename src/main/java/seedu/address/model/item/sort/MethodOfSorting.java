package seedu.address.model.item.sort;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a MethodOfSorting in the expiry date tracker.
 * Guarantees: immutable; name is valid as declared in {@link #isValidMethodOfSorting(String)}
 */
public class MethodOfSorting {

    public static final String MESSAGE_CONSTRAINTS = "Sorting can only be done by name or date.";
    private final String method;

    /**
     * Constructs a {@code MethodOfSorting}.
     * @param method A valid method of sorting.
     */
    public MethodOfSorting(String method) {
        requireNonNull(method);
        checkArgument(isValidMethodOfSorting(method), MESSAGE_CONSTRAINTS);
        this.method = method;
    }

    /**
     * Returns true if a given string is a valid method of sorting.
     */
    public static boolean isValidMethodOfSorting(String test) {
        System.out.println(test);
        boolean isValid = (test.equals("name") || test.equals("date"));
        System.out.println(isValid);
        return isValid;
    }

    /**
     * Returns the string value of the method of sorting.
     * @return The string representation of the method of sorting.
     */
    public String getValue() {
        return method;
    }
}
