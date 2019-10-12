package seedu.mark.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mark.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.mark.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.mark.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.mark.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.mark.logic.commands.CommandTestUtil.showBookmarkAtIndex;
import static seedu.mark.testutil.TypicalBookmarks.getTypicalMark;
import static seedu.mark.testutil.TypicalIndexes.INDEX_FIRST_BOOKMARK;
import static seedu.mark.testutil.TypicalIndexes.INDEX_SECOND_BOOKMARK;

import org.junit.jupiter.api.Test;

import seedu.mark.commons.core.Messages;
import seedu.mark.commons.core.index.Index;
import seedu.mark.logic.commands.CommandTestUtil.StorageStub;
import seedu.mark.model.Mark;
import seedu.mark.model.Model;
import seedu.mark.model.ModelManager;
import seedu.mark.model.UserPrefs;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.testutil.BookmarkBuilder;
import seedu.mark.testutil.EditBookmarkDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalMark(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Bookmark editedBookmark = new BookmarkBuilder().withFolder("contacts").build();
        EditCommand.EditBookmarkDescriptor descriptor = new EditBookmarkDescriptorBuilder(editedBookmark).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_BOOKMARK, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_BOOKMARK_SUCCESS, editedBookmark);

        Model expectedModel = new ModelManager(new Mark(model.getMark()), new UserPrefs());
        expectedModel.setBookmark(model.getFilteredBookmarkList().get(0), editedBookmark);
        expectedModel.saveMark();

        assertCommandSuccess(editCommand, model, new StorageStub(), expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastBookmark = Index.fromOneBased(model.getFilteredBookmarkList().size());
        Bookmark lastBookmark = model.getFilteredBookmarkList().get(indexLastBookmark.getZeroBased());

        BookmarkBuilder bookmarkInList = new BookmarkBuilder(lastBookmark);
        Bookmark editedBookmark = bookmarkInList.withName(VALID_NAME_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditCommand.EditBookmarkDescriptor descriptor = new EditBookmarkDescriptorBuilder().withName(VALID_NAME_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastBookmark, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_BOOKMARK_SUCCESS, editedBookmark);

        Model expectedModel = new ModelManager(new Mark(model.getMark()), new UserPrefs());
        expectedModel.setBookmark(lastBookmark, editedBookmark);
        expectedModel.saveMark();

        assertCommandSuccess(editCommand, model, new StorageStub(), expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_BOOKMARK, new EditCommand.EditBookmarkDescriptor());
        Bookmark editedBookmark = model.getFilteredBookmarkList().get(INDEX_FIRST_BOOKMARK.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_BOOKMARK_SUCCESS, editedBookmark);

        Model expectedModel = new ModelManager(new Mark(model.getMark()), new UserPrefs());
        expectedModel.saveMark();

        assertCommandSuccess(editCommand, model, new StorageStub(), expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showBookmarkAtIndex(model, INDEX_FIRST_BOOKMARK);

        Bookmark bookmarkInFilteredList = model.getFilteredBookmarkList().get(INDEX_FIRST_BOOKMARK.getZeroBased());
        Bookmark editedBookmark = new BookmarkBuilder(bookmarkInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_BOOKMARK,
                new EditBookmarkDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_BOOKMARK_SUCCESS, editedBookmark);

        Model expectedModel = new ModelManager(new Mark(model.getMark()), new UserPrefs());
        expectedModel.setBookmark(model.getFilteredBookmarkList().get(0), editedBookmark);
        expectedModel.saveMark();

        assertCommandSuccess(editCommand, model, new StorageStub(), expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateBookmarkUnfilteredList_failure() {
        Bookmark firstBookmark = model.getFilteredBookmarkList().get(INDEX_FIRST_BOOKMARK.getZeroBased());
        EditCommand.EditBookmarkDescriptor descriptor = new EditBookmarkDescriptorBuilder(firstBookmark).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_BOOKMARK, descriptor);

        assertCommandFailure(editCommand, model, new StorageStub(), EditCommand.MESSAGE_DUPLICATE_BOOKMARK);
    }

    @Test
    public void execute_duplicateBookmarkFilteredList_failure() {
        showBookmarkAtIndex(model, INDEX_FIRST_BOOKMARK);

        // edit bookmark in filtered list into a duplicate in Mark
        Bookmark bookmarkInList = model.getMark().getBookmarkList().get(INDEX_SECOND_BOOKMARK.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_BOOKMARK,
                new EditBookmarkDescriptorBuilder(bookmarkInList).build());

        assertCommandFailure(editCommand, model, new StorageStub(), EditCommand.MESSAGE_DUPLICATE_BOOKMARK);
    }

    @Test
    public void execute_invalidBookmarkIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBookmarkList().size() + 1);
        EditCommand.EditBookmarkDescriptor descriptor = new EditBookmarkDescriptorBuilder()
                .withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, new StorageStub(), Messages.MESSAGE_INVALID_BOOKMARK_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of mark's bookmark list
     */
    @Test
    public void execute_invalidBookmarkIndexFilteredList_failure() {
        showBookmarkAtIndex(model, INDEX_FIRST_BOOKMARK);
        Index outOfBoundIndex = INDEX_SECOND_BOOKMARK;
        // ensures that outOfBoundIndex is still in bounds of mark's bookmark list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getMark().getBookmarkList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditBookmarkDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, new StorageStub(), Messages.MESSAGE_INVALID_BOOKMARK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_BOOKMARK, DESC_AMY);

        // same values -> returns true
        EditCommand.EditBookmarkDescriptor copyDescriptor = new EditCommand.EditBookmarkDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_BOOKMARK, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_BOOKMARK, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_BOOKMARK, DESC_BOB)));
    }

}
