package seedu.address.logic.parser.editcommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.editcommand.EditParticipantCommand;
import seedu.address.logic.commands.editcommand.EditParticipantCommand.EditParticipantDescriptor;
import seedu.address.model.entity.Email;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Name;
import seedu.address.model.entity.Phone;
import seedu.address.testutil.EditParticipantDescriptorBuilder;
import seedu.address.testutil.TypicalIds;

class EditParticipantCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditParticipantCommand.MESSAGE_USAGE);

    private EditParticipantCommandParser parser = new EditParticipantCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "P-1", EditParticipantCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "P--5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "P-0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "P-1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "P-1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "P-1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "P-1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "P-1" + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email

        // invalid phone followed by valid email
        assertParseFailure(parser, "P-1" + INVALID_PHONE_DESC + EMAIL_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone.
        assertParseFailure(parser, "P-1" + PHONE_DESC_BOB + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "P-1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_ADDRESS_AMY
                + VALID_PHONE_AMY, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Id targetId = TypicalIds.ID_FIRST_PARTICIPANT;

        String userInput = targetId.toString() + PHONE_DESC_BOB
                + EMAIL_DESC_AMY + NAME_DESC_AMY;

        EditParticipantDescriptor descriptor = new EditParticipantDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY)
                .build();

        EditParticipantCommand expectedCommand = new EditParticipantCommand(targetId, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Id targetId = TypicalIds.ID_FIRST_PARTICIPANT;
        String userInput = targetId.toString() + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        EditParticipantDescriptor descriptor = new EditParticipantDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY).build();

        EditParticipantCommand expectedCommand = new EditParticipantCommand(targetId, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Id targetId = TypicalIds.ID_THIRD_PARTICIPANT;
        String userInput = targetId.toString() + NAME_DESC_AMY;
        EditParticipantDescriptor descriptor = new EditParticipantDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditParticipantCommand expectedCommand = new EditParticipantCommand(targetId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetId.toString() + PHONE_DESC_AMY;
        descriptor = new EditParticipantDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditParticipantCommand(targetId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetId.toString() + EMAIL_DESC_AMY;
        descriptor = new EditParticipantDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditParticipantCommand(targetId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Id targetId = TypicalIds.ID_THIRD_PARTICIPANT;

        String userInput = targetId.toString() + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + PHONE_DESC_AMY + EMAIL_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB;

        EditParticipantDescriptor descriptor = new EditParticipantDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).build();

        EditParticipantCommand expectedCommand = new EditParticipantCommand(targetId, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        Id targetId = TypicalIds.ID_THIRD_PARTICIPANT;
        String userInput = targetId.toString() + INVALID_PHONE_DESC + PHONE_DESC_BOB;

        EditParticipantDescriptor descriptor = new EditParticipantDescriptorBuilder()
                .withPhone(VALID_PHONE_BOB).build();

        EditParticipantCommand expectedCommand = new EditParticipantCommand(targetId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetId.toString() + EMAIL_DESC_BOB + INVALID_PHONE_DESC + PHONE_DESC_BOB;
        descriptor = new EditParticipantDescriptorBuilder().withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .build();
        expectedCommand = new EditParticipantCommand(targetId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
