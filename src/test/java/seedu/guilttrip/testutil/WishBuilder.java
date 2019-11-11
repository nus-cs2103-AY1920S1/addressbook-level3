package seedu.guilttrip.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.guilttrip.model.entry.Amount;
import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.model.entry.Date;
import seedu.guilttrip.model.entry.Description;
import seedu.guilttrip.model.entry.Wish;
import seedu.guilttrip.model.tag.Tag;
import seedu.guilttrip.model.util.CategoryType;
import seedu.guilttrip.model.util.SampleDataUtil;

/**
 * A utility class to help with building Wish objects.
 */
public class WishBuilder {

    public static final String DEFAULT_CATEGORY = "Shopping";
    public static final String DEFAULT_DESCRIPTION = "clothes";
    public static final String DEFAULT_AMOUNT = "56";
    public static final String DEFAULT_DATE = "2019 11 09";

    private Category cat;
    private Description desc;
    private Amount amt;
    private Date date;
    private Set<Tag> tags;

    public WishBuilder() {
        cat = new Category(DEFAULT_CATEGORY, CategoryType.EXPENSE);
        desc = new Description(DEFAULT_DESCRIPTION);
        amt = new Amount(DEFAULT_AMOUNT);
        date = new Date(DEFAULT_DATE);
        tags = new HashSet<>();
    }

    /**
     * Initializes the WishBuilder with the data of {@code wishToCopy}.
     */
    public WishBuilder(Wish wishToCopy) {
        desc = wishToCopy.getDesc();
        amt = wishToCopy.getAmount();
        date = wishToCopy.getDate();
        cat = wishToCopy.getCategory();
        tags = new HashSet<>(wishToCopy.getTags());
    }

    /**
     * Sets the {@code desc} of the {@code Wish} that we are building.
     */
    public WishBuilder withDesc(String desc) {
        this.desc = new Description(desc);
        return this;
    }

    /**
     * Sets the {@code date} of the {@code Wish} that we are building.
     */
    public WishBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code amt} of the {@code Wish} that we are building.
     */
    public WishBuilder withAmt(double amt) {
        this.amt = new Amount(Double.toString(amt));
        return this;
    }

    /**
     * Sets the {@code catName} of the {@code Wish} that we are building.
     */
    public WishBuilder withCategory(String catName) {
        this.cat = new Category(catName, CategoryType.EXPENSE);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Wish} that we are building.
     */
    public WishBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public Wish build() {
        return new Wish(cat, desc, date, amt, tags);
    }

}
