package seedu.moolah.testutil;

import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EARLY_TIMESTAMP;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_LATE_TIMESTAMP;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.moolah.model.expense.Expense;
import seedu.moolah.model.general.Category;
import seedu.moolah.model.general.Timestamp;
import seedu.moolah.model.statistics.PieChartStatistics;


//currently not in use even though will be good for OOP, may delete later
/**
 * A utility class to help with building PieChartStatistics objects.
 */
public class PieChartStatisticsBuilder {

    public static final String DEFAULT_START_DATE = VALID_EARLY_TIMESTAMP;

    public static final String DEFAULT_END_DATE = VALID_LATE_TIMESTAMP;
    //public static final String DEFAULT_EXPENSE_BUDGET = "Default Budget";

    private Timestamp startDate;

    private Timestamp endDate;

    private ObservableList<Expense> expenses;

    //after population

    private String title;

    private List<String> formattedCategories;

    private List<Double> formattedPercentages;

    private List<Category> budgetCategories;

    /**
     * Initializes the PieChartStatisticsBuilder to its unpopulated state
     */
    public PieChartStatisticsBuilder() {
        startDate = Timestamp.createTimestampIfValid(DEFAULT_START_DATE).get();
        endDate = Timestamp.createTimestampIfValid(DEFAULT_END_DATE).get();
        //by right must use budget, but I try to reduce the dependency
        expenses = FXCollections.observableArrayList(new ExpenseBuilder().build());
        //not sure about correctness because at default budget
        //while I don't really care about where the expenses come from, ideally is budget
    }

    /*
    void calculateStats_validInputForStats_success() {



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

        description = new Description(DEFAULT_EXPENSE_DESCRIPTION);
        price = new Price(DEFAULT_EXPENSE_PRICE);
        category = new Category(DEFAULT_EXPENSE_CATEGORY);
        uniqueIdentifier = new UniqueIdentifier(DEFAULT_EXPENSE_UNIQUE_IDENTIFIER);
        timestamp = Timestamp.createTimestampIfValid(DEFAULT_EXPENSE_TIMESTAMP).get();
        budgetName = new Description(DEFAULT_EXPENSE_BUDGET);
    }

     */



    /**
     * Sets the {@code Description} of the {@code Expense} that we are building.
     */
    public PieChartStatisticsBuilder withStartDate(String startDate) {
        this.startDate = Timestamp.createTimestampIfValid(startDate).get();
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Expense} that we are building.
     */
    public PieChartStatisticsBuilder withEndDate(String endDate) {
        this.endDate = Timestamp.createTimestampIfValid(endDate).get();
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Expense} that we are building.
     */
    public PieChartStatisticsBuilder withExpenses(ObservableList<Expense> expenses) {
        this.expenses = expenses;
        return this;
    }


    public PieChartStatistics build() {
        return new PieChartStatistics(expenses, startDate, endDate);
    }

}
