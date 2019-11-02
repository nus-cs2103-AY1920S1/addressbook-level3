package dukecooks.logic.parser.profile;

import static dukecooks.testutil.profile.TypicalProfiles.AMY;
import static dukecooks.testutil.profile.TypicalProfiles.BOB;

import org.junit.jupiter.api.Test;

import dukecooks.commons.core.Messages;
import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.logic.commands.profile.AddProfileCommand;
import dukecooks.logic.parser.CommandParserTestUtil;
import dukecooks.model.profile.medical.MedicalHistory;
import dukecooks.model.profile.person.Name;
import dukecooks.model.profile.person.Person;
import dukecooks.testutil.profile.PersonBuilder;

public class AddProfileCommandParserTest {

    private AddProfileCommandParser parser = new AddProfileCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withMedicalHistories(CommandTestUtil.VALID_HISTORY_STROKE)
                .build();

        // whitespace only preamble
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.PREAMBLE_WHITESPACE
                + CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.BLOODTYPE_DESC + CommandTestUtil.GENDER_DESC
                + CommandTestUtil.DOB_DESC + CommandTestUtil.WEIGHT_DESC + CommandTestUtil.HEIGHT_DESC
                + CommandTestUtil.HISTORY_DESC_STROKE, new AddProfileCommand(expectedPerson));

        // multiple names - last name accepted
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_AMY
                + CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.BLOODTYPE_DESC + CommandTestUtil.GENDER_DESC
                + CommandTestUtil.DOB_DESC + CommandTestUtil.WEIGHT_DESC + CommandTestUtil.HEIGHT_DESC
                 + CommandTestUtil.HISTORY_DESC_STROKE, new AddProfileCommand(expectedPerson));

        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder(BOB)
                .withMedicalHistories(CommandTestUtil.VALID_HISTORY_STROKE, CommandTestUtil.VALID_HISTORY_DENGUE)
                .build();
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_BOB
                + CommandTestUtil.BLOODTYPE_DESC + CommandTestUtil.GENDER_DESC
                + CommandTestUtil.DOB_DESC + CommandTestUtil.WEIGHT_DESC + CommandTestUtil.HEIGHT_DESC
                + CommandTestUtil.HISTORY_DESC_DENGUE + CommandTestUtil.HISTORY_DESC_STROKE,
                new AddProfileCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder(AMY).withMedicalHistories().build();
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_AMY
                        + CommandTestUtil.BLOODTYPE_DESC + CommandTestUtil.GENDER_DESC
                        + CommandTestUtil.DOB_DESC + CommandTestUtil.WEIGHT_DESC + CommandTestUtil.HEIGHT_DESC,
                new AddProfileCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                AddProfileCommand.MESSAGE_USAGE);

        // missing name prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_NAME_BOB,
                expectedMessage);

        // all prefixes missing
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_NAME_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_FOOD_NAME_DESC
                + CommandTestUtil.BLOODTYPE_DESC + CommandTestUtil.GENDER_DESC
                + CommandTestUtil.DOB_DESC + CommandTestUtil.WEIGHT_DESC + CommandTestUtil.HEIGHT_DESC
                + CommandTestUtil.HISTORY_DESC_DENGUE + CommandTestUtil.HISTORY_DESC_STROKE, Name.MESSAGE_CONSTRAINTS);

        // invalid tag
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_BOB
                + CommandTestUtil.BLOODTYPE_DESC + CommandTestUtil.GENDER_DESC
                + CommandTestUtil.DOB_DESC + CommandTestUtil.WEIGHT_DESC + CommandTestUtil.HEIGHT_DESC
                + CommandTestUtil.INVALID_TAG_DESC + CommandTestUtil.VALID_HISTORY_STROKE,
                MedicalHistory.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_FOOD_NAME_DESC
                        + CommandTestUtil.BLOODTYPE_DESC + CommandTestUtil.GENDER_DESC
                        + CommandTestUtil.DOB_DESC + CommandTestUtil.WEIGHT_DESC + CommandTestUtil.HEIGHT_DESC
                        + CommandTestUtil.INVALID_TAG_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.PREAMBLE_NON_EMPTY
                        + CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.BLOODTYPE_DESC + CommandTestUtil.GENDER_DESC
                        + CommandTestUtil.DOB_DESC + CommandTestUtil.WEIGHT_DESC + CommandTestUtil.HEIGHT_DESC
                 + CommandTestUtil.HISTORY_DESC_DENGUE + CommandTestUtil.HISTORY_DESC_STROKE,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddProfileCommand.MESSAGE_USAGE));
    }
}
