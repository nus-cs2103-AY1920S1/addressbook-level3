package seedu.address.model.transaction;

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

    private Amount getBudget() {
        return this.amount;
    }

    private Date getDeadline() {
        return this.deadline;
    }

    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
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
