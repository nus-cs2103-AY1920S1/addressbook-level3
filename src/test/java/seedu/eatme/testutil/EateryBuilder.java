package seedu.eatme.testutil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.eatme.model.eatery.Address;
import seedu.eatme.model.eatery.Category;
import seedu.eatme.model.eatery.Eatery;
import seedu.eatme.model.eatery.Name;
import seedu.eatme.model.eatery.Review;
import seedu.eatme.model.eatery.Tag;
import seedu.eatme.model.util.SampleDataUtil;

/**
 * A utility class to help with building Eatery objects.
 */
public class EateryBuilder {

    public static final String DEFAULT_NAME = "Popeyes";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_CATEGORY = "Western";
    public static final String DEFAULT_TAG = "fastfood";

    public static final Review DEFAULT_GOOD_REVIEW = new Review("good place", 5, 5,
            new Date());
    public static final Review DEFAULT_NEUTRAL_REVIEW = new Review("ok place", 3, 3,
            new Date());
    public static final Review DEFAULT_BAD_REVIEW = new Review("bad place", 5, 1,
            new Date());

    private Name name;
    private boolean isOpen;
    private Address address;
    private Category category;
    private Set<Tag> tags;
    private List<Review> reviews;

    public EateryBuilder() {
        name = new Name(DEFAULT_NAME);
        isOpen = true;
        address = new Address(DEFAULT_ADDRESS);
        category = new Category(DEFAULT_CATEGORY);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
        reviews = new ArrayList<>();
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
        reviews = new ArrayList<>();
    }

    /**
     * Sets the {@code Name} of the {@code Eatery} that we are building.
     */
    public EateryBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code isOpen} of the {@code Eatery} that we are building.
     */
    public EateryBuilder withIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
        return this;
    }

    /**
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

    /**
     * Sets the {@code Eatery} that we are building with some default reviews.
     */
    public EateryBuilder withReviews() {
        this.reviews.add(DEFAULT_GOOD_REVIEW);
        this.reviews.add(DEFAULT_NEUTRAL_REVIEW);
        this.reviews.add(DEFAULT_BAD_REVIEW);
        return this;
    }

    public Eatery build() {
        return new Eatery(name, isOpen, address, category, tags);
    }

    /**
     * Builds an eatery with some default reviews.
     */
    public Eatery buildWithReviews() {
        Eatery eatery = new Eatery(name, isOpen, address, category, tags);
        eatery.setReviews(reviews);
        return eatery;
    }
}
