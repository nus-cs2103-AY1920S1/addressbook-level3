package seedu.sugarmummy.logic.parser.records;

import static seedu.sugarmummy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

import org.junit.jupiter.api.Test;

import seedu.sugarmummy.logic.commands.records.AddCommand;
import seedu.sugarmummy.logic.parser.CommandParserTestUtil;
import seedu.sugarmummy.model.records.BloodSugar;
import seedu.sugarmummy.model.records.Bmi;
import seedu.sugarmummy.model.records.Concentration;
import seedu.sugarmummy.model.records.Height;
import seedu.sugarmummy.model.records.Weight;
import seedu.sugarmummy.model.time.DateTime;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {

        LocalDate ld = LocalDate.of(1970, Month.JANUARY, 1);
        LocalTime lt = LocalTime.of(8, 0, 0);
        DateTime dt = new DateTime(ld, lt);

        //bloodsugar success
        BloodSugar bs = new BloodSugar(new Concentration("12.34"), dt);
        CommandParserTestUtil.assertParseSuccess(parser, " rt/BLOODSUGAR con/12.34 dt/1970-01-01 08:00",
                new AddCommand(bs));

        //bmi success
        Bmi bmi = new Bmi(new Height("2.34"), new Weight("23.34"), dt);
        CommandParserTestUtil.assertParseSuccess(parser, " rt/BMI h/2.34 w/23.45 dt/1970-01-01 08:00",
                new AddCommand(bmi));

        //        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
        //            + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(bmi));

        //        Person expectedPerson = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND).build();
        //
        //        // whitespace only preamble
        //        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
        //                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));
        //
        //        // multiple names - last name accepted
        //        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
        //                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));
        //
        //        // multiple phones - last phone accepted
        //        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
        //                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));
        //
        //        // multiple emails - last email accepted
        //        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
        //                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));
        //
        //        // multiple addresses - last address accepted
        //        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
        //                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));
        //
        //        // multiple tags - all accepted
        //        Person expectedPersonMultipleTags = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND,
        //        VALID_TAG_HUSBAND)
        //                .build();
        //        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
        //                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddCommand(expectedPersonMultipleTags));
    }

    //    @Test
    //    public void parse_optionalFieldsMissing_success() {
    //        // zero tags
    //        Person expectedPerson = new PersonBuilder(AMY).withTags().build();
    //        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY,
    //                new AddCommand(expectedPerson));
    //    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        CommandParserTestUtil.assertParseFailure(parser, " rt/BLOODSUGAR dt/1970-01-01 08:00",
                expectedMessage);
        CommandParserTestUtil.assertParseFailure(parser, " rt/BLOODSUGAR con/12.34",
                expectedMessage);
        CommandParserTestUtil.assertParseFailure(parser, " rt/BMI w/1 h/1",
                expectedMessage);
        CommandParserTestUtil.assertParseFailure(parser, " rt/BMI h/1 dt/1970-01-01 08:00",
                expectedMessage);
        CommandParserTestUtil.assertParseFailure(parser, " rt/BMI w/1 dt/1970-01-01 08:00",
                expectedMessage);

        //        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        //
        //        // missing name prefix
        //        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
        //                expectedMessage);
        //
        //        // missing phone prefix
        //        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
        //                expectedMessage);
        //
        //        // missing email prefix
        //        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB,
        //                expectedMessage);
        //
        //        // missing address prefix
        //        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB,
        //                expectedMessage);
        //
        //        // all prefixes missing
        //        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB,
        //                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {

        //        assertParseFailure(parser, " rt/BLOODSUGAR con/-1.23 dt/hello", Bmi.MESSAGE_CONSTRAINTS);
        //
        //        assertParseFailure(parser, " rt/BMI h/-1 w/-1 dt/hello", Bmi.MESSAGE_CONSTRAINTS);

        //        // invalid name
        //        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
        //                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);
        //
        //        // invalid phone
        //        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
        //                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);
        //
        //        // invalid email
        //        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
        //                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);
        //
        //        // invalid address
        //        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
        //                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Address.MESSAGE_CONSTRAINTS);
        //
        //        // invalid tag
        //        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
        //                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);
        //
        //        // two invalid values, only first invalid value reported
        //        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC,
        //                Name.MESSAGE_CONSTRAINTS);
        //
        //        // non-empty preamble
        //        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
        //                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
        //                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
