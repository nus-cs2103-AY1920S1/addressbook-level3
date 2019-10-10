package seedu.savenus.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.savenus.model.food.Category;
import seedu.savenus.model.food.Description;
import seedu.savenus.model.food.Food;
import seedu.savenus.model.food.Location;
import seedu.savenus.model.food.Name;
import seedu.savenus.model.food.OpeningHours;
import seedu.savenus.model.food.Price;
import seedu.savenus.model.food.Restrictions;
import seedu.savenus.model.tag.Tag;
import seedu.savenus.model.util.SampleDataUtil;

/**
 * A utility class to help with building Food objects.
 */
public class FoodBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PRICE = "85355255";
    public static final String DEFAULT_DESCRIPTION = "Test Description.";
    public static final String DEFAULT_CATEGORY = "Test Category";
    public static final String DEFAULT_LOCATION = "Test Location";
    public static final String DEFAULT_OPENING_HOURS = "0800 1800";
    public static final String DEFAULT_RESTRICTIONS = "Test Restrictions";

    private Name name;
    private Price price;
    private Description description;
    private Category category;
    private Set<Tag> tags;
    private Location location;
    private OpeningHours openingHours;
    private Restrictions restrictions;

    public FoodBuilder() {
        name = new Name(DEFAULT_NAME);
        price = new Price(DEFAULT_PRICE);
        description = new Description(DEFAULT_DESCRIPTION);
        category = new Category(DEFAULT_CATEGORY);
        tags = new HashSet<>();
        location = new Location(DEFAULT_LOCATION);
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
        location = foodToCopy.getLocation();
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
     * Sets the {@code Location} of the {@code Food} that we are building.
     */
    public FoodBuilder withLocation(String location) {
        this.location = new Location(location);
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
        return new Food(name, price, description, category, tags, location, openingHours, restrictions);
    }

}
