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
            + "Note that you also cannot save 0 or a negative amount of money!";

    public static final String VALIDATION_REGEX = "(0|(0(\\.\\d{2,2}))|[1-9]+(\\d*(\\.\\d{2,2})?))";

    // Identity fields of a saving.
    private final Money savingsAmount; // the amount to be saved.
    private final TimeStamp timeStamp;
    private final boolean isWithdraw; // required for sanity check in addToHistory in model
    // Default starting savings amount is a deposit.
    // Constructor should never be called
    //    public Savings() {
    ////        savingsAmount = new Money("0.00");
    ////        timeStamp = new TimeStamp(TimeStamp.generateCurrentTimeStamp());
    ////        isWithdraw = false;
    ////    }

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

    /**
     * Makes the savings amount a negative value.
     * Use for Withdrawals only.
     */
    public void makeWithdraw() {
        // if it is a withdrawal, the saving should be a negative value.
        savingsAmount.negate();
    }

    public Money getSavingsAmount() {
        return this.savingsAmount;
    }

    public boolean isWithdraw() {
        return this.isWithdraw;
    }

    @Override
    public String toString() {
        return String.format("%.02f", savingsAmount.getAmount());
    }
}
