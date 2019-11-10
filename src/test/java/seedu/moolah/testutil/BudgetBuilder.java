package seedu.moolah.testutil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.moolah.logic.parser.ParserUtil;
import seedu.moolah.logic.parser.exceptions.ParseException;
import seedu.moolah.model.budget.Budget;
import seedu.moolah.model.budget.BudgetPeriod;
import seedu.moolah.model.expense.Description;
import seedu.moolah.model.expense.Expense;
import seedu.moolah.model.expense.Price;
import seedu.moolah.model.expense.Timestamp;


/**
 * A utility class to help with building Budget objects.
 */
public class BudgetBuilder {

    public static final String DEFAULT_DESCRIPTION = "Default Budget";
    public static final String DEFAULT_AMOUNT = "1000000000000000000000";
    public static final String DEFAULT_START_DATE = "01-01-2000";
    public static final String DEFAULT_PERIOD = "infinity";

    private Description description;
    private Price amount;
    private Timestamp startDate;
    private Timestamp endDate;
    private BudgetPeriod period;
    private ObservableList<Expense> expenses;
    private boolean isPrimary;

    public BudgetBuilder() {
        try {
            description = new Description(DEFAULT_DESCRIPTION);
            amount = new Price(DEFAULT_AMOUNT);
            expenses = FXCollections.observableArrayList();
            startDate = Timestamp.createTimestampIfValid(DEFAULT_START_DATE).get();
            period = ParserUtil.parsePeriod(DEFAULT_PERIOD);
            endDate = startDate.plus(period.getPeriod());
            isPrimary = false;
        } catch (ParseException e) {
            //shouldn't have an exception
        }
    }

    /**
     * Initializes the BudgetBuilder with the data of {@code budgetToCopy}.
     */
    public BudgetBuilder(Budget budgetToCopy) {
        description = budgetToCopy.getDescription();
        amount = budgetToCopy.getAmount();
        expenses = FXCollections.observableArrayList(budgetToCopy.getExpenses());
        startDate = budgetToCopy.getWindowStartDate();
        endDate = budgetToCopy.getWindowEndDate();
        period = budgetToCopy.getBudgetPeriod();
        isPrimary = budgetToCopy.isPrimary();
    }

    /**
     * Sets the {@code Description} of the {@code Budget} that we are building.
     */
    public BudgetBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Set {@code List<Expense>} to the {@code Budget} that we are building.
     */
    public BudgetBuilder withExpenses(ObservableList<Expense> expenses) {
        this.expenses = expenses;
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code Budget} that we are building.
     */
    public BudgetBuilder withAmount(String amount) {
        this.amount = new Price(amount);
        return this;
    }

    /**
     * Sets the {@code StartDate} of the {@code Budget} that we are building.
     */
    public BudgetBuilder withStartDate(String startDate) {
        this.startDate = Timestamp.createTimestampIfValid(startDate).get();
        return this;
    }

    /**
     * Sets the {@code EndDate} of the {@code Budget} that we are building.
     */
    public BudgetBuilder withEndDate(String endDate) {
        this.endDate = Timestamp.createTimestampIfValid(endDate).get();
        return this;
    }

    /**
     * Sets the {@code Period} of the {@code Budget} that we are building.
     */
    public BudgetBuilder withPeriod(String period) {
        try {
            this.period = ParserUtil.parsePeriod(period);
        } catch (ParseException e) {
            //shouldn't have an exception
        }
        return this;
    }

    /**
     * Sets the {@code IsPrimary Flag} of the {@code Budget} that we are building.
     */
    public BudgetBuilder withIsPrimary(boolean isPrimary) {
        this.isPrimary = isPrimary;
        return this;
    }

    public Budget build() {
        return new Budget(description, amount, startDate, period, expenses, isPrimary);
    }

}
