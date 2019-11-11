package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.category.Category;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Budget;
import seedu.address.model.util.Date;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Budget objects.
 */
public class BudgetBuilder {

    public static final String DEFAULT_AMOUNT = "1";
    public static final String DEFAULT_DATE = "10102019";

    private Amount amount;
    private Date date;
    private Set<Category> categories;

    public BudgetBuilder() {
        amount = new Amount(Double.parseDouble(DEFAULT_AMOUNT));
        date = new Date(DEFAULT_DATE);
        categories = new HashSet<>();
    }

    /**
     * Initializes the BudgetBuilder with the data of {@code budgetToCopy}.
     */
    public BudgetBuilder(Budget budgetToCopy) {
        amount = budgetToCopy.getBudget();
        date = budgetToCopy.getDeadline();
        categories = new HashSet<>(budgetToCopy.getCategories());
    }

    /**
     * Sets the {@code amount} of the {@code Budget} that we are building.
     */
    public BudgetBuilder withAmount(String amount) {
        this.amount = new Amount(Double.parseDouble(amount));
        return this;
    }

    /**
     * Parses the {@code categories} into a {@code Set<Category>}
     * and set it to the {@code Budget} that we are building.
     */
    public BudgetBuilder withCategories(String... categories) {
        this.categories = SampleDataUtil.getCategorySet(categories);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Budget} that we are building.
     */
    public BudgetBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    public Budget build() {
        return new Budget(amount, date, categories);
    }
}
