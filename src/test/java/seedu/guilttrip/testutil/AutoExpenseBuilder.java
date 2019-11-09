package seedu.guilttrip.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.guilttrip.model.entry.Amount;
import seedu.guilttrip.model.entry.AutoExpense;
import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.model.entry.Date;
import seedu.guilttrip.model.entry.Description;
import seedu.guilttrip.model.tag.Tag;
import seedu.guilttrip.model.util.Frequency;
import seedu.guilttrip.model.util.SampleDataUtil;

/**
 * A utility class to help with building AutoExpense objects.
 */
public class AutoExpenseBuilder {

    public static final String DEFAULT_DESCRIPTION = "Gongcha brown sugar milk tea with pearls";
    public static final String DEFAULT_AMOUNT = "4.50";
    public static final String DEFAULT_DATE = "2019 11 09";
    public static final String DEFAULT_FREQ = "weekly";
    public static final String DEFAULT_CATEGORY = "Food";

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
        date = new Date(DEFAULT_DATE);
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
    public AutoExpenseBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public AutoExpenseBuilder withAmt(double amt) {
        this.amt = new Amount(Double.toString(amt));
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
