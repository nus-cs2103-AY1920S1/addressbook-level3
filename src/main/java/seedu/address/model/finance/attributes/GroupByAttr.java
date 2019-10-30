package seedu.address.model.finance.attributes;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents a GroupBy attribute in the finance log.
 *
 * Guarantees: immutable; is valid as declared in {@link #isValidGroupByAttr(String)}
 */
public class GroupByAttr {

    public static final String MESSAGE_CONSTRAINTS =
            "Attributes to group by are only: \"entrytype\", \"met\", \"cat\", \"place\" and \"month\"";

    public final String attr;

    /**
     * Constructs a {@code GroupByAttr}.
     *
     * @param attr A valid attribute.
     */
    public GroupByAttr(String attr) {
        requireNonNull(attr);
        checkArgument(isValidGroupByAttr(attr), MESSAGE_CONSTRAINTS);
        this.attr = attr;
    }

    /**
     * Returns true if a given string is a valid attribute to group by.
     */
    public static boolean isValidGroupByAttr(String test) {
        ArrayList<String> attrList = new ArrayList<>(
                Arrays.asList("entrytype", "met", "cat", "place", "month"));
        return attrList.stream().anyMatch(test.toLowerCase()::contains);
    }

    @Override
    public String toString() {
        return attr;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GroupByAttr // instanceof handles nulls
                && attr.equals(((GroupByAttr) other).attr)); // state check
    }

    @Override
    public int hashCode() {
        return attr.hashCode();
    }

}
