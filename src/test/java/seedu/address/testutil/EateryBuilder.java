package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.eatery.Address;
import seedu.address.model.eatery.Category;
import seedu.address.model.eatery.Eatery;
import seedu.address.model.eatery.Name;
import seedu.address.model.eatery.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Eatery objects.
 */
public class EateryBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_CATEGORY = "Chinese";

    private Name name;
    private boolean isOpen;
    private Address address;
    private Category category;
    private Set<Tag> tags;

    public EateryBuilder() {
        name = new Name(DEFAULT_NAME);
        isOpen = true;
        address = new Address(DEFAULT_ADDRESS);
        category = new Category(DEFAULT_CATEGORY);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the EateryBuilder with the data of {@code eateryToCopy}.
     */
    public EateryBuilder(Eatery eateryToCopy) {
        name = eateryToCopy.getName();
        isOpen = eateryToCopy.getIsOpen();
        address = eateryToCopy.getAddress();
        category = eateryToCopy.getCategory();
        tags = new HashSet<>(eateryToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Eatery} that we are building.
     */
    public EateryBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
<<<<<<< HEAD
     * Sets the {@code isOpen} of the {@code Eatery} that we are building.
     */
    public EateryBuilder withIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
        return this;
    }

    /**
=======
>>>>>>> upstream/master
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Eatery} that we are building.
     */
    public EateryBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Eatery} that we are building.
     */
    public EateryBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Category} of the {@code Eatery} that we are building.
     */
    public EateryBuilder withCategory(String category) {
        this.category = new Category(category);
        return this;
    }

    public Eatery build() {
        return new Eatery(name, isOpen, address, category, tags);
    }
}
