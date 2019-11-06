package seedu.moolah.model.statistics;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moolah.testutil.Assert.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.moolah.logic.commands.statistics.StatsCommand;
import seedu.moolah.logic.commands.statistics.StatsCompareCommand;
import seedu.moolah.logic.commands.statistics.StatsTrendCommand;
import seedu.moolah.model.budget.Budget;
import seedu.moolah.model.expense.Category;
import seedu.moolah.model.expense.Expense;
import seedu.moolah.model.expense.Timestamp;
import seedu.moolah.testutil.BudgetBuilder;
import seedu.moolah.testutil.ExpenseBuilder;

class StatisticsTest {


    public static final Expense VALID_EXPENSE = new ExpenseBuilder().build();
    public static final ObservableList<Expense> VALID_EXPENSE_LIST =
            FXCollections.observableList(List.of(VALID_EXPENSE));

    /*
    public static final String EMPTY_STRING_COMMAND_WORD = "";
    public static final String WHITE_SPACE_COMMAND_WORD = " ";
    public static final String INVALID_COMMAND_WORD = "random";
     */
    public static final Budget VALID_BUDGET = new BudgetBuilder().build();
    public static final Budget INVALID_BUDGET = null;
    public static final Timestamp VALID_EARLY_TIMESTAMP = Timestamp.createTimestampIfValid("14-01-2019").get();
    public static final Timestamp VALID_LATE_TIMESTAMP = Timestamp.createTimestampIfValid("16-09-2019").get();
    public static final Timestamp INVALID_TIMESTAMP = null;



    @Test
    public void constructor_nullParameters_throwsNullPointerException() {

        List<Category> categories = Category.getValidCategories();

        assertThrows(NullPointerException.class, () -> new Statistics(VALID_EXPENSE_LIST, null));

        assertThrows(NullPointerException.class, () -> new Statistics(null, categories));

        //assertThrows(NullPointerException.class, () -> new Statistics(EMPTY_EXPENSE_LIST, categories));

    }



    /*
    @Test
    void calculateStats_validInputForStats_success() {

        ObservableList<Expense> VALID_EXPENSE_LIST = FXCollections.observableArrayList();
        VALID_EXPENSE_LIST.add(VALID_EXPENSE);
        String commandWord = StatsCommand.COMMAND_WORD;

        assertDoesNotThrow(() -> Statistics.calculateStats(VALID_EXPENSE_LIST, commandWord,
                VALID_EARLY_TIMESTAMP, VALID_LATE_TIMESTAMP, VALID_BUDGET, true));
        assertDoesNotThrow(() -> Statistics.calculateStats(VALID_EXPENSE_LIST, commandWord,
                VALID_EARLY_TIMESTAMP, VALID_EARLY_TIMESTAMP, VALID_BUDGET, true));
        assertDoesNotThrow(() -> Statistics.calculateStats(VALID_EXPENSE_LIST, commandWord,
                VALID_LATE_TIMESTAMP, INVALID_TIMESTAMP, VALID_BUDGET, false));
        assertDoesNotThrow(() -> Statistics.calculateStats(VALID_EXPENSE_LIST, commandWord,
                INVALID_TIMESTAMP, VALID_LATE_TIMESTAMP, VALID_BUDGET, true));
        assertDoesNotThrow(() -> Statistics.calculateStats(VALID_EXPENSE_LIST, commandWord,
                INVALID_TIMESTAMP, INVALID_TIMESTAMP, VALID_BUDGET, false));
    }

     */


    @Test
    void calculateStats_validInputForStats_success() {

        String commandWord = StatsCommand.COMMAND_WORD;

        assertTrue(Statistics.calculateStats(commandWord,
                VALID_EARLY_TIMESTAMP, VALID_LATE_TIMESTAMP, VALID_BUDGET, true) instanceof PieChartStatistics);
        assertTrue(Statistics.calculateStats(commandWord,
                VALID_EARLY_TIMESTAMP, VALID_EARLY_TIMESTAMP, VALID_BUDGET, true) instanceof PieChartStatistics);
        assertTrue(Statistics.calculateStats(commandWord,
                VALID_LATE_TIMESTAMP, INVALID_TIMESTAMP, VALID_BUDGET, false) instanceof PieChartStatistics);
        assertTrue(Statistics.calculateStats(commandWord,
                INVALID_TIMESTAMP, VALID_LATE_TIMESTAMP, VALID_BUDGET, true) instanceof PieChartStatistics);
        assertTrue(Statistics.calculateStats(commandWord,
                INVALID_TIMESTAMP, INVALID_TIMESTAMP, VALID_BUDGET, false) instanceof PieChartStatistics);
    }




