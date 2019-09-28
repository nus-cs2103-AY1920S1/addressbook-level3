package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.AUTHOR_DESC_BOOK_1;
import static seedu.address.logic.commands.CommandTestUtil.AUTHOR_DESC_BOOK_2;
import static seedu.address.logic.commands.CommandTestUtil.GENRE_DESC_ACTION;
import static seedu.address.logic.commands.CommandTestUtil.GENRE_DESC_FICTION;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GENRE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SERIAL_NUMBER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.SERIAL_NUMBER_DESC_BOOK_1;
import static seedu.address.logic.commands.CommandTestUtil.SERIAL_NUMBER_DESC_BOOK_2;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_BOOK_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_BOOK_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_BOOK_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENRE_ACTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENRE_FICTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SERIAL_NUMBER_BOOK_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SERIAL_NUMBER_BOOK_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_BOOK_1;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENRE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_BOOK;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditBookDescriptor;
import seedu.address.model.book.SerialNumber;
import seedu.address.model.genre.Genre;
import seedu.address.testutil.EditBookDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_GENRE;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_TITLE_BOOK_1, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + TITLE_DESC_BOOK_1, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + TITLE_DESC_BOOK_1, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid serial number
        assertParseFailure(parser, "1" + INVALID_SERIAL_NUMBER_DESC, SerialNumber.MESSAGE_CONSTRAINTS);
        //invalid genre
        assertParseFailure(parser, "1" + INVALID_GENRE_DESC, Genre.MESSAGE_CONSTRAINTS);

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_SERIAL_NUMBER_DESC + AUTHOR_DESC_BOOK_1,
                SerialNumber.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + SERIAL_NUMBER_DESC_BOOK_2 + INVALID_SERIAL_NUMBER_DESC,
                SerialNumber.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid genre results in error
        assertParseFailure(parser, "1" + GENRE_DESC_FICTION + GENRE_DESC_ACTION + TAG_EMPTY,
                Genre.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + GENRE_DESC_FICTION + TAG_EMPTY + GENRE_DESC_ACTION,
                Genre.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + GENRE_DESC_FICTION + GENRE_DESC_ACTION,
                Genre.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_SERIAL_NUMBER_DESC + INVALID_GENRE_DESC,
                SerialNumber.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_BOOK;
        String userInput = targetIndex.getOneBased() + SERIAL_NUMBER_DESC_BOOK_2
                + AUTHOR_DESC_BOOK_1 + TITLE_DESC_BOOK_1 + GENRE_DESC_FICTION;

        EditCommand.EditBookDescriptor descriptor = new EditBookDescriptorBuilder().withTitle(VALID_TITLE_BOOK_1)
                .withSerialNumber(VALID_SERIAL_NUMBER_BOOK_2).withAuthor(VALID_AUTHOR_BOOK_1)
                .withGenres(VALID_GENRE_FICTION).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_BOOK;
        String userInput = targetIndex.getOneBased() + SERIAL_NUMBER_DESC_BOOK_2 + AUTHOR_DESC_BOOK_2;

        EditBookDescriptor descriptor = new EditBookDescriptorBuilder().withSerialNumber(VALID_SERIAL_NUMBER_BOOK_2)
                .withAuthor(VALID_AUTHOR_BOOK_2).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // title
        Index targetIndex = INDEX_THIRD_BOOK;
        String userInput = targetIndex.getOneBased() + TITLE_DESC_BOOK_1;
        EditBookDescriptor descriptor = new EditBookDescriptorBuilder().withTitle(VALID_TITLE_BOOK_1).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // serial number
        userInput = targetIndex.getOneBased() + SERIAL_NUMBER_DESC_BOOK_1;
        descriptor = new EditBookDescriptorBuilder().withSerialNumber(VALID_SERIAL_NUMBER_BOOK_1).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // author
        userInput = targetIndex.getOneBased() + AUTHOR_DESC_BOOK_1;
        descriptor = new EditBookDescriptorBuilder().withAuthor(VALID_AUTHOR_BOOK_1).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // genre
        userInput = targetIndex.getOneBased() + GENRE_DESC_FICTION;
        descriptor = new EditBookDescriptorBuilder().withGenres(VALID_GENRE_FICTION).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_BOOK;
        String userInput = targetIndex.getOneBased() + SERIAL_NUMBER_DESC_BOOK_1 + AUTHOR_DESC_BOOK_1
                + GENRE_DESC_FICTION + SERIAL_NUMBER_DESC_BOOK_1 + AUTHOR_DESC_BOOK_1 + GENRE_DESC_FICTION
                + SERIAL_NUMBER_DESC_BOOK_2 + AUTHOR_DESC_BOOK_2 + GENRE_DESC_ACTION;

        EditCommand.EditBookDescriptor descriptor = new EditBookDescriptorBuilder()
                .withSerialNumber(VALID_SERIAL_NUMBER_BOOK_2)
                .withAuthor(VALID_AUTHOR_BOOK_2).withGenres(VALID_GENRE_FICTION, VALID_GENRE_ACTION)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_BOOK;
        String userInput = targetIndex.getOneBased() + INVALID_SERIAL_NUMBER_DESC + SERIAL_NUMBER_DESC_BOOK_2;
        EditBookDescriptor descriptor = new EditBookDescriptorBuilder()
                .withSerialNumber(VALID_SERIAL_NUMBER_BOOK_2).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + AUTHOR_DESC_BOOK_2 + INVALID_SERIAL_NUMBER_DESC
                + SERIAL_NUMBER_DESC_BOOK_2;
        descriptor = new EditBookDescriptorBuilder().withSerialNumber(VALID_SERIAL_NUMBER_BOOK_2)
                .withAuthor(VALID_AUTHOR_BOOK_2).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_BOOK;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditBookDescriptor descriptor = new EditBookDescriptorBuilder().withGenres().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
