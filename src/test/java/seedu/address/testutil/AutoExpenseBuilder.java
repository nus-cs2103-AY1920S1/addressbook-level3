package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Amount;
import seedu.address.model.person.AutoExpense;
import seedu.address.model.person.Category;
import seedu.address.model.person.Date;
import seedu.address.model.person.Description;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.Frequency;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building AutoExpense objects.
 */
public class AutoExpenseBuilder {

    public static final String DEFAULT_DESCRIPTION = "Alice Pauline";
    public static final double DEFAULT_AMOUNT = 5.60;
    public static final String DEFAULT_TIME = "2019-09-09";
    public static final String DEFAULT_FREQ = "weekly";
    public static final String DEFAULT_CATEGORY = "Foods and Drinks";

    private Category category;
    private Description desc;
    private Amount amt;
    private Date date;
    private Set<Tag> tags;
    private Frequency freq;

    public AutoExpenseBuilder() {
        category = new Category(DEFAULT_CATEGORY, "Expense");
        desc = new Description(DEFAULT_DESCRIPTION);
        amt = new Amount(DEFAULT_AMOUNT);
        date = new Date(DEFAULT_TIME);
        tags = new HashSet<>();
        freq = Frequency.parse(DEFAULT_FREQ);
    }

    /**
     * Initializes the AutoExpenseBuilder with the data of {@code AutoExpenseToCopy}.
     */
    public AutoExpenseBuilder(AutoExpense autoExpenseToCopy) {
        category = autoExpenseToCopy.getCategory();
        desc = autoExpenseToCopy.getDesc();
        amt = autoExpenseToCopy.getAmount();
        date = autoExpenseToCopy.getDate();
        tags = new HashSet<>(autoExpenseToCopy.getTags());
        freq = autoExpenseToCopy.getFrequency();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public AutoExpenseBuilder withDesc(String desc) {
        this.desc = new Description(desc);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public AutoExpenseBuilder withTime(String time) {
        this.date = new Date(time);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public AutoExpenseBuilder withAmt(double amt) {
        this.amt = new Amount(amt);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public AutoExpenseBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Freq} of the {@code AutoExpense} that we are building.
     */
    public AutoExpenseBuilder withFreq(String freqString) {
        this.freq = Frequency.parse(freqString);
        return this;
    }

    /**
     * Sets the {@code Category} of the {@code AutoExpense} that we are building.
     */
    public AutoExpenseBuilder withCategory(String categoryString) {
        this.category = new Category(categoryString, "Expense");
        return this;
    }

    public AutoExpense build() {
        return new AutoExpense(category, desc, amt, tags, freq, date);
    }
}
