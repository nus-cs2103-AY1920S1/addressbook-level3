package seedu.savenus.model.savings;

import static java.util.Objects.requireNonNull;

import seedu.savenus.model.util.Money;
import seedu.savenus.model.util.TimeStamp;

//@@author fatclarence
/**
 * Acts as the amount of money to be added into the {@code SavingsAccount} of the user
 * and also the amount of money to deduct from the {@code Wallet} of the user.
 */
public class Savings {

    public static final String MESSAGE_CONSTRAINTS =
            "Please provide a savings amount with 0 or 2 decimal places.\n"
            + "For example: 1.50 or 200\n"
            + "Note that you also CANNOT save 0 or a negative amount of money!";

    // Identity fields of a saving.
    private final Money savingsAmount; // the amount to be saved.
    private final TimeStamp timeStamp;
    private final boolean isWithdraw; // required for sanity check in addToHistory in model

    public Savings(String savings, String time, boolean withdraw) {
        requireNonNull(savings);
        savingsAmount = new Money(savings);
        timeStamp = new TimeStamp(time);
        isWithdraw = withdraw;
    }

    public String getTimeStampString() {
        return timeStamp.toString();
    }

    public TimeStamp getTimeStamp() {
        return timeStamp;
    }

    public Money getSavingsAmount() {
        return this.savingsAmount;
    }

    public boolean isWithdraw() {
        return this.isWithdraw;
    }

    /**
     * Labels a Saving as a withdrawal instead of a deposit
     */
    public void makeWithdraw() {
        // Sanity check again to ensure only a withdrawal can call this.
        if (isWithdraw) {
            this.savingsAmount.negate();
        }
    }

    @Override
    public String toString() {
        return String.format("%.02f", savingsAmount.getAmount());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Savings
            && this.savingsAmount.equals(((Savings) other).savingsAmount)
            && this.getTimeStamp().getTimeStampInLocalDateTime()
                .equals(((Savings) other).timeStamp.getTimeStampInLocalDateTime()));
    }
}
