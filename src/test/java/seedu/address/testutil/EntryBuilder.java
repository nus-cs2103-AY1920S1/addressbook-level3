package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Amount;
import seedu.address.model.person.Description;
import seedu.address.model.person.Entry;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Entry objects.
 */
public class EntryBuilder {

    public static final String DEFAULT_DESCRIPTION = "Alice Pauline";
    public static final double DEFAULT_AMOUNT = 5.60;

    private Description desc;
    private Amount amt;
    private Set<Tag> tags;

    public EntryBuilder() {
        desc = new Description(DEFAULT_DESCRIPTION);
        amt = new Amount(DEFAULT_AMOUNT);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public EntryBuilder(Entry entryToCopy) {
        desc = entryToCopy.getDesc();
        amt = entryToCopy.getAmount();
        tags = new HashSet<>(entryToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public EntryBuilder withDesc(String desc) {
        this.desc = new Description(desc);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public EntryBuilder withAmt(double amt) {
        this.amt = new Amount(amt);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public EntryBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public Entry build() {
        return new Entry(desc, amt, tags);
    }

}
