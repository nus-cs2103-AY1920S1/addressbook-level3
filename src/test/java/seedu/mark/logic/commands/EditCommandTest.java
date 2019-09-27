package seedu.mark.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mark.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.mark.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.mark.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.mark.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.mark.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.mark.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.mark.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.mark.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.mark.commons.core.Messages;
import seedu.mark.commons.core.index.Index;
import seedu.mark.model.BookmarkManager;
import seedu.mark.model.Model;
import seedu.mark.model.ModelManager;
import seedu.mark.model.UserPrefs;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.testutil.EditPersonDescriptorBuilder;
import seedu.mark.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Bookmark editedBookmark = new PersonBuilder().build();
        EditCommand.EditBookmarkDescriptor descriptor = new EditPersonDescriptorBuilder(editedBookmark).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_BOOKMARK_SUCCESS, editedBookmark);

        Model expectedModel = new ModelManager(new BookmarkManager(model.getBookmarkManager()), new UserPrefs());
        expectedModel.setBookmark(model.getFilteredBookmarkList().get(0), editedBookmark);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredBookmarkList().size());
        Bookmark lastBookmark = model.getFilteredBookmarkList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastBookmark);
        Bookmark editedBookmark = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditCommand.EditBookmarkDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_BOOKMARK_SUCCESS, editedBookmark);

        Model expectedModel = new ModelManager(new BookmarkManager(model.getBookmarkManager()), new UserPrefs());
        expectedModel.setBookmark(lastBookmark, editedBookmark);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, new EditCommand.EditBookmarkDescriptor());
        Bookmark editedBookmark = model.getFilteredBookmarkList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_BOOKMARK_SUCCESS, editedBookmark);

        Model expectedModel = new ModelManager(new BookmarkManager(model.getBookmarkManager()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Bookmark bookmarkInFilteredList = model.getFilteredBookmarkList().get(INDEX_FIRST_PERSON.getZeroBased());
        Bookmark editedBookmark = new PersonBuilder(bookmarkInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_BOOKMARK_SUCCESS, editedBookmark);

        Model expectedModel = new ModelManager(new BookmarkManager(model.getBookmarkManager()), new UserPrefs());
        expectedModel.setBookmark(model.getFilteredBookmarkList().get(0), editedBookmark);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Bookmark firstBookmark = model.getFilteredBookmarkList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditCommand.EditBookmarkDescriptor descriptor = new EditPersonDescriptorBuilder(firstBookmark).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_BOOKMARK);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // edit bookmark in filtered list into a duplicate in address book
        Bookmark bookmarkInList = model.getBookmarkManager().getBookmarkList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder(bookmarkInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_BOOKMARK);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBookmarkList().size() + 1);
        EditCommand.EditBookmarkDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_BOOKMARK_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getBookmarkManager().getBookmarkList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_BOOKMARK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditCommand.EditBookmarkDescriptor copyDescriptor = new EditCommand.EditBookmarkDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_PERSON, DESC_BOB)));
    }

}
