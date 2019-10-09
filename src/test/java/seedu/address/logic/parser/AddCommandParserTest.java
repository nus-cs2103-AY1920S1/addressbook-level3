package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.AGE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.AGE_DESC_IRENE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NRIC_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TYPE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_IRENE;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_IRENE;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_IRENE;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TYPE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TYPE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TYPE_DESC_IRENE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AGE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_BOB;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.IRENE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;

import seedu.address.model.person.Patient;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Type;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(AMY).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TYPE_DESC_AMY + NRIC_DESC_AMY
                + NAME_DESC_AMY + PHONE_DESC_AMY, new AddCommand(expectedPerson));

        // multiple names - last name accepted
        assertParseSuccess(parser, TYPE_DESC_AMY + NRIC_DESC_AMY + NAME_DESC_BOB
                + NAME_DESC_AMY + PHONE_DESC_AMY, new AddCommand(expectedPerson));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, TYPE_DESC_AMY + NRIC_DESC_AMY + NAME_DESC_AMY
                + PHONE_DESC_BOB + PHONE_DESC_AMY, new AddCommand(expectedPerson));

        // multiple nrics - last nric accepted
        assertParseSuccess(parser, TYPE_DESC_AMY + NRIC_DESC_BOB + NRIC_DESC_AMY
                + NAME_DESC_AMY + PHONE_DESC_AMY, new AddCommand(expectedPerson));

        // multiple types - last type accepted
        assertParseSuccess(parser, TYPE_DESC_BOB + TYPE_DESC_AMY + NRIC_DESC_AMY
            + NAME_DESC_AMY + PHONE_DESC_AMY, new AddCommand(expectedPerson));

        //Testing all fields present for a Patient
        Patient expectedPatient = IRENE;

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TYPE_DESC_IRENE + NRIC_DESC_IRENE
                + NAME_DESC_IRENE + PHONE_DESC_IRENE + AGE_DESC_IRENE, new AddCommand(expectedPatient));

        // multiple names - last name accepted
        assertParseSuccess(parser, TYPE_DESC_IRENE + NRIC_DESC_IRENE + NAME_DESC_AMY
                + NAME_DESC_IRENE + PHONE_DESC_IRENE + AGE_DESC_IRENE, new AddCommand(expectedPatient));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, TYPE_DESC_IRENE + NRIC_DESC_IRENE + NAME_DESC_IRENE
                + PHONE_DESC_AMY + PHONE_DESC_IRENE + AGE_DESC_IRENE, new AddCommand(expectedPatient));

        // multiple nrics - last nric accepted
        assertParseSuccess(parser, TYPE_DESC_IRENE + NRIC_DESC_AMY + NRIC_DESC_IRENE + NAME_DESC_IRENE
                + PHONE_DESC_IRENE + AGE_DESC_IRENE, new AddCommand(expectedPatient));

        // multiple types - last type accepted
        assertParseSuccess(parser, TYPE_DESC_BOB + TYPE_DESC_IRENE + NRIC_DESC_IRENE
                + NAME_DESC_IRENE + PHONE_DESC_IRENE + AGE_DESC_IRENE, new AddCommand(expectedPatient));

    }

    //    @Test
    //    public void parse_optionalFieldsMissing_success() {
    //        // zero tags
    //        Person expectedPerson = new PersonBuilder(AMY).build();
    //        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY,
    //                new AddCommand(expectedPerson));
    //    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing type prefix
        assertParseFailure(parser, VALID_TYPE_BOB + NRIC_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB,
            expectedMessage);

        // missing nric prefix -- not patient
        assertParseFailure(parser, TYPE_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB,
            expectedMessage);

        //missing nric prefix -- is patient
        assertParseFailure(parser, TYPE_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                + AGE_DESC_BOB, expectedMessage);

        // missing name prefix -- not patient
        assertParseFailure(parser, TYPE_DESC_AMY + NRIC_DESC_BOB + PHONE_DESC_BOB,
                expectedMessage);

        // missing name prefix -- is patient
        assertParseFailure(parser, TYPE_DESC_BOB + NRIC_DESC_BOB + PHONE_DESC_BOB
                + AGE_DESC_BOB, expectedMessage);

        // missing phone prefix -- not patient
        assertParseFailure(parser, TYPE_DESC_AMY + NRIC_DESC_BOB + NAME_DESC_BOB,
                expectedMessage);

        // missing phone prefix -- is patient
        assertParseFailure(parser, TYPE_DESC_BOB + NRIC_DESC_BOB + NAME_DESC_BOB + AGE_DESC_BOB,
                expectedMessage);

        // all prefixes missing -- not patient
        assertParseFailure(parser, VALID_TYPE_AMY + VALID_NRIC_BOB + VALID_NAME_BOB + VALID_PHONE_BOB,
                expectedMessage);

        // all prefixes missing -- is patient
        assertParseFailure(parser, VALID_TYPE_BOB + VALID_NRIC_BOB + VALID_NAME_BOB + VALID_PHONE_BOB
                + VALID_AGE_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid type
        assertParseFailure(parser, INVALID_TYPE_DESC + NRIC_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB,
            Type.MESSAGE_CONSTRAINTS);

        // invalid nric -- not patient
        assertParseFailure(parser, TYPE_DESC_AMY + INVALID_NRIC_DESC + NAME_DESC_BOB + PHONE_DESC_BOB,
                Nric.MESSAGE_CONSTRAINTS);

        // invalid nric -- is patient
        assertParseFailure(parser, TYPE_DESC_BOB + INVALID_NRIC_DESC + NAME_DESC_BOB + PHONE_DESC_BOB
                + AGE_DESC_BOB, Nric.MESSAGE_CONSTRAINTS);

        // invalid name -- not patient
        assertParseFailure(parser, TYPE_DESC_AMY + NRIC_DESC_BOB + INVALID_NAME_DESC + PHONE_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // invalid name -- is patient
        assertParseFailure(parser, TYPE_DESC_BOB + NRIC_DESC_BOB + INVALID_NAME_DESC + PHONE_DESC_BOB
                + AGE_DESC_BOB, Name.MESSAGE_CONSTRAINTS);


        // invalid phone -- not patient
        assertParseFailure(parser, TYPE_DESC_AMY + NRIC_DESC_BOB + NAME_DESC_BOB + INVALID_PHONE_DESC,
                Phone.MESSAGE_CONSTRAINTS);

        // invalid phone -- is patient
        assertParseFailure(parser, TYPE_DESC_BOB + NRIC_DESC_BOB + NAME_DESC_BOB + INVALID_PHONE_DESC
                + AGE_DESC_BOB, Phone.MESSAGE_CONSTRAINTS);


        // two invalid values, only first invalid value reported -- not patient
        assertParseFailure(parser, TYPE_DESC_AMY + NRIC_DESC_BOB + INVALID_NAME_DESC + INVALID_PHONE_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported -- is patient
        assertParseFailure(parser, TYPE_DESC_BOB + NRIC_DESC_BOB + INVALID_NAME_DESC + INVALID_PHONE_DESC
                + AGE_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble -- not patient
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + TYPE_DESC_AMY + NRIC_DESC_BOB + NAME_DESC_BOB
                + PHONE_DESC_BOB, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        // non-empty preamble -- is patient
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + TYPE_DESC_BOB + NRIC_DESC_BOB + NAME_DESC_BOB
                + PHONE_DESC_BOB + AGE_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