    @Test
    void calculateStats_validInputForStatsCompare_success() {

        String commandWord = StatsCompareCommand.COMMAND_WORD;

        assertTrue(Statistics.calculateStats(commandWord,
                VALID_EARLY_TIMESTAMP, VALID_EARLY_TIMESTAMP, VALID_BUDGET, true) instanceof TabularStatistics);
        assertTrue(Statistics.calculateStats(commandWord,
                VALID_EARLY_TIMESTAMP, VALID_LATE_TIMESTAMP, VALID_BUDGET, true) instanceof TabularStatistics);
        assertTrue(Statistics.calculateStats(commandWord,
                VALID_LATE_TIMESTAMP, VALID_EARLY_TIMESTAMP, VALID_BUDGET, false) instanceof TabularStatistics);
    }



    @Test
    void calculateStats_validInputForStatsTrend_success() {

        String commandWord = StatsTrendCommand.COMMAND_WORD;

        assertTrue(Statistics.calculateStats(commandWord,
                VALID_EARLY_TIMESTAMP, VALID_LATE_TIMESTAMP, VALID_BUDGET, true) instanceof TrendStatistics);
        assertTrue(Statistics.calculateStats(commandWord,
                VALID_EARLY_TIMESTAMP, VALID_EARLY_TIMESTAMP, VALID_BUDGET, true) instanceof TrendStatistics);
        assertTrue(Statistics.calculateStats(commandWord,
                VALID_LATE_TIMESTAMP, INVALID_TIMESTAMP, VALID_BUDGET, false) instanceof TrendStatistics);
        assertTrue(Statistics.calculateStats(commandWord,
                INVALID_TIMESTAMP, VALID_LATE_TIMESTAMP, VALID_BUDGET, true) instanceof TrendStatistics);
        assertTrue(Statistics.calculateStats(commandWord,
                INVALID_TIMESTAMP, INVALID_TIMESTAMP, VALID_BUDGET, false) instanceof TrendStatistics);
    }

    //Handled by parsing

    /*
    @Test
    void calculateStats_emptyCommandWord_throwsException() {

        ObservableList<Expense> VALID_EXPENSE_LIST = FXCollections.observableArrayList();
        VALID_EXPENSE_LIST.add(VALID_EXPENSE);

        assertThrows(NullPointerException.class, () -> Statistics.calculateStats(VALID_EXPENSE_LIST,
        EMPTY_STRING_COMMAND_WORD,
                VALID_EARLY_TIMESTAMP, VALID_LATE_TIMESTAMP, VALID_BUDGET, true));
        assertThrows(NullPointerException.class, () -> Statistics.calculateStats(VALID_EXPENSE_LIST,
        WHITE_SPACE_COMMAND_WORD,
                VALID_EARLY_TIMESTAMP, VALID_LATE_TIMESTAMP, VALID_BUDGET, true));
        assertThrows(NullPointerException.class, () -> Statistics.calculateStats(VALID_EXPENSE_LIST,
        INVALID_COMMAND_WORD,
                VALID_EARLY_TIMESTAMP, VALID_LATE_TIMESTAMP, VALID_BUDGET, true));
    }

     */


    @Test
    void calculateStats_invalidInputForStats_throwsException() {

        String commandWord = StatsCommand.COMMAND_WORD;

        assertThrows(NullPointerException.class, () -> Statistics.calculateStats(commandWord,
                VALID_EARLY_TIMESTAMP, VALID_LATE_TIMESTAMP, INVALID_BUDGET, false));
    }

    @Test
    void calculateStats_invalidInputForStatsCompare_throwsException() {

        String commandWord = StatsCompareCommand.COMMAND_WORD;

        assertThrows(NullPointerException.class, () -> Statistics.calculateStats(commandWord,
                VALID_EARLY_TIMESTAMP, VALID_LATE_TIMESTAMP, INVALID_BUDGET, false));


        assertThrows(NullPointerException.class, () -> Statistics.calculateStats(commandWord,
                INVALID_TIMESTAMP, VALID_LATE_TIMESTAMP, VALID_BUDGET, true));


        assertThrows(NullPointerException.class, () -> Statistics.calculateStats(commandWord,
                VALID_EARLY_TIMESTAMP, INVALID_TIMESTAMP, VALID_BUDGET, false));
    }

    @Test
    void calculateStats_invalidInputForStatsTrend_throwsException() {

        String commandWord = StatsTrendCommand.COMMAND_WORD;

        assertThrows(NullPointerException.class, () -> Statistics.calculateStats(commandWord,
                VALID_EARLY_TIMESTAMP, VALID_LATE_TIMESTAMP, INVALID_BUDGET, false));
    }

}
