package seedu.sugarmummy.logic.parser.biography;

import static seedu.sugarmummy.commons.core.Messages.MESSAGE_ENSURE_ONLY_ONE_PREFIX_SINGULAR;
import static seedu.sugarmummy.commons.core.Messages.MESSAGE_INCONSISTENT_SUBARGUMENT_INDEX;
import static seedu.sugarmummy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.sugarmummy.commons.core.Messages.MESSAGE_INVALID_SUBARGUMENT_INDEX;
import static seedu.sugarmummy.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.sugarmummy.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.sugarmummy.testutil.Assert.assertThrows;

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.sugarmummy.commons.core.index.Index;
import seedu.sugarmummy.logic.commands.biography.EditBioCommand;
import seedu.sugarmummy.logic.commands.biography.EditBioCommand.EditUserDescriptor;
import seedu.sugarmummy.logic.commands.exceptions.CommandException;
import seedu.sugarmummy.logic.parser.exceptions.ParseException;
import seedu.sugarmummy.model.biography.Address;
import seedu.sugarmummy.model.biography.DateOfBirth;
import seedu.sugarmummy.model.biography.Gender;
import seedu.sugarmummy.model.biography.Goal;
import seedu.sugarmummy.model.biography.MedicalCondition;
import seedu.sugarmummy.model.biography.Name;
import seedu.sugarmummy.model.biography.Nric;
import seedu.sugarmummy.model.biography.OtherBioInfo;
import seedu.sugarmummy.model.biography.Phone;
import seedu.sugarmummy.model.biography.ProfileDesc;

class EditBioCommandParserTest {

    private EditBioCommandParser parser = new EditBioCommandParser();

    @Test
    public void parseFailure_nullArguments() {
        assertThrows(RuntimeException.class, (new NullPointerException()).getMessage(), () -> parser.parse(null));
    }

    @Test
    public void parseFailure_emptyPreamble() {
        assertParseFailure(parser, "n/Bob n/Amy p/12345 e/23456 m/Type II Diabetes",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditBioCommand.MESSAGE_USAGE));
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
        EditUserDescriptor editUserDescriptor = new EditUserDescriptor();
        editUserDescriptor.setName(new Name("Bob"));
        assertParseSuccess(parser, " n/Bob", new EditBioCommand(editUserDescriptor));
    }

    @Test
    public void parseSuccess_allFieldsExceptDpPath_mixedOrder() {
        EditUserDescriptor editUserDescriptor = new EditUserDescriptor();
        editUserDescriptor.setName(new Name("Bob"));
        editUserDescriptor.setProfileDesc(new ProfileDesc("Example Desc"));
        editUserDescriptor.setNric(new Nric("s1234567a"));
        editUserDescriptor.setGender(new Gender("Male"));
        editUserDescriptor.setDateOfBirth(new DateOfBirth("1920-01-02"));
        editUserDescriptor.setContactNumbers(List.of(new Phone("12345")));
        editUserDescriptor.setEmergencyContacts(List.of(new Phone("23456")));
        editUserDescriptor.setMedicalConditions(List.of(new MedicalCondition("Type II Diabetes")));
        editUserDescriptor.setAddress(new Address("Example Address"));
        editUserDescriptor.setGoals(List.of(new Goal("Example Goal")));
        editUserDescriptor.setOtherBioInfo(new OtherBioInfo("others"));

        assertParseSuccess(parser, " gender/Male n/Bob desc/Example Desc nric/s1234567a goal/Example Goal "
                        + "e/23456 m/Type II Diabetes p/12345 o/others a/Example Address dob/1920-01-02",
                new EditBioCommand(editUserDescriptor));
    }

    @Test
    public void parseSuccess_multipleListableFields() throws ParseException, CommandException {

        EditUserDescriptor editUserDescriptor = new EditUserDescriptor();
        editUserDescriptor.setContactNumbers(List.of(new Phone("12345"), new Phone("54321")));

        HashMap<Index, Phone> indexEmergencyContactMap = new HashMap<>();
        indexEmergencyContactMap.put(Index.fromOneBased(1), new Phone("23456"));
        indexEmergencyContactMap.put(Index.fromOneBased(2), new Phone("65432"));
        editUserDescriptor.addToIndividualEmergencyContactsEdit(indexEmergencyContactMap);

        HashMap<Index, MedicalCondition> indexMedicalConditionMap = new HashMap<>();
        indexMedicalConditionMap.put(Index.fromOneBased(3), new MedicalCondition("High Blood Pressure"));
        indexMedicalConditionMap.put(Index.fromOneBased(2), new MedicalCondition("Type II Diabetes"));
        editUserDescriptor.addToIndividualMedicalConditionsEdit(indexMedicalConditionMap);

        editUserDescriptor.setGoals(List.of(new Goal("firstGoal"), new Goal("secondGoal")));

        assertParseSuccess(parser, " m/2/Type II Diabetes e/1/23456 e/2/65432 m/3/High Blood Pressure p/12345 "
                        + "p/54321 goal/firstGoal goal/secondGoal",
                new EditBioCommand(editUserDescriptor));
    }


    @Test
    public void parseFailure_inconsistentIndexing() throws ParseException {
        assertParseFailure(parser, " e/1/23456 e/65432", MESSAGE_INCONSISTENT_SUBARGUMENT_INDEX);
    }

    @Test
    public void parseFailure_invalidSubArgument_strings() throws ParseException {
        assertParseFailure(parser, " e/hello/23456", MESSAGE_INVALID_SUBARGUMENT_INDEX);
    }

    @Test
    public void parseFailure_invalidSubArgument_negativeNumbers() throws ParseException {
        assertParseFailure(parser, " e/-1/23456", MESSAGE_INVALID_SUBARGUMENT_INDEX);
    }

}
