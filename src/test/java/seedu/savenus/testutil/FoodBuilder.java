package seedu.savenus.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.savenus.model.food.Description;
import seedu.savenus.model.food.Food;
import seedu.savenus.model.food.Name;
import seedu.savenus.model.food.Price;
import seedu.savenus.model.tag.Tag;
import seedu.savenus.model.util.SampleDataUtil;

/**
 * A utility class to help with building Food objects.
 */
public class FoodBuilder {

    public static final String DEFAULT_NAME = "Chicken Teriyaki";
    public static final String DEFAULT_PRICE = "2.00";
    public static final String DEFAULT_DESCRIPTION = "Test Description.";

    private Name name;
    private Price price;
    private Description description;
    private Set<Tag> tags;

    public FoodBuilder() {
        name = new Name(DEFAULT_NAME);
        price = new Price(DEFAULT_PRICE);
        description = new Description(DEFAULT_DESCRIPTION);
        tags = new HashSet<>();
    }

    /**
     * Initializes the FoodBuilder with the data of {@code foodToCopy}.
     */
    public FoodBuilder(Food foodToCopy) {
        name = foodToCopy.getName();
        price = foodToCopy.getPrice();
        description = foodToCopy.getDescription();
        tags = new HashSet<>(foodToCopy.getTags());
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

    public Food build() {
        return new Food(name, price, description, tags);
    }

}
