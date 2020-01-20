package seedu.address.model.finance;

import seedu.address.model.project.Time;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

public class Spending {
    private final Money spending;
    private final Time time;
    private final String description;
    public static final String MESSAGE_CONSTRAINTS = "Expense should be in the form of description in text format, amount in xx.xxformat and date in dd/MM/yyyy hhmm format";

    public Spending(Money spending, Time time, String description) {
        requireAllNonNull(spending, time, description);
        this.spending = spending;
        this.time = time;
        this.description = description;
    }

    public Money getMoney() {
        return spending;
    }

    public Time getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return String.format("Spent $%s for %s on %s ", spending.toString(), description, time.toString());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Spending)) {
            return false;
        }

        Spending otherSpending = (Spending) other;
        return otherSpending.getMoney().equals(getMoney())
                && otherSpending.getTime().equals(getTime())
                && otherSpending.getDescription().equals(getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, time, spending);
    }
}
