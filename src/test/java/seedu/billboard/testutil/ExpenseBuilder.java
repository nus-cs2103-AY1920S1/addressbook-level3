package seedu.billboard.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.billboard.model.expense.Amount;
import seedu.billboard.model.expense.CreatedDateTime;
import seedu.billboard.model.expense.Description;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.expense.Name;
import seedu.billboard.model.tag.Tag;
import seedu.billboard.model.util.SampleDataUtil;

/**
 * A utility class to help with building Expense objects.
 */
public class ExpenseBuilder {

    public static final String DEFAULT_NAME = "pay school fees";
    public static final String DEFAULT_DESCRIPTION = "this is a description.";
    public static final String DEFAULT_AMOUNT = "4.20";
    public static final String DEFAULT_DATE = "1/1/2019 1234";
    public static final String DEFAULT_ARCHIVE_NAME = "";

    private Name name;
    private Description description;
    private Amount amount;
    private CreatedDateTime created;
    private Set<Tag> tags;
    private String archiveName;

    public ExpenseBuilder() {
        name = new Name(DEFAULT_NAME);
        description = new Description(DEFAULT_DESCRIPTION);
        amount = new Amount(DEFAULT_AMOUNT);
        created = new CreatedDateTime(DEFAULT_DATE);
        tags = new HashSet<>();
        archiveName = DEFAULT_ARCHIVE_NAME;
    }

    /**
     * Initializes the ExpenseBuilder with the data of {@code expenseToCopy}.
     */
    public ExpenseBuilder(Expense expenseToCopy) {
        name = expenseToCopy.getName();
        description = expenseToCopy.getDescription();
        amount = expenseToCopy.getAmount();
        created = expenseToCopy.getCreated();
        tags = new HashSet<>(expenseToCopy.getTags());
        archiveName = expenseToCopy.getArchiveName();
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
     * Parses the {@code tags} into a {@code Set<Tag>} and merge it with the existing tag set to create a new
     * set to set it to the {@code Expense} that we are building
     */
    public ExpenseBuilder withExistingTags(String ... tags) {
        Set<Tag> toSet = new HashSet<>();
        Set<Tag> toAdd = SampleDataUtil.getTagSet(tags);
        toSet.addAll(this.tags);
        toSet.addAll(toAdd);
        this.tags = toSet;
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Expense} that we are building.
     */
    public ExpenseBuilder withDescription(String description) {
        this.description = new Description(description);
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
     * Sets the {@code CreatedDateTime} of the {@code Expense} that we are building.
     */
    public ExpenseBuilder withCreatedDateTime(String createdDateTime) {
        this.created = new CreatedDateTime(createdDateTime);
        return this;
    }

    /**
     * Sets the {@code Archive} of the {@code Expense} that we are building.
     */
    public ExpenseBuilder withArchiveName(String archiveName) {
        this.archiveName = archiveName;
        return this;
    }

    public Expense build() {
        return new Expense(name, description, amount, created, tags, archiveName);
    }

}
