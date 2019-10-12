package thrift.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static thrift.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

import thrift.logic.commands.AddIncomeCommand;
import thrift.logic.commands.CommandTestUtil;
import thrift.model.tag.Tag;
import thrift.model.transaction.Value;


public class AddIncomeCommandParserTest {
    private AddIncomeCommandParser parser = new AddIncomeCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // whitespace only preamble
        assertDoesNotThrow(() -> parser.parse(CommandTestUtil.PREAMBLE_WHITESPACE
                + CommandTestUtil.DESC_BURSARY + CommandTestUtil.VALUE_BURSARY + CommandTestUtil.REMARK_LAKSA
                + CommandTestUtil.TAG_BURSARY));

        // multiple tags - all accepted
        assertDoesNotThrow(() -> parser.parse(CommandTestUtil.DESC_BURSARY + CommandTestUtil.VALUE_BURSARY
                + CommandTestUtil.REMARK_LAKSA + CommandTestUtil.TAG_BURSARY + CommandTestUtil.TAG_AIRPODS));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        assertDoesNotThrow(() -> parser.parse(CommandTestUtil.DESC_BURSARY + CommandTestUtil.VALUE_BURSARY));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        // zero tags
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddIncomeCommand.MESSAGE_USAGE);

        // missing description prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_DESCRIPTION_BURSARY
                + CommandTestUtil.VALUE_BURSARY, expectedMessage);

        // missing value prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.DESC_BURSARY
                + CommandTestUtil.VALID_VALUE_BURSARY, expectedMessage);

        // all prefixes missing
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_DESCRIPTION_BURSARY
                + CommandTestUtil.VALID_VALUE_BURSARY, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid value
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.DESC_BURSARY
                + CommandTestUtil.INVALID_VALUE + CommandTestUtil.TAG_BURSARY, Value.VALUE_CONSTRAINTS);

        // invalid tag
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.DESC_BURSARY
                + CommandTestUtil.VALUE_BURSARY + CommandTestUtil.INVALID_TAG, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.DESC_BURSARY
                + CommandTestUtil.INVALID_VALUE + CommandTestUtil.INVALID_TAG, Value.VALUE_CONSTRAINTS);

        // non-empty preamble
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.PREAMBLE_NON_EMPTY
                + CommandTestUtil.DESC_BURSARY + CommandTestUtil.VALUE_BURSARY + CommandTestUtil.TAG_BURSARY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddIncomeCommand.MESSAGE_USAGE));
    }

}
