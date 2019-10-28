package thrift.model.transaction;

import static thrift.commons.util.CollectionUtil.requireAllNonNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

/**
 * Represents a budget for a particular month, containing a Date and Value object.
 */
public class Budget {

    public static final SimpleDateFormat BUDGET_DATE_FORMAT = new SimpleDateFormat("MM/yyyy");
    public static final String DATE_CONSTRAINTS = "Month should be in the following format: MM/yyyy."
            + ", where MM is an integer between 01-12";
    public static final String VALIDATION_REGEX = "^(0[1-9]|10|11|12)\\/\\d{4}$";

    private Calendar period;
    private BudgetValue value;

    /**
     * Creates a Budget object that has {@code value} amount of money for the month of {@code period}.
     */
    public Budget(Calendar period, BudgetValue value) {
        requireAllNonNull(period, value);
        this.period = period;
        this.value = value;
    }

    public Calendar getBudgetDate() {
        return this.period;
    }

    public String getBudgetDateString() {
        return BUDGET_DATE_FORMAT.format(period.getTime());
    }

    public BudgetValue getBudgetValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Budget)) {
            return false;
        }

        Budget otherBudget = (Budget) other;
        return otherBudget.getBudgetDate().equals(getBudgetDate())
                && otherBudget.getBudgetValue().equals(getBudgetValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(period, value);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("\nAmount: $")
                .append(value.toString())
                .append("\nMonth: ")
                .append(getBudgetDateString());
        return builder.toString();
    }
}
