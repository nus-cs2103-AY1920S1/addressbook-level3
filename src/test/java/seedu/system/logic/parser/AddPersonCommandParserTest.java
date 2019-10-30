package seedu.system.logic.parser;

import static seedu.system.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.system.logic.commands.CommandTestUtil.DOB_DESC_BOB;
import static seedu.system.logic.commands.CommandTestUtil.GENDER_DESC_BOB;
import static seedu.system.logic.commands.CommandTestUtil.INVALID_DOB_DESC;
import static seedu.system.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.system.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.system.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.system.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.system.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.system.logic.commands.CommandTestUtil.VALID_DOB_BOB;
import static seedu.system.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.system.logic.commands.CommandTestUtil.VALID_NAME_BOB;

import static seedu.system.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.system.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.system.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.system.logic.commands.outofsession.AddPersonCommand;
import seedu.system.logic.parser.outofsession.AddPersonCommandParser;
import seedu.system.model.person.CustomDate;
import seedu.system.model.person.Name;
import seedu.system.model.person.Person;
import seedu.system.testutil.PersonBuilder;

public class AddPersonCommandParserTest {
    private AddPersonCommandParser parser = new AddPersonCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + DOB_DESC_BOB + GENDER_DESC_BOB,
                new AddPersonCommand(expectedPerson));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + DOB_DESC_BOB
                + GENDER_DESC_BOB, new AddPersonCommand(expectedPerson));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPersonCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + DOB_DESC_BOB + GENDER_DESC_BOB, expectedMessage);

        // missing DOB prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_DOB_BOB + GENDER_DESC_BOB, expectedMessage);

        // missing gender prefix
        assertParseFailure(parser, NAME_DESC_BOB + DOB_DESC_BOB + VALID_GENDER_BOB , expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_DOB_BOB + VALID_GENDER_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + DOB_DESC_BOB + GENDER_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // invalid DOB
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_DOB_DESC + GENDER_DESC_BOB, CustomDate.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + INVALID_DOB_DESC + GENDER_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + DOB_DESC_BOB + GENDER_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPersonCommand.MESSAGE_USAGE));
    }
}
