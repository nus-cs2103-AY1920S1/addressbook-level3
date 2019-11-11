package seedu.sugarmummy.model.biography;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

class UserTest {

    public static final User VALID_USER = new User(new Name("Bob"), new DisplayPicPath("/Users/dp.png"),
            new ProfileDesc("Example Desc"), new Nric("s1234567a"), new Gender("Male"), new DateOfBirth("1920-01-02"),
            List.of(new Phone("12345")), List.of(new Phone("23456")), List.of(new MedicalCondition("Type II Diabetes")),
            new Address("Example Address"), List.of(new Goal("Example Goal")),
            new OtherBioInfo("others"));

    public static final User VALID_USER_COPY = new User(new Name("Bob"), new DisplayPicPath("/Users/dp.png"),
            new ProfileDesc("Example Desc"), new Nric("s1234567a"), new Gender("Male"), new DateOfBirth("1920-01-02"),
            List.of(new Phone("12345")), List.of(new Phone("23456")), List.of(new MedicalCondition("Type II Diabetes")),
            new Address("Example Address"), List.of(new Goal("Example Goal")),
            new OtherBioInfo("others"));

    public static final User VALID_USER_WITH_DIFFERENCES = new User(new Name("Amy"), new DisplayPicPath("/Users/dp"
            + ".png"),
            new ProfileDesc("Example Desc"), new Nric("s1234567a"), new Gender("Female"), new DateOfBirth("1920-01-02"),
            List.of(new Phone("12345")), List.of(new Phone("23456")), List.of(new MedicalCondition("Type II Diabetes")),
            new Address("Example Address"), List.of(new Goal("Example Goal")),
            new OtherBioInfo("others"));

    @Test
    public void getName() {
        assertEquals(new Name("Bob"), VALID_USER.getName());
    }

    @Test
    public void getDpPath() {
        assertEquals(new DisplayPicPath("/Users/dp.png"), VALID_USER.getDpPath());
    }

    @Test
    public void getProfileDesc() {
        assertEquals(new ProfileDesc("Example Desc"), VALID_USER.getProfileDesc());
    }

    @Test
    public void getNric() {
        assertEquals(new Nric("s1234567a"), VALID_USER.getNric());
    }

    @Test
    public void getGender() {
        assertEquals(new Gender("Male"), VALID_USER.getGender());
    }

    @Test
    public void getDateOfBirth() {
        assertEquals(new DateOfBirth("1920-01-02"), VALID_USER.getDateOfBirth());
    }

    @Test
    public void getContactNumbers() {
        assertEquals(List.of(new Phone("12345")), VALID_USER.getContactNumbers());
    }

    @Test
    public void getEmergencyContacts() {
        assertEquals(List.of(new Phone("23456")), VALID_USER.getEmergencyContacts());
    }

    @Test
    public void getMedicalConditions() {
        assertEquals(List.of(new MedicalCondition("Type II Diabetes")), VALID_USER.getMedicalConditions());
    }

    @Test
    public void getAddress() {
        assertEquals(new Address("Example Address"), VALID_USER.getAddress());
    }

    @Test
    public void getGoals() {
        assertEquals(List.of(new Goal("Example Goal")), VALID_USER.getGoals());
    }

    @Test
    public void getOtherBioInfo() {
        assertEquals(new OtherBioInfo("others"), VALID_USER.getOtherBioInfo());
    }

    @Test
    public void isSameUser() {
        assertTrue(VALID_USER.isSameUser(VALID_USER_COPY));
    }

    @Test
    public void getDifferencesTo_noDifference() {
        LinkedHashMap<String, List<String>> differencesMap = new LinkedHashMap<>();
        differencesMap.put("Name", List.of("Bob", "Amy"));
        differencesMap.put("Gender", List.of("Male", "Female"));
        assertEquals(differencesMap, VALID_USER.getDifferencesTo(VALID_USER_WITH_DIFFERENCES));
    }

    @Test
    public void getFieldMap() {
        LinkedHashMap<String, String> expectedFieldMap = new LinkedHashMap<>();
        expectedFieldMap = new LinkedHashMap<>();
        expectedFieldMap.put(BioFieldName.LABEL_NAME, "Bob");
        expectedFieldMap.put(BioFieldName.LABEL_DP_PATH, "/Users/dp.png");
        expectedFieldMap.put(BioFieldName.LABEL_PROFILE_DESCRIPTION, "Example Desc");
        expectedFieldMap.put(BioFieldName.LABEL_NRIC, "s1234567a");
        expectedFieldMap.put(BioFieldName.LABEL_GENDER, "Male");
        expectedFieldMap.put(BioFieldName.LABEL_DATE_OF_BIRTH, "1920-01-02");
        expectedFieldMap.put(BioFieldName.LABEL_CONTACT_NUMBER, "[12345]");
        expectedFieldMap.put(BioFieldName.LABEL_EMERGENCY_CONTACT, "[23456]");
        expectedFieldMap.put(BioFieldName.LABEL_MEDICAL_CONDITION, "[Type II Diabetes]");
        expectedFieldMap.put(BioFieldName.LABEL_ADDRESS, "Example Address");
        expectedFieldMap.put(BioFieldName.LABEL_GOALS, "[Example Goal]");
        expectedFieldMap.put(BioFieldName.LABEL_OTHER_BIO_INFO, "others");
        assertEquals(expectedFieldMap, VALID_USER.getFieldMap());
    }

    @Test
    public void testEquals() {
        assertEquals(VALID_USER, VALID_USER_COPY);
    }

    @Test
    public void testToString() {

        String expectedString = "User with attributes:"
                + "\nName: Bob"
                + "\nDP Path: /Users/dp.png"
                + "\nProfile Desc: Example Desc"
                + "\nNRIC: s1234567a"
                + "\nGender: Male"
                + "\nDate Of Birth: 1920-01-02"
                + "\nContact Numbers: 12345"
                + "\nEmergency Contacts: 23456"
                + "\nMedical Conditions: Type II Diabetes"
                + "\nAddress: Example Address"
                + "\nGoals: Example Goal"
                + "\nOther Info: others";
        assertEquals(expectedString, VALID_USER.toString());
    }
}
