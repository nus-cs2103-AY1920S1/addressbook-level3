package seedu.eatme.logic.parser;

import static seedu.eatme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.eatme.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.eatme.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.eatme.testutil.TypicalIndexes.INDEX_FIRST_EATERY;
import static seedu.eatme.testutil.TypicalIndexes.INDEX_SECOND_EATERY;
import static seedu.eatme.testutil.TypicalIndexes.INDEX_THIRD_EATERY;

import org.junit.jupiter.api.Test;

import seedu.eatme.commons.core.index.Index;
import seedu.eatme.logic.commands.CommandTestUtil;
import seedu.eatme.logic.commands.EditCommand;
import seedu.eatme.logic.commands.EditCommand.EditEateryDescriptor;
import seedu.eatme.model.eatery.Address;
import seedu.eatme.model.eatery.Category;
import seedu.eatme.model.eatery.Name;
import seedu.eatme.model.eatery.Tag;
import seedu.eatme.testutil.EditEateryDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, CommandTestUtil.VALID_NAME_NO_PREFIX_KFC, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + CommandTestUtil.VALID_NAME_WITH_PREFIX_MAC, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + CommandTestUtil.VALID_NAME_WITH_PREFIX_MAC, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1"
            + CommandTestUtil.INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1"
            + CommandTestUtil.INVALID_ADDRESS_DESC, Address.MESSAGE_CONSTRAINTS); // invalid address
        assertParseFailure(parser, "1"
            + CommandTestUtil.INVALID_CATEGORY_DESC, Category.MESSAGE_CONSTRAINTS); // invalid category
        assertParseFailure(parser, "1"
            + CommandTestUtil.INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1"
            + CommandTestUtil.VALID_TAG_WITH_PREFIX_CHEAP
            + CommandTestUtil.VALID_TAG_WITH_PREFIX_NICE + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1"
                + CommandTestUtil.VALID_TAG_WITH_PREFIX_CHEAP + TAG_EMPTY
                + CommandTestUtil.VALID_TAG_WITH_PREFIX_CHEAP, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1"
            + TAG_EMPTY + CommandTestUtil.VALID_TAG_WITH_PREFIX_CHEAP
            + CommandTestUtil.VALID_TAG_WITH_PREFIX_NICE, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + CommandTestUtil.INVALID_NAME_DESC
                + CommandTestUtil.VALID_ADDRESS_NO_PREFIX_KFC ,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_EATERY;
        String userInput = targetIndex.getOneBased() + CommandTestUtil.VALID_TAG_WITH_PREFIX_NICE
                + CommandTestUtil.VALID_ADDRESS_WITH_PREFIX_KFC
                + CommandTestUtil.VALID_CATEGORY_WITH_PREFIX
                + CommandTestUtil.VALID_NAME_WITH_PREFIX_KFC
                + CommandTestUtil.VALID_TAG_WITH_PREFIX_NICE;

        EditEateryDescriptor descriptor = new EditEateryDescriptorBuilder()
                .withName(CommandTestUtil.VALID_NAME_NO_PREFIX_KFC)
                .withAddress(CommandTestUtil.VALID_ADDRESS_NO_PREFIX_KFC)
                .withCategory(CommandTestUtil.VALID_CATEGORY_NO_PREFIX)
                .withTags(CommandTestUtil.VALID_TAG_NO_PREFIX_NICE).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_EATERY;
        String userInput = targetIndex.getOneBased() + CommandTestUtil.VALID_NAME_WITH_PREFIX_KFC;
        EditEateryDescriptor descriptor = new EditEateryDescriptorBuilder()
            .withName(CommandTestUtil.VALID_NAME_NO_PREFIX_KFC).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + CommandTestUtil.VALID_ADDRESS_WITH_PREFIX_KFC;
        descriptor = new EditEateryDescriptorBuilder().withAddress(CommandTestUtil.VALID_ADDRESS_NO_PREFIX_KFC).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // category
        userInput = targetIndex.getOneBased() + CommandTestUtil.VALID_CATEGORY_WITH_PREFIX;
        descriptor = new EditEateryDescriptorBuilder().withCategory(CommandTestUtil.VALID_CATEGORY_NO_PREFIX).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + CommandTestUtil.VALID_TAG_WITH_PREFIX_NICE;
        descriptor = new EditEateryDescriptorBuilder().withTags(CommandTestUtil.VALID_TAG_NO_PREFIX_NICE).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_EATERY;
        String userInput = targetIndex.getOneBased() + CommandTestUtil.VALID_ADDRESS_WITH_PREFIX_KFC
                + CommandTestUtil.VALID_TAG_WITH_PREFIX_NICE
                + CommandTestUtil.VALID_ADDRESS_WITH_PREFIX_KFC
                + CommandTestUtil.VALID_TAG_WITH_PREFIX_NICE
                + CommandTestUtil.VALID_ADDRESS_WITH_PREFIX_KFC
                + CommandTestUtil.VALID_CATEGORY_WITH_PREFIX
                + CommandTestUtil.VALID_TAG_WITH_PREFIX_CHEAP;

        EditEateryDescriptor descriptor = new EditEateryDescriptorBuilder()
                .withAddress(CommandTestUtil.VALID_ADDRESS_NO_PREFIX_KFC)
                .withCategory(CommandTestUtil.VALID_CATEGORY_NO_PREFIX)
                .withTags(CommandTestUtil.VALID_TAG_NO_PREFIX_NICE, CommandTestUtil.VALID_TAG_NO_PREFIX_CHEAP)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_EATERY;
        String userInput = targetIndex.getOneBased() + CommandTestUtil.VALID_ADDRESS_WITH_PREFIX_KFC;
        EditEateryDescriptor descriptor = new EditEateryDescriptorBuilder()
                .withAddress(CommandTestUtil.VALID_ADDRESS_NO_PREFIX_KFC).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_EATERY;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditEateryDescriptor descriptor = new EditEateryDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
