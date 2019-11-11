package seedu.guilttrip.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.guilttrip.model.entry.Amount;
import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.model.entry.Date;
import seedu.guilttrip.model.entry.Description;
import seedu.guilttrip.model.entry.Entry;
import seedu.guilttrip.model.tag.Tag;
import seedu.guilttrip.model.util.CategoryType;
import seedu.guilttrip.model.util.SampleDataUtil;

/**
 * A utility class to help with building Entry objects.
 */
public class EntryBuilder {

    public static final String DEFAULT_CATEGORY = "FOOD";
    public static final String DEFAULT_DESCRIPTION = "Mala from deck";
    public static final String DEFAULT_AMOUNT = "5.60";
    public static final String DEFAULT_DATE = "2019-09-09";

    private Category cat;
    private Description desc;
    private Amount amt;
    private Date date;
    private Set<Tag> tags;

    public EntryBuilder() {
        cat = new Category(DEFAULT_CATEGORY, CategoryType.EXPENSE);
        desc = new Description(DEFAULT_DESCRIPTION);
        amt = new Amount(DEFAULT_AMOUNT);
        date = new Date(DEFAULT_DATE);
        tags = new HashSet<>();
    }

    /**
     * Initializes the EntryBuilder with the data of {@code entryToCopy}.
     */
    public EntryBuilder(Entry entryToCopy) {
        desc = entryToCopy.getDesc();
        amt = entryToCopy.getAmount();
        date = entryToCopy.getDate();
        cat = entryToCopy.getCategory();
        tags = new HashSet<>(entryToCopy.getTags());
    }

    /**
     * Sets the {@code desc} of the {@code Entry} that we are building.
     */
    public EntryBuilder withDesc(String desc) {
        this.desc = new Description(desc);
        return this;
    }

    /**
     * Sets the {@code date} of the {@code Entry} that we are building.
     */
    public EntryBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code amt} of the {@code Entry} that we are building.
     */
    public EntryBuilder withAmt(double amt) {
        this.amt = new Amount(Double.toString(amt));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Entry} that we are building.
     */
    public EntryBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public Entry build() {
        return new Entry(cat, desc, date, amt, tags);
    }

}
