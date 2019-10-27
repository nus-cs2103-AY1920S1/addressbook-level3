package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DATE_OF_BIRTH_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DATE_OF_BIRTH_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.PersonBuilder;
import seedu.address.logic.commands.merge.DoNotMergePersonCommand;
import seedu.address.model.person.Person;

public class DoNotMergePersonCommandParserTest {
    private DoNotMergePersonCommandParser parser = new DoNotMergePersonCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withPolicies().withTags().build();

        // whitespace only preamble
        assertParseSuccess(parser, DoNotMergePersonCommand.COMMAND_WORD + PREAMBLE_WHITESPACE + NAME_DESC_BOB
            + NRIC_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DATE_OF_BIRTH_DESC_BOB
            + GENDER_DESC_BOB, new DoNotMergePersonCommand(expectedPerson));

        // multiple names - last name accepted
        assertParseSuccess(parser, DoNotMergePersonCommand.COMMAND_WORD + NAME_DESC_AMY + NAME_DESC_BOB
            + NRIC_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + DATE_OF_BIRTH_DESC_BOB
            + GENDER_DESC_BOB, new DoNotMergePersonCommand(expectedPerson));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, DoNotMergePersonCommand.COMMAND_WORD + NAME_DESC_BOB + NRIC_DESC_BOB
            + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
            + DATE_OF_BIRTH_DESC_BOB + GENDER_DESC_BOB, new DoNotMergePersonCommand(expectedPerson));

        // multiple emails - last email accepted
        assertParseSuccess(parser, DoNotMergePersonCommand.COMMAND_WORD + NAME_DESC_BOB + NRIC_DESC_BOB
            + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
            + DATE_OF_BIRTH_DESC_BOB + GENDER_DESC_BOB, new DoNotMergePersonCommand(expectedPerson));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, DoNotMergePersonCommand.COMMAND_WORD + NAME_DESC_BOB + NRIC_DESC_BOB
            + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_AMY + ADDRESS_DESC_BOB
            + DATE_OF_BIRTH_DESC_BOB + GENDER_DESC_BOB, new DoNotMergePersonCommand(expectedPerson));

        // multiple date of births - last date of birth accepted
        assertParseSuccess(parser, DoNotMergePersonCommand.COMMAND_WORD + NAME_DESC_BOB + NRIC_DESC_BOB
            + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_AMY + ADDRESS_DESC_BOB + DATE_OF_BIRTH_DESC_AMY
            + DATE_OF_BIRTH_DESC_BOB + GENDER_DESC_BOB,
            new DoNotMergePersonCommand(expectedPerson));

        // multiple gender - last gender accepted
        assertParseSuccess(parser, DoNotMergePersonCommand.COMMAND_WORD + NAME_DESC_BOB + NRIC_DESC_BOB
            + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_AMY + ADDRESS_DESC_BOB + DATE_OF_BIRTH_DESC_BOB
            + GENDER_DESC_AMY + GENDER_DESC_BOB, new DoNotMergePersonCommand(expectedPerson));
    }
}
