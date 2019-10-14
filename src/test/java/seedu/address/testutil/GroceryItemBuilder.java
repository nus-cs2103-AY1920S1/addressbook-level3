package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.food.Amount;
import seedu.address.model.food.ExpiryDate;
import seedu.address.model.food.GroceryItem;
import seedu.address.model.food.Name;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class GroceryItemBuilder {
    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_AMOUNT = "300g";
    public static final String DEFAULT_EXPIRY_DATE = "10.08.2019";

    private Name name;
    private Amount amount;
    private ExpiryDate expiryDate;
    private Set<Tag> tags;

    public GroceryItemBuilder() {
        name = new Name(DEFAULT_NAME);
        amount = new Amount(DEFAULT_AMOUNT);
        expiryDate = new ExpiryDate(DEFAULT_EXPIRY_DATE);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public GroceryItemBuilder(GroceryItem foodToCopy) {
        name = foodToCopy.getName();
        tags = new HashSet<>(foodToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public GroceryItemBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public GroceryItemBuilder withAmount(String amount) {
        this.amount = new Amount(amount);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public GroceryItemBuilder withExpiryDate(String expiryDate) {
        this.expiryDate = new ExpiryDate(expiryDate);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public GroceryItemBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public GroceryItem build() {
        return new GroceryItem(name, amount, expiryDate, tags);
    }

}
