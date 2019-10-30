package organice.logic.parser;

import static organice.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static organice.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static organice.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static organice.logic.commands.CommandTestUtil.NAME_DESC_DOCTOR_AMY;
import static organice.logic.commands.CommandTestUtil.NAME_DESC_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.PHONE_DESC_DOCTOR_AMY;
import static organice.logic.commands.CommandTestUtil.PHONE_DESC_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_NAME_DOCTOR_AMY;
import static organice.logic.commands.CommandTestUtil.VALID_NAME_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_NRIC_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_PHONE_PATIENT_BOB;
import static organice.logic.parser.CommandParserTestUtil.assertParseFailure;
import static organice.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static organice.testutil.TypicalNrics.NRIC_FIRST_PERSON;
import static organice.testutil.TypicalNrics.NRIC_SECOND_PERSON;
import static organice.testutil.TypicalNrics.NRIC_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import organice.logic.commands.CommandTestUtil;
import organice.logic.commands.EditCommand;
import organice.logic.commands.EditCommand.EditPersonDescriptor;
import organice.model.person.Name;
import organice.model.person.Nric;
import organice.model.person.Phone;
import organice.testutil.EditPersonDescriptorBuilder;

public class EditCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no nric specified
        assertParseFailure(parser, VALID_NAME_DOCTOR_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_DOCTOR_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_DOCTOR_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        String targetNric = VALID_NRIC_PATIENT_BOB;
        assertParseFailure(parser, targetNric + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, targetNric + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, targetNric + PHONE_DESC_PATIENT_BOB + INVALID_PHONE_DESC,
                Phone.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, targetNric + INVALID_NAME_DESC + INVALID_PHONE_DESC, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Nric targetNric = NRIC_SECOND_PERSON;
        String userInput = targetNric + PHONE_DESC_PATIENT_BOB + NAME_DESC_DOCTOR_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_PATIENT_BOB)
                .withName(VALID_NAME_DOCTOR_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetNric, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Nric targetNric = NRIC_FIRST_PERSON;
        String userInput = targetNric + PHONE_DESC_PATIENT_BOB + NAME_DESC_PATIENT_BOB;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_PATIENT_BOB)
                .withName(VALID_NAME_PATIENT_BOB).build();
        EditCommand expectedCommand = new EditCommand(targetNric, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Nric targetNric = NRIC_THIRD_PERSON;
        String userInput = targetNric + NAME_DESC_DOCTOR_AMY;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_DOCTOR_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetNric, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetNric + PHONE_DESC_DOCTOR_AMY;
        descriptor = new EditPersonDescriptorBuilder().withPhone(CommandTestUtil.VALID_PHONE_DOCTOR_AMY).build();
        expectedCommand = new EditCommand(targetNric, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Nric targetNric = NRIC_FIRST_PERSON;
        String userInput = targetNric + NAME_DESC_DOCTOR_AMY + NAME_DESC_PATIENT_BOB
                + PHONE_DESC_DOCTOR_AMY + PHONE_DESC_PATIENT_BOB;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_PATIENT_BOB)
                .withPhone(VALID_PHONE_PATIENT_BOB).build();
        EditCommand expectedCommand = new EditCommand(targetNric, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Nric targetNric = NRIC_FIRST_PERSON;
        String userInput = targetNric + INVALID_PHONE_DESC + PHONE_DESC_PATIENT_BOB;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_PATIENT_BOB).build();
        EditCommand expectedCommand = new EditCommand(targetNric, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetNric + INVALID_PHONE_DESC + PHONE_DESC_PATIENT_BOB + NAME_DESC_PATIENT_BOB;
        descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_PATIENT_BOB)
                .withPhone(VALID_PHONE_PATIENT_BOB).build();
        expectedCommand = new EditCommand(targetNric, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
