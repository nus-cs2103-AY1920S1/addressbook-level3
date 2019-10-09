package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.expense.Description;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.Price;
import seedu.address.model.expense.UniqueIdentifier;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Expense objects.
 */
public class ExpenseBuilder {

    public static final String DEFAULT_DESCRIPTION = "Alices Birthday";
    public static final String DEFAULT_PRICE = "20";
    public static final String DEFAULT_UNIQUE_IDENTIFIER = "Expense@00000000-0000-0000-0000-000000000001";

    private Description description;
    private Price price;
    private Set<Tag> tags;
    private UniqueIdentifier uniqueIdentifier;

    public ExpenseBuilder() {
        description = new Description(DEFAULT_DESCRIPTION);
        price = new Price(DEFAULT_PRICE);
        tags = new HashSet<>();
        uniqueIdentifier = new UniqueIdentifier(DEFAULT_UNIQUE_IDENTIFIER);
    }

    /**
     * Initializes the ExpenseBuilder with the data of {@code expenseToCopy}.
     */
    public ExpenseBuilder(Expense expenseToCopy) {
        description = expenseToCopy.getDescription();
        price = expenseToCopy.getPrice();
        tags = new HashSet<>(expenseToCopy.getTags());
        uniqueIdentifier = expenseToCopy.getUniqueIdentifier();
    }

    /**
     * Sets the {@code Description} of the {@code Expense} that we are building.
     */
    public ExpenseBuilder withDescription(String description) {
        this.description = new Description(description);
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

    public Expense build() {
        return new Expense(description, price, tags, uniqueIdentifier);
    }

}
