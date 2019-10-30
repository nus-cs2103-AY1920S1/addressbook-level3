package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_BOOK_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENRE_ACTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENRE_FICTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SERIAL_NUMBER_BOOK_1;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENRE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERIAL_NUMBER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.FindCommand;
import seedu.address.model.book.BookPredicate;

public class FindCommandParserTest {

    private static final String SPACES = "     ";

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, SPACES,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new BookPredicate()
                        .setTitle("Harry Potter")
                        .setAuthor(VALID_AUTHOR_BOOK_1)
                        .setSerialNumber(VALID_SERIAL_NUMBER_BOOK_1)
                        .setGenres(VALID_GENRE_FICTION, VALID_GENRE_ACTION)
                        .setLoanState(Flag.LOANED));

        assertParseSuccess(parser, " t/Harry Potter" + " "
                + PREFIX_AUTHOR + VALID_AUTHOR_BOOK_1 + " "
                + PREFIX_SERIAL_NUMBER + VALID_SERIAL_NUMBER_BOOK_1 + " "
                + PREFIX_GENRE + VALID_GENRE_FICTION + " "
                + PREFIX_GENRE + VALID_GENRE_ACTION + " "
                + "-loaned", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, SPACES + " t/ Harry Potter "
                + PREFIX_AUTHOR + SPACES + VALID_AUTHOR_BOOK_1 + " "
                + PREFIX_SERIAL_NUMBER + SPACES + VALID_SERIAL_NUMBER_BOOK_1 + " "
                + PREFIX_GENRE + SPACES + VALID_GENRE_FICTION + " "
                + PREFIX_GENRE + VALID_GENRE_ACTION + " "
                + SPACES + "-loaned", expectedFindCommand);
    }

    @Test
    public void parse_duplicateLoanState_throwsParseException() {
        assertParseFailure(parser, " -loaned -available",
                Messages.MESSAGE_LOAN_STATE_CONSTRAINTS);
    }

}
