package seedu.address.model.transaction;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.tag.Tag;
import seedu.address.model.util.Date;


/**
 * Handles Budget of a BankAccount.
 */
public class Budget {
    private Amount amount;
    private Date deadline;
    private boolean valid;

    private final Set<Tag> tags = new HashSet<>();

    public Budget() {
        this.valid = false;
    }

    /**
     * Constructor for Budget with no tags given.
     * By default, tag is "general"
     */
    public Budget(Amount amount, Date date) {
        this.amount = amount;
        this.deadline = date;
        this.tags.add(new Tag("general"));
        this.valid = true;
    }

    public Budget(Amount amount, Date date, Set<Tag> tags) {
        this.amount = amount;
        this.deadline = date;
        this.tags.addAll(tags);
        this.valid = true;
    }

    public Budget(Amount amount, int duration) {
        this.amount = amount;
        this.deadline = calculateDeadline(duration);
        this.valid = true;
    }

    public Amount getBudget() {
        return this.amount;
    }

    public Date getDeadline() {
        return this.deadline;
    }

    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }


    public boolean isValid() {
        return this.valid;
    }

    /**
     * Updates the amount of this budget given a new amount.
     * @param amount
     * @return
     */
    public Budget updateBudget(Amount amount) {
        Amount newBudget = this.amount.subtractAmount(amount);
        this.amount = newBudget;
        return this;
    }

    private void updateDeadline(Date date) {
        this.deadline = date;
    }

    /**
     * Calculates the new Date given the amount of duration from Today.
     * @return Date after {@code duration} days from today
     */
    private Date calculateDeadline(int duration) {
        LocalDate today = LocalDate.now();
        LocalDate newDeadline = today.plus(duration, DAYS);
        return new Date(newDeadline.toString());
    }

    /**
     * Checks if the given budget is the same Budget object as this budget.
     * @param otherBudget
     * @return
     */
    public boolean isSameBudget(Budget otherBudget) {
        if (otherBudget == this) {
            return true;
        }

        return otherBudget != null
                && otherBudget.getBudget().equals(getBudget())
                && otherBudget.getDeadline().equals(getDeadline());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Budget) {
            Budget inObj = (Budget) obj;
            return amount.equals(inObj.amount)
                    && deadline.equals(inObj.deadline);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format("$%s by %s", this.amount.toString(), this.deadline.toString());
    }

}
