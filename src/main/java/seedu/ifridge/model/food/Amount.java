package seedu.ifridge.model.food;

import static java.util.Objects.requireNonNull;
import static seedu.ifridge.commons.util.AppUtil.checkArgument;

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
    public static final String UNIT_POUND = "lb";
    public static final String UNIT_KILOGRAM = "kg";
    public static final String UNIT_GRAM = "g";
    public static final String UNIT_OUNCE = "oz";
    public static final String UNIT_LITRE = "l";
    public static final String UNIT_MILLILITRE = "ml";
    public static final String UNIT_QUANTITY = "units";
    public static final String UNIT = "(lbs?|g|kg|oz?|L|ml|units?)+";
    public static final String VALIDATION_REGEX = VALUE_BEFORE_DECIMAL + "\\.?" + VALUE_AFTER_DECIMAL + "\\s*" + UNIT;
    public static final float KG_FROM_GRAM = 0.001f;
    public static final float KG_FROM_POUND = 0.453592f;
    public static final float KG_FROM_OUNCE = 0.0283495f;
    public static final float LITRE_FROM_MILLILITRE = 0.001f;


    private static Pattern p = Pattern.compile("(\\d*\\.?\\d+)(\\s*)((lbs?|g|kg|oz?|L|ml|units?)+)");
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

    /**
     * Tests whether an input amount is valid.
     *
     * @param test The input amount as a {@code String}/
     * @return true if the input amount is valid.
     */
    public static boolean isValidAmount(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Retrieves the numerical value of an {@code Amount} object, without the units
     *
     * @param amt The {@code Amount} object to get the value from.
     * @return The numerical value of the given Amount object.
     */
    public static float getValue(Amount amt) {
        m = p.matcher(amt.toString());
        String valueAsString = "";

        m.find();
        valueAsString = m.group(1);

        return Float.valueOf(valueAsString);
    }

    /**
     * Retrieves the unit of an {@code Amount} object, without the numerical value
     *
     * @param amt The {@code Amount} object to get the unit from.
     * @return The unit of the Amount object in String format.
     */
    public static String getUnit(Amount amt) {
        m = p.matcher(amt.toString());
        String unit = "";

        m.find();
        unit = m.group(3);

        return unit;
    }

    /**
     * Retrieves the weight of the Amount object.
     *
     * @param amt The Amount object to get the weight from.
     * @return The weight of the given Amount object.
     */
    public static float getAmountInKg(Amount amt) {
        String unit = getUnit(amt);
        float value = getValue(amt);

        switch (unit) {
        case UNIT_KILOGRAM:
            return value;
        case UNIT_GRAM:
            return value * KG_FROM_GRAM;
        case UNIT_POUND:
            return value * KG_FROM_POUND;
        case UNIT_OUNCE:
            return value * KG_FROM_OUNCE;
        default:
            return 0;
        }
    }

    /**
     * Retrieves the volume of the Amount object.
     *
     * @param amt The Amount object to get the volume from.
     * @return The volume of the given Amount object.
     */
    public static float getAmountInLitre(Amount amt) {
        String unit = getUnit(amt);
        float value = getValue(amt);

        switch (unit) {
        case UNIT_LITRE:
            return value;
        case UNIT_MILLILITRE:
            return value * LITRE_FROM_MILLILITRE;
        default:
            return 0;
        }
    }

    /**
     * Retrieves the number of units specified in the Amount object.
     *
     * @param amt The Amount object to get the number of units from.
     * @return The number of units specified in the given Amount object.
     */
    public static float getAmountInUnit(Amount amt) {
        String unit = getUnit(amt);
        float value = getValue(amt);

        return unit.equals(UNIT_QUANTITY) ? value : 0;
    }

    /**
     * Reduces the value of amount by the specified amount
     * @param amt the Amount class to be reduced by
     * @return Returns Amount with its value deducted
     */
    public Amount reduceBy(Amount amt) {
        float resultantAmount = Amount.getValue(this) - Amount.getValue(amt);
        String unit = Amount.getUnit(this);
        return new Amount(resultantAmount + unit);
    }

    /**
     * Increases the value of amount by the specified amount
     * @param amt the Amount to be increased by
     * @return Returns Amount with its value increased
     */
    public Amount increaseBy(Amount amt) {
        float resultantAmount = Amount.getValue(this) + Amount.getValue(amt);
        String unit = Amount.getUnit(this);
        return new Amount(resultantAmount + unit);
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
