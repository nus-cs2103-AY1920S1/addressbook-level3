package seedu.address.testutil;

import java.time.Period;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.budget.Budget;
import seedu.address.model.budget.Percentage;
import seedu.address.model.expense.Description;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.Price;
import seedu.address.model.expense.Timestamp;


/**
 * A utility class to help with building Budget objects.
 */
public class BudgetBuilder {

    public static final String DEFAULT_DESCRIPTION = "default budget";
    public static final String DEFAULT_AMOUNT = "9999";
    public static final String DEFAULT_START_DATE = "01-01-2000";
    public static final String DEFAULT_PERIOD = "infinity";

    private Description description;
    private Price amount;
    private Timestamp startDate;
    private Timestamp endDate;
    private Period period;
    private ObservableList<Expense> expenses;
    private boolean isPrimary;
    private Percentage proportionUsed;

    public BudgetBuilder() {
        try {
            description = new Description(DEFAULT_DESCRIPTION);
            amount = new Price(DEFAULT_AMOUNT);
            expenses = FXCollections.observableArrayList();
            startDate = Timestamp.createTimestampIfValid(DEFAULT_START_DATE).get();
            period = ParserUtil.parsePeriod(DEFAULT_PERIOD);
            endDate = startDate.plus(period);
            isPrimary = false;
            proportionUsed = new Percentage(0);
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
        startDate = budgetToCopy.getStartDate();
        endDate = budgetToCopy.getEndDate();
        period = budgetToCopy.getPeriod();
        isPrimary = budgetToCopy.isPrimary();
        proportionUsed = budgetToCopy.getProportionUsed();
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
     * Sets the {@code PercentageUsed} of the {@code Budget} that we are building.
     */
    public BudgetBuilder withProportionUsed(Percentage proportionUsed) {
        this.proportionUsed = proportionUsed;
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
        return new Budget(description, amount, startDate, endDate, period, expenses, isPrimary, proportionUsed);
    }

}
