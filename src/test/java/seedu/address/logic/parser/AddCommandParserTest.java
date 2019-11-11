package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DATE_OF_BIRTH_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DATE_OF_BIRTH_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_OF_BIRTH_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GENDER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NRIC_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.POLICY_DESC_LIFE;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_SMOKER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_OF_BIRTH_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.PersonBuilder;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withPolicies().withTags().build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + NRIC_DESC_BOB + PHONE_DESC_BOB
            + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DATE_OF_BIRTH_DESC_BOB
            + GENDER_DESC_BOB, new AddCommand(expectedPerson));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + NRIC_DESC_BOB + PHONE_DESC_BOB
            + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DATE_OF_BIRTH_DESC_BOB
            + GENDER_DESC_BOB, new AddCommand(expectedPerson));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + NRIC_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB
            + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
            + DATE_OF_BIRTH_DESC_BOB + GENDER_DESC_BOB, new AddCommand(expectedPerson));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + NRIC_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY
            + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
            + DATE_OF_BIRTH_DESC_BOB + GENDER_DESC_BOB, new AddCommand(expectedPerson));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_BOB + NRIC_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
            + ADDRESS_DESC_AMY + ADDRESS_DESC_BOB
            + DATE_OF_BIRTH_DESC_BOB + GENDER_DESC_BOB, new AddCommand(expectedPerson));

        // multiple date of births - last date of birth accepted
        assertParseSuccess(parser, NAME_DESC_BOB + NRIC_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_AMY + ADDRESS_DESC_BOB + DATE_OF_BIRTH_DESC_AMY
                + DATE_OF_BIRTH_DESC_BOB + GENDER_DESC_BOB,
            new AddCommand(expectedPerson));

        // multiple gender - last gender accepted
        assertParseSuccess(parser, NAME_DESC_BOB + NRIC_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_AMY + ADDRESS_DESC_BOB + DATE_OF_BIRTH_DESC_BOB + GENDER_DESC_AMY + GENDER_DESC_BOB,
            new AddCommand(expectedPerson));
    }

    @Test
    public void parse_policyFieldIncluded_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        assertParseFailure(parser, NAME_DESC_AMY + NRIC_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
            + ADDRESS_DESC_AMY + DATE_OF_BIRTH_DESC_AMY + GENDER_DESC_AMY + POLICY_DESC_LIFE, expectedMessage);
    }

    @Test
    public void parse_tagFieldIncluded_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        assertParseFailure(parser, NAME_DESC_AMY + NRIC_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
            + ADDRESS_DESC_AMY + DATE_OF_BIRTH_DESC_AMY + GENDER_DESC_AMY + TAG_DESC_SMOKER, expectedMessage);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + NRIC_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + DATE_OF_BIRTH_DESC_BOB + GENDER_DESC_AMY,
            expectedMessage);

        // missing nric prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_NRIC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + DATE_OF_BIRTH_DESC_BOB + GENDER_DESC_AMY,
            expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + NRIC_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + DATE_OF_BIRTH_DESC_BOB + GENDER_DESC_AMY,
            expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + NRIC_DESC_BOB + PHONE_DESC_BOB
                + VALID_EMAIL_BOB + ADDRESS_DESC_BOB + DATE_OF_BIRTH_DESC_BOB + GENDER_DESC_AMY,
            expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + NRIC_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + VALID_ADDRESS_BOB + DATE_OF_BIRTH_DESC_BOB + GENDER_DESC_AMY,
            expectedMessage);

        // missing date of birth prefix
        assertParseFailure(parser, NAME_DESC_BOB + NRIC_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + VALID_DATE_OF_BIRTH_BOB + GENDER_DESC_AMY,
            expectedMessage);

        // missing gender prefix
        assertParseFailure(parser, NAME_DESC_BOB + NRIC_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + VALID_DATE_OF_BIRTH_BOB,
            expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_NRIC_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB
                + VALID_ADDRESS_BOB + VALID_DATE_OF_BIRTH_BOB,
            expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + NRIC_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
            + ADDRESS_DESC_BOB + DATE_OF_BIRTH_DESC_BOB + GENDER_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // invalid nric
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_NRIC_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
            + ADDRESS_DESC_BOB + DATE_OF_BIRTH_DESC_BOB + GENDER_DESC_BOB, Nric.MESSAGE_CONSTRAINTS
            + ParseExceptionHandler.CONTACT_SUGGESTION_HEADER + VALID_NAME_BOB + "\n" + Phone.HEADER
            + VALID_PHONE_BOB + "\n" + Email.HEADER + VALID_EMAIL_BOB);

        // invalid nric and invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_NRIC_DESC + INVALID_PHONE_DESC + EMAIL_DESC_BOB
            + ADDRESS_DESC_BOB + DATE_OF_BIRTH_DESC_BOB + GENDER_DESC_BOB, Nric.MESSAGE_CONSTRAINTS
            + ParseExceptionHandler.CONTACT_SUGGESTION_HEADER + VALID_NAME_BOB + "\n"
            + Email.HEADER + VALID_EMAIL_BOB);

        // invalid nric and invalid email
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_NRIC_DESC + PHONE_DESC_BOB + INVALID_EMAIL_DESC
            + ADDRESS_DESC_BOB + DATE_OF_BIRTH_DESC_BOB + GENDER_DESC_BOB, Nric.MESSAGE_CONSTRAINTS
            + ParseExceptionHandler.CONTACT_SUGGESTION_HEADER + VALID_NAME_BOB + "\n"
            + Phone.HEADER + VALID_PHONE_BOB);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + NRIC_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + DATE_OF_BIRTH_DESC_BOB + GENDER_DESC_BOB,
            Phone.MESSAGE_CONSTRAINTS + ParseExceptionHandler.CONTACT_SUGGESTION_HEADER
                + VALID_NAME_BOB + "\n" + Email.HEADER + VALID_EMAIL_BOB);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + NRIC_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC
                + ADDRESS_DESC_BOB + DATE_OF_BIRTH_DESC_BOB + GENDER_DESC_BOB,
            Email.MESSAGE_CONSTRAINTS + ParseExceptionHandler.CONTACT_SUGGESTION_HEADER
                + VALID_NAME_BOB + "\n" + Phone.HEADER + VALID_PHONE_BOB);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + NRIC_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + INVALID_ADDRESS_DESC + DATE_OF_BIRTH_DESC_BOB + GENDER_DESC_BOB,
            Address.MESSAGE_CONSTRAINTS);

        // invalid date of birth
        assertParseFailure(parser, NAME_DESC_BOB + NRIC_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + INVALID_DATE_OF_BIRTH_DESC + GENDER_DESC_BOB,
            DateOfBirth.MESSAGE_CONSTRAINTS);

        // invalid gender
        assertParseFailure(parser, NAME_DESC_BOB + NRIC_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
            + ADDRESS_DESC_BOB + DATE_OF_BIRTH_DESC_BOB + INVALID_GENDER_DESC, Gender.getMessageConstraints());

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + NRIC_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + INVALID_ADDRESS_DESC + DATE_OF_BIRTH_DESC_BOB + GENDER_DESC_BOB,
            Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + NRIC_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DATE_OF_BIRTH_DESC_BOB + GENDER_DESC_BOB,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
