package io.xpire.logic.parser;

import static io.xpire.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static io.xpire.logic.commands.CommandTestUtil.EXPIRY_DATE_DESC_APPLE;
import static io.xpire.logic.commands.CommandTestUtil.EXPIRY_DATE_DESC_BANANA;
import static io.xpire.logic.commands.CommandTestUtil.EXPIRY_DATE_DESC_KIWI;
import static io.xpire.logic.commands.CommandTestUtil.INVALID_EXPIRY_DATE_DESC;
import static io.xpire.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static io.xpire.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static io.xpire.logic.commands.CommandTestUtil.NAME_DESC_APPLE;
import static io.xpire.logic.commands.CommandTestUtil.NAME_DESC_KIWI;
import static io.xpire.logic.commands.CommandTestUtil.TAG_DESC_FRUIT;
//import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_GREEN;
import static io.xpire.logic.commands.CommandTestUtil.VALID_EXPIRY_DATE_APPLE;
import static io.xpire.logic.commands.CommandTestUtil.VALID_EXPIRY_DATE_BANANA;
import static io.xpire.logic.commands.CommandTestUtil.VALID_EXPIRY_DATE_KIWI;
import static io.xpire.logic.commands.CommandTestUtil.VALID_NAME_APPLE;
import static io.xpire.logic.commands.CommandTestUtil.VALID_NAME_KIWI;
import static io.xpire.logic.commands.CommandTestUtil.VALID_TAG_FRUIT;
import static io.xpire.logic.parser.CliSyntax.PREFIX_TAG;
import static io.xpire.logic.parser.CommandParserTestUtil.assertParseFailure;
import static io.xpire.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static io.xpire.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static io.xpire.testutil.TypicalIndexes.INDEX_SECOND_ITEM;
import static io.xpire.testutil.TypicalIndexes.INDEX_THIRD_ITEM;

import org.junit.jupiter.api.Test;

import io.xpire.commons.core.index.Index;
import io.xpire.logic.commands.EditCommand;
import io.xpire.logic.commands.EditCommand.EditItemDescriptor;
import io.xpire.model.item.ExpiryDate;
import io.xpire.model.item.Name;
import io.xpire.model.tag.Tag;
import io.xpire.testutil.EditItemDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_KIWI, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_KIWI, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_KIWI, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_EXPIRY_DATE_DESC, ExpiryDate.MESSAGE_CONSTRAINTS); // invalid address
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid name followed by valid expiry date
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + VALID_EXPIRY_DATE_APPLE, Name.MESSAGE_CONSTRAINTS);

        // valid name followed by invalid expiry date. The test case for invalid expiry date followed by valid
        // expiry date is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + NAME_DESC_KIWI + INVALID_EXPIRY_DATE_DESC, ExpiryDate.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid tag results in error
        //Tests turned off so that empty "" tags CANNOT be parsed.
        //assertParseFailure(parser, "1" + TAG_DESC_GREEN + TAG_DESC_FRUIT + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        //assertParseFailure(parser, "1" + TAG_DESC_GREEN + TAG_EMPTY + TAG_DESC_FRUIT, Tag.MESSAGE_CONSTRAINTS);
        //assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_GREEN + TAG_DESC_FRUIT, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_EXPIRY_DATE_DESC,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_ITEM;
        String userInput = targetIndex.getOneBased() + NAME_DESC_APPLE + EXPIRY_DATE_DESC_APPLE + TAG_DESC_FRUIT;

        EditItemDescriptor descriptor = new EditItemDescriptorBuilder().withName(VALID_NAME_APPLE)
                .withExpiryDate(VALID_EXPIRY_DATE_APPLE)
                .withTags(VALID_TAG_FRUIT).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_ITEM;
        String userInput = targetIndex.getOneBased() + EXPIRY_DATE_DESC_BANANA;

        EditItemDescriptor descriptor = new EditItemDescriptorBuilder()
                .withExpiryDate(VALID_EXPIRY_DATE_BANANA).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_ITEM;
        String userInput = targetIndex.getOneBased() + NAME_DESC_APPLE;
        EditItemDescriptor descriptor = new EditItemDescriptorBuilder().withName(VALID_NAME_APPLE).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // expiry date
        userInput = targetIndex.getOneBased() + EXPIRY_DATE_DESC_KIWI;
        descriptor = new EditItemDescriptorBuilder().withExpiryDate(VALID_EXPIRY_DATE_KIWI).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FRUIT;
        descriptor = new EditItemDescriptorBuilder().withTags(VALID_TAG_FRUIT).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_ITEM;
        String userInput = targetIndex.getOneBased() + EXPIRY_DATE_DESC_KIWI + TAG_DESC_FRUIT
                + EXPIRY_DATE_DESC_KIWI + TAG_DESC_FRUIT + EXPIRY_DATE_DESC_KIWI + TAG_DESC_FRUIT;

        EditItemDescriptor descriptor = new EditItemDescriptorBuilder().withExpiryDate(VALID_EXPIRY_DATE_KIWI)
                .withTags(VALID_TAG_FRUIT)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // other valid values specified
        Index targetIndex = INDEX_FIRST_ITEM;
        String userInput = targetIndex.getOneBased() + INVALID_EXPIRY_DATE_DESC + EXPIRY_DATE_DESC_APPLE;
        EditItemDescriptor descriptor = new EditItemDescriptorBuilder().withExpiryDate(VALID_EXPIRY_DATE_APPLE).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_ITEM;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditItemDescriptor descriptor = new EditItemDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
