package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.BLOODTYPE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.DOB_DESC;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.HEIGHT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.HISTORY_DESC_DENGUE;
import static seedu.address.logic.commands.CommandTestUtil.HISTORY_DESC_STROKE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HISTORY_DENGUE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HISTORY_STROKE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.WEIGHT_DESC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddProfileCommand;
import seedu.address.profile.medical.MedicalHistory;
import seedu.address.profile.person.Name;
import seedu.address.profile.person.Person;
import seedu.address.testutil.PersonBuilder;

public class AddProfileCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withMedicalHistories(VALID_HISTORY_STROKE).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + BLOODTYPE_DESC + GENDER_DESC
                + DOB_DESC + WEIGHT_DESC + HEIGHT_DESC + HISTORY_DESC_STROKE, new AddProfileCommand(expectedPerson));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + BLOODTYPE_DESC + GENDER_DESC
                + DOB_DESC + WEIGHT_DESC + HEIGHT_DESC
                 + HISTORY_DESC_STROKE, new AddProfileCommand(expectedPerson));

        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder(BOB)
                .withMedicalHistories(VALID_HISTORY_STROKE, VALID_HISTORY_DENGUE)
                .build();
        assertParseSuccess(parser, NAME_DESC_BOB + BLOODTYPE_DESC + GENDER_DESC
                + DOB_DESC + WEIGHT_DESC + HEIGHT_DESC
                + HISTORY_DESC_DENGUE + HISTORY_DESC_STROKE, new AddProfileCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder(AMY).withMedicalHistories().build();
        assertParseSuccess(parser, NAME_DESC_AMY + BLOODTYPE_DESC + GENDER_DESC
                        + DOB_DESC + WEIGHT_DESC + HEIGHT_DESC,
                new AddProfileCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddProfileCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + BLOODTYPE_DESC + GENDER_DESC
                + DOB_DESC + WEIGHT_DESC + HEIGHT_DESC
                + HISTORY_DESC_DENGUE + HISTORY_DESC_STROKE, Name.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + BLOODTYPE_DESC + GENDER_DESC
                + DOB_DESC + WEIGHT_DESC + HEIGHT_DESC
                + INVALID_TAG_DESC + VALID_HISTORY_STROKE, MedicalHistory.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + BLOODTYPE_DESC + GENDER_DESC
                        + DOB_DESC + WEIGHT_DESC + HEIGHT_DESC + INVALID_TAG_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + BLOODTYPE_DESC + GENDER_DESC
                        + DOB_DESC + WEIGHT_DESC + HEIGHT_DESC
                 + HISTORY_DESC_DENGUE + HISTORY_DESC_STROKE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddProfileCommand.MESSAGE_USAGE));
    }
}
