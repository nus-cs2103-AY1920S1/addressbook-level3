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
public class Budget implements Comparable<Budget> {
    private Amount initialAmount;
    private Amount amount;
    private Date start = Date.now();
    private Date deadline;
    private boolean valid;
    private int between;

    private final Set<Category> categories = new HashSet<>();

    public Budget() {
        this.valid = false;
    }

    /**
     * Constructor for Budget with no categories given.
     * By default, category is "Uncategorised"
     */
    public Budget(Amount initialAmount, Amount amount, Date date, Set<Category> categories) {
        this.initialAmount = initialAmount;
        this.amount = amount;
        this.deadline = date;
        this.categories.addAll(categories);
        this.valid = true;
        this.between = calculateRemaining();
    }

    public Budget(Amount amount, Date date) {
        this.initialAmount = amount;
        this.amount = amount;
        this.deadline = date;
        this.categories.add(Category.GENERAL);
        this.valid = true;
        this.between = calculateRemaining();
    }

    public Budget(Amount amount, Date date, Set<Category> categories) {
        this.initialAmount = amount;
        this.amount = amount;
        this.deadline = date;
        this.categories.addAll(categories);
        this.valid = true;
        this.between = calculateRemaining();
    }

    public Budget(Amount amount, int duration) {
        this.initialAmount = amount;
        this.amount = amount;
        this.deadline = calculateDeadline(duration);
        this.categories.add(Category.GENERAL);
        this.valid = true;
        this.between = calculateRemaining();
    }

    public Date getStart() {
        return this.start;
    }

    public void setInitialAmount(Amount amount) {
        this.initialAmount = amount;
    }

    public Amount getInitialBudget() {
        return this.initialAmount;
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

    public boolean isGeneral() {
        return this.categories.contains(Category.GENERAL);
    }

    public int getBetweenRaw() {
        return this.between;
    }

    public String getBetween() {
        return String.format("%d more days", this.between);
    }

    public boolean isValid() {
        return this.valid;
    }

    /**
     * Updates the amount of this budget given a new amount if the Transaction is of the same category.
     *
     * @param amount
     * @return
     */
    public Budget updateBudget(Amount amount, Set<Category> categories, boolean isRemoveTransaction) {
        boolean isSameCategory = false;

        for (Category ct : categories) {
            if (this.categories.contains(ct)) {
                isSameCategory = true;
                break;
            }
        }

        if (isSameCategory && isRemoveTransaction) {
            Amount newAmount = this.amount.subtractAmount(amount);
            Budget newBudget = new Budget(newAmount, this.getDeadline(), this.getCategories());
            newBudget.setInitialAmount(this.initialAmount);
            return newBudget;
        } else if (isSameCategory) {
            Amount newAmount = this.amount.addAmount(amount);
            Budget newBudget = new Budget(newAmount, this.getDeadline(), this.getCategories());
            newBudget.setInitialAmount(this.initialAmount);
            return newBudget;
        } else {
            return this;
        }
    }

    /** Updates the amount of this budget if a Transaction of the same category is replaced/changed.
     *
     * @param amountToReplace accepts the amount to be replaced
     * @param amountReplacement accepts the amount to replace {@code amountToReplace}
     * @param categories accepts the set of categories to be cross checked with existing budgets
     * @param isSameTransactionCategory returns true if the categories of both transactions
     * to be edited and replaced are the same.
     * @return
     */
    public Budget updateBudget(Amount amountToReplace, Amount amountReplacement,
                               Set<Category> categories, boolean isSameTransactionCategory) {
        boolean isSameCategory = false;

        for (Category ct : categories) {
            if (this.categories.contains(ct)) {
                isSameCategory = true;
                break;
            }
        }

        if (isSameCategory && !isSameTransactionCategory) {
            Amount newAmount = this.amount.addAmount(amountReplacement);
            Budget newBudget = new Budget(newAmount, this.getDeadline(), this.getCategories());
            newBudget.setInitialAmount(this.initialAmount);
            return newBudget;
        } else if (isSameCategory) {
            Amount newAmount = this.amount.addAmount(amountReplacement).subtractAmount(amountToReplace);
            Budget newBudget = new Budget(newAmount, this.getDeadline(), this.getCategories());
            newBudget.setInitialAmount(this.initialAmount);
            return newBudget;
        } else {
            return this;
        }
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
        return Date.daysBetween(Date.TODAY, deadline);
    }

    /**
     * Checks if the given budget is the same Budget object as this budget.
     *
     * @param otherBudget
     */
    public boolean isSameBudget(Budget otherBudget) {
        if (otherBudget == this) {
            return true;
        }
        return otherBudget != null
            && otherBudget.getBudget().equals(getBudget())
            && otherBudget.getCategories().equals(getCategories())
            && otherBudget.getDeadline().equals(getDeadline());
    }

    public String displayBudget() {
        return String.format("$%s out of $%s remaining", this.amount.toString(), this.initialAmount.toString());
    }

    /**
     * Displays the percentage of budget remaining out of initial budget set.
     *
     * @return String representing the float percentage of remaining budget
     */
    public String displayPercentage() {
        Amount remainingAmount = this.initialAmount.subtractAmount(this.amount);
        double percentage = remainingAmount.divideAmount(this.initialAmount) * 100;
        if (percentage < 0.00) {
            percentage = 0.0; // should not display a negative percentage
        } else if (percentage > 100.00) {
            percentage = 100.0; // should not display a percentage greater than 100%
        }
        return String.format("%.2f%% remaining", percentage);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Budget) {
            Budget inObj = (Budget) obj;
            return initialAmount.equals(inObj.initialAmount)
                && deadline.equals(inObj.deadline)
                && categories.equals(inObj.categories)
                && valid == inObj.valid;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format("$%s by %s", this.amount.toString(), this.deadline.toString());
    }

    public String toLabelText() {
        return String.format("$%s by %s", this.getInitialBudget(), this.deadline.toString());
    }

    @Override
    public int compareTo(Budget o) {
        return o.getInitialBudget().compareTo(this.getInitialBudget());
    }
}
