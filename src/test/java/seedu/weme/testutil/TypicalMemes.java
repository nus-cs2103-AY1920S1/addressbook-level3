package seedu.weme.testutil;

import static seedu.weme.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
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
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withPhone("94351253")
            .withTags("friends").build();
    public static final Meme BENSON = new MemeBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25").withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Meme CARL = new MemeBuilder().withName("Carl Kurz").withPhone("95352563")
            .withAddress("wall street").build();
    public static final Meme DANIEL = new MemeBuilder().withName("Daniel Meier").withPhone("87652533")
            .withAddress("10th street").withTags("friends").build();
    public static final Meme ELLE = new MemeBuilder().withName("Elle Meyer").withPhone("9482224")
            .withAddress("michegan ave").build();
    public static final Meme FIONA = new MemeBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withAddress("little tokyo").build();
    public static final Meme GEORGE = new MemeBuilder().withName("George Best").withPhone("9482442")
            .withAddress("4th street").build();

    // Manually added
    public static final Meme HOON = new MemeBuilder().withName("Hoon Meier").withPhone("8482424")
            .withAddress("little india").build();
    public static final Meme IDA = new MemeBuilder().withName("Ida Mueller").withPhone("8482131")
            .withAddress("chicago ave").build();

    // Manually added - Meme's details found in {@code CommandTestUtil}
    public static final Meme AMY = new MemeBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Meme BOB = new MemeBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
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
