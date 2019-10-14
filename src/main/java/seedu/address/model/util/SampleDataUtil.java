package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.DukeCooks;
import seedu.address.model.ReadOnlyDukeCooks;
import seedu.address.model.recipe.Name;
import seedu.address.model.recipe.Recipe;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code DukeCooks} with sample data.
 */
public class SampleDataUtil {
    public static Recipe[] getSamplePersons() {
        return new Recipe[] {
            new Recipe(new Name("Alex Yeoh"),
                getTagSet("friends")),
            new Recipe(new Name("Bernice Yu"),
                getTagSet("colleagues", "friends")),
            new Recipe(new Name("Charlotte Oliveiro"),
                getTagSet("neighbours")),
            new Recipe(new Name("David Li"),
                getTagSet("family")),
            new Recipe(new Name("Irfan Ibrahim"),
                getTagSet("classmates")),
            new Recipe(new Name("Roy Balakrishnan"),
                getTagSet("colleagues"))
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
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
