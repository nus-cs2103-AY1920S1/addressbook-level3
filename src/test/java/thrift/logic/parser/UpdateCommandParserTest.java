package thrift.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static thrift.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static thrift.logic.parser.CommandParserTestUtil.assertParseFailure;
import static thrift.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import thrift.commons.core.index.Index;
import thrift.logic.commands.CommandTestUtil;
import thrift.logic.commands.UpdateCommand;
import thrift.logic.commands.UpdateCommand.UpdateTransactionDescriptor;
import thrift.model.tag.Tag;
import thrift.model.transaction.Value;
import thrift.testutil.TypicalIndexes;
import thrift.testutil.UpdateTransactionDescriptorBuilder;

public class UpdateCommandParserTest {

    private static final String TAG_EMPTY = " " + CliSyntax.PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE);

    private UpdateCommandParser parser = new UpdateCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, CommandTestUtil.DESC_LAKSA, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, String.format(CommandTestUtil.INDEX_TOKEN + "1"), UpdateCommand.MESSAGE_NOT_UPDATED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, CommandTestUtil.INDEX_TOKEN + "-5"
                + CommandTestUtil.DESC_LAKSA, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, CommandTestUtil.INDEX_TOKEN + "0"
                + CommandTestUtil.DESC_LAKSA, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, CommandTestUtil.INDEX_TOKEN + "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, CommandTestUtil.INDEX_TOKEN + "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, " i/1"
                + CommandTestUtil.INVALID_VALUE, Value.VALUE_CONSTRAINTS); // invalid value
        assertParseFailure(parser, " i/1"
                + CommandTestUtil.INVALID_TAG, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid value followed by valid tag
        assertParseFailure(parser, " i/1"
                + CommandTestUtil.INVALID_VALUE + CommandTestUtil.TAG_LAKSA, Value.VALUE_CONSTRAINTS);

        // valid value followed by invalid value. The test case for invalid value followed by valid value
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, " i/1"
                + CommandTestUtil.VALUE_LAKSA + CommandTestUtil.INVALID_VALUE, Value.VALUE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Transaction} being updated,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, " i/1"
                + CommandTestUtil.TAG_LAKSA + CommandTestUtil.TAG_BRUNCH
                + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " i/1"
                + CommandTestUtil.TAG_LAKSA + TAG_EMPTY
                + CommandTestUtil.TAG_BRUNCH, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " i/1" + TAG_EMPTY
                + CommandTestUtil.TAG_LAKSA + CommandTestUtil.TAG_BRUNCH, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, " i/1" + CommandTestUtil.INVALID_VALUE
                + CommandTestUtil.INVALID_TAG, Value.VALUE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        assertDoesNotThrow(() -> new UpdateTransactionDescriptorBuilder()
                .withDescription(CommandTestUtil.VALID_DESCRIPTION_AIRPODS)
                .withValue(CommandTestUtil.VALID_VALUE_AIRPODS)
                .withTags(CommandTestUtil.VALID_TAG_ACCESSORY).build());
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = TypicalIndexes.INDEX_FIRST_TRANSACTION;
        String userInput = CommandTestUtil.INDEX_TOKEN + targetIndex.getOneBased() + CommandTestUtil.VALUE_LAKSA
                + CommandTestUtil.TAG_LAKSA;

        UpdateTransactionDescriptor descriptor = new UpdateTransactionDescriptorBuilder()
                .withValue(CommandTestUtil.VALID_VALUE_LAKSA).withTags(CommandTestUtil.VALID_TAG_LUNCH).build();
        UpdateCommand expectedCommand = new UpdateCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        Index targetIndex = TypicalIndexes.INDEX_THIRD_TRANSACTION;

        // description
        String userInput = CommandTestUtil.INDEX_TOKEN + targetIndex.getOneBased() + CommandTestUtil.DESC_AIRPODS;
        UpdateTransactionDescriptor descriptor = new UpdateTransactionDescriptorBuilder()
                .withDescription(CommandTestUtil.VALID_DESCRIPTION_AIRPODS).build();
        UpdateCommand expectedCommand = new UpdateCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = CommandTestUtil.INDEX_TOKEN + targetIndex.getOneBased() + CommandTestUtil.TAG_AIRPODS;
        descriptor = new UpdateTransactionDescriptorBuilder()
                .withTags(CommandTestUtil.VALID_TAG_ACCESSORY).build();
        expectedCommand = new UpdateCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = TypicalIndexes.INDEX_THIRD_TRANSACTION;
        String userInput = CommandTestUtil.INDEX_TOKEN + targetIndex.getOneBased() + CommandTestUtil.DESC_LAKSA
                + CommandTestUtil.VALUE_LAKSA + CommandTestUtil.TAG_LAKSA
                + CommandTestUtil.DESC_AIRPODS + CommandTestUtil.VALUE_AIRPODS
                + CommandTestUtil.TAG_AIRPODS;

        UpdateTransactionDescriptor descriptor = new UpdateTransactionDescriptorBuilder()
                .withDescription(CommandTestUtil.VALID_DESCRIPTION_AIRPODS)
                .withValue(CommandTestUtil.VALID_VALUE_AIRPODS)
                .withTags(CommandTestUtil.VALID_TAG_ACCESSORY, CommandTestUtil.VALID_TAG_LUNCH)
                .build();
        UpdateCommand expectedCommand = new UpdateCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = TypicalIndexes.INDEX_FIRST_TRANSACTION;
        String userInput = CommandTestUtil.INDEX_TOKEN + targetIndex.getOneBased()
                + CommandTestUtil.INVALID_VALUE + CommandTestUtil.VALUE_AIRPODS;
        UpdateTransactionDescriptor descriptor = new UpdateTransactionDescriptorBuilder()
                .withValue(CommandTestUtil.VALID_VALUE_AIRPODS).build();
        UpdateCommand expectedCommand = new UpdateCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = CommandTestUtil.INDEX_TOKEN + targetIndex.getOneBased() + CommandTestUtil.DESC_LAKSA
                + CommandTestUtil.INVALID_VALUE + CommandTestUtil.VALUE_LAKSA;
        descriptor = new UpdateTransactionDescriptorBuilder()
                .withDescription(CommandTestUtil.VALID_DESCRIPTION_LAKSA)
                .withValue(CommandTestUtil.VALID_VALUE_LAKSA)
                .build();
        expectedCommand = new UpdateCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = TypicalIndexes.INDEX_THIRD_TRANSACTION;
        String userInput = CommandTestUtil.INDEX_TOKEN + targetIndex.getOneBased() + TAG_EMPTY;

        UpdateTransactionDescriptor descriptor = new UpdateTransactionDescriptorBuilder().withTags().build();
        UpdateCommand expectedCommand = new UpdateCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
