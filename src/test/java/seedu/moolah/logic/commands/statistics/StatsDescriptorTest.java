package seedu.moolah.logic.commands.statistics;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EARLIER_TIMESTAMP;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_LATER_TIMESTAMP;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_STATS_DESCRIPTOR;

import org.junit.jupiter.api.Test;

import seedu.moolah.testutil.StatsDescriptorBuilder;

class StatsDescriptorTest {

//    static {
//        VALID_STATS_DESCRIPTOR = new StatsDescriptorBuilder().withDescription(VALID_EXPENSE_DESCRIPTION_CHICKEN)
//                .withPrice(VALID_EXPENSE_PRICE_CHICKEN)
//                .withTimestamp(VALID_EXPENSE_TIMESTAMP_CHICKEN)
//                .withCategory(VALID_EXPENSE_CATEGORY_TAXI).build();
//    }



//    public static final Expense VALID_EXPENSE = new ExpenseBuilder().build();
//    public static final ObservableList<Expense> VALID_EXPENSE_LIST =
//            FXCollections.observableList(List.of(VALID_EXPENSE));

    /*
    public static final String EMPTY_STRING_COMMAND_WORD = "";
    public static final String WHITE_SPACE_COMMAND_WORD = " ";
    public static final String INVALID_COMMAND_WORD = "random";
     */
//    public static final Budget VALID_BUDGET = new BudgetBuilder().build();
//    public static final Budget INVALID_BUDGET = null;


//    public static final Timestamp VALID_EARLY_TIMESTAMP = Timestamp.createTimestampIfValid("14-01-2019").get();
//    public static final Timestamp VALID_LATE_TIMESTAMP = Timestamp.createTimestampIfValid("16-09-2019").get();
//    public static final Timestamp INVALID_TIMESTAMP = null;

    @Test
    public void equals() {
        // same values -> returns true
        StatsDescriptor descriptorWithSameValues = new StatsDescriptor(VALID_STATS_DESCRIPTOR);
        assertTrue(VALID_STATS_DESCRIPTOR.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(VALID_STATS_DESCRIPTOR.equals(VALID_STATS_DESCRIPTOR));

        // null -> returns false
        assertFalse(VALID_STATS_DESCRIPTOR.equals(null));

        // different types -> returns false
        assertFalse(VALID_STATS_DESCRIPTOR.equals(5));

        // different start date -> returns false
        StatsDescriptor editedStatsDescriptor = new StatsDescriptorBuilder(VALID_STATS_DESCRIPTOR)
                .withStartDate(VALID_EARLIER_TIMESTAMP).build();
        assertFalse(VALID_STATS_DESCRIPTOR.equals(editedStatsDescriptor));

        // different end date -> returns false
        editedStatsDescriptor = new StatsDescriptorBuilder(VALID_STATS_DESCRIPTOR).withEndDate(VALID_LATER_TIMESTAMP).build();
        assertFalse(VALID_STATS_DESCRIPTOR.equals(editedStatsDescriptor));

    }
}
