package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.address.commons.core.Messages.MESSAGE_BOOKS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import static seedu.address.testutil.TypicalBooks.BOOK_2;
import static seedu.address.testutil.TypicalBooks.BOOK_3;
import static seedu.address.testutil.TypicalBooks.BOOK_4;
import static seedu.address.testutil.TypicalBooks.getTypicalCatalog;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.BorrowerRecords;
import seedu.address.model.LoanRecords;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.BookPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    // TODO implement and add getTypicalLoanRecords() and getTypicalBorrowerRecords()
    private Model model =
            new ModelManager(getTypicalCatalog(), new LoanRecords(), new BorrowerRecords(), new UserPrefs());
    private Model expectedModel =
            new ModelManager(getTypicalCatalog(), new LoanRecords(), new BorrowerRecords(), new UserPrefs());

    @Test
    public void equals() {
        BookPredicate firstPredicate =
                new BookPredicate().addTitle("first");
        BookPredicate secondPredicate =
                new BookPredicate().addTitle("second");

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_BOOKS_LISTED_OVERVIEW, 0);
        BookPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredBookList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredBookList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_BOOKS_LISTED_OVERVIEW, 3);
        BookPredicate predicate = preparePredicate("the");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredBookList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BOOK_2, BOOK_3, BOOK_4), model.getFilteredBookList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private BookPredicate preparePredicate(String userInput) {
        return new BookPredicate().addTitle(userInput);
    }
}
