package seedu.sugarmummy.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sugarmummy.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.sugarmummy.testutil.Assert.assertThrows;
import static seedu.sugarmummy.testutil.TypicalIndexes.INDEX_FIRST_RECORD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

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
import seedu.sugarmummy.model.statistics.AverageType;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_NRIC = "S23\'5+";
    private static final String INVALID_GENDER = "#male";
    private static final String INVALID_DATE_OF_BIRTH = "2019-02-31";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_MEDICAL_CONDITION = " ";

    private static final String INVALID_POSITIVEFLOAT_1 = "-1";
    private static final String INVALID_POSITIVEFLOAT_2 = "123asb";
    private static final String INVALID_POSITIVEFLOAT_3 = "-1.asd";
    private static final String INVALID_POSITIVEFLOAT_4 = "asbd123";
    private static final String VALID_POSITIVEFLOAT = "12.34";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PROFILE_DESC = "Sometimes I pretend I'm a carrot.";
    private static final String VALID_NRIC = "S1234567A";
    private static final String VALID_GENDER = "Female";
    private static final String VALID_DATE_OF_BIRTH = "2019-01-29";
    private static final String VALID_PHONE_1 = "123456";
    private static final String VALID_PHONE_2 = "987654";
    private static final String VALID_MEDICAL_CONDITION_1 = "Type II Diabetes";
    private static final String VALID_MEDICAL_CONDITION_2 = "High Cholesterol.";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_GOAL_1 = "lose 5kg by 20/10/2019.";
    private static final String VALID_GOAL_2 = "run 10km this week.";
    private static final String VALID_OTHER_BIO_INFO = "dislikes potatoes.";

    private static final String INVALID_AVERAGE_TYPE_1 = "yearly";
    private static final String INVALID_AVERAGE_TYPE_2 = "23";

    private static final String VALID_AVERAGE_TYPE_1 = "daily";
    private static final String VALID_AVERAGE_TYPE_2 = "WEEKLY";
    private static final String VALID_AVERAGE_TYPE_3 = "MoNtHlY";


    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_RECORD, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_RECORD, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseHeight_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseHeight(null));
    }

    @Test
    public void parseWeight_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseWeight(null));
    }

    @Test
    public void parseConcentration_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseConcentration(null));
    }

    @Test
    public void parseHeight_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseHeight(INVALID_POSITIVEFLOAT_1));
        assertThrows(ParseException.class, () -> ParserUtil.parseHeight(INVALID_POSITIVEFLOAT_2));
        assertThrows(ParseException.class, () -> ParserUtil.parseHeight(INVALID_POSITIVEFLOAT_3));
        assertThrows(ParseException.class, () -> ParserUtil.parseHeight(INVALID_POSITIVEFLOAT_4));
    }

    @Test
    public void parseWeight_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseWeight(INVALID_POSITIVEFLOAT_1));
        assertThrows(ParseException.class, () -> ParserUtil.parseWeight(INVALID_POSITIVEFLOAT_2));
        assertThrows(ParseException.class, () -> ParserUtil.parseWeight(INVALID_POSITIVEFLOAT_3));
        assertThrows(ParseException.class, () -> ParserUtil.parseWeight(INVALID_POSITIVEFLOAT_4));
    }

    @Test
    public void parseConcentration_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseConcentration(INVALID_POSITIVEFLOAT_1));
        assertThrows(ParseException.class, () -> ParserUtil.parseConcentration(INVALID_POSITIVEFLOAT_2));
        assertThrows(ParseException.class, () -> ParserUtil.parseConcentration(INVALID_POSITIVEFLOAT_3));
        assertThrows(ParseException.class, () -> ParserUtil.parseConcentration(INVALID_POSITIVEFLOAT_4));
    }

    @Test
    public void parseAverageType_invalid_throwsParseException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAverageType(null));
    }

    @Test
    public void parseAverageType_invalidValue_throwsParseException() {
        // String
        assertThrows(ParseException.class, () -> ParserUtil.parseAverageType(INVALID_AVERAGE_TYPE_1));
        // int
        assertThrows(ParseException.class, () -> ParserUtil.parseAverageType(INVALID_AVERAGE_TYPE_2));
    }

    @Test
    public void parseAverageType_validValueInLowerCase_returnsAverageType() throws Exception {
        assertEquals(AverageType.valueOf("DAILY"), ParserUtil.parseAverageType(VALID_AVERAGE_TYPE_1));
    }

    @Test
    public void parseAverageType_validValueUpperCase_returnsAverageType() throws Exception {
        assertEquals(AverageType.valueOf("WEEKLY"), ParserUtil.parseAverageType(VALID_AVERAGE_TYPE_2));
    }

    @Test
    public void parseAverageType_validValueMixCase_returnsAverageType() throws Exception {
        assertEquals(AverageType.valueOf("MONTHLY"), ParserUtil.parseAverageType(VALID_AVERAGE_TYPE_3));
    }

    @Test
    public void parseAverageType_validValueWithWhiteSpace_returnsAverageType() throws Exception {
        String actualInput = VALID_AVERAGE_TYPE_1 + WHITESPACE;
        assertEquals(AverageType.valueOf("DAILY"), ParserUtil.parseAverageType(VALID_AVERAGE_TYPE_1));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName(null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parseProfileDesc_validValueWithoutWhitespace_returnsProfileDesc() throws Exception {
        ProfileDesc expectedProfileDesc = new ProfileDesc(VALID_PROFILE_DESC);
        assertEquals(expectedProfileDesc, ParserUtil.parseProfileDesc(Optional.of(VALID_PROFILE_DESC)));
    }

    @Test
    public void parseProfileDesc_validValueWithWhitespace_returnsTrimmedProfileDesc() throws Exception {
        String profileDescWithWhitespace = WHITESPACE + VALID_PROFILE_DESC + WHITESPACE;
        ProfileDesc expectedProfileDesc = new ProfileDesc(VALID_PROFILE_DESC);
        assertEquals(expectedProfileDesc, ParserUtil.parseProfileDesc(Optional.of(profileDescWithWhitespace)));
    }

    @Test
    public void parseNric_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseNric(Optional.of(INVALID_NRIC)));
    }

    @Test
    public void parseNric_validValueWithoutWhitespace_returnsNric() throws Exception {
        Nric expectedNric = new Nric(VALID_NRIC);
        assertEquals(expectedNric, ParserUtil.parseNric(Optional.of(VALID_NRIC)));
    }

    @Test
    public void parseNric_validValueWithWhitespace_returnsTrimmedNric() throws Exception {
        String nricWithWhitespace = WHITESPACE + VALID_NRIC + WHITESPACE;
        Nric expectedNric = new Nric(VALID_NRIC);
        assertEquals(expectedNric, ParserUtil.parseNric(Optional.of(nricWithWhitespace)));
    }

    @Test
    public void parseGender_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseGender(Optional.of(INVALID_GENDER)));
    }

    @Test
    public void parseGender_validValueWithoutWhitespace_returnsGender() throws Exception {
        Gender expectedGender = new Gender(VALID_GENDER);
        assertEquals(expectedGender, ParserUtil.parseGender(Optional.of(VALID_GENDER)));
    }

    @Test
    public void parseGender_validValueWithWhitespace_returnsTrimmedGender() throws Exception {
        String genderWithWhitespace = WHITESPACE + VALID_GENDER + WHITESPACE;
        Gender expectedGender = new Gender(VALID_GENDER);
        assertEquals(expectedGender, ParserUtil.parseGender(Optional.of(genderWithWhitespace)));
    }

    @Test
    public void parseDateOfBirth_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDateOfBirth(null));
    }

    @Test
    public void parseDateOfBirth_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDateOfBirth(Optional.of(INVALID_DATE_OF_BIRTH)));
    }

    @Test
    public void parseDateOfBirth_validValueWithoutWhitespace_returnsDateOfBirth() throws Exception {
        DateOfBirth expectedDateOfBirth = new DateOfBirth(VALID_DATE_OF_BIRTH);
        assertEquals(expectedDateOfBirth, ParserUtil.parseDateOfBirth(Optional.of(VALID_DATE_OF_BIRTH)));
    }

    @Test
    public void parseDateOfBirth_validValueWithWhitespace_returnsTrimmedDateOfBirth() throws Exception {
        String dateOfBirthWithWhitespace = WHITESPACE + VALID_DATE_OF_BIRTH + WHITESPACE;
        DateOfBirth expectedDateOfBirth = new DateOfBirth(VALID_DATE_OF_BIRTH);
        assertEquals(expectedDateOfBirth, ParserUtil.parseDateOfBirth(Optional.of(dateOfBirthWithWhitespace)));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone(null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE_1);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE_1));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE_1 + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE_1);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parsePhones_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhones(null));
    }

    @Test
    public void parsePhones_collectionWithInvalidPhones_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhones(Arrays.asList(VALID_PHONE_1, INVALID_PHONE)));
    }

    @Test
    public void parsePhones_emptyCollection_returnsEmptySet() throws Exception {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhones(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parsePhones_collectionWithValidPhones_returnsPhoneSet() throws Exception {
        List<Phone> actualPhoneList = ParserUtil.parsePhones(Arrays.asList(VALID_PHONE_1, VALID_PHONE_2));
        List<Phone> expectedPhoneList = new ArrayList<Phone>(Arrays.asList(new Phone(VALID_PHONE_1),
                new Phone(VALID_PHONE_2)));
        assertEquals(expectedPhoneList, actualPhoneList);
    }

    @Test
    public void parseGoal_validValueWithoutWhitespace_returnsGoal() throws Exception {
        Goal expectedGoal = new Goal(VALID_GOAL_1);
        assertEquals(expectedGoal, ParserUtil.parseGoal(VALID_GOAL_1));
    }

    @Test
    public void parseGoal_validValueWithWhitespace_returnsTrimmedGoal() throws Exception {
        String goalWithWhitespace = WHITESPACE + VALID_GOAL_1 + WHITESPACE;
        Goal expectedGoal = new Goal(VALID_GOAL_1);
        assertEquals(expectedGoal, ParserUtil.parseGoal(goalWithWhitespace));
    }

    @Test
    public void parseGoals_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseGoals(null));
    }

    @Test
    public void parseGoals_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseGoals(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseGoals_collectionWithValidGoals_returnsGoalSet()
            throws Exception {
        List<Goal> actualGoalList = ParserUtil.parseGoals(
                Arrays.asList(VALID_GOAL_1, VALID_GOAL_2));
        List<Goal> expectedGoalList = new ArrayList<Goal>(
                Arrays.asList(new Goal(VALID_GOAL_1),
                        new Goal(VALID_GOAL_2)));
        assertEquals(expectedGoalList, actualGoalList);
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress(null));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(Optional.of(VALID_ADDRESS)));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(Optional.of(addressWithWhitespace)));
    }


    @Test
    public void parseMedicalCondition_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseMedicalCondition(INVALID_MEDICAL_CONDITION));
    }

    @Test
    public void parseMedicalCondition_validValueWithoutWhitespace_returnsMedicalCondition() throws Exception {
        MedicalCondition expectedMedicalCondition = new MedicalCondition(VALID_MEDICAL_CONDITION_1);
        assertEquals(expectedMedicalCondition, ParserUtil.parseMedicalCondition(VALID_MEDICAL_CONDITION_1));
    }

    @Test
    public void parseMedicalCondition_validValueWithWhitespace_returnsTrimmedMedicalCondition() throws Exception {
        String medicalConditionWithWhitespace = WHITESPACE + VALID_MEDICAL_CONDITION_1 + WHITESPACE;
        MedicalCondition expectedMedicalCondition = new MedicalCondition(VALID_MEDICAL_CONDITION_1);
        assertEquals(expectedMedicalCondition, ParserUtil.parseMedicalCondition(medicalConditionWithWhitespace));
    }

    @Test
    public void parseMedicalConditions_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseMedicalConditions(null));
    }

    @Test
    public void parseMedicalConditions_collectionWithInvalidMedicalConditions_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseMedicalConditions(Arrays.asList(
                VALID_MEDICAL_CONDITION_1, INVALID_MEDICAL_CONDITION)));
    }

    @Test
    public void parseMedicalConditions_emptyCollection_returnsEmptySet() throws Exception {
        assertThrows(ParseException.class, () -> ParserUtil.parseMedicalConditions(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseMedicalConditions_collectionWithValidMedicalConditions_returnsMedicalConditionSet()
            throws Exception {
        List<MedicalCondition> actualMedicalConditionList = ParserUtil.parseMedicalConditions(
                Arrays.asList(VALID_MEDICAL_CONDITION_1, VALID_MEDICAL_CONDITION_2));
        List<MedicalCondition> expectedMedicalConditionList = new ArrayList<MedicalCondition>(
                Arrays.asList(new MedicalCondition(VALID_MEDICAL_CONDITION_1),
                        new MedicalCondition(VALID_MEDICAL_CONDITION_2)));
        assertEquals(expectedMedicalConditionList, actualMedicalConditionList);
    }

    @Test
    public void parseOtherBioInfo_validValueWithoutWhitespace_returnsOtherBioInfo() throws Exception {
        OtherBioInfo expectedOtherBioInfo = new OtherBioInfo(VALID_OTHER_BIO_INFO);
        assertEquals(expectedOtherBioInfo, ParserUtil.parseOtherBioInfo(Optional.of(VALID_OTHER_BIO_INFO)));
    }

    @Test
    public void parseOtherBioInfo_validValueWithWhitespace_returnsTrimmedOtherBioInfo() throws Exception {
        String otherBioInfoWithWhitespace = WHITESPACE + VALID_OTHER_BIO_INFO + WHITESPACE;
        OtherBioInfo expectedOtherBioInfo = new OtherBioInfo(VALID_OTHER_BIO_INFO);
        assertEquals(expectedOtherBioInfo, ParserUtil.parseOtherBioInfo(Optional.of(otherBioInfoWithWhitespace)));
    }
}
