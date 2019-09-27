package thrift.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static thrift.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

import thrift.logic.commands.AddExpenseCommand;
import thrift.logic.commands.CommandTestUtil;
import thrift.model.tag.Tag;
import thrift.model.transaction.Value;

public class AddExpenseCommandParserTest {
    private AddExpenseCommandParser parser = new AddExpenseCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // whitespace only preamble
        assertDoesNotThrow(() -> parser.parse(CommandTestUtil.PREAMBLE_WHITESPACE
                + CommandTestUtil.DESC_LAKSA + CommandTestUtil.VALUE_LAKSA
                + CommandTestUtil.TAG_LAKSA));

        // multiple tags - all accepted
        assertDoesNotThrow(() -> parser.parse(CommandTestUtil.DESC_LAKSA
                + CommandTestUtil.VALUE_LAKSA + CommandTestUtil.TAG_LAKSA + CommandTestUtil.TAG_BRUNCH));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        assertDoesNotThrow(() -> parser.parse(CommandTestUtil.DESC_LAKSA + CommandTestUtil.VALUE_LAKSA));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddExpenseCommand.MESSAGE_USAGE);

        // missing description prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_DESCRIPTION_LAKSA
                + CommandTestUtil.VALUE_LAKSA, expectedMessage);

        // missing value prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.DESC_LAKSA
                + CommandTestUtil.VALID_VALUE_LAKSA, expectedMessage);

        // all prefixes missing
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_DESCRIPTION_LAKSA
                        + CommandTestUtil.VALID_VALUE_LAKSA, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {


        // invalid value
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.DESC_LAKSA
                + CommandTestUtil.INVALID_VALUE + CommandTestUtil.TAG_LAKSA
                + CommandTestUtil.TAG_BRUNCH, Value.VALUE_CONSTRAINTS);

        // invalid tag
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.DESC_LAKSA
                + CommandTestUtil.VALUE_LAKSA + CommandTestUtil.INVALID_TAG, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.DESC_LAKSA
                + CommandTestUtil.INVALID_VALUE + CommandTestUtil.INVALID_TAG,
                Value.VALUE_CONSTRAINTS);

        // non-empty preamble
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.PREAMBLE_NON_EMPTY
                + CommandTestUtil.DESC_LAKSA + CommandTestUtil.VALUE_LAKSA
                + CommandTestUtil.TAG_LAKSA + CommandTestUtil.TAG_BRUNCH,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddExpenseCommand.MESSAGE_USAGE));
    }
}
