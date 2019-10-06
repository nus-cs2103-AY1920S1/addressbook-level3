package seedu.weme.logic.parser;

import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.weme.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.weme.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.weme.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.weme.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.weme.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.weme.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.weme.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.weme.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.weme.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.weme.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.weme.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.weme.testutil.TypicalIndexes.INDEX_FIRST_MEME;
import static seedu.weme.testutil.TypicalIndexes.INDEX_SECOND_MEME;
import static seedu.weme.testutil.TypicalIndexes.INDEX_THIRD_MEME;

import org.junit.jupiter.api.Test;

import seedu.weme.commons.core.index.Index;
import seedu.weme.logic.commands.EditCommand;
import seedu.weme.logic.commands.EditCommand.EditMemeDescriptor;
import seedu.weme.model.meme.Address;
import seedu.weme.model.meme.Name;
import seedu.weme.model.tag.Tag;
import seedu.weme.testutil.EditMemeDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_ADDRESS_DESC, Address.MESSAGE_CONSTRAINTS); // invalid weme
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Meme} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + VALID_ADDRESS_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_MEME;
        String userInput = targetIndex.getOneBased() + TAG_DESC_HUSBAND
                + ADDRESS_DESC_AMY + NAME_DESC_AMY + TAG_DESC_FRIEND;

        EditMemeDescriptor descriptor = new EditMemeDescriptorBuilder().withName(VALID_NAME_AMY)
                .withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_MEME;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY + ADDRESS_DESC_BOB;

        EditCommand.EditMemeDescriptor descriptor = new EditMemeDescriptorBuilder().withName(VALID_NAME_AMY)
                .withAddress(VALID_ADDRESS_BOB).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_MEME;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditMemeDescriptor descriptor = new EditMemeDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // weme
        userInput = targetIndex.getOneBased() + ADDRESS_DESC_AMY;
        descriptor = new EditMemeDescriptorBuilder().withAddress(VALID_ADDRESS_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
        descriptor = new EditMemeDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_MEME;
        String userInput = targetIndex.getOneBased() + ADDRESS_DESC_AMY
                + TAG_DESC_FRIEND + ADDRESS_DESC_AMY + TAG_DESC_FRIEND
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND;

        EditMemeDescriptor descriptor = new EditMemeDescriptorBuilder()
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_MEME;
        String userInput = targetIndex.getOneBased() + INVALID_NAME_DESC + NAME_DESC_AMY;
        EditMemeDescriptor descriptor = new EditMemeDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + INVALID_NAME_DESC + ADDRESS_DESC_BOB
                + NAME_DESC_AMY;
        descriptor = new EditMemeDescriptorBuilder().withAddress(VALID_ADDRESS_BOB)
                .withName(VALID_NAME_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_MEME;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditMemeDescriptor descriptor = new EditMemeDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
