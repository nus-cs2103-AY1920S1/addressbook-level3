package seedu.weme.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.weme.model.MemeBook;
import seedu.weme.model.meme.Meme;

/**
 * A utility class containing a list of {@code Meme} objects to be used in tests.
 */
public class TypicalMemes {

    public static final Meme CHARMANDER = new MemeBuilder()
            .withFilePath("src/test/data/memes/charmander_meme.jpg")
            .withDescription("A meme about Char and charmander.")
            .withTags("charmander").build();
    public static final Meme DOGE = new MemeBuilder()
            .withFilePath("src/test/data/memes/doge_meme.jpg")
            .withDescription("A meme about doge.")
            .withTags("doge").build();
    public static final Meme JOKER = new MemeBuilder()
            .withFilePath("src/test/data/memes/joker_meme.jpg")
            .withDescription("A meme about joker.")
            .withTags("joker").build();
    public static final Meme TOY = new MemeBuilder()
            .withFilePath("src/test/data/memes/toy_meme.jpg")
            .withDescription("A toy story meme.")
            .withTags("toy").build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalMemes() {} // prevents instantiation

    /**
     * Returns an {@code MemeBook} with all the typical memes.
     */
    public static MemeBook getTypicalMemeBook() {
        MemeBook ab = new MemeBook();
        for (Meme meme : getTypicalMemes()) {
            ab.addMeme(meme);
        }
        return ab;
    }

    public static List<Meme> getTypicalMemes() {
        return new ArrayList<>(Arrays.asList(CHARMANDER, DOGE, JOKER, TOY));
    }
}
