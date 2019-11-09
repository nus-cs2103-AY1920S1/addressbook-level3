package seedu.guilttrip.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.guilttrip.model.entry.Amount;
import seedu.guilttrip.model.entry.Budget;
import seedu.guilttrip.model.entry.BudgetAmount;
import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.model.entry.Date;
import seedu.guilttrip.model.entry.Description;
import seedu.guilttrip.model.entry.Period;
import seedu.guilttrip.model.tag.Tag;
import seedu.guilttrip.model.util.SampleDataUtil;

/**
 * A utility class containing a list of {@code Budget} objects to be used in tests.
 */
public class BudgetBuilder {

    public static final String DEFAULT_CATEGORY = "Food";
    public static final String DEFAULT_DESCRIPTION = "november budget";
    public static final String DEFAULT_SPENT_AMOUNT = "0";
    public static final String DEFAULT_TOTAL_AMOUNT = "200";
    public static final String DEFAULT_DATE = "2019 11 01";
    public static final String DEFAULT_PERIOD = "1m";

    private Category cat;
    private Description desc;
    private Amount totalAmt;
    private BudgetAmount spentAmt;
    private Date date;
    private Period period;
    private Set<Tag> tags;

    public BudgetBuilder() {
        cat = new Category(DEFAULT_CATEGORY, "Expense");
        desc = new Description(DEFAULT_DESCRIPTION);
        spentAmt = new BudgetAmount(DEFAULT_SPENT_AMOUNT);
        totalAmt = new Amount(DEFAULT_TOTAL_AMOUNT);
        date = new Date(DEFAULT_DATE);
        period = new Period(DEFAULT_PERIOD);
        tags = new HashSet<>();
    }

    /**
     * Initializes the BudgetBuilder with the data of {@code budgetToCopy}.
     */
    public BudgetBuilder(Budget budgetToCopy) {
        desc = budgetToCopy.getDesc();
        spentAmt = budgetToCopy.getSpent();
        totalAmt = budgetToCopy.getAmount();
        date = budgetToCopy.getDate();
        period = budgetToCopy.getPeriod();
        cat = budgetToCopy.getCategory();
        tags = new HashSet<>(budgetToCopy.getTags());
    }

    /**
     * Sets the {@code description} of the {@code Budget} that we are building.
     */
    public BudgetBuilder withDesc(String desc) {
        this.desc = new Description(desc);
        return this;
    }

    /**
     * Sets the {@code category name} of the {@code Budget} that we are building.
     */
    public BudgetBuilder withCategory(String catName) {
        this.cat = new Category(catName, "Expense");
        return this;
    }

    /**
     * Sets the {@code date} of the {@code Budget} that we are building.
     */
    public BudgetBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code total amount} of the {@code Budget} that we are building.
     */
    public BudgetBuilder withTotalAmt(double amt) {
        this.totalAmt = new Amount(Double.toString(amt));
        return this;
    }

    /**
     * Sets the {@code spent amount} of the {@code Budget} that we are building.
     */
    public BudgetBuilder withSpentAmt(double amt) {
        this.spentAmt = new BudgetAmount(Double.toString(amt));
        return this;
    }

    /**
     * Sets the {@code period} of the {@code Budget} that we are building.
     */
    public BudgetBuilder withPeriod(String period) {
        this.period = new Period(period);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Budget} that we are building.
     */
    public BudgetBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public Budget build() {
        return new Budget(cat, desc, date, period, totalAmt, tags, spentAmt);
    }

}
