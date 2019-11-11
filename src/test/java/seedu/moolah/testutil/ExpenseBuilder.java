package seedu.moolah.testutil;

import seedu.moolah.model.general.Category;
import seedu.moolah.model.general.Description;
import seedu.moolah.model.expense.Expense;
import seedu.moolah.model.general.Price;
import seedu.moolah.model.general.Timestamp;
import seedu.moolah.model.expense.UniqueIdentifier;

/**
 * A utility class to help with building Expense objects.
 */
public class ExpenseBuilder {

    public static final String DEFAULT_EXPENSE_DESCRIPTION = "Alices Birthday";
    public static final String DEFAULT_EXPENSE_PRICE = "20";
    public static final String DEFAULT_EXPENSE_CATEGORY = "Healthcare";
    public static final String DEFAULT_EXPENSE_TIMESTAMP = "2019-12-01T12:00";
    public static final String DEFAULT_EXPENSE_BUDGET = "Default Budget";
    public static final String DEFAULT_EXPENSE_UNIQUE_IDENTIFIER = "Expense@00000000-0000-0000-0000-000000000001";

    private Description description;
    private Price price;
    private Category category;
    private UniqueIdentifier uniqueIdentifier;
    private Timestamp timestamp;
    private Description budgetName;

    public ExpenseBuilder() {
        description = new Description(DEFAULT_EXPENSE_DESCRIPTION);
        price = new Price(DEFAULT_EXPENSE_PRICE);
        category = new Category(DEFAULT_EXPENSE_CATEGORY);
        uniqueIdentifier = new UniqueIdentifier(DEFAULT_EXPENSE_UNIQUE_IDENTIFIER);
        timestamp = Timestamp.createTimestampIfValid(DEFAULT_EXPENSE_TIMESTAMP).get();
        budgetName = new Description(DEFAULT_EXPENSE_BUDGET);
    }

    /**
     * Initializes the ExpenseBuilder with the data of {@code expenseToCopy}.
     */
    public ExpenseBuilder(Expense expenseToCopy) {
        description = expenseToCopy.getDescription();
        price = expenseToCopy.getPrice();
        category = expenseToCopy.getCategory();
        uniqueIdentifier = expenseToCopy.getUniqueIdentifier();
        timestamp = expenseToCopy.getTimestamp();
        budgetName = expenseToCopy.getBudgetName();
    }

    /**
     * Sets the {@code Description} of the {@code Expense} that we are building.
     */
    public ExpenseBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code Category} of the {@code Expense} that we are building.
     */
    public ExpenseBuilder withCategory(String category) {
        this.category = new Category(category);
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code Expense} that we are building.
     */
    public ExpenseBuilder withPrice(String price) {
        this.price = new Price(price);
        return this;
    }

    /**
     * Sets the {@code UniqueIdentifier} of the {@code Expense} that we are building.
     */
    public ExpenseBuilder withUniqueIdentifier(String uniqueIdentifier) {
        this.uniqueIdentifier = new UniqueIdentifier(uniqueIdentifier);
        return this;
    }

    /**
     * Sets the {@code Timestamp} of the {@code Expense} that we are building.
     */
    public ExpenseBuilder withTimestamp(String rawTimestamp) {
        this.timestamp = Timestamp.createTimestampIfValid(rawTimestamp).get();
        return this;
    }

    /**
     * Sets the {@code Timestamp} of the {@code Expense} that we are building.
     */
    public ExpenseBuilder withBudgetName(String budgetName) {
        this.budgetName = new Description(budgetName);
        return this;
    }

    public Expense build() {
        return new Expense(description, price, category, timestamp, budgetName, uniqueIdentifier);
    }

}
