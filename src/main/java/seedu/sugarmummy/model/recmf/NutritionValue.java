package seedu.sugarmummy.model.recmf;

import static java.util.Objects.requireNonNull;
import static seedu.sugarmummy.commons.util.AppUtil.checkArgument;

import java.text.DecimalFormat;
import java.util.logging.Logger;

import seedu.sugarmummy.commons.core.LogsCenter;

/**
 * Represents a kind of nutrition value of <code>Food</code> in the recommended food list. Guarantees: immutable; is
 * valid as declared in {@link #isValidValue(String)}
 */
public abstract class NutritionValue implements Comparable<NutritionValue> {

    public static final String MESSAGE_CONSTRAINTS =
            "Nutrition value should only contain one number (with no more than 4 decimals) and should be non-negative.";
    private static final String VALIDATION_REGEX = "^[+]?\\d{0,}\\.?\\d{0,4}$";
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.####");

    public final String value;
    private final Logger logger = LogsCenter.getLogger(UniqueFoodList.class);

    /**
     * Constructs a {@code NutritionValue}.
     *
     * @param value a valid nutrition value
     */
    public NutritionValue(String value) {
        requireNonNull(value);
        logger.info("----------------[Nutriention Value][Adding " + value + "]");
        checkArgument(isValidValue(value), MESSAGE_CONSTRAINTS);
        this.value = DECIMAL_FORMAT.format(Double.parseDouble(value));
    }

    /**
     * Returns true if a given string is a valid nutrition value.
     */
    public static boolean isValidValue(String test) {
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }
        try {
            Double.parseDouble(test);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Returns a specific warning message about the suitable range.
     */

    /**
     * Indicates whether this {@code NutritionValue} is suitable for diabetes patients.
     *
     * @return {@code true} if a certain nutrition value is too high to be considered safe for diabetes, {@code false}
     * otherwise.
     */
    public abstract boolean isInDangerousRange();

    /**
     * Returns the double value indicated in the {@code value} string
     */
    public double getNumericalValue() {
        return Double.parseDouble(value);
    }

    /**
     * Returns the specific warning message which indicates the suitable range of the input food for diabetes patients.
     */
    public abstract String getWarningMessage();

    //Helps the uniform warning message formats
    String getWarningMessage(String nutritionType, Double boundary) {
        return "The input value for " + nutritionType + " is too high. For diabetes patients, it should be lower than "
                + boundary + "\n";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof NutritionValue)) {
            return false;
        }
        return value.equals(((NutritionValue) other).value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int compareTo(NutritionValue another) {
        if (getNumericalValue() > another.getNumericalValue()) {
            return 1;
        } else if (getNumericalValue() == another.getNumericalValue()) {
            return 0;
        } else {
            return -1;
        }
    }
}

