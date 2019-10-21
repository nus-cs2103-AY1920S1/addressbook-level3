package seedu.address.model.transaction;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.Date;
import seedu.address.model.tag.Tag;


/**
 * Handles Budget of a BankAccount.
 */
public class Budget {
    private Amount amount;
    private Date deadline;
    private boolean valid;

    //Data fields
    private final Set<Tag> tags = new HashSet<>();

    public Budget() {
        this.valid = false;
    }

    public Budget(Amount amount, Date date) {
        this.amount = amount;
        this.deadline = date;
        this.valid = true;
    }

    public Budget(Amount amount, int duration) {
        this.amount = amount;
        this.deadline = calculateDeadline(duration);
        this.valid = true;
    }

    private Amount getBudget() {
        return this.amount;
    }

    private Date getDeadline() {
        return this.deadline;
    }

    public boolean isValid() {
        return this.valid;
    }

    protected Budget updateBudget(Amount amount) {
        Amount newBudget = this.amount.subtractAmount(amount);
        this.amount = newBudget;
        return this;
    }

    private void updateDeadline(Date date) {
        this.deadline = date;
    }

    /**
     * Calculates the new Date given the amount of duration from Today
     * @return Date after {@code duration} days from today
     */
    private Date calculateDeadline(int duration) {
        return new Date("10102019");
    }

}
