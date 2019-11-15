package dukecooks.logic.parser.health;

import static dukecooks.testutil.health.TypicalRecords.GLUCOSE;

import org.junit.jupiter.api.Test;

import dukecooks.commons.core.Messages;
import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.logic.commands.health.AddRecordCommand;
import dukecooks.logic.parser.CommandParserTestUtil;
import dukecooks.model.health.components.Record;
import dukecooks.model.health.components.Remark;
import dukecooks.model.health.components.Type;
import dukecooks.testutil.health.RecordBuilder;

public class AddRecordCommandParserTest {
    private AddRecordCommandParser parser = new AddRecordCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Record expectedRecord = new RecordBuilder(GLUCOSE).build();

        // whitespace only preamble
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.PREAMBLE_WHITESPACE
                + CommandTestUtil.TYPE_DESC_GLUCOSE
                 + CommandTestUtil.REMARK_DESC_GLUCOSE + CommandTestUtil.VALUE_DESC_GLUCOSE
                + CommandTestUtil.TIMESTAMP_DESC_GLUCOSE, new AddRecordCommand(expectedRecord));

        // multiple types - last type accepted
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.TYPE_DESC_GLUCOSE
                + CommandTestUtil.TYPE_DESC_GLUCOSE
                + CommandTestUtil.REMARK_DESC_GLUCOSE + CommandTestUtil.VALUE_DESC_GLUCOSE
                + CommandTestUtil.TIMESTAMP_DESC_GLUCOSE, new AddRecordCommand(expectedRecord));

        // multiple remarks - all accepted
        Record expectedRecordMultipleRemarks = new RecordBuilder(GLUCOSE)
                .withRemarks(CommandTestUtil.VALID_REMARK_GLUCOSE, CommandTestUtil.VALID_REMARK_CALORIES)
                .build();
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.TYPE_DESC_GLUCOSE
                + CommandTestUtil.REMARK_DESC_GLUCOSE + CommandTestUtil.REMARK_DESC_CALORIES
                + CommandTestUtil.VALUE_DESC_GLUCOSE + CommandTestUtil.TIMESTAMP_DESC_GLUCOSE,
                new AddRecordCommand(expectedRecordMultipleRemarks));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddRecordCommand.MESSAGE_USAGE);

        // missing type prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_TYPE_GLUCOSE,
                expectedMessage);

        // all prefixes missing
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_TYPE_GLUCOSE,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid type
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_TYPE_DESC
                + CommandTestUtil.REMARK_DESC_GLUCOSE
                + CommandTestUtil.VALUE_DESC_GLUCOSE + CommandTestUtil.TIMESTAMP_DESC_GLUCOSE,
                Type.messageConstraints());

        // invalid remark
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.TYPE_DESC_GLUCOSE
                + CommandTestUtil.INVALID_REMARK_DESC
                + CommandTestUtil.VALUE_DESC_GLUCOSE + CommandTestUtil.TIMESTAMP_DESC_GLUCOSE,
                Remark.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_TYPE_DESC
                + CommandTestUtil.INVALID_REMARK_DESC
                + CommandTestUtil.VALUE_DESC_GLUCOSE + CommandTestUtil.TIMESTAMP_DESC_GLUCOSE,
                Type.messageConstraints());

        // non-empty preamble
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.PREAMBLE_NON_EMPTY
                + CommandTestUtil.TYPE_DESC_GLUCOSE + CommandTestUtil.INVALID_REMARK_DESC
                + CommandTestUtil.VALUE_DESC_GLUCOSE + CommandTestUtil.TIMESTAMP_DESC_GLUCOSE,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddRecordCommand.MESSAGE_USAGE));
    }
}
