package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BORROWER_ID_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SERIAL_NUMBER_BOOK_1;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BORROWER_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERIAL_NUMBER;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBorrowers.ID_FIRST_BORROWER;
import static seedu.address.testutil.TypicalBorrowers.getTypicalBorrowerRecords;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.UserSettings;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteByIndexCommand;
import seedu.address.logic.commands.DeleteBySerialNumberCommand;
import seedu.address.logic.commands.DoneCommand;
import seedu.address.logic.commands.EditBorrowerCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.LoanCommand;
import seedu.address.logic.commands.PayCommand;
import seedu.address.logic.commands.RegisterCommand;
import seedu.address.logic.commands.RenewCommand;
import seedu.address.logic.commands.ReturnCommand;
import seedu.address.logic.commands.ServeCommand;
import seedu.address.logic.commands.SetCommand;
import seedu.address.logic.commands.UnregisterCommand;
import seedu.address.logic.commands.ViewSettingsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.BorrowerRecords;
import seedu.address.model.Catalog;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookPredicate;
import seedu.address.model.book.SerialNumber;
import seedu.address.model.book.SerialNumberGenerator;
import seedu.address.model.borrower.Borrower;
import seedu.address.model.borrower.BorrowerIdGenerator;
import seedu.address.testutil.BookBuilder;
import seedu.address.testutil.BookUtil;
import seedu.address.testutil.BorrowerBuilder;
import seedu.address.testutil.BorrowerUtil;
import seedu.address.testutil.EditBorrowerDescriptorBuilder;
import seedu.address.testutil.SetUserSettingsDescriptorBuilder;
import seedu.address.testutil.UserSettingsBuilder;
import seedu.address.testutil.UserSettingsUtil;


public class CatalogParserTest {

    public static final String SPACE_AND_VALID_INDEX = " 3";
    public static final String SPACE_AND_VALID_DOLLAR_AMOUNT = " $1.23";

    private final CatalogParser parser = new CatalogParser();

    @Test
    public void parseCommand_add() throws Exception {
        SerialNumberGenerator.setCatalog(new Catalog());
        Book book = new BookBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(BookUtil.getAddCommand(book));
        assertEquals(new AddCommand(book), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + SPACE_AND_VALID_INDEX) instanceof ClearCommand);
    }

    @Test
    public void parseCommand_deleteByIndex() throws Exception {
        DeleteByIndexCommand command = (DeleteByIndexCommand) parser.parseCommand(
                DeleteByIndexCommand.COMMAND_WORD + " " + INDEX_FIRST_BOOK.getOneBased());
        assertEquals(new DeleteByIndexCommand(INDEX_FIRST_BOOK), command);
    }

    @Test
    public void parseCommand_deleteBySerialNumber() throws Exception {
        DeleteBySerialNumberCommand command = (DeleteBySerialNumberCommand) parser.parseCommand(
                DeleteBySerialNumberCommand.COMMAND_WORD
                        + " " + PREFIX_SERIAL_NUMBER + VALID_SERIAL_NUMBER_BOOK_1);
        assertEquals(new DeleteBySerialNumberCommand(new SerialNumber(VALID_SERIAL_NUMBER_BOOK_1)), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Borrower borrower = new BorrowerBuilder().build();
        EditBorrowerCommand.EditBorrowerDescriptor descriptor = new EditBorrowerDescriptorBuilder(borrower)
            .build();
        EditBorrowerCommand command = (EditBorrowerCommand) parser.parseCommand(EditBorrowerCommand
                .EditBorrowerDescriptor.COMMAND_WORD + " " + BorrowerUtil.getEditBorrowerDescriptorDetails(descriptor));
        assertEquals(new EditBorrowerCommand(descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + SPACE_AND_VALID_INDEX) instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        SerialNumberGenerator.setCatalog(new Catalog());
        Book book = new BookBuilder().build();
        FindCommand command = (FindCommand) parser.parseCommand(BookUtil.getFindCommand(book));
        assertEquals(new FindCommand(new BookPredicate()
            .setTitle(book.getTitle().value)
            .setAuthor(book.getAuthor().value)
            .setSerialNumber(book.getSerialNumber().value)
            .setGenres(book.getGenres())), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + SPACE_AND_VALID_INDEX) instanceof HelpCommand);
    }

    @Test
    public void parseCommand_loan() throws Exception {
        assertTrue(parser.parseCommand(
                LoanCommand.COMMAND_WORD + " " + PREFIX_SERIAL_NUMBER + VALID_SERIAL_NUMBER_BOOK_1)
                instanceof LoanCommand);
    }

    @Test
    public void parseCommand_register() throws Exception {
        BorrowerIdGenerator.setBorrowers(new BorrowerRecords());
        assertTrue(parser.parseCommand(
                RegisterCommand.COMMAND_WORD + " " + PREFIX_NAME + VALID_NAME_AMY + " "
                        + PREFIX_PHONE + VALID_PHONE_AMY + " " + PREFIX_EMAIL + VALID_EMAIL_AMY)
                instanceof RegisterCommand);
    }

    @Test
    public void parseCommand_unregister() throws Exception {
        BorrowerIdGenerator.setBorrowers(getTypicalBorrowerRecords());
        assertTrue(parser.parseCommand(
                UnregisterCommand.COMMAND_WORD + " " + PREFIX_BORROWER_ID + ID_FIRST_BORROWER)
                instanceof UnregisterCommand);
    }

    @Test
    public void parseCommand_serve() throws Exception {
        assertTrue(parser.parseCommand(
                ServeCommand.COMMAND_WORD + " " + PREFIX_BORROWER_ID + VALID_BORROWER_ID_1)
                instanceof ServeCommand);
    }

    @Test
    public void parseCommand_done() throws Exception {
        assertTrue(parser.parseCommand(DoneCommand.COMMAND_WORD) instanceof DoneCommand);
        assertTrue(parser.parseCommand(DoneCommand.COMMAND_WORD + SPACE_AND_VALID_INDEX) instanceof DoneCommand);
    }

    @Test
    public void parseCommand_return() throws Exception {
        assertTrue(parser.parseCommand(ReturnCommand.COMMAND_WORD + SPACE_AND_VALID_INDEX) instanceof ReturnCommand);
    }

    @Test
    public void parseCommand_renew() throws Exception {
        assertTrue(parser.parseCommand(RenewCommand.COMMAND_WORD + SPACE_AND_VALID_INDEX) instanceof RenewCommand);
    }

    @Test
    public void parseCommand_set() throws Exception {
        // Set command without any arguments
        assertEquals(parser.parseCommand(SetCommand.COMMAND_WORD), new ViewSettingsCommand());

        // Set command with arguments
        UserSettings userSettings = new UserSettingsBuilder().build();
        SetCommand.SetUserSettingsDescriptor descriptor = new SetUserSettingsDescriptorBuilder(userSettings).build();
        SetCommand command = (SetCommand) parser.parseCommand(SetCommand.COMMAND_WORD + " "
                + UserSettingsUtil.getSetUserSettingsDescriptorDetails(descriptor));
        assertEquals(new SetCommand(descriptor), command);
    }

    @Test
    public void parseCommand_pay() throws Exception {
        assertTrue(parser.parseCommand(PayCommand.COMMAND_WORD + SPACE_AND_VALID_DOLLAR_AMOUNT)
                instanceof PayCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
