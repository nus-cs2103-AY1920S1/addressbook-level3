package seedu.moolah.logic.parser.event;

import static seedu.moolah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.moolah.logic.commands.CommandTestUtil.EVENT_CATEGORY_DESC_BUFFET;
import static seedu.moolah.logic.commands.CommandTestUtil.EVENT_DESCRIPTION_DESC_BUFFET;
import static seedu.moolah.logic.commands.CommandTestUtil.EVENT_PRICE_DESC_BIRTHDAY;
import static seedu.moolah.logic.commands.CommandTestUtil.EVENT_PRICE_DESC_BUFFET;
import static seedu.moolah.logic.commands.CommandTestUtil.EVENT_TIMESTAMP_DESC_BUFFET;
import static seedu.moolah.logic.commands.CommandTestUtil.INVALID_EVENT_CATEGORY_DESC;
import static seedu.moolah.logic.commands.CommandTestUtil.INVALID_EVENT_DESCRIPTION_DESC;
import static seedu.moolah.logic.commands.CommandTestUtil.INVALID_EVENT_PRICE_DESC;
import static seedu.moolah.logic.commands.CommandTestUtil.INVALID_EVENT_TIMESTAMP_DESC;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EVENT_CATEGORY_BUFFET;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EVENT_DESCRIPTION_BUFFET;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EVENT_PRICE_BIRTHDAY;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EVENT_PRICE_BUFFET;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EVENT_TIMESTAMP_BUFFET;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.moolah.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.moolah.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.moolah.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.moolah.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.moolah.testutil.TypicalIndexes.INDEX_THIRD;

import org.junit.jupiter.api.Test;

import seedu.moolah.commons.core.index.Index;
import seedu.moolah.logic.commands.event.EditEventCommand;
import seedu.moolah.model.expense.Category;
import seedu.moolah.model.expense.Description;
import seedu.moolah.model.expense.Price;
import seedu.moolah.model.expense.Timestamp;
import seedu.moolah.testutil.EditEventDescriptorBuilder;

public class EditEventCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_CATEGORY;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEventCommand.MESSAGE_USAGE);

    private EditEventCommandParser parser = new EditEventCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_EVENT_DESCRIPTION_BUFFET, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditEventCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + EVENT_DESCRIPTION_DESC_BUFFET, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + EVENT_DESCRIPTION_DESC_BUFFET, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(
                parser,
                "1" + INVALID_EVENT_DESCRIPTION_DESC, Description.MESSAGE_CONSTRAINTS); // invalid description
        assertParseFailure(parser,
                "1" + INVALID_EVENT_PRICE_DESC, Price.MESSAGE_CONSTRAINTS); // invalid price
        assertParseFailure(parser,
                "1" + INVALID_EVENT_CATEGORY_DESC, Category.MESSAGE_CONSTRAINTS); // invalid category
        assertParseFailure(parser,
                "1" + INVALID_EVENT_TIMESTAMP_DESC, Timestamp.MESSAGE_CONSTRAINTS_GENERAL); // invalid timestamp

        // invalid price followed by valid description
        assertParseFailure(
                parser,
                "1" + INVALID_EVENT_PRICE_DESC + VALID_EVENT_DESCRIPTION_BUFFET, Price.MESSAGE_CONSTRAINTS);

        // valid price followed by invalid price. The test case for invalid price followed by valid price
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser,
                "1" + EVENT_PRICE_DESC_BIRTHDAY + INVALID_EVENT_PRICE_DESC, Price.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(
                parser,
                "1" + INVALID_EVENT_DESCRIPTION_DESC + INVALID_EVENT_PRICE_DESC
                        + VALID_EVENT_CATEGORY_BUFFET + VALID_EVENT_TIMESTAMP_BUFFET,
                Description.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + EVENT_PRICE_DESC_BIRTHDAY + EVENT_DESCRIPTION_DESC_BUFFET
                + EVENT_CATEGORY_DESC_BUFFET + EVENT_TIMESTAMP_DESC_BUFFET;

        EditEventCommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withDescription(VALID_EVENT_DESCRIPTION_BUFFET)
                .withPrice(VALID_EVENT_PRICE_BIRTHDAY)
                .withCategory(VALID_EVENT_CATEGORY_BUFFET)
                .withTimestamp(VALID_EVENT_TIMESTAMP_BUFFET).build();

        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + EVENT_PRICE_DESC_BIRTHDAY;

        EditEventCommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withPrice(VALID_EVENT_PRICE_BIRTHDAY).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // description
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + EVENT_DESCRIPTION_DESC_BUFFET;
        EditEventCommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withDescription(VALID_EVENT_DESCRIPTION_BUFFET).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // price
        userInput = targetIndex.getOneBased() + EVENT_PRICE_DESC_BUFFET;
        descriptor = new EditEventDescriptorBuilder().withPrice(VALID_EVENT_PRICE_BUFFET).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // category
        userInput = targetIndex.getOneBased() + EVENT_CATEGORY_DESC_BUFFET;
        descriptor = new EditEventDescriptorBuilder().withCategory(VALID_EVENT_CATEGORY_BUFFET).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // timestamp
        userInput = targetIndex.getOneBased() + EVENT_TIMESTAMP_DESC_BUFFET;
        descriptor = new EditEventDescriptorBuilder().withTimestamp(VALID_EVENT_TIMESTAMP_BUFFET).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + INVALID_EVENT_PRICE_DESC + EVENT_PRICE_DESC_BIRTHDAY;
        EditEventCommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withPrice(VALID_EVENT_PRICE_BIRTHDAY).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + INVALID_EVENT_PRICE_DESC + EVENT_PRICE_DESC_BIRTHDAY;
        descriptor = new EditEventDescriptorBuilder().withPrice(VALID_EVENT_PRICE_BIRTHDAY).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
