package calofit.logic.parser;

import static calofit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

import calofit.commons.core.index.Index;
import calofit.logic.commands.CommandTestUtil;
import calofit.logic.commands.EditCommand;
import calofit.model.dish.Name;
import calofit.model.tag.Tag;
import calofit.testutil.EditDishDescriptorBuilder;
import calofit.testutil.TypicalIndexes;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + CliSyntax.PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_NAME_DUCK_RICE, MESSAGE_INVALID_FORMAT);

        // no field specified
        CommandParserTestUtil.assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        CommandParserTestUtil.assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        CommandParserTestUtil.assertParseFailure(parser, "-5"
                + CommandTestUtil.NAME_DESC_DUCK_RICE, MESSAGE_INVALID_FORMAT);

        // zero index
        CommandParserTestUtil.assertParseFailure(parser, "0"
                + CommandTestUtil.NAME_DESC_DUCK_RICE, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        CommandParserTestUtil.assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        CommandParserTestUtil.assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        CommandParserTestUtil.assertParseFailure(parser,
                "1" + CommandTestUtil.INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        CommandParserTestUtil.assertParseFailure(parser,
                "1" + CommandTestUtil.INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Dish} being edited,
        // parsing it together with a valid tag results in error
        CommandParserTestUtil.assertParseFailure(parser,
                "1" + CommandTestUtil.TAG_DESC_EXPENSIVE
                        + CommandTestUtil.TAG_DESC_SALTY + TAG_EMPTY, EditCommand.MESSAGE_NO_SIMULTANEOUS_CLEAR_ADD);
        CommandParserTestUtil.assertParseFailure(parser,
                "1" + CommandTestUtil.TAG_DESC_EXPENSIVE + TAG_EMPTY
                        + CommandTestUtil.TAG_DESC_SALTY, EditCommand.MESSAGE_NO_SIMULTANEOUS_CLEAR_ADD);
        CommandParserTestUtil.assertParseFailure(parser,
                "1" + TAG_EMPTY + CommandTestUtil.TAG_DESC_EXPENSIVE
                        + CommandTestUtil.TAG_DESC_SALTY, EditCommand.MESSAGE_NO_SIMULTANEOUS_CLEAR_ADD);

    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = TypicalIndexes.INDEX_SECOND_MEAL;
        String userInput = targetIndex.getOneBased() + CommandTestUtil.TAG_DESC_SALTY
                + CommandTestUtil.NAME_DESC_DUCK_RICE + CommandTestUtil.TAG_DESC_EXPENSIVE;

        EditCommand.EditDishDescriptor descriptor = new EditDishDescriptorBuilder()
                .withName(CommandTestUtil.VALID_NAME_DUCK_RICE)
                .withTags(CommandTestUtil.VALID_TAG_SALTY, CommandTestUtil.VALID_TAG_EXPENSIVE).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = TypicalIndexes.INDEX_THIRD_MEAL;
        String userInput = targetIndex.getOneBased() + CommandTestUtil.NAME_DESC_DUCK_RICE;
        EditCommand.EditDishDescriptor descriptor = new EditDishDescriptorBuilder()
                .withName(CommandTestUtil.VALID_NAME_DUCK_RICE).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + CommandTestUtil.TAG_DESC_EXPENSIVE;
        descriptor = new EditDishDescriptorBuilder().withTags(CommandTestUtil.VALID_TAG_EXPENSIVE).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = TypicalIndexes.INDEX_THIRD_MEAL;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditCommand.EditDishDescriptor descriptor = new EditDishDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }
}
