package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import seedu.address.model.phone.Brand;
import seedu.address.model.phone.Capacity;
import seedu.address.model.phone.Colour;
import seedu.address.model.phone.Cost;
import seedu.address.model.phone.Phone;
import seedu.address.model.phone.PhoneName;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Phone objects.
 */
public class PhoneBuilder {

    public static final String DEFAULT_NAME = "Samsung Galaxy 10";
    public static final String DEFAULT_BRAND = "Samsung";
    public static final Capacity DEFAULT_CAPACITY = Capacity.SIZE_8GB;
    public static final String DEFAULT_COLOUR = "Vanilla White";
    public static final String DEFAULT_COST = "$42.42";

    private UUID id;
    private PhoneName phoneName;
    private Brand brand;
    private Capacity capacity;
    private Colour colour;
    private Cost cost;
    private Set<Tag> tags;

    public PhoneBuilder() {
        id = UUID.randomUUID();
        phoneName = new PhoneName(DEFAULT_NAME);
        brand = new Brand(DEFAULT_BRAND);
        capacity = DEFAULT_CAPACITY;
        colour = new Colour(DEFAULT_COLOUR);
        cost = new Cost(DEFAULT_COST);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PhoneBuilder with the data of {@code phoneToCopy}.
     */
    public PhoneBuilder(Phone phoneToCopy, boolean copyId) {
        if (copyId) {
            id = phoneToCopy.getId();
        } else {
            id = UUID.randomUUID();
        }
        phoneName = phoneToCopy.getPhoneName();
        brand = phoneToCopy.getBrand();
        capacity = phoneToCopy.getCapacity();
        colour = phoneToCopy.getColour();
        cost = phoneToCopy.getCost();
        tags = new HashSet<>(phoneToCopy.getTags());
    }

    /**
     * Initializes the PhoneBuilder with the data of {@code phoneToCopy}.
     */
    public PhoneBuilder(Phone phoneToCopy) {
        this(phoneToCopy, true);
    }

    /**
     * Sets the {@code Name} of the {@code Phone} that we are building.
     */
    public PhoneBuilder withName(String phoneName) {
        this.phoneName = new PhoneName(phoneName);
        return this;
    }

    /**
     * Sets the {@code Brand} of the {@code Phone} that we are building.
     */
    public PhoneBuilder withBrand(String brand) {
        this.brand = new Brand(brand);
        return this;
    }

    /**
     * Sets the {@code Capacity} of the {@code Phone} that we are building.
     */
    public PhoneBuilder withCapacity(Capacity capacity) {
        this.capacity = capacity;
        return this;
    }

    /**
     * Sets the {@code Colour} of the {@code Phone} that we are building.
     */
    public PhoneBuilder withColour(String colour) {
        this.colour = new Colour(colour);
        return this;
    }

    /**
     * Sets the {@code Cost} of the {@code Phone} that we are building.
     */
    public PhoneBuilder withCost(String cost) {
        this.cost = new Cost(cost);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Phone} that we are building.
     */
    public PhoneBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public Phone build() {
        return new Phone(id, phoneName, brand, capacity, colour, cost, tags);
    }

}
