package organice.logic.parser;

import static organice.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static organice.logic.commands.CommandTestUtil.AGE_DESC_DONOR_JOHN;
import static organice.logic.commands.CommandTestUtil.AGE_DESC_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.AGE_DESC_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.BLOOD_TYPE_DESC_DONOR_JOHN;
import static organice.logic.commands.CommandTestUtil.BLOOD_TYPE_DESC_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.BLOOD_TYPE_DESC_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.DOCTOR_IN_CHARGE_DESC_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.DOCTOR_IN_CHARGE_DESC_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.INVALID_AGE_DESC;
import static organice.logic.commands.CommandTestUtil.INVALID_BLOOD_TYPE_DESC;
import static organice.logic.commands.CommandTestUtil.INVALID_DOCTOR_IN_CHARGE_DESC;
import static organice.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static organice.logic.commands.CommandTestUtil.INVALID_NRIC_DESC;
import static organice.logic.commands.CommandTestUtil.INVALID_ORGAN_DESC;
import static organice.logic.commands.CommandTestUtil.INVALID_ORGAN_EXPIRY_DATE_DESC;
import static organice.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static organice.logic.commands.CommandTestUtil.INVALID_PRIORITY_DESC;
import static organice.logic.commands.CommandTestUtil.INVALID_TISSUE_TYPE_DESC;
import static organice.logic.commands.CommandTestUtil.INVALID_TYPE_DESC;
import static organice.logic.commands.CommandTestUtil.NAME_DESC_DOCTOR_AMY;
import static organice.logic.commands.CommandTestUtil.NAME_DESC_DONOR_JOHN;
import static organice.logic.commands.CommandTestUtil.NAME_DESC_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.NAME_DESC_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.NRIC_DESC_DOCTOR_AMY;
import static organice.logic.commands.CommandTestUtil.NRIC_DESC_DONOR_JOHN;
import static organice.logic.commands.CommandTestUtil.NRIC_DESC_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.NRIC_DESC_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.ORGAN_DESC_DONOR_JOHN;
import static organice.logic.commands.CommandTestUtil.ORGAN_DESC_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.ORGAN_DESC_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.ORGAN_EXPIRY_DATE_DESC_DONOR_JOHN;
import static organice.logic.commands.CommandTestUtil.ORGAN_EXPIRY_DATE_DESC_DONOR_JOHNY;
import static organice.logic.commands.CommandTestUtil.PHONE_DESC_DOCTOR_AMY;
import static organice.logic.commands.CommandTestUtil.PHONE_DESC_DONOR_JOHN;
import static organice.logic.commands.CommandTestUtil.PHONE_DESC_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.PHONE_DESC_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static organice.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static organice.logic.commands.CommandTestUtil.PRIORITY_DESC_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.PRIORITY_DESC_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.TISSUE_TYPE_DESC_DONOR_JOHN;
import static organice.logic.commands.CommandTestUtil.TISSUE_TYPE_DESC_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.TISSUE_TYPE_DESC_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.TYPE_DESC_DOCTOR_AMY;
import static organice.logic.commands.CommandTestUtil.TYPE_DESC_DONOR_JOHN;
import static organice.logic.commands.CommandTestUtil.TYPE_DESC_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.TYPE_DESC_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.VALID_AGE_DONOR_JOHN;
import static organice.logic.commands.CommandTestUtil.VALID_AGE_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.VALID_BLOOD_TYPE_DONOR_JOHN;
import static organice.logic.commands.CommandTestUtil.VALID_BLOOD_TYPE_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.VALID_DOCTOR_IN_CHARGE_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.VALID_NAME_DOCTOR_AMY;
import static organice.logic.commands.CommandTestUtil.VALID_NAME_DONOR_JOHN;
import static organice.logic.commands.CommandTestUtil.VALID_NAME_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.VALID_NRIC_DOCTOR_AMY;
import static organice.logic.commands.CommandTestUtil.VALID_NRIC_DONOR_JOHN;
import static organice.logic.commands.CommandTestUtil.VALID_NRIC_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.VALID_ORGAN_DONOR_JOHN;
import static organice.logic.commands.CommandTestUtil.VALID_ORGAN_EXPIRY_DATE_DONOR_JOHN;
import static organice.logic.commands.CommandTestUtil.VALID_ORGAN_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.VALID_PHONE_DOCTOR_AMY;
import static organice.logic.commands.CommandTestUtil.VALID_PHONE_DONOR_JOHN;
import static organice.logic.commands.CommandTestUtil.VALID_PHONE_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.VALID_PRIORITY_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.VALID_TISSUE_TYPE_DONOR_JOHN;
import static organice.logic.commands.CommandTestUtil.VALID_TISSUE_TYPE_PATIENT_IRENE;
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
import organice.model.person.Age;
import organice.model.person.BloodType;
import organice.model.person.Doctor;
import organice.model.person.DoctorInCharge;
import organice.model.person.Donor;
import organice.model.person.Name;
import organice.model.person.Nric;
import organice.model.person.Organ;
import organice.model.person.OrganExpiryDate;
import organice.model.person.Patient;
import organice.model.person.Phone;
import organice.model.person.Priority;
import organice.model.person.TissueType;
import organice.model.person.Type;
import organice.testutil.DonorBuilder;
import organice.testutil.PatientBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresentPatient_success() {
        Patient expectedPatient = new PatientBuilder(PATIENT_IRENE).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TYPE_DESC_PATIENT_IRENE + NRIC_DESC_PATIENT_IRENE
                + NAME_DESC_PATIENT_IRENE + PHONE_DESC_PATIENT_IRENE + AGE_DESC_PATIENT_IRENE
                + PRIORITY_DESC_PATIENT_IRENE + BLOOD_TYPE_DESC_PATIENT_IRENE + TISSUE_TYPE_DESC_PATIENT_IRENE
                + ORGAN_DESC_PATIENT_IRENE + DOCTOR_IN_CHARGE_DESC_PATIENT_IRENE,
                        new AddCommand(expectedPatient));
    }

    @Test
    public void parse_allFieldsPresentDonor_success() {
        Donor expectedDonor = new DonorBuilder(DONOR_JOHN).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TYPE_DESC_DONOR_JOHN + NRIC_DESC_DONOR_JOHN
                + NAME_DESC_DONOR_JOHN + PHONE_DESC_DONOR_JOHN + AGE_DESC_DONOR_JOHN
                + BLOOD_TYPE_DESC_DONOR_JOHN + TISSUE_TYPE_DESC_DONOR_JOHN
                + ORGAN_DESC_DONOR_JOHN + ORGAN_EXPIRY_DATE_DESC_DONOR_JOHN,
                        new AddCommand(expectedDonor));
    }

    @Test
    public void parse_allFieldsPresentDoctor_success() {
        //Testing all fields present for a Doctor
        Doctor expectedDonor = DOCTOR_AMY;

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TYPE_DESC_DOCTOR_AMY + NRIC_DESC_DOCTOR_AMY
                + NAME_DESC_DOCTOR_AMY + PHONE_DESC_DOCTOR_AMY, new AddCommand(expectedDonor));
    }

    @Test
    public void parse_multipleFieldsPresentPatient_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // multiple names
        assertParseFailure(parser, TYPE_DESC_PATIENT_IRENE + NRIC_DESC_PATIENT_IRENE + NAME_DESC_DOCTOR_AMY
                        + NAME_DESC_PATIENT_IRENE + PHONE_DESC_PATIENT_IRENE + AGE_DESC_PATIENT_IRENE
                        + PRIORITY_DESC_PATIENT_IRENE + BLOOD_TYPE_DESC_PATIENT_IRENE + TISSUE_TYPE_DESC_PATIENT_IRENE
                        + ORGAN_DESC_PATIENT_IRENE + DOCTOR_IN_CHARGE_DESC_PATIENT_IRENE,
                expectedMessage);

        // multiple phones
        assertParseFailure(parser, TYPE_DESC_PATIENT_IRENE + NRIC_DESC_PATIENT_IRENE + NAME_DESC_PATIENT_IRENE
                        + PHONE_DESC_DOCTOR_AMY + PHONE_DESC_PATIENT_IRENE + AGE_DESC_PATIENT_IRENE
                        + PRIORITY_DESC_PATIENT_IRENE + BLOOD_TYPE_DESC_PATIENT_IRENE + TISSUE_TYPE_DESC_PATIENT_IRENE
                        + ORGAN_DESC_PATIENT_IRENE + DOCTOR_IN_CHARGE_DESC_PATIENT_IRENE,
                expectedMessage);

        // multiple nrics
        assertParseFailure(parser, TYPE_DESC_PATIENT_IRENE + NRIC_DESC_DOCTOR_AMY + NRIC_DESC_PATIENT_IRENE
                        + NAME_DESC_PATIENT_IRENE + PHONE_DESC_PATIENT_IRENE + AGE_DESC_PATIENT_IRENE
                        + PRIORITY_DESC_PATIENT_IRENE + BLOOD_TYPE_DESC_PATIENT_IRENE + TISSUE_TYPE_DESC_PATIENT_IRENE
                        + ORGAN_DESC_PATIENT_IRENE + DOCTOR_IN_CHARGE_DESC_PATIENT_IRENE,
                expectedMessage);

        // multiple types
        assertParseFailure(parser, TYPE_DESC_PATIENT_BOB + TYPE_DESC_PATIENT_IRENE + NRIC_DESC_PATIENT_IRENE
                        + NAME_DESC_PATIENT_IRENE + PHONE_DESC_PATIENT_IRENE + AGE_DESC_PATIENT_IRENE
                        + PRIORITY_DESC_PATIENT_IRENE + BLOOD_TYPE_DESC_PATIENT_IRENE + TISSUE_TYPE_DESC_PATIENT_IRENE
                        + ORGAN_DESC_PATIENT_IRENE + DOCTOR_IN_CHARGE_DESC_PATIENT_IRENE,
                expectedMessage);

        //multiple priority
        assertParseFailure(parser, TYPE_DESC_PATIENT_IRENE + NRIC_DESC_PATIENT_IRENE + NAME_DESC_PATIENT_IRENE
                        + PHONE_DESC_PATIENT_IRENE + AGE_DESC_PATIENT_IRENE + PRIORITY_DESC_PATIENT_BOB
                        + PRIORITY_DESC_PATIENT_IRENE + BLOOD_TYPE_DESC_PATIENT_IRENE + TISSUE_TYPE_DESC_PATIENT_IRENE
                        + ORGAN_DESC_PATIENT_IRENE + DOCTOR_IN_CHARGE_DESC_PATIENT_IRENE,
                expectedMessage);

        // multiple ages
        assertParseFailure(parser, TYPE_DESC_PATIENT_IRENE + NRIC_DESC_PATIENT_IRENE + NAME_DESC_PATIENT_IRENE
                        + PHONE_DESC_PATIENT_IRENE + AGE_DESC_PATIENT_BOB + AGE_DESC_PATIENT_IRENE
                        + PRIORITY_DESC_PATIENT_IRENE + BLOOD_TYPE_DESC_PATIENT_IRENE + TISSUE_TYPE_DESC_PATIENT_IRENE
                        + ORGAN_DESC_PATIENT_IRENE + DOCTOR_IN_CHARGE_DESC_PATIENT_IRENE,
                expectedMessage);

        // multiple blood type
        assertParseFailure(parser, TYPE_DESC_PATIENT_IRENE + NRIC_DESC_PATIENT_IRENE + NAME_DESC_PATIENT_IRENE
                        + PHONE_DESC_PATIENT_IRENE + AGE_DESC_PATIENT_IRENE + PRIORITY_DESC_PATIENT_IRENE
                        + BLOOD_TYPE_DESC_PATIENT_BOB + BLOOD_TYPE_DESC_PATIENT_IRENE + TISSUE_TYPE_DESC_PATIENT_IRENE
                        + ORGAN_DESC_PATIENT_IRENE + DOCTOR_IN_CHARGE_DESC_PATIENT_IRENE,
                expectedMessage);

        // multiple tissue type
        assertParseFailure(parser, TYPE_DESC_PATIENT_IRENE + NRIC_DESC_PATIENT_IRENE + NAME_DESC_PATIENT_IRENE
                        + PHONE_DESC_PATIENT_IRENE + AGE_DESC_PATIENT_IRENE + PRIORITY_DESC_PATIENT_IRENE
                        + BLOOD_TYPE_DESC_PATIENT_IRENE + TISSUE_TYPE_DESC_PATIENT_BOB + TISSUE_TYPE_DESC_PATIENT_IRENE
                        + ORGAN_DESC_PATIENT_IRENE + DOCTOR_IN_CHARGE_DESC_PATIENT_IRENE,
                expectedMessage);

        // multiple organ
        assertParseFailure(parser, TYPE_DESC_PATIENT_IRENE + NRIC_DESC_PATIENT_IRENE + NAME_DESC_PATIENT_IRENE
                        + PHONE_DESC_PATIENT_IRENE + AGE_DESC_PATIENT_IRENE + PRIORITY_DESC_PATIENT_IRENE
                        + BLOOD_TYPE_DESC_PATIENT_IRENE + TISSUE_TYPE_DESC_PATIENT_IRENE + ORGAN_DESC_DONOR_JOHN
                        + ORGAN_DESC_PATIENT_BOB + DOCTOR_IN_CHARGE_DESC_PATIENT_IRENE,
                expectedMessage);

        // multiple doctor in charge
        assertParseFailure(parser, TYPE_DESC_PATIENT_IRENE + NRIC_DESC_PATIENT_IRENE + NAME_DESC_PATIENT_IRENE
                        + PHONE_DESC_PATIENT_IRENE + AGE_DESC_PATIENT_IRENE + PRIORITY_DESC_PATIENT_IRENE
                        + BLOOD_TYPE_DESC_PATIENT_IRENE + TISSUE_TYPE_DESC_PATIENT_IRENE
                        + ORGAN_DESC_PATIENT_IRENE + DOCTOR_IN_CHARGE_DESC_PATIENT_BOB
                        + DOCTOR_IN_CHARGE_DESC_PATIENT_IRENE,
                expectedMessage);
    }

    @Test
    public void parse_multipleFieldsPresentDonor_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // multiple names
        assertParseFailure(parser, TYPE_DESC_DONOR_JOHN + NRIC_DESC_DONOR_JOHN + NAME_DESC_DOCTOR_AMY
                        + NAME_DESC_DONOR_JOHN + PHONE_DESC_DONOR_JOHN + AGE_DESC_DONOR_JOHN
                        + BLOOD_TYPE_DESC_DONOR_JOHN + TISSUE_TYPE_DESC_DONOR_JOHN
                        + ORGAN_DESC_DONOR_JOHN + ORGAN_EXPIRY_DATE_DESC_DONOR_JOHN,
                expectedMessage);

        // multiple phones
        assertParseFailure(parser, TYPE_DESC_DONOR_JOHN + NRIC_DESC_DONOR_JOHN + NAME_DESC_DONOR_JOHN
                        + PHONE_DESC_DOCTOR_AMY + PHONE_DESC_DONOR_JOHN + AGE_DESC_DONOR_JOHN
                        + BLOOD_TYPE_DESC_DONOR_JOHN + TISSUE_TYPE_DESC_DONOR_JOHN
                        + ORGAN_DESC_DONOR_JOHN + ORGAN_EXPIRY_DATE_DESC_DONOR_JOHN,
                expectedMessage);

        // multiple nrics
        assertParseFailure(parser, TYPE_DESC_DONOR_JOHN + NRIC_DESC_DOCTOR_AMY + NRIC_DESC_DONOR_JOHN
                        + NAME_DESC_DONOR_JOHN + PHONE_DESC_DONOR_JOHN + AGE_DESC_DONOR_JOHN
                        + BLOOD_TYPE_DESC_DONOR_JOHN + TISSUE_TYPE_DESC_DONOR_JOHN
                        + ORGAN_DESC_DONOR_JOHN + ORGAN_EXPIRY_DATE_DESC_DONOR_JOHN,
                expectedMessage);

        // multiple types
        assertParseFailure(parser, TYPE_DESC_PATIENT_BOB + TYPE_DESC_DONOR_JOHN + NRIC_DESC_DONOR_JOHN
                        + NAME_DESC_DONOR_JOHN + PHONE_DESC_DONOR_JOHN + AGE_DESC_DONOR_JOHN
                        + BLOOD_TYPE_DESC_DONOR_JOHN + TISSUE_TYPE_DESC_DONOR_JOHN
                        + ORGAN_DESC_DONOR_JOHN + ORGAN_EXPIRY_DATE_DESC_DONOR_JOHN,
                expectedMessage);

        // multiple ages
        assertParseFailure(parser, TYPE_DESC_DONOR_JOHN + NRIC_DESC_DONOR_JOHN + NAME_DESC_DONOR_JOHN
                        + PHONE_DESC_DONOR_JOHN + AGE_DESC_PATIENT_BOB + AGE_DESC_DONOR_JOHN
                        + BLOOD_TYPE_DESC_DONOR_JOHN + TISSUE_TYPE_DESC_DONOR_JOHN
                        + ORGAN_DESC_DONOR_JOHN + ORGAN_EXPIRY_DATE_DESC_DONOR_JOHN,
                expectedMessage);

        // multiple blood type
        assertParseFailure(parser, TYPE_DESC_DONOR_JOHN + NRIC_DESC_DONOR_JOHN + NAME_DESC_DONOR_JOHN
                        + PHONE_DESC_DONOR_JOHN + AGE_DESC_DONOR_JOHN
                        + BLOOD_TYPE_DESC_PATIENT_BOB + BLOOD_TYPE_DESC_DONOR_JOHN + TISSUE_TYPE_DESC_DONOR_JOHN
                        + ORGAN_DESC_DONOR_JOHN + ORGAN_EXPIRY_DATE_DESC_DONOR_JOHN,
                expectedMessage);

        // multiple tissue type
        assertParseFailure(parser, TYPE_DESC_DONOR_JOHN + NRIC_DESC_DONOR_JOHN + NAME_DESC_DONOR_JOHN
                        + PHONE_DESC_DONOR_JOHN + AGE_DESC_DONOR_JOHN
                        + BLOOD_TYPE_DESC_DONOR_JOHN + TISSUE_TYPE_DESC_PATIENT_BOB + TISSUE_TYPE_DESC_DONOR_JOHN
                        + ORGAN_DESC_DONOR_JOHN + ORGAN_EXPIRY_DATE_DESC_DONOR_JOHN,
                expectedMessage);

        // multiple organ
        assertParseFailure(parser, TYPE_DESC_DONOR_JOHN + NRIC_DESC_DONOR_JOHN + NAME_DESC_DONOR_JOHN
                        + PHONE_DESC_DONOR_JOHN + AGE_DESC_DONOR_JOHN
                        + BLOOD_TYPE_DESC_DONOR_JOHN + TISSUE_TYPE_DESC_DONOR_JOHN
                        + ORGAN_DESC_PATIENT_BOB + ORGAN_DESC_DONOR_JOHN + ORGAN_EXPIRY_DATE_DESC_DONOR_JOHN,
                expectedMessage);

        // multiple organ expiry date
        assertParseFailure(parser, TYPE_DESC_DONOR_JOHN + NRIC_DESC_DONOR_JOHN + NAME_DESC_DONOR_JOHN
                        + PHONE_DESC_DONOR_JOHN + AGE_DESC_DONOR_JOHN
                        + BLOOD_TYPE_DESC_DONOR_JOHN + TISSUE_TYPE_DESC_DONOR_JOHN
                        + ORGAN_DESC_DONOR_JOHN + ORGAN_EXPIRY_DATE_DESC_DONOR_JOHNY
                        + ORGAN_EXPIRY_DATE_DESC_DONOR_JOHN, expectedMessage);
    }

    @Test
    public void parse_multipleFieldsPresentDoctor_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // multiple names
        assertParseFailure(parser, TYPE_DESC_DOCTOR_AMY + NRIC_DESC_DOCTOR_AMY + NAME_DESC_DOCTOR_AMY
                + NAME_DESC_DOCTOR_AMY + PHONE_DESC_DOCTOR_AMY, expectedMessage);

        // multiple phones
        assertParseFailure(parser, TYPE_DESC_DOCTOR_AMY + NRIC_DESC_DOCTOR_AMY + NAME_DESC_DOCTOR_AMY
                + PHONE_DESC_DOCTOR_AMY + PHONE_DESC_DOCTOR_AMY, expectedMessage);

        // multiple nrics
        assertParseFailure(parser, TYPE_DESC_DOCTOR_AMY + NRIC_DESC_DOCTOR_AMY + NRIC_DESC_DOCTOR_AMY
                + NAME_DESC_DOCTOR_AMY + PHONE_DESC_DOCTOR_AMY, expectedMessage);

        // multiple types
        assertParseFailure(parser, TYPE_DESC_PATIENT_BOB + TYPE_DESC_DOCTOR_AMY + NRIC_DESC_DOCTOR_AMY
                + NAME_DESC_DOCTOR_AMY + PHONE_DESC_DOCTOR_AMY, expectedMessage);

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing type prefix
        assertParseFailure(parser, VALID_TYPE_PATIENT_BOB + NRIC_DESC_PATIENT_BOB + NAME_DESC_PATIENT_BOB
                + PHONE_DESC_PATIENT_BOB, expectedMessage);

        // missing nric prefix -- donor
        assertParseFailure(parser, TYPE_DESC_DONOR_JOHN + NAME_DESC_DONOR_JOHN + VALID_NRIC_DONOR_JOHN
                + PHONE_DESC_DONOR_JOHN + AGE_DESC_DONOR_JOHN
                + BLOOD_TYPE_DESC_DONOR_JOHN + TISSUE_TYPE_DESC_DONOR_JOHN
                + ORGAN_DESC_DONOR_JOHN + ORGAN_EXPIRY_DATE_DESC_DONOR_JOHN,
                        expectedMessage);

        //missing nric prefix -- patient
        assertParseFailure(parser, TYPE_DESC_PATIENT_IRENE + NAME_DESC_PATIENT_IRENE
                + VALID_NRIC_PATIENT_IRENE + PHONE_DESC_PATIENT_IRENE + AGE_DESC_PATIENT_IRENE
                + PRIORITY_DESC_PATIENT_IRENE + BLOOD_TYPE_DESC_PATIENT_IRENE + TISSUE_TYPE_DESC_PATIENT_IRENE
                + ORGAN_DESC_PATIENT_IRENE + DOCTOR_IN_CHARGE_DESC_PATIENT_IRENE,
                        expectedMessage);
        // missing nric prefix -- doctor
        assertParseFailure(parser, TYPE_DESC_DOCTOR_AMY + NAME_DESC_DOCTOR_AMY
                + VALID_NRIC_DOCTOR_AMY + PHONE_DESC_DOCTOR_AMY, expectedMessage);

        // missing name prefix -- donor
        assertParseFailure(parser, TYPE_DESC_DONOR_JOHN + NRIC_DESC_DONOR_JOHN + VALID_NAME_DONOR_JOHN
                + PHONE_DESC_DONOR_JOHN + AGE_DESC_DONOR_JOHN
                + BLOOD_TYPE_DESC_DONOR_JOHN + TISSUE_TYPE_DESC_DONOR_JOHN
                + ORGAN_DESC_DONOR_JOHN + ORGAN_EXPIRY_DATE_DESC_DONOR_JOHN,
                        expectedMessage);

        // missing name prefix -- patient
        assertParseFailure(parser, TYPE_DESC_PATIENT_IRENE + NRIC_DESC_PATIENT_IRENE
                + VALID_NAME_PATIENT_IRENE + PHONE_DESC_PATIENT_IRENE + AGE_DESC_PATIENT_IRENE
                + PRIORITY_DESC_PATIENT_IRENE + BLOOD_TYPE_DESC_PATIENT_IRENE + TISSUE_TYPE_DESC_PATIENT_IRENE
                + ORGAN_DESC_PATIENT_IRENE + DOCTOR_IN_CHARGE_DESC_PATIENT_IRENE,
                        expectedMessage);

        // missing name prefix -- doctor
        assertParseFailure(parser, TYPE_DESC_DOCTOR_AMY + NRIC_DESC_DOCTOR_AMY
            + VALID_NAME_DOCTOR_AMY + PHONE_DESC_DOCTOR_AMY, expectedMessage);

        // missing phone prefix -- donor
        assertParseFailure(parser, TYPE_DESC_DONOR_JOHN + NRIC_DESC_DONOR_JOHN + NAME_DESC_DONOR_JOHN
                + VALID_PHONE_DONOR_JOHN + AGE_DESC_DONOR_JOHN
                + BLOOD_TYPE_DESC_DONOR_JOHN + TISSUE_TYPE_DESC_DONOR_JOHN
                + ORGAN_DESC_DONOR_JOHN + ORGAN_EXPIRY_DATE_DESC_DONOR_JOHN,
                        expectedMessage);

        // missing phone prefix -- patient
        assertParseFailure(parser, TYPE_DESC_PATIENT_IRENE + NRIC_DESC_PATIENT_IRENE
                + NAME_DESC_PATIENT_IRENE + VALID_PHONE_PATIENT_IRENE + AGE_DESC_PATIENT_IRENE
                + PRIORITY_DESC_PATIENT_IRENE + BLOOD_TYPE_DESC_PATIENT_IRENE + TISSUE_TYPE_DESC_PATIENT_IRENE
                + ORGAN_DESC_PATIENT_IRENE + DOCTOR_IN_CHARGE_DESC_PATIENT_IRENE,
                        expectedMessage);

        // missing phone prefix -- doctor
        assertParseFailure(parser, TYPE_DESC_DOCTOR_AMY + NRIC_DESC_DOCTOR_AMY
                + NAME_DESC_DOCTOR_AMY + VALID_PHONE_DOCTOR_AMY, expectedMessage);

        // missing age prefix -- donor
        assertParseFailure(parser, TYPE_DESC_DONOR_JOHN + NRIC_DESC_DONOR_JOHN + NAME_DESC_DONOR_JOHN
                + PHONE_DESC_DONOR_JOHN + VALID_AGE_DONOR_JOHN
                + BLOOD_TYPE_DESC_DONOR_JOHN + TISSUE_TYPE_DESC_DONOR_JOHN
                + ORGAN_DESC_DONOR_JOHN + ORGAN_EXPIRY_DATE_DESC_DONOR_JOHN,
                        expectedMessage);

        // missing age prefix -- patient
        assertParseFailure(parser, TYPE_DESC_PATIENT_IRENE + NRIC_DESC_PATIENT_IRENE
                + NAME_DESC_PATIENT_IRENE + PHONE_DESC_PATIENT_IRENE + VALID_AGE_PATIENT_IRENE
                + PRIORITY_DESC_PATIENT_IRENE + BLOOD_TYPE_DESC_PATIENT_IRENE + TISSUE_TYPE_DESC_PATIENT_IRENE
                + ORGAN_DESC_PATIENT_IRENE + DOCTOR_IN_CHARGE_DESC_PATIENT_IRENE,
                        expectedMessage);

        // missing priority prefix -- patient
        assertParseFailure(parser, TYPE_DESC_PATIENT_IRENE + NRIC_DESC_PATIENT_IRENE
                + NAME_DESC_PATIENT_IRENE + PHONE_DESC_PATIENT_IRENE + AGE_DESC_PATIENT_IRENE
                + VALID_PRIORITY_PATIENT_IRENE + BLOOD_TYPE_DESC_PATIENT_IRENE + TISSUE_TYPE_DESC_PATIENT_IRENE
                + ORGAN_DESC_PATIENT_IRENE + DOCTOR_IN_CHARGE_DESC_PATIENT_IRENE,
                        expectedMessage);

        // missing blood type prefix -- donor
        assertParseFailure(parser, TYPE_DESC_DONOR_JOHN + NRIC_DESC_DONOR_JOHN + NAME_DESC_DONOR_JOHN
                + PHONE_DESC_DONOR_JOHN + AGE_DESC_DONOR_JOHN
                + VALID_BLOOD_TYPE_DONOR_JOHN + TISSUE_TYPE_DESC_DONOR_JOHN
                + ORGAN_DESC_DONOR_JOHN + ORGAN_EXPIRY_DATE_DESC_DONOR_JOHN,
                        expectedMessage);

        // missing blood type prefix -- patient
        assertParseFailure(parser, TYPE_DESC_PATIENT_IRENE + NRIC_DESC_PATIENT_IRENE
                + NAME_DESC_PATIENT_IRENE + PHONE_DESC_PATIENT_IRENE + AGE_DESC_PATIENT_IRENE
                + PRIORITY_DESC_PATIENT_IRENE + VALID_BLOOD_TYPE_PATIENT_IRENE + TISSUE_TYPE_DESC_PATIENT_IRENE
                + ORGAN_DESC_PATIENT_IRENE + DOCTOR_IN_CHARGE_DESC_PATIENT_IRENE,
                        expectedMessage);

        // missing tissue type prefix -- donor
        assertParseFailure(parser, TYPE_DESC_DONOR_JOHN + NRIC_DESC_DONOR_JOHN + NAME_DESC_DONOR_JOHN
                + PHONE_DESC_DONOR_JOHN + AGE_DESC_DONOR_JOHN
                + BLOOD_TYPE_DESC_DONOR_JOHN + VALID_TISSUE_TYPE_DONOR_JOHN
                + ORGAN_DESC_DONOR_JOHN + ORGAN_EXPIRY_DATE_DESC_DONOR_JOHN,
                        expectedMessage);

        // missing tissue type prefix -- patient
        assertParseFailure(parser, TYPE_DESC_PATIENT_IRENE + NRIC_DESC_PATIENT_IRENE
                + NAME_DESC_PATIENT_IRENE + PHONE_DESC_PATIENT_IRENE + AGE_DESC_PATIENT_IRENE
                + PRIORITY_DESC_PATIENT_IRENE + BLOOD_TYPE_DESC_PATIENT_IRENE + VALID_TISSUE_TYPE_PATIENT_IRENE
                + ORGAN_DESC_PATIENT_IRENE + DOCTOR_IN_CHARGE_DESC_PATIENT_IRENE,
                        expectedMessage);

        // missing organ prefix -- donor
        assertParseFailure(parser, TYPE_DESC_DONOR_JOHN + NRIC_DESC_DONOR_JOHN + NAME_DESC_DONOR_JOHN
                + PHONE_DESC_DONOR_JOHN + AGE_DESC_DONOR_JOHN
                + BLOOD_TYPE_DESC_DONOR_JOHN + TISSUE_TYPE_DESC_DONOR_JOHN
                + VALID_ORGAN_DONOR_JOHN + ORGAN_EXPIRY_DATE_DESC_DONOR_JOHN,
                        expectedMessage);

        // missing organ prefix -- patient
        assertParseFailure(parser, TYPE_DESC_PATIENT_IRENE + NRIC_DESC_PATIENT_IRENE
                + NAME_DESC_PATIENT_IRENE + PHONE_DESC_PATIENT_IRENE + AGE_DESC_PATIENT_IRENE
                + PRIORITY_DESC_PATIENT_IRENE + BLOOD_TYPE_DESC_PATIENT_IRENE + TISSUE_TYPE_DESC_PATIENT_IRENE
                + VALID_ORGAN_PATIENT_IRENE + DOCTOR_IN_CHARGE_DESC_PATIENT_IRENE,
                        expectedMessage);

        // missing organ expiry date prefix -- donor
        assertParseFailure(parser, TYPE_DESC_DONOR_JOHN + NRIC_DESC_DONOR_JOHN + NAME_DESC_DONOR_JOHN
                + PHONE_DESC_DONOR_JOHN + AGE_DESC_DONOR_JOHN
                + BLOOD_TYPE_DESC_DONOR_JOHN + TISSUE_TYPE_DESC_DONOR_JOHN
                + ORGAN_DESC_DONOR_JOHN + VALID_ORGAN_EXPIRY_DATE_DONOR_JOHN,
                        expectedMessage);

        // missing doctor in charge prefix -- patient
        assertParseFailure(parser, TYPE_DESC_PATIENT_IRENE + NRIC_DESC_PATIENT_IRENE
                + NAME_DESC_PATIENT_IRENE + PHONE_DESC_PATIENT_IRENE + AGE_DESC_PATIENT_IRENE
                + PRIORITY_DESC_PATIENT_IRENE + BLOOD_TYPE_DESC_PATIENT_IRENE + TISSUE_TYPE_DESC_PATIENT_IRENE
                + ORGAN_DESC_PATIENT_IRENE + VALID_DOCTOR_IN_CHARGE_PATIENT_IRENE,
                        expectedMessage);

        // all prefixes missing -- donor
        assertParseFailure(parser, VALID_TYPE_DONOR_JOHN + VALID_NRIC_DONOR_JOHN + VALID_NAME_DONOR_JOHN
                + VALID_PHONE_DONOR_JOHN + VALID_AGE_DONOR_JOHN
                + VALID_BLOOD_TYPE_DONOR_JOHN + VALID_TISSUE_TYPE_DONOR_JOHN
                + VALID_ORGAN_DONOR_JOHN + VALID_ORGAN_EXPIRY_DATE_DONOR_JOHN,
                        expectedMessage);

        // all prefixes missing -- patient
        assertParseFailure(parser, VALID_TYPE_PATIENT_IRENE + VALID_NRIC_PATIENT_IRENE + VALID_NAME_PATIENT_IRENE
                + VALID_PHONE_PATIENT_IRENE + VALID_AGE_PATIENT_IRENE
                + VALID_BLOOD_TYPE_PATIENT_IRENE + VALID_TISSUE_TYPE_PATIENT_IRENE
                + VALID_ORGAN_PATIENT_IRENE + VALID_DOCTOR_IN_CHARGE_PATIENT_IRENE,
                        expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid type
        assertParseFailure(parser, INVALID_TYPE_DESC + NRIC_DESC_PATIENT_BOB + NAME_DESC_PATIENT_BOB
            + PHONE_DESC_PATIENT_BOB, Type.MESSAGE_CONSTRAINTS);

        // invalid nric -- donor
        assertParseFailure(parser, TYPE_DESC_DONOR_JOHN + NAME_DESC_DONOR_JOHN + INVALID_NRIC_DESC
                + PHONE_DESC_DONOR_JOHN + AGE_DESC_DONOR_JOHN
                + BLOOD_TYPE_DESC_DONOR_JOHN + TISSUE_TYPE_DESC_DONOR_JOHN
                + ORGAN_DESC_DONOR_JOHN + ORGAN_EXPIRY_DATE_DESC_DONOR_JOHN,
                        Nric.MESSAGE_CONSTRAINTS);

        //invalid nric -- patient
        assertParseFailure(parser, TYPE_DESC_PATIENT_IRENE + NAME_DESC_PATIENT_IRENE
                + INVALID_NRIC_DESC + PHONE_DESC_PATIENT_IRENE + AGE_DESC_PATIENT_IRENE
                + PRIORITY_DESC_PATIENT_IRENE + BLOOD_TYPE_DESC_PATIENT_IRENE + TISSUE_TYPE_DESC_PATIENT_IRENE
                + ORGAN_DESC_PATIENT_IRENE + DOCTOR_IN_CHARGE_DESC_PATIENT_IRENE,
                        Nric.MESSAGE_CONSTRAINTS);

        // invalid nric -- doctor
        assertParseFailure(parser, TYPE_DESC_DOCTOR_AMY + NAME_DESC_DOCTOR_AMY
            + INVALID_NRIC_DESC + PHONE_DESC_DOCTOR_AMY, Nric.MESSAGE_CONSTRAINTS);

        // invalid name -- donor
        assertParseFailure(parser, TYPE_DESC_DONOR_JOHN + NRIC_DESC_DONOR_JOHN + INVALID_NAME_DESC
                + PHONE_DESC_DONOR_JOHN + AGE_DESC_DONOR_JOHN
                + BLOOD_TYPE_DESC_DONOR_JOHN + TISSUE_TYPE_DESC_DONOR_JOHN
                + ORGAN_DESC_DONOR_JOHN + ORGAN_EXPIRY_DATE_DESC_DONOR_JOHN,
                        Name.MESSAGE_CONSTRAINTS);

        // invalid name -- patient
        assertParseFailure(parser, TYPE_DESC_PATIENT_IRENE + NRIC_DESC_PATIENT_IRENE
                + INVALID_NAME_DESC + PHONE_DESC_PATIENT_IRENE + AGE_DESC_PATIENT_IRENE
                + PRIORITY_DESC_PATIENT_IRENE + BLOOD_TYPE_DESC_PATIENT_IRENE + TISSUE_TYPE_DESC_PATIENT_IRENE
                + ORGAN_DESC_PATIENT_IRENE + DOCTOR_IN_CHARGE_DESC_PATIENT_IRENE,
                        Name.MESSAGE_CONSTRAINTS);

        // invalid name -- doctor
        assertParseFailure(parser, TYPE_DESC_DOCTOR_AMY + NRIC_DESC_DOCTOR_AMY
            + INVALID_NAME_DESC + PHONE_DESC_DOCTOR_AMY, Name.MESSAGE_CONSTRAINTS);

        // invalid phone -- donor
        assertParseFailure(parser, TYPE_DESC_DONOR_JOHN + NRIC_DESC_DONOR_JOHN + NAME_DESC_DONOR_JOHN
                + INVALID_PHONE_DESC + AGE_DESC_DONOR_JOHN
                + BLOOD_TYPE_DESC_DONOR_JOHN + TISSUE_TYPE_DESC_DONOR_JOHN
                + ORGAN_DESC_DONOR_JOHN + ORGAN_EXPIRY_DATE_DESC_DONOR_JOHN,
                        Phone.MESSAGE_CONSTRAINTS);

        // invalid phone -- patient
        assertParseFailure(parser, TYPE_DESC_PATIENT_IRENE + NRIC_DESC_PATIENT_IRENE
                + NAME_DESC_PATIENT_IRENE + INVALID_PHONE_DESC + AGE_DESC_PATIENT_IRENE
                + PRIORITY_DESC_PATIENT_IRENE + BLOOD_TYPE_DESC_PATIENT_IRENE + TISSUE_TYPE_DESC_PATIENT_IRENE
                + ORGAN_DESC_PATIENT_IRENE + DOCTOR_IN_CHARGE_DESC_PATIENT_IRENE,
                        Phone.MESSAGE_CONSTRAINTS);

        // invalid phone -- doctor
        assertParseFailure(parser, TYPE_DESC_DOCTOR_AMY + NRIC_DESC_DOCTOR_AMY
            + NAME_DESC_DOCTOR_AMY + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // invalid age -- donor
        assertParseFailure(parser, TYPE_DESC_DONOR_JOHN + NRIC_DESC_DONOR_JOHN + NAME_DESC_DONOR_JOHN
                + PHONE_DESC_DONOR_JOHN + INVALID_AGE_DESC
                + BLOOD_TYPE_DESC_DONOR_JOHN + TISSUE_TYPE_DESC_DONOR_JOHN
                + ORGAN_DESC_DONOR_JOHN + ORGAN_EXPIRY_DATE_DESC_DONOR_JOHN,
                        Age.MESSAGE_CONSTRAINTS);

        // invalid age -- patient
        assertParseFailure(parser, TYPE_DESC_PATIENT_IRENE + NRIC_DESC_PATIENT_IRENE
                + NAME_DESC_PATIENT_IRENE + PHONE_DESC_PATIENT_IRENE + INVALID_AGE_DESC
                + PRIORITY_DESC_PATIENT_IRENE + BLOOD_TYPE_DESC_PATIENT_IRENE + TISSUE_TYPE_DESC_PATIENT_IRENE
                + ORGAN_DESC_PATIENT_IRENE + DOCTOR_IN_CHARGE_DESC_PATIENT_IRENE,
                        Age.MESSAGE_CONSTRAINTS);

        // invalid priority -- patient
        assertParseFailure(parser, TYPE_DESC_PATIENT_IRENE + NRIC_DESC_PATIENT_IRENE
                + NAME_DESC_PATIENT_IRENE + PHONE_DESC_PATIENT_IRENE + AGE_DESC_PATIENT_IRENE
                + INVALID_PRIORITY_DESC + BLOOD_TYPE_DESC_PATIENT_IRENE + TISSUE_TYPE_DESC_PATIENT_IRENE
                + ORGAN_DESC_PATIENT_IRENE + DOCTOR_IN_CHARGE_DESC_PATIENT_IRENE,
                        Priority.MESSAGE_CONSTRAINTS);

        // invalid blood type -- donor
        assertParseFailure(parser, TYPE_DESC_DONOR_JOHN + NRIC_DESC_DONOR_JOHN + NAME_DESC_DONOR_JOHN
                + PHONE_DESC_DONOR_JOHN + AGE_DESC_DONOR_JOHN
                + INVALID_BLOOD_TYPE_DESC + TISSUE_TYPE_DESC_DONOR_JOHN
                + ORGAN_DESC_DONOR_JOHN + ORGAN_EXPIRY_DATE_DESC_DONOR_JOHN,
                        BloodType.MESSAGE_CONSTRAINTS);

        // invalid blood type -- patient
        assertParseFailure(parser, TYPE_DESC_PATIENT_IRENE + NRIC_DESC_PATIENT_IRENE
                + NAME_DESC_PATIENT_IRENE + PHONE_DESC_PATIENT_IRENE + AGE_DESC_PATIENT_IRENE
                + PRIORITY_DESC_PATIENT_IRENE + INVALID_BLOOD_TYPE_DESC + TISSUE_TYPE_DESC_PATIENT_IRENE
                + ORGAN_DESC_PATIENT_IRENE + DOCTOR_IN_CHARGE_DESC_PATIENT_IRENE,
                        BloodType.MESSAGE_CONSTRAINTS);

        // invalid tissue type -- donor
        assertParseFailure(parser, TYPE_DESC_DONOR_JOHN + NRIC_DESC_DONOR_JOHN + NAME_DESC_DONOR_JOHN
                + PHONE_DESC_DONOR_JOHN + AGE_DESC_DONOR_JOHN
                + BLOOD_TYPE_DESC_DONOR_JOHN + INVALID_TISSUE_TYPE_DESC
                + ORGAN_DESC_DONOR_JOHN + ORGAN_EXPIRY_DATE_DESC_DONOR_JOHN,
                        TissueType.MESSAGE_CONSTRAINTS);

        // invalid tissue type -- patient
        assertParseFailure(parser, TYPE_DESC_PATIENT_IRENE + NRIC_DESC_PATIENT_IRENE
                + NAME_DESC_PATIENT_IRENE + PHONE_DESC_PATIENT_IRENE + AGE_DESC_PATIENT_IRENE
                + PRIORITY_DESC_PATIENT_IRENE + BLOOD_TYPE_DESC_PATIENT_IRENE + INVALID_TISSUE_TYPE_DESC
                + ORGAN_DESC_PATIENT_IRENE + DOCTOR_IN_CHARGE_DESC_PATIENT_IRENE,
                        TissueType.MESSAGE_CONSTRAINTS);

        // invalid organ -- donor
        assertParseFailure(parser, TYPE_DESC_DONOR_JOHN + NRIC_DESC_DONOR_JOHN + NAME_DESC_DONOR_JOHN
                + PHONE_DESC_DONOR_JOHN + AGE_DESC_DONOR_JOHN
                + BLOOD_TYPE_DESC_DONOR_JOHN + TISSUE_TYPE_DESC_DONOR_JOHN
                + INVALID_ORGAN_DESC + ORGAN_EXPIRY_DATE_DESC_DONOR_JOHN,
                        Organ.MESSAGE_CONSTRAINTS);

        // invalid organ -- patient
        assertParseFailure(parser, TYPE_DESC_PATIENT_IRENE + NRIC_DESC_PATIENT_IRENE
                + NAME_DESC_PATIENT_IRENE + PHONE_DESC_PATIENT_IRENE + AGE_DESC_PATIENT_IRENE
                + PRIORITY_DESC_PATIENT_IRENE + BLOOD_TYPE_DESC_PATIENT_IRENE + TISSUE_TYPE_DESC_PATIENT_IRENE
                + INVALID_ORGAN_DESC + DOCTOR_IN_CHARGE_DESC_PATIENT_IRENE,
                        Organ.MESSAGE_CONSTRAINTS);

        // invalid organ expiry date -- donor
        assertParseFailure(parser, TYPE_DESC_DONOR_JOHN + NRIC_DESC_DONOR_JOHN + NAME_DESC_DONOR_JOHN
                + PHONE_DESC_DONOR_JOHN + AGE_DESC_DONOR_JOHN
                + BLOOD_TYPE_DESC_DONOR_JOHN + TISSUE_TYPE_DESC_DONOR_JOHN
                + ORGAN_DESC_DONOR_JOHN + INVALID_ORGAN_EXPIRY_DATE_DESC,
                        OrganExpiryDate.MESSAGE_CONSTRAINTS);

        // invalid doctor in charge -- patient
        assertParseFailure(parser, TYPE_DESC_PATIENT_IRENE + NRIC_DESC_PATIENT_IRENE
                + NAME_DESC_PATIENT_IRENE + PHONE_DESC_PATIENT_IRENE + AGE_DESC_PATIENT_IRENE
                + PRIORITY_DESC_PATIENT_IRENE + BLOOD_TYPE_DESC_PATIENT_IRENE + TISSUE_TYPE_DESC_PATIENT_IRENE
                + ORGAN_DESC_PATIENT_IRENE + INVALID_DOCTOR_IN_CHARGE_DESC,
                        DoctorInCharge.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported -- donor
        assertParseFailure(parser, TYPE_DESC_DONOR_JOHN + NRIC_DESC_DONOR_JOHN + NAME_DESC_DONOR_JOHN
                + PHONE_DESC_DONOR_JOHN + AGE_DESC_DONOR_JOHN
                + BLOOD_TYPE_DESC_DONOR_JOHN + TISSUE_TYPE_DESC_DONOR_JOHN
                + INVALID_ORGAN_DESC + INVALID_ORGAN_EXPIRY_DATE_DESC,
                        Organ.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported -- patient
        assertParseFailure(parser, TYPE_DESC_PATIENT_IRENE + NRIC_DESC_PATIENT_IRENE
                + NAME_DESC_PATIENT_IRENE + PHONE_DESC_PATIENT_IRENE + AGE_DESC_PATIENT_IRENE
                + INVALID_PRIORITY_DESC + BLOOD_TYPE_DESC_PATIENT_IRENE + TISSUE_TYPE_DESC_PATIENT_IRENE
                + ORGAN_DESC_PATIENT_IRENE + INVALID_DOCTOR_IN_CHARGE_DESC,
                        Priority.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported -- doctor
        assertParseFailure(parser, TYPE_DESC_DOCTOR_AMY + NAME_DESC_DOCTOR_AMY
            + INVALID_NRIC_DESC + INVALID_PHONE_DESC, Nric.MESSAGE_CONSTRAINTS);

        // non-empty preamble -- donor
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + TYPE_DESC_DONOR_JOHN + NRIC_DESC_DONOR_JOHN
                + NAME_DESC_DONOR_JOHN + PHONE_DESC_DONOR_JOHN + AGE_DESC_DONOR_JOHN,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        // non-empty preamble -- patient
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + TYPE_DESC_PATIENT_BOB + NRIC_DESC_PATIENT_BOB
                + NAME_DESC_PATIENT_BOB + PHONE_DESC_PATIENT_BOB + AGE_DESC_PATIENT_BOB + PRIORITY_DESC_PATIENT_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        // non-empty preamble -- doctor
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + TYPE_DESC_DOCTOR_AMY + PHONE_DESC_DOCTOR_AMY
            + NRIC_DESC_DOCTOR_AMY + NAME_DESC_DOCTOR_AMY,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
