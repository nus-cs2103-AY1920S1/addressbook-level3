package seedu.mark.testutil;

import static seedu.mark.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_URL_AMY;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_URL_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.mark.model.BookmarkManager;
import seedu.mark.model.bookmark.Bookmark;

/**
 * A utility class containing a list of {@code Bookmark} objects to be used in tests.
 */
public class TypicalBookmarks {

    public static final Bookmark ALICE = new BookmarkBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withUrl("alice@example.com")
            .withPhone("94351253")
            .withTags("friends").build();
    public static final Bookmark BENSON = new BookmarkBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withUrl("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Bookmark CARL = new BookmarkBuilder().withName("Carl Kurz").withPhone("95352563")
            .withUrl("heinz@example.com").withAddress("wall street").build();
    public static final Bookmark DANIEL = new BookmarkBuilder().withName("Daniel Meier").withPhone("87652533")
            .withUrl("cornelia@example.com").withAddress("10th street").withTags("friends").build();
    public static final Bookmark ELLE = new BookmarkBuilder().withName("Elle Meyer").withPhone("9482224")
            .withUrl("werner@example.com").withAddress("michegan ave").build();
    public static final Bookmark FIONA = new BookmarkBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withUrl("lydia@example.com").withAddress("little tokyo").build();
    public static final Bookmark GEORGE = new BookmarkBuilder().withName("George Best").withPhone("9482442")
            .withUrl("anna@example.com").withAddress("4th street").build();

    // Manually added
    public static final Bookmark HOON = new BookmarkBuilder().withName("Hoon Meier").withPhone("8482424")
            .withUrl("stefan@example.com").withAddress("little india").build();
    public static final Bookmark IDA = new BookmarkBuilder().withName("Ida Mueller").withPhone("8482131")
            .withUrl("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Bookmark's details found in {@code CommandTestUtil}
    public static final Bookmark AMY = new BookmarkBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withUrl(VALID_URL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Bookmark BOB = new BookmarkBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withUrl(VALID_URL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalBookmarks() {} // prevents instantiation

    /**
     * Returns an {@code BookmarkManager} with all the typical bookmarks.
     */
    public static BookmarkManager getTypicalBookmarkManager() {
        BookmarkManager ab = new BookmarkManager();
        for (Bookmark bookmark : getTypicalBookmarks()) {
            ab.addBookmark(bookmark);
        }
        return ab;
    }

    public static List<Bookmark> getTypicalBookmarks() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
