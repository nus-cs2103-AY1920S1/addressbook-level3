package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOOK_1;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOOK_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_BOOK_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENRE_ACTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SERIAL_NUMBER_BOOK_3;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_BOOK_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_BOOK_3;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showBookAtIndex;
import static seedu.address.testutil.TypicalBooks.getTypicalCatalog;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BOOK;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand.EditBookDescriptor;
import seedu.address.model.Catalog;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.Book;
import seedu.address.testutil.BookBuilder;
import seedu.address.testutil.EditBookDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalCatalog(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Book editedBook = new BookBuilder().build();
        EditCommand.EditBookDescriptor descriptor = new EditBookDescriptorBuilder(editedBook).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_BOOK, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_BOOK_SUCCESS, editedBook);

        Model expectedModel = new ModelManager(new Catalog(model.getCatalog()), new UserPrefs());
        expectedModel.setBook(model.getFilteredBookList().get(0), editedBook);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredBookList().size());
        Book lastBook = model.getFilteredBookList().get(indexLastPerson.getZeroBased());

        BookBuilder bookInList = new BookBuilder(lastBook);
        Book editedBook = bookInList.withTitle(VALID_TITLE_BOOK_3).withSerialNumber(VALID_SERIAL_NUMBER_BOOK_3)
                .withGenres(VALID_GENRE_ACTION).withAuthor(VALID_AUTHOR_BOOK_2).build();

        EditCommand.EditBookDescriptor descriptor = new EditBookDescriptorBuilder().withTitle(VALID_TITLE_BOOK_3)
                .withSerialNumber(VALID_SERIAL_NUMBER_BOOK_3).withGenres(VALID_GENRE_ACTION)
                .withAuthor(VALID_AUTHOR_BOOK_2).build();
        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_BOOK_SUCCESS, editedBook);

        Model expectedModel = new ModelManager(new Catalog(model.getCatalog()), new UserPrefs());
        expectedModel.setBook(lastBook, editedBook);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_BOOK, new EditCommand.EditBookDescriptor());
        Book editedBook = model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_BOOK_SUCCESS, editedBook);

        Model expectedModel = new ModelManager(new Catalog(model.getCatalog()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showBookAtIndex(model, INDEX_FIRST_BOOK);

        Book bookInFilteredList = model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased());
        Book editedBook = new BookBuilder(bookInFilteredList).withTitle(VALID_TITLE_BOOK_2).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_BOOK,
                new EditBookDescriptorBuilder().withTitle(VALID_TITLE_BOOK_2).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_BOOK_SUCCESS, editedBook);

        Model expectedModel = new ModelManager(new Catalog(model.getCatalog()), new UserPrefs());
        expectedModel.setBook(model.getFilteredBookList().get(0), editedBook);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Book firstBook = model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased());
        EditBookDescriptor descriptor = new EditBookDescriptorBuilder(firstBook).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_BOOK, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_BOOK);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showBookAtIndex(model, INDEX_FIRST_BOOK);

        // edit person in filtered list into a duplicate in address book
        Book bookInList = model.getCatalog().getBookList().get(INDEX_SECOND_BOOK.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_BOOK,
                new EditBookDescriptorBuilder(bookInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_BOOK);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBookList().size() + 1);
        EditBookDescriptor descriptor = new EditBookDescriptorBuilder().withTitle(VALID_TITLE_BOOK_2).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showBookAtIndex(model, INDEX_FIRST_BOOK);
        Index outOfBoundIndex = INDEX_SECOND_BOOK;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getCatalog().getBookList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditBookDescriptorBuilder().withTitle(VALID_TITLE_BOOK_2).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_BOOK, DESC_BOOK_1);

        // same values -> returns true
        EditCommand.EditBookDescriptor copyDescriptor = new EditBookDescriptor(DESC_BOOK_1);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_BOOK, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_BOOK, DESC_BOOK_1)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_BOOK, DESC_BOOK_2)));
    }

}
