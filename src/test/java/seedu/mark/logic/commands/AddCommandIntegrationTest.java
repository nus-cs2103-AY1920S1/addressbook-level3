package seedu.mark.logic.commands;

import static seedu.mark.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.mark.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.mark.testutil.TypicalBookmarks.getTypicalMark;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.mark.logic.commands.CommandTestUtil.StorageStub;
import seedu.mark.model.Model;
import seedu.mark.model.ModelManager;
import seedu.mark.model.UserPrefs;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.testutil.BookmarkBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalMark(), new UserPrefs());
    }

    @Test
    public void execute_newBookmark_success() {
        Bookmark validBookmark = new BookmarkBuilder().build();

        Model expectedModel = new ModelManager(model.getMark(), new UserPrefs());
        expectedModel.addBookmark(validBookmark);
        expectedModel.saveMark();

        assertCommandSuccess(new AddCommand(validBookmark), model, new StorageStub(),
                String.format(AddCommand.MESSAGE_SUCCESS, validBookmark), expectedModel);
    }

    @Test
    public void execute_duplicateBookmark_throwsCommandException() {
        Bookmark bookmarkInList = model.getMark().getBookmarkList().get(0);
        assertCommandFailure(new AddCommand(bookmarkInList), model, new StorageStub(),
                AddCommand.MESSAGE_DUPLICATE_BOOKMARK);
    }

}
