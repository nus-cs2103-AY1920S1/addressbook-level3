package seedu.eatme.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.eatme.model.eatery.Address;
import seedu.eatme.model.eatery.Eatery;
import seedu.eatme.model.eatery.Name;
import seedu.eatme.model.eatery.Tag;
import seedu.eatme.model.util.SampleDataUtil;

/**
 * A utility class to help with building Eatery objects.
 */
public class TodoEateryBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private boolean isOpen;
    private Address address;
    private Set<Tag> tags;

    public TodoEateryBuilder() {
        name = new Name(DEFAULT_NAME);
        isOpen = true;
        address = new Address(DEFAULT_ADDRESS);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the EateryBuilder with the data of {@code eateryToCopy}.
     */
    public TodoEateryBuilder(Eatery eateryToCopy) {
        name = eateryToCopy.getName();
        isOpen = eateryToCopy.getIsOpen();
        address = eateryToCopy.getAddress();
        tags = new HashSet<>(eateryToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Eatery} that we are building.
     */
    public TodoEateryBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Eatery} that we are building.
     */
    public TodoEateryBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Eatery} that we are building.
     */
    public TodoEateryBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    public Eatery build() {
        return new Eatery(name, address, tags);
    }
}
