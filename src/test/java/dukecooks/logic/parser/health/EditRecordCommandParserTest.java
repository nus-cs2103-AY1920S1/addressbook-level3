package dukecooks.logic.parser.health;

import org.junit.jupiter.api.Test;

import dukecooks.commons.core.Messages;
import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.logic.commands.health.EditRecordCommand;
import dukecooks.logic.parser.CliSyntax;
import dukecooks.logic.parser.CommandParserTestUtil;
import dukecooks.model.health.components.Remark;
import dukecooks.model.health.components.Value;
import dukecooks.testutil.TypicalIndexes;
import dukecooks.testutil.health.EditRecordDescriptorBuilder;

public class EditRecordCommandParserTest {

    private static final String REMARK_EMPTY = " " + CliSyntax.PREFIX_REMARK;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditRecordCommand.MESSAGE_USAGE);

    private EditRecordCommandParser parser = new EditRecordCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_TYPE_CALORIES, MESSAGE_INVALID_FORMAT);

        // no field specified
        CommandParserTestUtil.assertParseFailure(parser, "1", EditRecordCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        CommandParserTestUtil.assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        CommandParserTestUtil.assertParseFailure(parser, "-5" + CommandTestUtil.TYPE_DESC_CALORIES,
                MESSAGE_INVALID_FORMAT);

        // zero index
        CommandParserTestUtil.assertParseFailure(parser, "0" + CommandTestUtil.TYPE_DESC_CALORIES,
                MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        CommandParserTestUtil.assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        CommandParserTestUtil.assertParseFailure(parser, "1 zxc/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.INVALID_REMARK_DESC,
                Remark.MESSAGE_CONSTRAINTS); // invalid remark

        // while parsing {@code PREFIX_REMARK} alone will reset the remarks of the {@code Record} being edited,
        // parsing it together with a valid remark results in error
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.REMARK_DESC_CALORIES
                + CommandTestUtil.REMARK_DESC_GLUCOSE + REMARK_EMPTY,
                Remark.MESSAGE_CONSTRAINTS);
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.REMARK_DESC_CALORIES
                + REMARK_EMPTY + CommandTestUtil.REMARK_DESC_GLUCOSE,
                Remark.MESSAGE_CONSTRAINTS);
        CommandParserTestUtil.assertParseFailure(parser, "1" + REMARK_EMPTY
                + CommandTestUtil.REMARK_DESC_CALORIES + CommandTestUtil.REMARK_DESC_GLUCOSE,
                Remark.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.INVALID_VALUE_DESC
                + CommandTestUtil.INVALID_REMARK_DESC, Value.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_recordTypeSpecified_failure() {
        // invalid type
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.INVALID_TYPE_DESC,
                Messages.MESSAGE_CANNOT_EDIT_RECORD_TYPE);

        // valid type
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.TYPE_DESC_CALORIES,
                Messages.MESSAGE_CANNOT_EDIT_RECORD_TYPE);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = TypicalIndexes.INDEX_SECOND_RECORD;
        String userInput = targetIndex.getOneBased()
                 + CommandTestUtil.REMARK_DESC_CALORIES + CommandTestUtil.VALUE_DESC_CALORIES
                 + CommandTestUtil.TIMESTAMP_DESC_CALORIES;

        EditRecordCommand.EditRecordDescriptor descriptor = new EditRecordDescriptorBuilder()
                .withRemarksToAdd(CommandTestUtil.VALID_REMARK_CALORIES)
                .withValue(CommandTestUtil.VALID_VALUE_CALORIES)
                .withTimestamp(CommandTestUtil.VALID_TIMESTAMP_CALORIES)
                .build();
        EditRecordCommand expectedCommand = new EditRecordCommand(targetIndex, descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }


    @Test
    public void parse_oneFieldSpecified_success() {
        Index targetIndex = TypicalIndexes.INDEX_THIRD_RECORD;

        // remarks
        String userInput = targetIndex.getOneBased() + CommandTestUtil.REMARK_DESC_CALORIES;
        EditRecordCommand.EditRecordDescriptor descriptor = new EditRecordDescriptorBuilder()
                .withRemarksToAdd(CommandTestUtil.VALID_REMARK_CALORIES)
                .build();
        EditRecordCommand expectedCommand = new EditRecordCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // remove remarks
        userInput = targetIndex.getOneBased() + CommandTestUtil.REMOVEREMARK_DESC_CALORIES;
        descriptor = new EditRecordDescriptorBuilder().withRemarksToRemove(CommandTestUtil.VALID_REMARK_CALORIES)
                .build();
        expectedCommand = new EditRecordCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // value
        userInput = targetIndex.getOneBased() + CommandTestUtil.VALUE_DESC_CALORIES;
        descriptor = new EditRecordDescriptorBuilder().withValue(CommandTestUtil.VALID_VALUE_CALORIES).build();
        expectedCommand = new EditRecordCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // timestamp
        userInput = targetIndex.getOneBased() + CommandTestUtil.TIMESTAMP_DESC_CALORIES;
        descriptor = new EditRecordDescriptorBuilder().withTimestamp(CommandTestUtil.VALID_TIMESTAMP_CALORIES).build();
        expectedCommand = new EditRecordCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = TypicalIndexes.INDEX_FIRST_RECORD;
        String userInput = targetIndex.getOneBased()
                + CommandTestUtil.REMARK_DESC_CALORIES + CommandTestUtil.REMARK_DESC_CALORIES
                + CommandTestUtil.REMARK_DESC_GLUCOSE;

        EditRecordCommand.EditRecordDescriptor descriptor = new EditRecordDescriptorBuilder()
                .withRemarksToAdd(CommandTestUtil.VALID_REMARK_CALORIES, CommandTestUtil.VALID_REMARK_GLUCOSE)
                .build();
        EditRecordCommand expectedCommand = new EditRecordCommand(targetIndex, descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetRemarks_success() {
        Index targetIndex = TypicalIndexes.INDEX_THIRD_RECORD;
        String userInput = targetIndex.getOneBased() + REMARK_EMPTY;

        EditRecordCommand.EditRecordDescriptor descriptor = new EditRecordDescriptorBuilder().withRemarksToAdd()
                .build();
        EditRecordCommand expectedCommand = new EditRecordCommand(targetIndex, descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }
}
