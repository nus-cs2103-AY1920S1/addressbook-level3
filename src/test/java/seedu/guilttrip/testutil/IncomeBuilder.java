package seedu.guilttrip.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.guilttrip.model.entry.Amount;
import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.model.entry.Date;
import seedu.guilttrip.model.entry.Description;
import seedu.guilttrip.model.entry.Income;
import seedu.guilttrip.model.tag.Tag;
import seedu.guilttrip.model.util.SampleDataUtil;

/**
 * A utility class containing a list of {@code Income} objects to be used in tests.
 */
public class IncomeBuilder {

    public static final String DEFAULT_CATEGORY = "Salary";
    public static final String DEFAULT_DESCRIPTION = "November salary";
    public static final String DEFAULT_AMOUNT = "4000";
    public static final String DEFAULT_DATE = "2019-10-31";

    private Category cat;
    private Description desc;
    private Amount amt;
    private Date date;
    private Set<Tag> tags;

    public IncomeBuilder() {
        cat = new Category(DEFAULT_CATEGORY, "Income");
        desc = new Description(DEFAULT_DESCRIPTION);
        amt = new Amount(DEFAULT_AMOUNT);
        date = new Date(DEFAULT_DATE);
        tags = new HashSet<>();
    }

    /**
     * Initializes the IncomeBuilder with the data of {@code incomeToCopy}.
     */
    public IncomeBuilder(Income incomeToCopy) {
        desc = incomeToCopy.getDesc();
        amt = incomeToCopy.getAmount();
        date = incomeToCopy.getDate();
        cat = incomeToCopy.getCategory();
        tags = new HashSet<>(incomeToCopy.getTags());
    }

    /**
     * Sets the {@code desc} of the {@code Income} that we are building.
     */
    public IncomeBuilder withDesc(String desc) {
        this.desc = new Description(desc);
        return this;
    }

    /**
     * Sets the {@code Category} of the {@code Income} that we are building.
     */
    public IncomeBuilder withCategory(String catName) {
        this.cat = new Category(catName, "Income");
        return this;
    }

    /**
     * Sets the {@code date} of the {@code Income} that we are building.
     */
    public IncomeBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code amt} of the {@code Income} that we are building.
     */
    public IncomeBuilder withAmt(String amt) {
        this.amt = new Amount(amt);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public IncomeBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public Income build() {
        return new Income(cat, desc, date, amt, tags);
    }

}
