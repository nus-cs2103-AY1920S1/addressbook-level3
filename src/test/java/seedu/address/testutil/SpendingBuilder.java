package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.spending.Cost;
import seedu.address.model.spending.Email;
import seedu.address.model.spending.Name;
import seedu.address.model.spending.Phone;
import seedu.address.model.spending.Spending;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class SpendingBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_COST = "123";

    private Name name;
    private Phone phone;
    private Email email;
    private Cost cost;
    private Set<Tag> tags;

    public SpendingBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        cost = new Cost(DEFAULT_COST);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public SpendingBuilder(Spending spendingToCopy) {
        name = spendingToCopy.getName();
        phone = spendingToCopy.getPhone();
        email = spendingToCopy.getEmail();
        cost = spendingToCopy.getCost();
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
    public SpendingBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Spending} that we are building.
     */
    public SpendingBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Spending} that we are building.
     */
    public SpendingBuilder withEmail(String email) {
        this.email = new Email(email);
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
        return new Spending(name, phone, email, cost, tags);
    }

}
