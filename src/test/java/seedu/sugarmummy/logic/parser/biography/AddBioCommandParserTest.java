package seedu.sugarmummy.logic.parser.biography;

import static seedu.sugarmummy.commons.core.Messages.MESSAGE_ENSURE_ONLY_ONE_PREFIX_SINGULAR;
import static seedu.sugarmummy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.sugarmummy.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.sugarmummy.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.sugarmummy.logic.commands.biography.AddBioCommand;
import seedu.sugarmummy.logic.parser.exceptions.ParseException;
import seedu.sugarmummy.model.biography.Address;
import seedu.sugarmummy.model.biography.DateOfBirth;
import seedu.sugarmummy.model.biography.DisplayPicPath;
import seedu.sugarmummy.model.biography.Gender;
import seedu.sugarmummy.model.biography.Goal;
import seedu.sugarmummy.model.biography.MedicalCondition;
import seedu.sugarmummy.model.biography.Name;
import seedu.sugarmummy.model.biography.Nric;
import seedu.sugarmummy.model.biography.OtherBioInfo;
import seedu.sugarmummy.model.biography.Phone;
import seedu.sugarmummy.model.biography.ProfileDesc;
import seedu.sugarmummy.model.biography.User;

class AddBioCommandParserTest {

    private AddBioCommandParser parser = new AddBioCommandParser();

    @Test
    public void parseFailure_missingName() {
        assertParseFailure(parser, " p/12345 e/23456 m/Type II Diabetes",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        AddBioCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseFailure_missingContactNumbers() {
        assertParseFailure(parser, " n/Bob e/23456 m/Type II Diabetes",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        AddBioCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseFailure_missingEmergencyContacts() {
        assertParseFailure(parser, " n/Bob p/12345 m/Type II Diabetes",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        AddBioCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseFailure_missingMedicalConditions() {
        assertParseFailure(parser, " n/Bob p/12345 e/23456",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        AddBioCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseFailure_multipleNames() {
        assertParseFailure(parser, " n/Bob n/Amy p/12345 e/23456 m/Type II Diabetes",
                MESSAGE_ENSURE_ONLY_ONE_PREFIX_SINGULAR + "[n/]");
    }

    @Test
    public void parseFailure_multipleDpPaths() {
        assertParseFailure(parser, " n/Bob dp//Users/doge.png dp//Users/doge.jpg p/12345 e/23456"
                        + " m/Type II Diabetes",
                MESSAGE_ENSURE_ONLY_ONE_PREFIX_SINGULAR + "[dp/]");
    }

    @Test
    public void parseFailure_multipleProfileDescriptions() {
        assertParseFailure(parser, " n/Bob desc/a description desc/another description p/12345 e/23456 "
                        + "m/Type II Diabetes",
                MESSAGE_ENSURE_ONLY_ONE_PREFIX_SINGULAR + "[desc/]");
    }

    @Test
    public void parseFailure_multipleNrics() {
        assertParseFailure(parser, " n/Bob nric/s1234567a nric/s2345678b p/12345 e/23456 m/Type II Diabetes",
                MESSAGE_ENSURE_ONLY_ONE_PREFIX_SINGULAR + "[nric/]");
    }

    @Test
    public void parseFailure_multipleGenders() {
        assertParseFailure(parser, " n/Bob gender/male gender/female p/12345 e/23456 m/Type II Diabetes",
                MESSAGE_ENSURE_ONLY_ONE_PREFIX_SINGULAR + "[gender/]");
    }

    @Test
    public void parseFailure_multipleDobs() {
        assertParseFailure(parser, " n/Bob dob/2019-10-02 dob/2019-10-03 p/12345 e/23456 m/Type II Diabetes",
                MESSAGE_ENSURE_ONLY_ONE_PREFIX_SINGULAR + "[dob/]");
    }

    @Test
    public void parseFailure_multipleAddresses() {
        assertParseFailure(parser, " n/Bob a/Sunny Road a/RainyRoad p/12345 e/23456 m/Type II Diabetes",
                MESSAGE_ENSURE_ONLY_ONE_PREFIX_SINGULAR + "[a/]");
    }

    @Test
    public void parseFailure_multipleOtherBioInfos() {
        assertParseFailure(parser, " n/Bob o/other info o/more other info p/12345 e/23456 m/Type II Diabetes",
                MESSAGE_ENSURE_ONLY_ONE_PREFIX_SINGULAR + "[o/]");
    }

    @Test
    public void parseSuccess_minimal() {
        User user = new User(new Name("Bob"), new DisplayPicPath(""), new ProfileDesc(""), new Nric(""),
                new Gender(""), new DateOfBirth(""), List.of(new Phone("12345")), List.of(new Phone("23456")),
                List.of(new MedicalCondition("Type II Diabetes")), new Address(""), Collections.emptyList(),
                new OtherBioInfo(""));
        assertParseSuccess(parser, " n/Bob p/12345 e/23456 m/Type II Diabetes", new AddBioCommand(user));
    }

    @Test
    public void parseSuccess_allFieldsExceptDpPath_mixedOrder() {
        User user = new User(new Name("Bob"), new DisplayPicPath(""), new ProfileDesc("Example Desc"),
                new Nric("s1234567a"), new Gender("Male"), new DateOfBirth("1920-01-02"), List.of(new Phone("12345")),
                List.of(new Phone("23456")), List.of(new MedicalCondition("Type II Diabetes")),
                new Address("Example Address"), List.of(new Goal("Example Goal")),
                new OtherBioInfo("others"));

        assertParseSuccess(parser, " gender/Male n/Bob desc/Example Desc nric/s1234567a goal/Example Goal "
                        + "e/23456 m/Type II Diabetes p/12345 o/others a/Example Address dob/1920-01-02",
                new AddBioCommand(user));
    }

    @Test
    public void parseSuccess_multipleListableFields() throws ParseException {
        User user = new User(new Name("Bob"), new DisplayPicPath(""), new ProfileDesc(""), new Nric(""), new Gender(""),
                new DateOfBirth(""),
                List.of(new Phone("12345"), new Phone("54321")), List.of(new Phone("23456"), new Phone("65432")),
                List.of(new MedicalCondition(
                        "Type II Diabetes"), new MedicalCondition("High Blood Pressure")), new Address(""),
                List.of(new Goal("firstGoal"), new Goal("secondGoal")), new OtherBioInfo(""));
        assertParseSuccess(parser, " m/Type II Diabetes e/23456 n/Bob e/65432 m/High Blood Pressure p/12345 "
                        + "p/54321 goal/firstGoal goal/secondGoal",
                new AddBioCommand(user));
    }

}
