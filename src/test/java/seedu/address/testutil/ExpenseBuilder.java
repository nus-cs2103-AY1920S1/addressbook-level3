package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.expense.Name;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.Amount;
import seedu.address.model.expense.Date;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Expense objects.
 */
public class ExpenseBuilder {

    public static final String DEFAULT_NAME = "coffee";
    public static final String DEFAULT_AMOUNT = "$23.50";
    public static final String DEFAULT_DATE = "13/10/2019";

    private Name name;
    private Amount amount;
    private Date date;
    private Set<Tag> tags;

    public ExpenseBuilder() {
        name = new Name(DEFAULT_NAME);
        amount = new Amount(DEFAULT_AMOUNT);
        date = new Date(DEFAULT_DATE);
        tags = new HashSet<>();
    }

    /**
     * Initializes the ExpenseBuilder with the data of {@code expenseToCopy}.
     */
    public ExpenseBuilder(Expense expenseToCopy) {
        name = expenseToCopy.getName();
        amount = expenseToCopy.getAmount();
        date = expenseToCopy.getDate();
        tags = new HashSet<>(expenseToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Expense} that we are building.
     */
    public ExpenseBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Expense} that we are building.
     */
    public ExpenseBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code Expense} that we are building.
     */
    public ExpenseBuilder withAmount(String amount) {
        this.amount = new Amount(amount);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Expense} that we are building.
     */
    public ExpenseBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    public Expense build() {
        return new Expense(name, amount, date, tags);
    }

}
