package seedu.guilttrip.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.guilttrip.model.entry.AutoExpense;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalAutoExpenses {

    public static final AutoExpense FOOD_AUTO_EXPENSE = new AutoExpenseBuilder().withDesc("pgp mala")
            .withDate("2019-09-09").withAmt(5.50).withTags("food").withFreq("daily").build();
    public static final AutoExpense BBT_AUTO_EXPENSE = new AutoExpenseBuilder()
            .withDesc("UTown gongcha passion fruit tea with 70% sugar").withAmt(4.38)
            .withTags("food", "luxury", "drinks").withFreq("daily").build();

    private TypicalAutoExpenses() {
    } // prevents instantiation

    public static List<AutoExpense> getTypicalAutoExpenses() {
        return new ArrayList<>(Arrays.asList(FOOD_AUTO_EXPENSE, BBT_AUTO_EXPENSE));
    }
}
