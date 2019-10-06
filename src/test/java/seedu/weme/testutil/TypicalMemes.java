package seedu.weme.testutil;

import static seedu.weme.logic.commands.CommandTestUtil.VALID_DESCRIPTION_AMY;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.weme.model.MemeBook;
import seedu.weme.model.meme.Meme;

/**
 * A utility class containing a list of {@code Meme} objects to be used in tests.
 */
public class TypicalMemes {

    public static final Meme ALICE = new MemeBuilder().withName("Alice Pauline")
            .withDescription("Description of Alice")
            .withTags("friends").build();
    public static final Meme BENSON = new MemeBuilder().withName("Benson Meier")
            .withDescription("Description of Benson")
            .withTags("owesMoney", "friends").build();
    public static final Meme CARL = new MemeBuilder().withName("Carl Kurz")
            .withDescription("Description of Carl").build();
    public static final Meme DANIEL = new MemeBuilder().withName("Daniel Meier")
            .withDescription("Description of Daniel").withTags("friends").build();
    public static final Meme ELLE = new MemeBuilder().withName("Elle Meyer")
            .withDescription("Description of Elle").build();
    public static final Meme FIONA = new MemeBuilder().withName("Fiona Kunz")
            .withDescription("Description of Fiona").build();
    public static final Meme GEORGE = new MemeBuilder().withName("George Best")
            .withDescription("Description of George").build();

    // Manually added
    public static final Meme HOON = new MemeBuilder().withName("Hoon Meier")
            .withDescription("Meme created in CS2103 Lecture").build();
    public static final Meme IDA = new MemeBuilder().withName("Ida Mueller")
            .withDescription("Meme created in CS2103 Lecture").build();

    // Manually added - Meme's details found in {@code CommandTestUtil}
    public static final Meme AMY = new MemeBuilder().withName(VALID_NAME_AMY)
            .withDescription(VALID_DESCRIPTION_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Meme BOB = new MemeBuilder().withName(VALID_NAME_BOB)
            .withDescription(VALID_DESCRIPTION_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

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
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
