package organice.logic.parser;

import static organice.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static organice.logic.commands.CommandTestUtil.AGE_DESC_DONOR_JOHN;
import static organice.logic.commands.CommandTestUtil.AGE_DESC_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.AGE_DESC_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.INVALID_AGE_DESC;
import static organice.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static organice.logic.commands.CommandTestUtil.INVALID_NRIC_DESC;
import static organice.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static organice.logic.commands.CommandTestUtil.INVALID_TYPE_DESC;
import static organice.logic.commands.CommandTestUtil.NAME_DESC_DOCTOR_AMY;
import static organice.logic.commands.CommandTestUtil.NAME_DESC_DONOR_JOHN;
import static organice.logic.commands.CommandTestUtil.NAME_DESC_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.NAME_DESC_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.NRIC_DESC_DOCTOR_AMY;
import static organice.logic.commands.CommandTestUtil.NRIC_DESC_DONOR_JOHN;
import static organice.logic.commands.CommandTestUtil.NRIC_DESC_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.NRIC_DESC_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.PHONE_DESC_DOCTOR_AMY;
import static organice.logic.commands.CommandTestUtil.PHONE_DESC_DONOR_JOHN;
import static organice.logic.commands.CommandTestUtil.PHONE_DESC_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static organice.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static organice.logic.commands.CommandTestUtil.TYPE_DESC_DOCTOR_AMY;
import static organice.logic.commands.CommandTestUtil.TYPE_DESC_DONOR_JOHN;
import static organice.logic.commands.CommandTestUtil.TYPE_DESC_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.TYPE_DESC_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.VALID_AGE_DONOR_JOHN;
import static organice.logic.commands.CommandTestUtil.VALID_AGE_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.VALID_NAME_DONOR_JOHN;
import static organice.logic.commands.CommandTestUtil.VALID_NAME_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.VALID_NRIC_DONOR_JOHN;
import static organice.logic.commands.CommandTestUtil.VALID_NRIC_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.VALID_PHONE_DONOR_JOHN;
import static organice.logic.commands.CommandTestUtil.VALID_PHONE_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.VALID_TYPE_DONOR_JOHN;
import static organice.logic.commands.CommandTestUtil.VALID_TYPE_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_TYPE_PATIENT_IRENE;
import static organice.logic.parser.CommandParserTestUtil.assertParseFailure;
import static organice.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static organice.testutil.TypicalPersons.DOCTOR_AMY;
import static organice.testutil.TypicalPersons.DONOR_JOHN;
import static organice.testutil.TypicalPersons.PATIENT_IRENE;

import org.junit.jupiter.api.Test;

import organice.logic.commands.AddCommand;
import organice.logic.commands.CommandTestUtil;
import organice.model.person.Age;
import organice.model.person.Donor;
import organice.model.person.Name;
import organice.model.person.Nric;

