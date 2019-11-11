package seedu.weme.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.weme.model.meme.Meme;
import seedu.weme.model.statistics.Stats;
import seedu.weme.model.statistics.StatsManager;

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
    public static final Meme PIKACHU = new MemeBuilder()
            .withFilePath("src/test/data/memes/pikachu_meme.png")
            .withDescription("Pikachu")
            .withTags("pikachu")
            .withIsArchived(true).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalMemes() {} // prevents instantiation

    public static List<Meme> getTypicalMemes() {
        return new ArrayList<>(Arrays.asList(CHARMANDER, DOGE, JOKER, TOY, PIKACHU));
    }

    public static Stats getTypicalStats() {
        Stats typicalStats = new StatsManager();
        typicalStats.addDefaultLikeData(CHARMANDER);
        typicalStats.addDefaultLikeData(DOGE);
        typicalStats.addDefaultLikeData(JOKER);
        typicalStats.addDefaultLikeData(TOY);
        typicalStats.addDefaultDislikeData(CHARMANDER);
        typicalStats.addDefaultDislikeData(DOGE);
        typicalStats.addDefaultDislikeData(JOKER);
        typicalStats.addDefaultDislikeData(TOY);

        typicalStats.incrementMemeLikeCount(CHARMANDER);
        typicalStats.incrementMemeLikeCount(DOGE);
        typicalStats.incrementMemeLikeCount(DOGE);

        typicalStats.incrementMemeDislikeCount(JOKER);
        typicalStats.incrementMemeDislikeCount(TOY);
        typicalStats.incrementMemeDislikeCount(TOY);

        return typicalStats;
    }
}
