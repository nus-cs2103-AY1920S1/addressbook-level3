package calofit.testutil;

import java.util.HashSet;
import java.util.Set;

import calofit.model.dish.Calorie;
import calofit.model.dish.Dish;
import calofit.model.dish.Name;
import calofit.model.tag.Tag;
import calofit.model.util.SampleDataUtil;

/**
 * A utility class to help with building Dish objects.
 */
public class DishBuilder {

    public static final String DEFAULT_NAME = "Spaghetti";
    public static final int DEFAULT_CALORIE = 1000;

    private Name name;
    private Calorie calories;
    private Set<Tag> tags;

    public DishBuilder() {
        name = new Name(DEFAULT_NAME);
        calories = new Calorie(DEFAULT_CALORIE);
        tags = new HashSet<>();
    }

    /**
     * Initializes the DishBuilder with the data of {@code dishToCopy}.
     */
    public DishBuilder(Dish dishToCopy) {
        name = dishToCopy.getName();
        calories = dishToCopy.getCalories();
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
     * Sets the {@code Calorie} of the {@code Dish} that we are building.
     */
    public DishBuilder withCalories(int calories) {
        this.calories = new Calorie(calories);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Dish} that we are building.
     */
    public DishBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Parses the {@code setOfTags} into a {@code Set<Tag>} and set it to the {@code Dish} that we are building.
     */
    public DishBuilder withTagsSet(Set<Tag> setOfTags) {
        this.tags = setOfTags;
        return this;
    }

    /**
     * Removes tags in {@code setOfTagsToRemove} from the original set of tags
     */
    public DishBuilder withTagsToRemove(Set<Tag> setOfTagsToRemove) {
        this.tags.removeAll(setOfTagsToRemove);
        return this;
    }

    public Dish build() {
        return new Dish(name, calories, tags);
    }

}
