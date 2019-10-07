package calofit.testutil;

import calofit.model.meal.Meal;
import calofit.model.meal.Name;
import calofit.model.tag.Tag;
import calofit.model.util.SampleDataUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * A utility class to help with building Meal objects.
 */
public class MealBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";

    private Name name;
    private Set<Tag> tags;

    public MealBuilder() {
        name = new Name(DEFAULT_NAME);
        tags = new HashSet<>();
    }

    /**
     * Initializes the MealBuilder with the data of {@code mealToCopy}.
     */
    public MealBuilder(Meal mealToCopy) {
        name = mealToCopy.getName();
        tags = new HashSet<>(mealToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Meal} that we are building.
     */
    public MealBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Meal} that we are building.
     */
    public MealBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public Meal build() {
        return new Meal(name, tags);
    }

}
