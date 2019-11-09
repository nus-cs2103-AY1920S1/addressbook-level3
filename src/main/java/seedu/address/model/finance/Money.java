package seedu.address.model.finance;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Money {
    private BigDecimal amount;
    public static final String VALIDATION_REGEX = "\\d*.\\d{1,2}?";
    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,###.00");

    public Money(BigDecimal amount) {
        this.amount = amount;
    }

    public Money(String amount) {
        this.amount = new BigDecimal(amount);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Return is a string is a valid money amount.
     */
    public static boolean isValidAmount(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return DECIMAL_FORMAT.format(amount);
    }
}
