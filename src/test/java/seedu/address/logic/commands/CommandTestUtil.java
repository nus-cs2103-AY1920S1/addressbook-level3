package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENRE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERIAL_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Catalog;
import seedu.address.model.Model;
import seedu.address.model.book.Book;
import seedu.address.model.book.TitleContainsKeywordPredicate;
import seedu.address.testutil.EditBookDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_TITLE_BOOK_1 = "Harry Botter";
    public static final String VALID_TITLE_BOOK_2 = "Legend of the Condor Heroes";
    public static final String VALID_TITLE_BOOK_3 = "Heavenly Sword and Dragon Saber";
    public static final String VALID_SERIAL_NUMBER_BOOK_1 = "B00001";
    public static final String VALID_SERIAL_NUMBER_BOOK_2 = "B00002";
    public static final String VALID_SERIAL_NUMBER_BOOK_3 = "B00005";
    public static final String VALID_AUTHOR_BOOK_1 = "J K Rowling";
    public static final String VALID_AUTHOR_BOOK_2 = "Jin Yong";
    public static final String VALID_GENRE_ACTION = "Action";
    public static final String VALID_GENRE_FICTION = "Fiction";
    public static final String VALID_BORROWER_ID = "K0001";

    public static final String TITLE_DESC_BOOK_1 = " " + PREFIX_TITLE + VALID_TITLE_BOOK_1;
    public static final String TITLE_DESC_BOOK_2 = " " + PREFIX_TITLE + VALID_TITLE_BOOK_2;
    public static final String SERIAL_NUMBER_DESC_BOOK_1 = " " + PREFIX_SERIAL_NUMBER + VALID_SERIAL_NUMBER_BOOK_1;
    public static final String SERIAL_NUMBER_DESC_BOOK_2 = " " + PREFIX_SERIAL_NUMBER + VALID_SERIAL_NUMBER_BOOK_2;
    public static final String AUTHOR_DESC_BOOK_1 = " " + PREFIX_AUTHOR + VALID_AUTHOR_BOOK_1;
    public static final String AUTHOR_DESC_BOOK_2 = " " + PREFIX_AUTHOR + VALID_AUTHOR_BOOK_2;
    public static final String GENRE_DESC_FICTION = " " + PREFIX_GENRE + VALID_GENRE_FICTION;
    public static final String GENRE_DESC_ACTION = " " + PREFIX_GENRE + VALID_GENRE_ACTION;

    public static final String INVALID_SERIAL_NUMBER_DESC = " " + PREFIX_SERIAL_NUMBER + "9a"; // 'a' not allowed
    public static final String INVALID_GENRE_DESC = " " + PREFIX_GENRE + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditBookDescriptor DESC_BOOK_1;
    public static final EditCommand.EditBookDescriptor DESC_BOOK_2;

    static {
        DESC_BOOK_1 = new EditBookDescriptorBuilder().withTitle(VALID_TITLE_BOOK_1)
                .withSerialNumber(VALID_SERIAL_NUMBER_BOOK_1).withAuthor(VALID_AUTHOR_BOOK_1)
                .withGenres(VALID_GENRE_FICTION).build();
        DESC_BOOK_2 = new EditBookDescriptorBuilder().withTitle(VALID_TITLE_BOOK_2)
                .withSerialNumber(VALID_SERIAL_NUMBER_BOOK_2).withAuthor(VALID_AUTHOR_BOOK_2)
                .withGenres(VALID_GENRE_ACTION, VALID_GENRE_FICTION).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        Catalog expectedCatalog = new Catalog(actualModel.getCatalog());
        List<Book> expectedFilteredList = new ArrayList<>(actualModel.getFilteredBookList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedCatalog, actualModel.getCatalog());
        assertEquals(expectedFilteredList, actualModel.getFilteredBookList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the book at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showBookAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredBookList().size());

        Book book = model.getFilteredBookList().get(targetIndex.getZeroBased());
        final String[] splitName = book.getTitle().value.split("\\s+");
        model.updateFilteredBookList(new TitleContainsKeywordPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredBookList().size());
    }

}
