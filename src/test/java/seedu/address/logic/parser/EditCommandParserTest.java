package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ACTIVITY2;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TITLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_ACTIVITY;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_ACTIVITY2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ACTIVITY_TITLE2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditActivityDescriptor;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditActivityDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_preambleTests() {
        // Non-empty preamble -> failure
        assertParseFailure(parser, "   hi   " + VALID_EMAIL_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        // Whitespace preamble -> success
        assertParseSuccess(parser, "   " + TITLE_DESC_ACTIVITY2,
                new EditCommand(new EditPersonDescriptor(), DESC_ACTIVITY2));
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, INVALID_ADDRESS_DESC, Address.MESSAGE_CONSTRAINTS); // invalid address
        assertParseFailure(parser, INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid phone followed by valid email
        assertParseFailure(parser, INVALID_PHONE_DESC + EMAIL_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, PHONE_DESC_BOB + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_ADDRESS_AMY + VALID_PHONE_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = PHONE_DESC_BOB + TAG_DESC_HUSBAND + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + NAME_DESC_AMY + TAG_DESC_FRIEND + TITLE_DESC_ACTIVITY2;

        EditPersonDescriptor pd = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        EditCommand expectedCommand = new EditCommand(pd, DESC_ACTIVITY2);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        // including activity
        String userInput = PHONE_DESC_BOB + EMAIL_DESC_AMY + TITLE_DESC_ACTIVITY2;

        EditPersonDescriptor pd = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY).build();
        EditCommand expectedCommand = new EditCommand(pd, DESC_ACTIVITY2);

        assertParseSuccess(parser, userInput, expectedCommand);

        // excluding activity
        userInput = PHONE_DESC_BOB + EMAIL_DESC_AMY;

        expectedCommand = new EditCommand(pd, new EditActivityDescriptor());

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        String userInput = NAME_DESC_AMY;
        EditPersonDescriptor pd = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditActivityDescriptor ad = new EditActivityDescriptor();
        EditCommand expectedCommand = new EditCommand(pd, ad);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = PHONE_DESC_AMY;
        pd = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditCommand(pd, ad);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = EMAIL_DESC_AMY;
        pd = new EditPersonDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditCommand(pd, ad);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = ADDRESS_DESC_AMY;
        pd = new EditPersonDescriptorBuilder().withAddress(VALID_ADDRESS_AMY).build();
        expectedCommand = new EditCommand(pd, ad);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = TAG_DESC_FRIEND;
        pd = new EditPersonDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditCommand(pd, ad);
        assertParseSuccess(parser, userInput, expectedCommand);

        // title
        userInput = TITLE_DESC_ACTIVITY2;
        pd = new EditPersonDescriptor();
        ad = new EditActivityDescriptorBuilder().withTitle(VALID_ACTIVITY_TITLE2).build();
        expectedCommand = new EditCommand(pd, ad);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        String userInput = PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY + TITLE_DESC_ACTIVITY
                + TAG_DESC_FRIEND + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY + TAG_DESC_FRIEND
                + PHONE_DESC_BOB + ADDRESS_DESC_BOB + EMAIL_DESC_BOB + TAG_DESC_HUSBAND + TITLE_DESC_ACTIVITY2;

        EditPersonDescriptor pd = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        EditCommand expectedCommand = new EditCommand(pd, DESC_ACTIVITY2);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        String userInput = INVALID_PHONE_DESC + PHONE_DESC_BOB;
        EditPersonDescriptor pd = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB).build();
        EditCommand expectedCommand = new EditCommand(pd, new EditActivityDescriptor());
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = EMAIL_DESC_BOB + INVALID_PHONE_DESC + ADDRESS_DESC_BOB + INVALID_TITLE_DESC
                + PHONE_DESC_BOB + TITLE_DESC_ACTIVITY2;
        pd = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).build();
        expectedCommand = new EditCommand(pd, DESC_ACTIVITY2);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        String userInput = TAG_EMPTY + TITLE_DESC_ACTIVITY2;

        EditPersonDescriptor pd = new EditPersonDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(pd, DESC_ACTIVITY2);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
