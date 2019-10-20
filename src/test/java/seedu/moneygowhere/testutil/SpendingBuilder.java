package seedu.moneygowhere.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.moneygowhere.model.spending.Cost;
import seedu.moneygowhere.model.spending.Date;
import seedu.moneygowhere.model.spending.Name;
import seedu.moneygowhere.model.spending.Remark;
import seedu.moneygowhere.model.spending.Spending;
import seedu.moneygowhere.model.tag.Tag;
import seedu.moneygowhere.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class SpendingBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_DATE = "25/12/2019";
    public static final String DEFAULT_REMARK = "Likes to play games";
    public static final String DEFAULT_COST = "123";

    private Name name;
    private Date date;
    private Remark remark;
    private Cost cost;
    private Set<Tag> tags;

    public SpendingBuilder() {
        name = new Name(DEFAULT_NAME);
        date = new Date(DEFAULT_DATE);
        cost = new Cost(DEFAULT_COST);
        remark = new Remark(DEFAULT_REMARK);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public SpendingBuilder(Spending spendingToCopy) {
        name = spendingToCopy.getName();
        date = spendingToCopy.getDate();
        cost = spendingToCopy.getCost();
        remark = spendingToCopy.getRemark();
        tags = new HashSet<>(spendingToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Spending} that we are building.
     */
    public SpendingBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Spending} that we are building.
     */
    public SpendingBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Spending} that we are building.
     */
    public SpendingBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Spending} that we are building.
     */
    public SpendingBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    /**
     * Sets the {@code Cost} of the {@code Spending} that we are building.
     */
    public SpendingBuilder withCost(String cost) {
        this.cost = new Cost(cost);
        return this;
    }

    public Spending build() {
        return new Spending(name, date, remark, cost, tags);
    }

}