import organice.model.person.Patient;
import organice.model.person.Person;
import organice.model.person.Phone;
import organice.model.person.Type;
import organice.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(DOCTOR_AMY).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TYPE_DESC_DOCTOR_AMY + NRIC_DESC_DOCTOR_AMY
                + NAME_DESC_DOCTOR_AMY + PHONE_DESC_DOCTOR_AMY, new AddCommand(expectedPerson));

        // multiple names - last name accepted
        assertParseSuccess(parser, TYPE_DESC_DOCTOR_AMY + NRIC_DESC_DOCTOR_AMY + NAME_DESC_PATIENT_BOB
                + NAME_DESC_DOCTOR_AMY + PHONE_DESC_DOCTOR_AMY, new AddCommand(expectedPerson));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, TYPE_DESC_DOCTOR_AMY + NRIC_DESC_DOCTOR_AMY + NAME_DESC_DOCTOR_AMY
                + PHONE_DESC_PATIENT_BOB + PHONE_DESC_DOCTOR_AMY, new AddCommand(expectedPerson));

        // multiple nrics - last nric accepted
        assertParseSuccess(parser, TYPE_DESC_DOCTOR_AMY + NRIC_DESC_PATIENT_BOB + NRIC_DESC_DOCTOR_AMY
                + NAME_DESC_DOCTOR_AMY + PHONE_DESC_DOCTOR_AMY, new AddCommand(expectedPerson));

        // multiple types - last type accepted
        assertParseSuccess(parser, TYPE_DESC_PATIENT_BOB + TYPE_DESC_DOCTOR_AMY + NRIC_DESC_DOCTOR_AMY
            + NAME_DESC_DOCTOR_AMY + PHONE_DESC_DOCTOR_AMY, new AddCommand(expectedPerson));

        //Testing all fields present for a Donor
        Patient expectedPatient = PATIENT_IRENE;

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TYPE_DESC_PATIENT_IRENE + NRIC_DESC_PATIENT_IRENE
                + NAME_DESC_PATIENT_IRENE + CommandTestUtil.PHONE_DESC_PATIENT_IRENE + AGE_DESC_PATIENT_IRENE,
                new AddCommand(expectedPatient));

        // multiple names - last name accepted
        assertParseSuccess(parser, TYPE_DESC_PATIENT_IRENE + NRIC_DESC_PATIENT_IRENE + NAME_DESC_DOCTOR_AMY
                + NAME_DESC_PATIENT_IRENE + CommandTestUtil.PHONE_DESC_PATIENT_IRENE + AGE_DESC_PATIENT_IRENE,
                new AddCommand(expectedPatient));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, TYPE_DESC_PATIENT_IRENE + NRIC_DESC_PATIENT_IRENE + NAME_DESC_PATIENT_IRENE
                + PHONE_DESC_DOCTOR_AMY + CommandTestUtil.PHONE_DESC_PATIENT_IRENE + AGE_DESC_PATIENT_IRENE,
                new AddCommand(expectedPatient));

        // multiple nrics - last nric accepted
        assertParseSuccess(parser, TYPE_DESC_PATIENT_IRENE + NRIC_DESC_DOCTOR_AMY + NRIC_DESC_PATIENT_IRENE
                + NAME_DESC_PATIENT_IRENE + CommandTestUtil.PHONE_DESC_PATIENT_IRENE + AGE_DESC_PATIENT_IRENE,
                new AddCommand(expectedPatient));

        // multiple types - last type accepted
        assertParseSuccess(parser, TYPE_DESC_PATIENT_BOB + TYPE_DESC_PATIENT_IRENE + NRIC_DESC_PATIENT_IRENE
                + NAME_DESC_PATIENT_IRENE + CommandTestUtil.PHONE_DESC_PATIENT_IRENE + AGE_DESC_PATIENT_IRENE,
                new AddCommand(expectedPatient));

        //Testing all fields present for a Donor
        Donor expectedDonor = DONOR_JOHN;

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TYPE_DESC_DONOR_JOHN + NRIC_DESC_DONOR_JOHN
            + NAME_DESC_DONOR_JOHN + PHONE_DESC_DONOR_JOHN + AGE_DESC_DONOR_JOHN, new AddCommand(expectedDonor));

        // multiple names - last name accepted
        assertParseSuccess(parser, TYPE_DESC_DONOR_JOHN + NRIC_DESC_DONOR_JOHN + NAME_DESC_DOCTOR_AMY
            + NAME_DESC_DONOR_JOHN + PHONE_DESC_DONOR_JOHN + AGE_DESC_DONOR_JOHN, new AddCommand(expectedDonor));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, TYPE_DESC_DONOR_JOHN + NRIC_DESC_DONOR_JOHN + NAME_DESC_DONOR_JOHN
            + PHONE_DESC_DOCTOR_AMY + PHONE_DESC_DONOR_JOHN + AGE_DESC_DONOR_JOHN, new AddCommand(expectedDonor));

        // multiple nrics - last nric accepted
        assertParseSuccess(parser, TYPE_DESC_DONOR_JOHN + NRIC_DESC_DOCTOR_AMY + NRIC_DESC_DONOR_JOHN
                + NAME_DESC_DONOR_JOHN + PHONE_DESC_DONOR_JOHN + AGE_DESC_DONOR_JOHN, new AddCommand(expectedDonor));

        // multiple types - last type accepted
        assertParseSuccess(parser, TYPE_DESC_PATIENT_BOB + TYPE_DESC_DONOR_JOHN + NRIC_DESC_DONOR_JOHN
                + NAME_DESC_DONOR_JOHN + PHONE_DESC_DONOR_JOHN + AGE_DESC_DONOR_JOHN, new AddCommand(expectedDonor));

    }

    //    @Test
    //    public void parse_optionalFieldsMissing_success() {
    //        // zero tags
    //        Person expectedPerson = new PersonBuilder(DOCTOR_AMY).build();
    //        assertParseSuccess(parser, NAME_DESC_DOCTOR_AMY + PHONE_DESC_DOCTOR_AMY,
    //                new AddCommand(expectedPerson));
    //    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing type prefix
        assertParseFailure(parser, VALID_TYPE_PATIENT_BOB + NRIC_DESC_PATIENT_BOB + NAME_DESC_PATIENT_BOB
                + PHONE_DESC_PATIENT_BOB, expectedMessage);

        // missing nric prefix -- donor
        assertParseFailure(parser, TYPE_DESC_DONOR_JOHN + NAME_DESC_DONOR_JOHN + VALID_NRIC_DONOR_JOHN
                + PHONE_DESC_DONOR_JOHN + AGE_DESC_DONOR_JOHN, expectedMessage);

        //missing nric prefix -- patient
        assertParseFailure(parser, TYPE_DESC_PATIENT_IRENE + NAME_DESC_PATIENT_IRENE + VALID_NRIC_PATIENT_IRENE
                + CommandTestUtil.PHONE_DESC_PATIENT_IRENE + AGE_DESC_PATIENT_IRENE, expectedMessage);

        // missing name prefix -- donor
        assertParseFailure(parser, TYPE_DESC_DONOR_JOHN + NRIC_DESC_DONOR_JOHN + VALID_NAME_DONOR_JOHN
                + PHONE_DESC_DONOR_JOHN + AGE_DESC_DONOR_JOHN, expectedMessage);

        // missing name prefix -- patient
        assertParseFailure(parser, TYPE_DESC_PATIENT_IRENE + NRIC_DESC_PATIENT_IRENE + VALID_NAME_PATIENT_IRENE
                + CommandTestUtil.PHONE_DESC_PATIENT_IRENE + AGE_DESC_PATIENT_IRENE, expectedMessage);

        // missing phone prefix -- donor
        assertParseFailure(parser, TYPE_DESC_DONOR_JOHN + NRIC_DESC_DONOR_JOHN + NAME_DESC_DONOR_JOHN
                + VALID_PHONE_DONOR_JOHN + AGE_DESC_DONOR_JOHN, expectedMessage);

        // missing phone prefix -- patient
        assertParseFailure(parser, TYPE_DESC_PATIENT_IRENE + NRIC_DESC_PATIENT_IRENE + NAME_DESC_PATIENT_IRENE
                + VALID_PHONE_PATIENT_IRENE + AGE_DESC_PATIENT_IRENE, expectedMessage);

        // missing age prefix -- donor
        assertParseFailure(parser, TYPE_DESC_DONOR_JOHN + NRIC_DESC_DONOR_JOHN + NAME_DESC_DONOR_JOHN
                + PHONE_DESC_DONOR_JOHN + VALID_AGE_DONOR_JOHN, expectedMessage);

        // missing age prefix -- patient
        assertParseFailure(parser, TYPE_DESC_PATIENT_IRENE + NRIC_DESC_PATIENT_IRENE + NAME_DESC_PATIENT_IRENE
                + CommandTestUtil.PHONE_DESC_PATIENT_IRENE + VALID_AGE_PATIENT_IRENE, expectedMessage);

        // all prefixes missing -- donor
        assertParseFailure(parser, VALID_TYPE_DONOR_JOHN + VALID_NRIC_DONOR_JOHN + VALID_NAME_DONOR_JOHN
                + VALID_PHONE_DONOR_JOHN + VALID_AGE_DONOR_JOHN, expectedMessage);

        // all prefixes missing -- patient
        assertParseFailure(parser, VALID_TYPE_PATIENT_IRENE + VALID_NRIC_PATIENT_IRENE
                + VALID_NAME_PATIENT_IRENE + VALID_PHONE_PATIENT_IRENE + VALID_AGE_PATIENT_IRENE, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid type
        assertParseFailure(parser, INVALID_TYPE_DESC + NRIC_DESC_PATIENT_BOB + NAME_DESC_PATIENT_BOB
                + PHONE_DESC_PATIENT_BOB, Type.MESSAGE_CONSTRAINTS);

        // invalid nric -- donor
        assertParseFailure(parser, TYPE_DESC_DONOR_JOHN + INVALID_NRIC_DESC + NAME_DESC_DONOR_JOHN
                + PHONE_DESC_DONOR_JOHN + AGE_DESC_DONOR_JOHN, Nric.MESSAGE_CONSTRAINTS);

        // invalid nric -- patient
        assertParseFailure(parser, TYPE_DESC_PATIENT_IRENE + INVALID_NRIC_DESC + NAME_DESC_PATIENT_IRENE
                + CommandTestUtil.PHONE_DESC_PATIENT_IRENE + AGE_DESC_PATIENT_IRENE, Nric.MESSAGE_CONSTRAINTS);

        // invalid name -- donor
        assertParseFailure(parser, TYPE_DESC_DONOR_JOHN + NRIC_DESC_DONOR_JOHN + INVALID_NAME_DESC
                + PHONE_DESC_DONOR_JOHN + AGE_DESC_DONOR_JOHN, Name.MESSAGE_CONSTRAINTS);

        // invalid name -- patient
        assertParseFailure(parser, TYPE_DESC_PATIENT_IRENE + NRIC_DESC_PATIENT_IRENE + INVALID_NAME_DESC
                + CommandTestUtil.PHONE_DESC_PATIENT_IRENE + AGE_DESC_PATIENT_IRENE, Name.MESSAGE_CONSTRAINTS);

        // invalid phone -- donor
        assertParseFailure(parser, TYPE_DESC_DONOR_JOHN + NRIC_DESC_DONOR_JOHN + NAME_DESC_DONOR_JOHN
                + INVALID_PHONE_DESC + AGE_DESC_DONOR_JOHN, Phone.MESSAGE_CONSTRAINTS);

        // invalid phone -- patient
        assertParseFailure(parser, TYPE_DESC_PATIENT_IRENE + NRIC_DESC_PATIENT_IRENE + NAME_DESC_PATIENT_IRENE
                + INVALID_PHONE_DESC + AGE_DESC_PATIENT_IRENE, Phone.MESSAGE_CONSTRAINTS);

        // invalid age -- donor
        assertParseFailure(parser, TYPE_DESC_DONOR_JOHN + NRIC_DESC_DONOR_JOHN + NAME_DESC_DONOR_JOHN
                + PHONE_DESC_DONOR_JOHN + INVALID_AGE_DESC, Age.MESSAGE_CONSTRAINTS);

        // invalid age -- patient
        assertParseFailure(parser, TYPE_DESC_PATIENT_IRENE + NRIC_DESC_PATIENT_IRENE + NAME_DESC_PATIENT_IRENE
                + CommandTestUtil.PHONE_DESC_PATIENT_IRENE + INVALID_AGE_DESC, Age.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported -- donor
        assertParseFailure(parser, TYPE_DESC_DONOR_JOHN + NRIC_DESC_DONOR_JOHN + INVALID_NAME_DESC
                + INVALID_PHONE_DESC + AGE_DESC_DONOR_JOHN, Name.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported -- patient
        assertParseFailure(parser, TYPE_DESC_PATIENT_BOB + NRIC_DESC_PATIENT_BOB + INVALID_NAME_DESC
                + INVALID_PHONE_DESC + AGE_DESC_PATIENT_BOB, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble -- donor
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + TYPE_DESC_DONOR_JOHN + NRIC_DESC_DONOR_JOHN
                + NAME_DESC_DONOR_JOHN + PHONE_DESC_DONOR_JOHN + AGE_DESC_DONOR_JOHN,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        // non-empty preamble -- patient
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + TYPE_DESC_PATIENT_BOB + NRIC_DESC_PATIENT_BOB
                + NAME_DESC_PATIENT_BOB + PHONE_DESC_PATIENT_BOB + AGE_DESC_PATIENT_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
