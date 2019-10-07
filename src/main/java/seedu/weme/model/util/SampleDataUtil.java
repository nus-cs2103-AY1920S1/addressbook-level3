package seedu.weme.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.weme.model.MemeBook;
import seedu.weme.model.ReadOnlyMemeBook;
import seedu.weme.model.meme.Description;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.meme.Name;
import seedu.weme.model.tag.Tag;

/**
 * Contains utility methods for populating {@code MemeBook} with sample data.
 */
public class SampleDataUtil {
    public static Meme[] getSampleMemes() {
        return new Meme[] {
            new Meme(new Name("Alex Yeoh"),
                new Description("Meme Description 01"),
                getTagSet("friends")),
            new Meme(new Name("Bernice Yu"),
                new Description("Meme Description 02"),
                getTagSet("colleagues", "friends")),
            new Meme(new Name("Charlotte Oliveiro"),
                new Description("Meme Description 03"),
                getTagSet("neighbours")),
            new Meme(new Name("David Li"),
                new Description("Meme Description 04"),
                getTagSet("family")),
            new Meme(new Name("Irfan Ibrahim"),
                new Description("Meme Description 05"),
                getTagSet("classmates")),
            new Meme(new Name("Roy Balakrishnan"),
                new Description("Meme Description 06"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyMemeBook getSampleMemeBook() {
        MemeBook sampleMb = new MemeBook();
        for (Meme sampleMeme : getSampleMemes()) {
            sampleMb.addMeme(sampleMeme);
        }
        return sampleMb;
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
