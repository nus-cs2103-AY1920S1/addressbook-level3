package seedu.address.model.finance;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

public class Budget {
    private final String name;
    private final Double amount;
    private final List<Spending> spendings = new ArrayList<>();
    public static final String MESSAGE_CONSTRAINTS = "Budget should have a name followed by an amount in the form 99.99";
    public static final String VALIDATION_REGEX = "\\d+.\\d{1,2}?";

    public Budget(String name, Double amount, List<Spending> spendings) {
        requireAllNonNull(name, amount, spendings);
        this.name = name;
        this.amount = amount;
        this.spendings.addAll(spendings);
    }

    /**
     * The project has spend on something under this type of budget.
     * Add a spending object to the list of spendings
     * @param spending A spending object
     */
    public void addSpending(Spending spending) {
        this.spendings.add(spending);
    }

    public Double getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    public List<Spending> getSpendings() {
        return spendings;
    }

    /**
     * Return is a string is a valid money amount.
     */
    public static boolean isValidAmount(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(name + " $" + amount.toString() + ": ");
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
