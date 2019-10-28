package seedu.address.model.finance;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

public class Budget {
    private final String name;
    private final BigDecimal amount;
    private final List<Spending> spendings = new ArrayList<>();
    private BigDecimal remainingAmount;
    public static final String MESSAGE_CONSTRAINTS = "Budget should have a name followed by an amount in the form 99.99";
    public static final String VALIDATION_REGEX = "\\d+.\\d{1,2}?";
    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,###.00");

    public Budget(String name, BigDecimal amount, List<Spending> spendings) {
        requireAllNonNull(name, amount, spendings);
        this.name = name;
        this.amount = amount;
        this.spendings.addAll(spendings);
        this.remainingAmount = calculateRemaining(amount, spendings);
    }

    /**
     * The project has spend on something under this type of budget.
     * Add a spending object to the list of spendings
     * @param spending A spending object
     */
    public void addSpending(Spending spending) {
        spendings.add(spending);
        remainingAmount = remainingAmount.subtract(spending.getSpending());
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String amountInString() {
        return DECIMAL_FORMAT.format(amount);
    }

    public BigDecimal getRemainingAmount() {
        return remainingAmount;
    }

    public String remainingAmountInString() {
        return DECIMAL_FORMAT.format(remainingAmount);
    }

    public String getName() {
        return name;
    }

    public List<Spending> getSpendings() {
        return spendings;
    }

    public BigDecimal calculateRemaining(BigDecimal amount, List<Spending> spendings) {
        BigDecimal result = new BigDecimal(amount.toString());
        for (Spending spending : spendings) {
            result = result.subtract(spending.getSpending());
        }
        return result;
    }

    /**
     * Return is a string is a valid money amount.
     */
    public static boolean isValidAmount(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Budget %s has $%s remaining: ", name, DECIMAL_FORMAT.format(remainingAmount)));
        for (Spending spending : spendings) {
            sb.append("\n " + spending.toString());
        }
        return sb.toString();
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
        return otherBudget.getAmount().equals(getAmount())
                && otherBudget.getName().equals(getName())
                && otherBudget.getSpendings().equals(getSpendings());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, amount, spendings);
    }
}
