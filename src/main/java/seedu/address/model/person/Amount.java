package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a Food's amount in the grocery list.
 */
public class Amount {
    public static final String MESSAGE_CONSTRAINTS = "Amounts should be of the format value unit "
            + "and adhere to the following constraints:\n"
            + "1. The value part should only contain digits and can have be decimal point or not.\n"
            + "2. This is followed by a unit that can have a space in between or not. \n"
            + "The unit must be one of the following: \n"
            + "    - lbs, kgs, g, pounds, oz, L, ml, units.";

    public static final String VALUE_BEFORE_DECIMAL = "(\\d*)";
    public static final String VALUE_AFTER_DECIMAL = "(\\d+)";
    public static final String UNIT = "(lbs?|g|kgs|oz?|L|ml|units?)+";
    public static final String VALIDATION_REGEX = VALUE_BEFORE_DECIMAL + "\\.?" + VALUE_AFTER_DECIMAL + "\\s*" + UNIT;

    private static Pattern p = Pattern.compile("(\\d*\\.?\\d+)(\\s*)((lbs?|g|kgs|oz?|L|ml|units?)+)");
    private static Matcher m;

    public final String fullAmt;

    /**
     * Constructs a {@code Name}.
     *
     * @param amount A valid amount.
     */
    public Amount(String amount) {
        requireNonNull(amount);
        checkArgument(isValidAmount(amount), MESSAGE_CONSTRAINTS);
        fullAmt = amount;
    }

    public static boolean isValidAmount(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public static float getValue(Amount amt) {
        m = p.matcher(amt.toString());
        String valueAsString = "";

        m.find();
        valueAsString = m.group(1);

        return Float.valueOf(valueAsString);
    }

    public static String getUnit(Amount amt) {
        m = p.matcher(amt.toString());
        String unit = "";

        m.find();
        unit = m.group(3);

        return unit;
    }

    @Override
    public String toString() {
        return fullAmt;
    }

    @Override
    public int hashCode() {
        return fullAmt.hashCode();
    }

}
