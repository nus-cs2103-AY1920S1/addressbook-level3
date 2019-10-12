package seedu.mark.testutil;

import static seedu.mark.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_URL_AMY;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_URL_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.mark.model.Mark;
import seedu.mark.model.bookmark.Bookmark;

/**
 * A utility class containing a list of {@code Bookmark} objects to be used in tests.
 */
public class TypicalBookmarks {

    public static final Bookmark ALICE = new BookmarkBuilder().withName("Alice Pauline")
            .withRemark("123, Jurong West Ave 6, #08-111").withUrl("https://alice@example.com")
            .withTags("friends").build();
    public static final Bookmark BENSON = new BookmarkBuilder().withName("Benson Meier")
            .withRemark("311, Clementi Ave 2, #02-25")
            .withUrl("https://johnd@example.com")
            .withTags("owesMoney", "friends").build();
    public static final Bookmark CARL = new BookmarkBuilder().withName("Carl Kurz")
            .withUrl("https://heinz@example.com").withRemark("wall street").build();
    public static final Bookmark DANIEL = new BookmarkBuilder().withName("Daniel Meier")
            .withUrl("https://cornelia@example.com").withRemark("10th street").withTags("friends").build();
    public static final Bookmark ELLE = new BookmarkBuilder().withName("Elle Meyer")
            .withUrl("https://werner@example.com").withRemark("michegan ave").build();
    public static final Bookmark FIONA = new BookmarkBuilder().withName("Fiona Kunz")
            .withUrl("https://lydia@example.com").withRemark("little tokyo").build();
    public static final Bookmark GEORGE = new BookmarkBuilder().withName("George Best")
            .withUrl("https://anna@example.com").withRemark("4th street").build();

    // Manually added
    public static final Bookmark HOON = new BookmarkBuilder().withName("Hoon Meier")
            .withUrl("https://stefan@example.com").withRemark("little india").build();
    public static final Bookmark IDA = new BookmarkBuilder().withName("Ida Mueller")
            .withUrl("https://hans@example.com").withRemark("chicago ave").build();

    // Manually added - Bookmark's details found in {@code CommandTestUtil}
    public static final Bookmark AMY = new BookmarkBuilder().withName(VALID_NAME_AMY)
            .withUrl(VALID_URL_AMY).withRemark(VALID_REMARK_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Bookmark BOB = new BookmarkBuilder().withName(VALID_NAME_BOB)
            .withUrl(VALID_URL_BOB).withRemark(VALID_REMARK_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalBookmarks() {} // prevents instantiation

    /**
     * Returns a {@code Mark} instance with all the typical bookmarks.
     */
    public static Mark getTypicalMark() {
        Mark mark = new Mark();
        for (Bookmark bookmark : getTypicalBookmarks()) {
            mark.addBookmark(bookmark);
        }
        return mark;
    }

    public static List<Bookmark> getTypicalBookmarks() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
