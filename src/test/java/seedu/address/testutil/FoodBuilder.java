package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.food.Category;
import seedu.address.model.food.Description;
import seedu.address.model.food.Food;
import seedu.address.model.food.Name;
import seedu.address.model.food.OpeningHours;
import seedu.address.model.food.Price;
import seedu.address.model.food.Restrictions;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Food objects.
 */
public class FoodBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PRICE = "85355255";
    public static final String DEFAULT_DESCRIPTION = "Test Description.";
    public static final String DEFAULT_CATEGORY = "Test Category";
    public static final String DEFAULT_OPENING_HOURS = "0800 1800";
    public static final String DEFAULT_RESTRICTIONS = "Test Restrictions";

    private Name name;
    private Price price;
    private Description description;
    private Category category;
    private Set<Tag> tags;
    private OpeningHours openingHours;
    private Restrictions restrictions;

    public FoodBuilder() {
        name = new Name(DEFAULT_NAME);
        price = new Price(DEFAULT_PRICE);
        description = new Description(DEFAULT_DESCRIPTION);
        category = new Category(DEFAULT_CATEGORY);
        tags = new HashSet<>();
        openingHours = new OpeningHours(DEFAULT_OPENING_HOURS);
        restrictions = new Restrictions(DEFAULT_RESTRICTIONS);
    }

    /**
     * Initializes the FoodBuilder with the data of {@code foodToCopy}.
     */
    public FoodBuilder(Food foodToCopy) {
        name = foodToCopy.getName();
        price = foodToCopy.getPrice();
        description = foodToCopy.getDescription();
        category = foodToCopy.getCategory();
        tags = new HashSet<>(foodToCopy.getTags());
        openingHours = foodToCopy.getOpeningHours();
        restrictions = foodToCopy.getRestrictions();
    }

    /**
     * Sets the {@code Name} of the {@code Food} that we are building.
     */
    public FoodBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Food} that we are building.
     */
    public FoodBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code Food} that we are building.
     */
    public FoodBuilder withPrice(String price) {
        this.price = new Price(price);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Food} that we are building.
     */
    public FoodBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code Category} of the {@code Food} that we are building.
     */
    public FoodBuilder withCategory(String category) {
        this.category = new Category(category);
        return this;
    }

    /**
     * Sets the {@code OpeningHours} of the {@code Food} that we are building.
     */
    public FoodBuilder withOpeningHours(String openingHours) {
        this.openingHours = new OpeningHours(openingHours);
        return this;
    }

    /**
     * Sets the {@code Restrictions} of the {@code Food} that we are building.
     */
    public FoodBuilder withRestrictions(String restrictions) {
        this.restrictions = new Restrictions(restrictions);
        return this;
    }

    public Food build() {
        return new Food(name, price, description, category, tags, openingHours, restrictions);
    }

}
