package seedu.billboard.testutil;

import static seedu.billboard.testutil.TypicalExpenses.TAXES;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.billboard.commons.core.date.DateInterval;
import seedu.billboard.model.Billboard;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.recurrence.Recurrence;


/**
 * A utility class containing a list of {@code Expense} objects to be used in tests.
 */
public class TypicalRecurrences {

    //Mnually added - for recurrence testing
    public static final Expense BILL1 = new ExpenseBuilder().withName("bill")
            .withDescription("pay bill")
            .withAmount("10.0")
            .withCreatedDateTime("1/1/2019 0000")
            .build();
    public static final Expense BILL2 = new ExpenseBuilder().withName("bill")
            .withDescription("pay bill")
            .withAmount("10.0")
            .withCreatedDateTime("8/3/2019 0000")
            .build();
    public static final Expense BILL3 = new ExpenseBuilder().withName("bill")
            .withDescription("pay bill")
            .withAmount("10.0")
            .withCreatedDateTime("15/1/2019 0000")
            .build();
    public static final Expense BILL4 = new ExpenseBuilder().withName("bill")
            .withDescription("pay bill")
            .withAmount("10.0")
            .withCreatedDateTime("22/1/2019 0000")
            .build();
    public static final Expense BILL5 = new ExpenseBuilder().withName("bill")
            .withDescription("pay bill")
            .withAmount("10.0")
            .withCreatedDateTime("29/1/2019 0000")
            .build();
    public static final DateInterval BILL_INTERVAL = DateInterval.WEEK;

    public static final Recurrence RECUR_BILLS = new RecurrenceBuilder()
            .withExpenses(getTypicalRecurrenceExpenses(), BILL_INTERVAL).build();

    public static final Recurrence RECUR_TAXES = new RecurrenceBuilder()
            .withExpenses(Arrays.asList(TAXES), DateInterval.WEEK).build();

    private TypicalRecurrences() {} // prevents instantiation

    /**
     * Returns an {@code Billboard} with all the typical persons with Recurrences.
     */
    public static Billboard getTypicalBillboardWithRecurrenceExpenses() {
        Billboard bb = new Billboard();
        for (Expense expense : getTypicalRecurrenceExpenses()) {
            bb.addExpense(expense);
        }
        for (Recurrence recurrence : getTypicalRecurrences()) {
            bb.addRecurrence(recurrence);
        }
        return bb;
    }

    public static List<Recurrence> getTypicalRecurrences() {
        return new ArrayList<>(Arrays.asList(RECUR_BILLS));
    }

    public static List<Expense> getTypicalRecurrenceExpenses() {
        return new ArrayList<>(Arrays.asList(BILL1, BILL2, BILL3, BILL4, BILL5));
    }
}
