package seedu.address.logic.parser.editcommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ORGANISATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SUBJECT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ORGANIZATION_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ORGANIZATION_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORGANIZATION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORGANIZATION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.editcommand.EditMentorCommand;
import seedu.address.logic.commands.editcommand.EditMentorCommand.EditMentorDescriptor;
import seedu.address.model.entity.Email;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Name;
import seedu.address.model.entity.Phone;
import seedu.address.model.entity.SubjectName;
import seedu.address.testutil.EditMentorDescriptorBuilder;
import seedu.address.testutil.TypicalIds;

class EditMentorCommandParserTest {


    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditMentorCommand.MESSAGE_USAGE);

    private EditMentorCommandParser parser = new EditMentorCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "M-1", EditMentorCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "M--5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "M-0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "M-1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "M-1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "M-1" + INVALID_NAME_DESC,
                Name.MESSAGE_CONSTRAINTS); // invalid name

        assertParseFailure(parser, "M-1" + INVALID_SUBJECT_DESC,
                SubjectName.MESSAGE_CONSTRAINTS); // invalid phone

        assertParseFailure(parser, "M-1" + INVALID_EMAIL_DESC,
                Email.MESSAGE_CONSTRAINTS); // invalid subject

        assertParseFailure(parser, "M-1" + INVALID_ORGANISATION_DESC,
                Name.MESSAGE_CONSTRAINTS); // invalid email

        assertParseFailure(parser, "M-1" + INVALID_PHONE_DESC,
                Phone.MESSAGE_CONSTRAINTS); // invalid email


        // invalid phone followed by valid email
        assertParseFailure(parser, "M-1" + INVALID_PHONE_DESC + EMAIL_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone.
        assertParseFailure(parser, "M-1" + PHONE_DESC_BOB + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "M-1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_ADDRESS_AMY
                + VALID_PHONE_AMY, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Id targetId = TypicalIds.ID_FIRST_MENTOR;

        String userInput = targetId.toString() + PHONE_DESC_BOB
                + EMAIL_DESC_AMY + NAME_DESC_AMY + ORGANIZATION_DESC_AMY + SUBJECT_DESC_AMY;

        EditMentorDescriptor descriptor = new EditMentorDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY).withOrganization(VALID_ORGANIZATION_AMY)
                .withSubject(VALID_SUBJECT_AMY).build();

        EditMentorCommand expectedCommand = new EditMentorCommand(targetId, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Id targetId = TypicalIds.ID_FIRST_MENTOR;
        String userInput = targetId.toString() + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        EditMentorDescriptor descriptor = new EditMentorDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY).build();

        EditMentorCommand expectedCommand = new EditMentorCommand(targetId, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Id targetId = TypicalIds.ID_FIRST_MENTOR;

        String userInput = targetId.toString() + NAME_DESC_AMY;
        EditMentorDescriptor descriptor = new EditMentorDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditMentorCommand expectedCommand = new EditMentorCommand(targetId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetId.toString() + PHONE_DESC_AMY;
        descriptor = new EditMentorDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditMentorCommand(targetId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetId.toString() + EMAIL_DESC_AMY;
        descriptor = new EditMentorDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditMentorCommand(targetId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // Organization
        userInput = targetId.toString() + ORGANIZATION_DESC_AMY;
        descriptor = new EditMentorDescriptorBuilder().withOrganization(VALID_ORGANIZATION_AMY).build();
        expectedCommand = new EditMentorCommand(targetId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // Subject
        userInput = targetId.toString() + SUBJECT_DESC_AMY;
        descriptor = new EditMentorDescriptorBuilder().withSubject(VALID_SUBJECT_AMY).build();
        expectedCommand = new EditMentorCommand(targetId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Id targetId = TypicalIds.ID_FIRST_MENTOR;

        String userInput = targetId.toString() + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + PHONE_DESC_AMY + EMAIL_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB + SUBJECT_DESC_AMY
                + SUBJECT_DESC_BOB;

        EditMentorDescriptor descriptor = new EditMentorDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withSubject(VALID_SUBJECT_BOB).build();

        EditMentorCommand expectedCommand = new EditMentorCommand(targetId, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        Id targetId = TypicalIds.ID_FIRST_MENTOR;
        String userInput = targetId.toString() + INVALID_PHONE_DESC + PHONE_DESC_BOB;

        EditMentorDescriptor descriptor = new EditMentorDescriptorBuilder().withPhone(VALID_PHONE_BOB).build();

        EditMentorCommand expectedCommand = new EditMentorCommand(targetId, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetId.toString() + EMAIL_DESC_BOB + INVALID_PHONE_DESC + PHONE_DESC_BOB
                + INVALID_ORGANISATION_DESC + ORGANIZATION_DESC_BOB + INVALID_SUBJECT_DESC + SUBJECT_DESC_BOB;

        descriptor = new EditMentorDescriptorBuilder().withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withOrganization(VALID_ORGANIZATION_BOB).withSubject(VALID_SUBJECT_BOB).build();
        expectedCommand = new EditMentorCommand(targetId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
