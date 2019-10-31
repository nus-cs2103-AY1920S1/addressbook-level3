package seedu.address.model.transaction;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.category.Category;
import seedu.address.model.util.Date;


/**
 * Handles Budget of a BankAccount.
 */
public class Budget {
    private Amount amount;
    private Date deadline;
    private boolean valid;
    private int between;

    private final Set<Category> categories = new HashSet<>();

    public Budget() {
        this.valid = false;
    }

    /**
     * Constructor for Budget with no categories given.
     * By default, category is "general"
     */
    public Budget(Amount amount, Date date) {
        this.amount = amount;
        this.deadline = date;
        this.categories.add(new Category("general"));
        this.valid = true;
        this.between = calculateRemaining();
    }

    public Budget(Amount amount, Date date, Set<Category> categories) {
        this.amount = amount;
        this.deadline = date;
        this.categories.addAll(categories);
        this.valid = true;
        this.between = calculateRemaining();
    }

    public Budget(Amount amount, int duration) {
        this.amount = amount;
        this.deadline = calculateDeadline(duration);
        this.valid = true;
        this.between = calculateRemaining();
    }

    public Amount getBudget() {
        return this.amount;
    }

    public Date getDeadline() {
        return this.deadline;
    }

    public Set<Category> getCategories() {
        return Collections.unmodifiableSet(categories);
    }

    public String getBetween() {
        return String.format("%d more days", this.between);
    }


    public boolean isValid() {
        return this.valid;
    }

    /**
     * Updates the amount of this budget given a new amount.
     *
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
     *
     * @return Date after {@code duration} days from today
     */
    private Date calculateDeadline(int duration) {
        LocalDate today = LocalDate.now();
        LocalDate newDeadline = today.plus(duration, DAYS);
        return new Date(newDeadline.toString());
    }

    /**
     * Calculates the number of days between budget's deadline and Today.
     *
     * @return int number of days between budget deadline and today
     */
    public int calculateRemaining() {
        Date today = new Date (LocalDate.now().toString());
        int numberOfDays = today.compareTo(this.deadline);
        return numberOfDays;

    }

    /**
     * Checks if the given budget is the same Budget object as this budget.
     *
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
                && deadline.equals(inObj.deadline)
                && valid == inObj.valid;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format("$%s by %s", this.amount.toString(), this.deadline.toString());
    }

}
