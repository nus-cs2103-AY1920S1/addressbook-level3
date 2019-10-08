package seedu.address.model.budget;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

import seedu.address.model.expense.Description;
import seedu.address.model.expense.Price;

public class Budget {
    private final Description description;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Period period;
    private final Price amount;


    public Budget(Description description, Price amount, LocalDate startDate, Period period) {
        requireAllNonNull(description, startDate, period, amount);
        this.description = description;
        this.amount = amount;
        this.startDate = startDate;
        this.period = period;
        this.endDate = startDate.plus(period);
    }

    public Description getDescription() {
        return description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Period getPeriod() {
        return period;
    }

    public Price getAmount() {
        return amount;
    }

    public boolean isSameBudget(Budget otherBudget) {
        if (otherBudget == this) {
            return true;
        }

        return otherBudget != null
                && otherBudget.getDescription().equals(getDescription())
                && otherBudget.getAmount().equals(getAmount())
                && otherBudget.getStartDate().equals(getStartDate())
                && otherBudget.getPeriod().equals(getPeriod());
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
        return otherBudget.getDescription().equals(getDescription())
                && otherBudget.getAmount().equals(getAmount())
                && otherBudget.getStartDate().equals(getStartDate())
                && otherBudget.getPeriod().equals(getPeriod())
                && otherBudget.getEndDate().equals(getEndDate());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(description, startDate, period, amount);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("|| Description: ")
                .append(getDescription())
                .append(" Amount: ")
                .append(getAmount())
                .append(" Period: ")
                .append(getPeriod())
                .append(" Start date: ")
                .append(getStartDate())
                .append(" End date: ")
                .append(getEndDate())
                .append("||");
        return builder.toString();
    }
}
