package seedu.guilttrip.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.guilttrip.model.entry.Amount;
import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.model.entry.Date;
import seedu.guilttrip.model.entry.Description;
import seedu.guilttrip.model.entry.Expense;
import seedu.guilttrip.model.tag.Tag;
import seedu.guilttrip.model.util.CategoryType;
import seedu.guilttrip.model.util.SampleDataUtil;

/**
 * A utility class containing a list of {@code Expense} objects to be used in tests.
 */
public class ExpenseBuilder {

    public static final String DEFAULT_CATEGORY = "Food";
    public static final String DEFAULT_DESCRIPTION = "Char Siew Pau";
    public static final String DEFAULT_AMOUNT = "5.60";
    public static final String DEFAULT_TIME = "2019-10-09";

    private Category cat;
    private Description desc;
    private Amount amt;
    private Date date;
    private Set<Tag> tags;

    public ExpenseBuilder() {
        cat = new Category(DEFAULT_CATEGORY, CategoryType.EXPENSE);
        desc = new Description(DEFAULT_DESCRIPTION);
        amt = new Amount(DEFAULT_AMOUNT);
        date = new Date(DEFAULT_TIME);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public ExpenseBuilder(Expense expenseToCopy) {
        desc = expenseToCopy.getDesc();
        amt = expenseToCopy.getAmount();
        date = expenseToCopy.getDate();
        cat = expenseToCopy.getCategory();
        tags = new HashSet<>(expenseToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public ExpenseBuilder withDesc(String desc) {
        this.desc = new Description(desc);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public ExpenseBuilder withCategory(String catName) {
        this.cat = new Category(catName, CategoryType.EXPENSE);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public ExpenseBuilder withTime(String time) {
        this.date = new Date(time);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public ExpenseBuilder withAmt(double amt) {
        this.amt = new Amount(Double.toString(amt));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public ExpenseBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public Expense build() {
        return new Expense(cat, desc, date, amt, tags);
    }

}
