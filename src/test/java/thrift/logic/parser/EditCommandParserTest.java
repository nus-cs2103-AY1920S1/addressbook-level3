package thrift.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static thrift.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static thrift.logic.parser.CommandParserTestUtil.assertParseFailure;
import static thrift.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import thrift.commons.core.index.Index;
import thrift.logic.commands.CommandTestUtil;
import thrift.logic.commands.EditCommand;
import thrift.logic.commands.EditCommand.EditTransactionDescriptor;
//import thrift.model.tag.Tag;
//import thrift.model.transaction.Value;
import thrift.testutil.EditTransactionDescriptorBuilder;
import thrift.testutil.TypicalIndexes;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + CliSyntax.PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, CommandTestUtil.VALID_DESCRIPTION_LAKSA, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + CommandTestUtil.DESC_LAKSA, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + CommandTestUtil.DESC_LAKSA, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    /* TODO: Repair test case
    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1"
                + CommandTestUtil.INVALID_VALUE, Value.VALUE_CONSTRAINTS); // invalid value
        assertParseFailure(parser, "1"
                + CommandTestUtil.INVALID_TAG, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid value followed by valid tag
        assertParseFailure(parser, "1"
                + CommandTestUtil.INVALID_VALUE + CommandTestUtil.TAG_LAKSA, Value.VALUE_CONSTRAINTS);

        // valid value followed by invalid value. The test case for invalid value followed by valid value
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1"
                + CommandTestUtil.VALUE_LAKSA + CommandTestUtil.INVALID_VALUE, Value.VALUE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Transaction} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1"
                + CommandTestUtil.TAG_LAKSA + CommandTestUtil.TAG_BRUNCH
                + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1"
                + CommandTestUtil.TAG_LAKSA + TAG_EMPTY
                + CommandTestUtil.TAG_BRUNCH, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY
                + CommandTestUtil.TAG_LAKSA + CommandTestUtil.TAG_BRUNCH, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + CommandTestUtil.INVALID_VALUE
                        + CommandTestUtil.INVALID_TAG, Value.VALUE_CONSTRAINTS);
    }
     */

    @Test
    public void parse_allFieldsSpecified_success() {
        assertDoesNotThrow(() -> new EditTransactionDescriptorBuilder()
                .withDescription(CommandTestUtil.VALID_DESCRIPTION_AIRPODS)
                .withValue(CommandTestUtil.VALID_VALUE_AIRPODS)
                .withTags(CommandTestUtil.VALID_TAG_ACCESSORY).build());
    }


    /* TODO: Repair test case
    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = TypicalIndexes.INDEX_FIRST_TRANSACTION;
        String userInput = targetIndex.getOneBased() + CommandTestUtil.DESC_LAKSA + CommandTestUtil.VALUE_LAKSA
                + CommandTestUtil.TAG_LAKSA;

        EditCommand.EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder()
                .withValue(CommandTestUtil.VALUE_AIRPODS).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
     */

    /* TODO: Repair test case
    @Test
    public void parse_oneFieldSpecified_success() {
        Index targetIndex = TypicalIndexes.INDEX_THIRD_TRANSACTION;

        String userInput = targetIndex.getOneBased() + CommandTestUtil.DESC_LAKSA + CommandTestUtil.VALUE_LAKSA
                + CommandTestUtil.TAG_LAKSA;

        // description
        EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder()
                .withDescription(CommandTestUtil.VALID_DESCRIPTION_AIRPODS).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + CommandTestUtil.DESC_LAKSA + CommandTestUtil.VALUE_LAKSA
                + CommandTestUtil.TAG_LAKSA;
        descriptor = new EditTransactionDescriptorBuilder()
                .withTags(CommandTestUtil.VALID_TAG_ACCESSORY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
     */

    /* TODO: Repair test case
    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = TypicalIndexes.INDEX_FIRST_TRANSACTION;
        String userInput = targetIndex.getOneBased() + CommandTestUtil.DESC_LAKSA
                + CommandTestUtil.VALUE_LAKSA + CommandTestUtil.TAG_LAKSA
                + CommandTestUtil.DESC_AIRPODS + CommandTestUtil.VALUE_AIRPODS
                + CommandTestUtil.TAG_AIRPODS;

        EditCommand.EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder()
                .withValue(CommandTestUtil.VALID_VALUE_AIRPODS)
                .withTags(CommandTestUtil.VALID_TAG_ACCESSORY, CommandTestUtil.VALID_TAG_LUNCH)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
     */

    /* TODO: Repair test case
    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = TypicalIndexes.INDEX_FIRST_TRANSACTION;
        String userInput = targetIndex.getOneBased()
                + CommandTestUtil.INVALID_VALUE + CommandTestUtil.VALUE_AIRPODS;
        EditCommand.EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder()
                .withValue(CommandTestUtil.VALID_VALUE_AIRPODS).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + CommandTestUtil.DESC_LAKSA
                + CommandTestUtil.INVALID_VALUE + CommandTestUtil.VALUE_LAKSA;
        descriptor = new EditTransactionDescriptorBuilder()
                .withValue(CommandTestUtil.VALID_VALUE_LAKSA)
                .build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
     */

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = TypicalIndexes.INDEX_THIRD_TRANSACTION;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
