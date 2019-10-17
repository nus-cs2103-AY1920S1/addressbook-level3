package tagline.logic.parser;

import static tagline.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tagline.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static tagline.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static tagline.logic.commands.CommandTestUtil.CONTACT_ID_ONE;
import static tagline.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static tagline.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static tagline.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static tagline.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static tagline.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static tagline.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static tagline.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static tagline.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static tagline.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static tagline.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static tagline.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static tagline.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static tagline.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static tagline.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static tagline.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static tagline.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tagline.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import tagline.logic.commands.contact.EditContactCommand;
import tagline.logic.commands.contact.EditContactCommand.EditContactDescriptor;
import tagline.logic.parser.contact.EditCommandParser;
import tagline.model.contact.ContactId;
import tagline.model.contact.Email;
import tagline.model.contact.Name;
import tagline.model.contact.Phone;
import tagline.testutil.EditContactDescriptorBuilder;

public class EditContactCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditContactCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditContactCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative id
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + EMAIL_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + PHONE_DESC_BOB + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_ADDRESS_AMY + VALID_PHONE_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        ContactId targetContactId = CONTACT_ID_ONE;

        String userInput = targetContactId.toString() + PHONE_DESC_BOB
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + NAME_DESC_AMY;

        EditContactDescriptor descriptor = new EditContactDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withContactId(CONTACT_ID_ONE).build();
        EditContactCommand expectedCommand = new EditContactCommand(targetContactId, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        ContactId targetContactId = CONTACT_ID_ONE;
        String userInput = targetContactId.toString() + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        EditContactDescriptor descriptor = new EditContactDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY).build();
        EditContactCommand expectedCommand = new EditContactCommand(targetContactId, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        ContactId targetContactId = CONTACT_ID_ONE;
        String userInput = targetContactId.toString() + NAME_DESC_AMY;
        EditContactDescriptor descriptor = new EditContactDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditContactCommand expectedCommand = new EditContactCommand(targetContactId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetContactId.toString() + PHONE_DESC_AMY;
        descriptor = new EditContactDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditContactCommand(targetContactId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetContactId.toString() + EMAIL_DESC_AMY;
        descriptor = new EditContactDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditContactCommand(targetContactId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetContactId.toString() + ADDRESS_DESC_AMY;
        descriptor = new EditContactDescriptorBuilder().withAddress(VALID_ADDRESS_AMY).build();
        expectedCommand = new EditContactCommand(targetContactId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        ContactId targetContactId = CONTACT_ID_ONE;
        String userInput = targetContactId.toString() + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY
                + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY
                + PHONE_DESC_BOB + ADDRESS_DESC_BOB + EMAIL_DESC_BOB;

        EditContactDescriptor descriptor = new EditContactDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .build();
        EditContactCommand expectedCommand = new EditContactCommand(targetContactId, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        ContactId targetContactId = CONTACT_ID_ONE;
        String userInput = targetContactId.toString() + INVALID_PHONE_DESC + PHONE_DESC_BOB;
        EditContactDescriptor descriptor = new EditContactDescriptorBuilder().withPhone(VALID_PHONE_BOB).build();
        EditContactCommand expectedCommand = new EditContactCommand(targetContactId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetContactId.toString() + EMAIL_DESC_BOB + INVALID_PHONE_DESC + ADDRESS_DESC_BOB
                + PHONE_DESC_BOB;
        descriptor = new EditContactDescriptorBuilder().withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).build();
        expectedCommand = new EditContactCommand(targetContactId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
