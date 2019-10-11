package seedu.weme.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.weme.model.MemeBook;
import seedu.weme.model.ReadOnlyMemeBook;
import seedu.weme.model.meme.Description;
import seedu.weme.model.meme.ImagePath;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.tag.Tag;

/**
 * Contains utility methods for populating {@code MemeBook} with sample data.
 */
public class SampleDataUtil {
    public static Meme[] getSampleMemes() {
        return new Meme[] {
            new Meme(new ImagePath("src/main/resources/memes/charmander_meme.jpg"),
                new Description("A meme about Char and charmander."),
                getTagSet("charmander")),
            new Meme(new ImagePath("src/main/resources/memes/doge_meme.jpg"),
                    new Description("A meme about doge."),
                    getTagSet("doge")),
            new Meme(new ImagePath("src/main/resources/memes/joker_meme.jpg"),
                    new Description("A meme about joker."),
                    getTagSet("joker")),
            new Meme(new ImagePath("src/main/resources/memes/toy_meme.jpg"),
                    new Description("A meme about toy."),
                    getTagSet("toy"))
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
