package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.AUTHOR_DESC_BOOK_1;
import static seedu.address.logic.commands.CommandTestUtil.AUTHOR_DESC_BOOK_2;
import static seedu.address.logic.commands.CommandTestUtil.GENRE_DESC_ACTION;
import static seedu.address.logic.commands.CommandTestUtil.GENRE_DESC_FICTION;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GENRE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SERIAL_NUMBER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.SERIAL_NUMBER_DESC_BOOK_1;
import static seedu.address.logic.commands.CommandTestUtil.SERIAL_NUMBER_DESC_BOOK_2;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_BOOK_1;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_BOOK_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_BOOK_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENRE_ACTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENRE_FICTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SERIAL_NUMBER_BOOK_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_BOOK_2;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalBooks.BOOK_1;
import static seedu.address.testutil.TypicalBooks.BOOK_2;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.Catalog;
import seedu.address.model.book.Book;
import seedu.address.model.book.SerialNumber;
import seedu.address.model.book.SerialNumberGenerator;
import seedu.address.model.genre.Genre;
import seedu.address.testutil.BookBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Book expectedBook = new BookBuilder(BOOK_1).withGenres(VALID_GENRE_FICTION).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TITLE_DESC_BOOK_1 + SERIAL_NUMBER_DESC_BOOK_1
                + AUTHOR_DESC_BOOK_1 + GENRE_DESC_FICTION, new AddCommand(expectedBook));

        //need to add test to check unique serial number

        // multiple genres - all accepted
        Book expectedBookMultipleTags = new BookBuilder(BOOK_2).withGenres(VALID_GENRE_FICTION, VALID_GENRE_ACTION)
                .build();
        assertParseSuccess(parser, TITLE_DESC_BOOK_2 + SERIAL_NUMBER_DESC_BOOK_2 + AUTHOR_DESC_BOOK_2
                + GENRE_DESC_FICTION + GENRE_DESC_ACTION, new AddCommand(expectedBookMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Book expectedBook = new BookBuilder(BOOK_1).withGenres().build();
        assertParseSuccess(parser, TITLE_DESC_BOOK_1 + SERIAL_NUMBER_DESC_BOOK_1 + AUTHOR_DESC_BOOK_1,
                new AddCommand(expectedBook));

        // missing serial number
        SerialNumberGenerator.setCatalog(new Catalog());
        assertParseSuccess(parser, TITLE_DESC_BOOK_1 + AUTHOR_DESC_BOOK_1,
                new AddCommand(expectedBook));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing title prefix
        assertParseFailure(parser, VALID_TITLE_BOOK_2 + SERIAL_NUMBER_DESC_BOOK_2 + AUTHOR_DESC_BOOK_2,
                expectedMessage);

        // missing author prefix
        assertParseFailure(parser, TITLE_DESC_BOOK_2 + SERIAL_NUMBER_DESC_BOOK_2 + VALID_AUTHOR_BOOK_2,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_TITLE_BOOK_2 + VALID_SERIAL_NUMBER_BOOK_2 + VALID_AUTHOR_BOOK_2,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid serial number
        assertParseFailure(parser, TITLE_DESC_BOOK_2 + INVALID_SERIAL_NUMBER_DESC + AUTHOR_DESC_BOOK_2
                + GENRE_DESC_ACTION + GENRE_DESC_FICTION, SerialNumber.MESSAGE_CONSTRAINTS);

        // invalid genre
        assertParseFailure(parser, TITLE_DESC_BOOK_2 + SERIAL_NUMBER_DESC_BOOK_2 + AUTHOR_DESC_BOOK_2
                + INVALID_GENRE_DESC + VALID_GENRE_FICTION, Genre.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, TITLE_DESC_BOOK_2 + INVALID_SERIAL_NUMBER_DESC + AUTHOR_DESC_BOOK_2,
                SerialNumber.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + TITLE_DESC_BOOK_2 + SERIAL_NUMBER_DESC_BOOK_2
                + AUTHOR_DESC_BOOK_2 + GENRE_DESC_ACTION + GENRE_DESC_FICTION,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
