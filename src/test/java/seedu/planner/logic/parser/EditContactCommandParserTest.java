package seedu.planner.logic.parser;

import static seedu.planner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.planner.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.planner.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.planner.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.planner.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.planner.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.planner.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.planner.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.planner.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.planner.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.planner.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.planner.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.planner.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.planner.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.planner.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.planner.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.planner.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.planner.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.planner.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.planner.testutil.TypicalIndexes.INDEX_THIRD;

import org.junit.jupiter.api.Test;

import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.commands.editcommand.EditContactCommand;
import seedu.planner.logic.commands.editcommand.EditContactCommand.EditContactDescriptor;
import seedu.planner.model.contact.Email;
import seedu.planner.model.contact.Phone;
import seedu.planner.model.field.Address;
import seedu.planner.model.field.Name;
import seedu.planner.model.tag.Tag;
import seedu.planner.testutil.contact.EditContactDescriptorBuilder;

public class EditContactCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditContactCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, EditContactCommand.SECOND_COMMAND_WORD + " " + VALID_NAME_AMY,
                MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, EditContactCommand.SECOND_COMMAND_WORD + " " + "1",
                EditContactCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, EditContactCommand.SECOND_COMMAND_WORD + " " + "",
                MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, EditContactCommand.SECOND_COMMAND_WORD + " -5" + NAME_DESC_AMY,
                MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, EditContactCommand.SECOND_COMMAND_WORD + " 0" + NAME_DESC_AMY,
                MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, EditContactCommand.SECOND_COMMAND_WORD + " 1 some random string",
                MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, EditContactCommand.SECOND_COMMAND_WORD + " 1 i/ string",
                MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, EditContactCommand.SECOND_COMMAND_WORD + " 1" + INVALID_NAME_DESC,
                Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, EditContactCommand.SECOND_COMMAND_WORD + " 1" + INVALID_PHONE_DESC,
                Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, EditContactCommand.SECOND_COMMAND_WORD + " 1" + INVALID_EMAIL_DESC,
                Email.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, EditContactCommand.SECOND_COMMAND_WORD + " 1" + INVALID_ADDRESS_DESC,
                Address.MESSAGE_CONSTRAINTS); // invalid address
        assertParseFailure(parser, EditContactCommand.SECOND_COMMAND_WORD + " 1" + INVALID_TAG_DESC,
                Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid phone followed by valid email
        assertParseFailure(parser, EditContactCommand.SECOND_COMMAND_WORD + " 1" + INVALID_PHONE_DESC
                + EMAIL_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, EditContactCommand.SECOND_COMMAND_WORD + " 1" + PHONE_DESC_BOB
                + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Contact} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, EditContactCommand.SECOND_COMMAND_WORD + " 1" + TAG_DESC_FRIEND
                + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, EditContactCommand.SECOND_COMMAND_WORD + " 1" + TAG_DESC_FRIEND
                + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, EditContactCommand.SECOND_COMMAND_WORD + " 1" + TAG_EMPTY
                + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, EditContactCommand.SECOND_COMMAND_WORD + " 1" + INVALID_NAME_DESC
                        + INVALID_EMAIL_DESC + VALID_ADDRESS_AMY + VALID_PHONE_AMY, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = EditContactCommand.SECOND_COMMAND_WORD + " " + targetIndex.getOneBased() + PHONE_DESC_BOB
                + TAG_DESC_HUSBAND + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + NAME_DESC_AMY + TAG_DESC_FRIEND;

        EditContactDescriptor descriptor = new EditContactDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        EditContactCommand expectedCommand = new EditContactCommand(targetIndex, descriptor, false);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST;
        String userInput = EditContactCommand.SECOND_COMMAND_WORD + " " + targetIndex.getOneBased() + PHONE_DESC_BOB
                + EMAIL_DESC_AMY;

        EditContactDescriptor descriptor = new EditContactDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY).build();
        EditContactCommand expectedCommand = new EditContactCommand(targetIndex, descriptor, false);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD;
        String userInput = EditContactCommand.SECOND_COMMAND_WORD + " " + targetIndex.getOneBased() + NAME_DESC_AMY;
        EditContactDescriptor descriptor = new EditContactDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditContactCommand expectedCommand = new EditContactCommand(targetIndex, descriptor, false);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = EditContactCommand.SECOND_COMMAND_WORD + " " + targetIndex.getOneBased() + PHONE_DESC_AMY;
        descriptor = new EditContactDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditContactCommand(targetIndex, descriptor, false);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = EditContactCommand.SECOND_COMMAND_WORD + " " + targetIndex.getOneBased() + EMAIL_DESC_AMY;
        descriptor = new EditContactDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditContactCommand(targetIndex, descriptor, false);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = EditContactCommand.SECOND_COMMAND_WORD + " " + targetIndex.getOneBased() + ADDRESS_DESC_AMY;
        descriptor = new EditContactDescriptorBuilder().withAddress(VALID_ADDRESS_AMY).build();
        expectedCommand = new EditContactCommand(targetIndex, descriptor, false);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = EditContactCommand.SECOND_COMMAND_WORD + " " + targetIndex.getOneBased() + TAG_DESC_FRIEND;
        descriptor = new EditContactDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditContactCommand(targetIndex, descriptor, false);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST;
        String userInput = EditContactCommand.SECOND_COMMAND_WORD + " " + targetIndex.getOneBased() + PHONE_DESC_AMY
                + ADDRESS_DESC_AMY + EMAIL_DESC_AMY + TAG_DESC_FRIEND + PHONE_DESC_AMY + ADDRESS_DESC_AMY
                + EMAIL_DESC_AMY + TAG_DESC_FRIEND + PHONE_DESC_BOB + ADDRESS_DESC_BOB + EMAIL_DESC_BOB
                + TAG_DESC_HUSBAND;

        EditContactDescriptor descriptor = new EditContactDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        EditContactCommand expectedCommand = new EditContactCommand(targetIndex, descriptor, false);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST;
        String userInput = EditContactCommand.SECOND_COMMAND_WORD + " " + targetIndex.getOneBased()
                + INVALID_PHONE_DESC + PHONE_DESC_BOB;
        EditContactDescriptor descriptor = new EditContactDescriptorBuilder().withPhone(VALID_PHONE_BOB).build();
        EditContactCommand expectedCommand = new EditContactCommand(targetIndex, descriptor, false);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = EditContactCommand.SECOND_COMMAND_WORD + " " + targetIndex.getOneBased() + EMAIL_DESC_BOB
                + INVALID_PHONE_DESC + ADDRESS_DESC_BOB + PHONE_DESC_BOB;
        descriptor = new EditContactDescriptorBuilder().withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).build();
        expectedCommand = new EditContactCommand(targetIndex, descriptor, false);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD;
        String userInput = EditContactCommand.SECOND_COMMAND_WORD + " " + targetIndex.getOneBased() + TAG_EMPTY;

        EditContactDescriptor descriptor = new EditContactDescriptorBuilder().withTags().build();
        EditContactCommand expectedCommand = new EditContactCommand(targetIndex, descriptor, false);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
