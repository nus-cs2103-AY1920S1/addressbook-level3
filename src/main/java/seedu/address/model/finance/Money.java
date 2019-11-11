package seedu.address.model.finance;

import seedu.address.commons.core.LogsCenter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Money {
    private BigDecimal amount;
    public static final String VALIDATION_REGEX = "\\d+(\\.\\d{1,2})?";
    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,###.00");
    public static final String MESSAGE_CONSTRAINTS = "Money should only contain numeric characters and should not be blank and should not contain more than two decimal places and should not be zero";
    private final Logger logger = LogsCenter.getLogger(getClass());

    public Money(BigDecimal amount) {
        requireNonNull(amount);
        this.amount = amount;
    }

    public Money(String amount) {
        requireNonNull(amount);
        checkArgument(isValidAmount(amount), MESSAGE_CONSTRAINTS);
        this.amount = new BigDecimal(amount);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Return is a string is a valid money amount.
     */
    public static boolean isValidAmount(String test) {
        return test.matches(VALIDATION_REGEX) && new BigDecimal(test).doubleValue() != 0;
    }

    @Override
    public String toString() {
        return DECIMAL_FORMAT.format(amount);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Money)) {
            return false;
        }

        Money otherMoney = (Money) other;
        return otherMoney.getAmount().equals(getAmount());
    }
}
