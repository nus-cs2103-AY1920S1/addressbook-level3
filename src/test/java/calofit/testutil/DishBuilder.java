package calofit.testutil;

import calofit.model.meal.Dish;
import calofit.model.meal.Name;
import calofit.model.tag.Tag;
import calofit.model.util.SampleDataUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * A utility class to help with building Dish objects.
 */
public class DishBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";

    private Name name;
    private Set<Tag> tags;

    public DishBuilder() {
        name = new Name(DEFAULT_NAME);
        tags = new HashSet<>();
    }

    /**
     * Initializes the DishBuilder with the data of {@code dishToCopy}.
     */
    public DishBuilder(Dish dishToCopy) {
        name = dishToCopy.getName();
        tags = new HashSet<>(dishToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Dish} that we are building.
     */
    public DishBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Dish} that we are building.
     */
    public DishBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public Dish build() {
        return new Dish(name, tags);
    }

}
