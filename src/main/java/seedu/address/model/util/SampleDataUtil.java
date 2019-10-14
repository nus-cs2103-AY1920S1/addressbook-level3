package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.DukeCooks;
import seedu.address.model.ReadOnlyDukeCooks;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.recipe.Name;
import seedu.address.model.recipe.Recipe;

/**
 * Contains utility methods for populating {@code DukeCooks} with sample data.
 */
public class SampleDataUtil {
    public static Recipe[] getSamplePersons() {
        return new Recipe[] {
            new Recipe(new Name("Alex Yeoh"),
                getIngredientSet("friends")),
            new Recipe(new Name("Bernice Yu"),
                getIngredientSet("colleagues", "friends")),
            new Recipe(new Name("Charlotte Oliveiro"),
                getIngredientSet("neighbours")),
            new Recipe(new Name("David Li"),
                getIngredientSet("family")),
            new Recipe(new Name("Irfan Ibrahim"),
                getIngredientSet("classmates")),
            new Recipe(new Name("Roy Balakrishnan"),
                getIngredientSet("colleagues"))
        };
    }

    public static ReadOnlyDukeCooks getSampleDukeCooks() {
        DukeCooks sampleDc = new DukeCooks();
        for (Recipe sampleRecipe : getSamplePersons()) {
            sampleDc.addRecipe(sampleRecipe);
        }
        return sampleDc;
    }

    /**
     * Returns a ingredient set containing the list of strings given.
     */
    public static Set<Ingredient> getIngredientSet(String... strings) {
        return Arrays.stream(strings)
                .map(Ingredient::new)
                .collect(Collectors.toSet());
    }

}
