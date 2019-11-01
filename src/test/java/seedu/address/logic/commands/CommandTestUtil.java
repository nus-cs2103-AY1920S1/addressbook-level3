package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BORROWER_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FINE_INCREMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENRE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOAN_PERIOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENEW_PERIOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERIAL_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Catalog;
import seedu.address.model.Model;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookPredicate;
import seedu.address.testutil.EditBorrowerDescriptorBuilder;
import seedu.address.testutil.SetUserSettingsDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_ID_AMY = "K1234";
    public static final String VALID_ID_BOB = "K2345";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String BORROWER_ID_DESC_BOB = " " + PREFIX_BORROWER_ID + VALID_ID_BOB;
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "$123";
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "yo";

    public static final String VALID_TITLE_BOOK_1 = "Harry Botter";
    public static final String VALID_TITLE_BOOK_2 = "Legend of the Condor Heroes";
    public static final String VALID_TITLE_BOOK_3 = "Heavenly Sword and Dragon Saber";
    public static final String VALID_SERIAL_NUMBER_BOOK_1 = "B00001";
    public static final String VALID_SERIAL_NUMBER_BOOK_2 = "B00002";
    public static final String VALID_SERIAL_NUMBER_BOOK_3 = "B00005";
    public static final String VALID_SERIAL_NUMBER_BOOK_4 = "B00007";
    public static final String VALID_AUTHOR_BOOK_1 = "J K Rowling";
    public static final String VALID_AUTHOR_BOOK_2 = "Jin Yong";
    public static final String VALID_GENRE_ACTION = "ACTION";
    public static final String VALID_GENRE_FICTION = "FICTION";
    public static final String VALID_GENRE_NONFICTION = "NON-FICTION";
    public static final String VALID_BORROWER_ID_1 = "K0001";
    public static final String VALID_BORROWER_ID_2 = "K0002";

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

    public static final String VALID_LOAN_ID = "L000001";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final String VALID_LOAN_PERIOD_1 = "14";
    public static final String VALID_LOAN_PERIOD_2 = "7";
    public static final String VALID_RENEW_PERIOD_1 = "14";
    public static final String VALID_RENEW_PERIOD_2 = "7";
    public static final String VALID_FINE_INCREMENT_1 = "30";
    public static final String VALID_FINE_INCREMENT_2 = "40";
    public static final String VALID_MAX_RENEWS_1 = "2";
    public static final String VALID_MAX_RENEWS_2 = "3";

    public static final String INVALID_LOAN_PERIOD_DESC = " " + PREFIX_LOAN_PERIOD + "7a"; // 'a' not allowed
    public static final String INVALID_RENEW_PERIOD_DESC = " " + PREFIX_RENEW_PERIOD + "7b"; // 'a' not allowed
    public static final String INVALID_FINE_INCREMENT_DESC = " " + PREFIX_FINE_INCREMENT + "10c"; // 'a' not allowed

    public static final String VALID_LOAN_PERIOD_1_DESC = " " + PREFIX_LOAN_PERIOD + VALID_LOAN_PERIOD_1;
    public static final String VALID_RENEW_PERIOD_1_DESC = " " + PREFIX_RENEW_PERIOD + VALID_RENEW_PERIOD_1;
    public static final String VALID_FINE_INCREMENT_1_DESC = " " + PREFIX_FINE_INCREMENT + VALID_FINE_INCREMENT_1;

    public static final String VALID_LOAN_PERIOD_2_DESC = " " + PREFIX_LOAN_PERIOD + VALID_LOAN_PERIOD_2;
    public static final String VALID_RENEW_PERIOD_2_DESC = " " + PREFIX_RENEW_PERIOD + VALID_RENEW_PERIOD_2;
    public static final String VALID_FINE_INCREMENT_2_DESC = " " + PREFIX_FINE_INCREMENT + VALID_FINE_INCREMENT_2;

    public static final int VALID_CENT_AMOUNT = 120;

    public static final SetCommand.SetUserSettingsDescriptor DESC_USER_SETTINGS_1;
    public static final SetCommand.SetUserSettingsDescriptor DESC_USER_SETTINGS_2;

    static {
        DESC_USER_SETTINGS_1 = new SetUserSettingsDescriptorBuilder()
                .withLoanPeriod(VALID_LOAN_PERIOD_1)
                .withRenewPeriod(VALID_RENEW_PERIOD_1)
                .withFineIncrement(VALID_FINE_INCREMENT_1)
                .withMaxRenews(VALID_MAX_RENEWS_1)
                .build();
        DESC_USER_SETTINGS_2 = new SetUserSettingsDescriptorBuilder()
                .withLoanPeriod(VALID_LOAN_PERIOD_2)
                .withRenewPeriod(VALID_RENEW_PERIOD_2)
                .withFineIncrement(VALID_FINE_INCREMENT_2)
                .withMaxRenews(VALID_MAX_RENEWS_2)
                .build();
    }

    public static final EditBorrowerCommand.EditBorrowerDescriptor DESC_AMY;
    public static final EditBorrowerCommand.EditBorrowerDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditBorrowerDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withBorrowerId(VALID_ID_AMY).build();
        DESC_BOB = new EditBorrowerDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withBorrowerId(VALID_ID_BOB).build();
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
        final String title = book.getTitle().value;
        model.updateFilteredBookList(new BookPredicate().setTitle(title));

        assertEquals(1, model.getFilteredBookList().size());
    }

}
