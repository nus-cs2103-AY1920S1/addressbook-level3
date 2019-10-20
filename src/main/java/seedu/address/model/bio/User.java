package seedu.address.model.bio;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.bio.BioFieldName.LABEL_ADDRESS;
import static seedu.address.model.bio.BioFieldName.LABEL_CONTACT_NUMBER;
import static seedu.address.model.bio.BioFieldName.LABEL_DATE_OF_BIRTH;
import static seedu.address.model.bio.BioFieldName.LABEL_DP_PATH;
import static seedu.address.model.bio.BioFieldName.LABEL_EMERGENCY_CONTACT;
import static seedu.address.model.bio.BioFieldName.LABEL_GENDER;
import static seedu.address.model.bio.BioFieldName.LABEL_GOALS;
import static seedu.address.model.bio.BioFieldName.LABEL_MEDICAL_CONDITION;
import static seedu.address.model.bio.BioFieldName.LABEL_NAME;
import static seedu.address.model.bio.BioFieldName.LABEL_NRIC;
import static seedu.address.model.bio.BioFieldName.LABEL_OTHER_BIO_INFO;
import static seedu.address.model.bio.BioFieldName.LABEL_PROFILE_DESCRIPTION;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

/**
 * Represents the patient using this program.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class User {

    // Identity fields
    private final Name name;
    private final DisplayPicPath dpPath;
    private final ProfileDesc profileDesc;
    private final Nric nric;
    private final Gender gender;
    private final DateOfBirth dateOfBirth;
    private final Address address;
    private final OtherBioInfo otherBioInfo;

    // Data fields
    private final List<Phone> contactNumbers = new ArrayList<>();
    private final List<Phone> emergencyContacts = new ArrayList<>();
    private final List<MedicalCondition> medicalConditions = new ArrayList<>();
    private final List<Goal> goals = new ArrayList<>();

    // Contains a data structure that maps  bio fields of the User.
    private LinkedHashMap<String, String> fieldMap;

    /**
     * Every field must be present and not null.
     */
    public User(Name name, DisplayPicPath dpPath, ProfileDesc profileDesc, Nric nric, Gender gender, DateOfBirth dateOfBirth,
                List<Phone> contactNumbers, List<Phone> emergencyContacts, List<MedicalCondition> medicalConditions,
                Address address, List<Goal> goals,
                OtherBioInfo otherBioInfo) {
        requireAllNonNull(name, dpPath, profileDesc, nric, gender, dateOfBirth, contactNumbers, emergencyContacts,
                medicalConditions, goals, otherBioInfo);
        this.name = name;
        this.dpPath = dpPath;
        this.profileDesc = profileDesc;
        this.nric = nric;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.contactNumbers.addAll(contactNumbers);
        this.emergencyContacts.addAll(emergencyContacts);
        this.medicalConditions.addAll(medicalConditions);
        this.address = address;
        this.goals.addAll(goals);
        this.otherBioInfo = otherBioInfo;
    }

    public Name getName() {
        return name;
    }

    public DisplayPicPath getDpPath() {
        return dpPath;
    }

    public ProfileDesc getProfileDesc() {
        return profileDesc;
    }

    public Nric getNric() {
        return nric;
    }

    public Gender getGender() {
        return gender;
    }

    public DateOfBirth getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Returns an immutable contact number set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public List<Phone> getContactNumbers() {
        return Collections.unmodifiableList(contactNumbers);
    }

    /**
     * Returns an immutable emergency contact set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public List<Phone> getEmergencyContacts() {
        return Collections.unmodifiableList(emergencyContacts);
    }

    /**
     * Returns an immutable medical condition set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public List<MedicalCondition> getMedicalConditions() {
        return Collections.unmodifiableList(medicalConditions);
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable goal set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public List<Goal> getGoals() {
        return Collections.unmodifiableList(goals);
    }

    public OtherBioInfo getOtherBioInfo() {
        return otherBioInfo;
    }

    /**
     * Returns true if both users of the same name have the same NRIC.
     * This defines a weaker notion of equality between two users.
     */
    public boolean isSameUser(User otherUser) {
        if (otherUser == this) {
            return true;
        }

        return otherUser != null
                && otherUser.getName().equals(getName())
                && (otherUser.getNric().equals(getNric()));
    }

    /**
     * Returns a {@code LinkedHashMap} containing differences of this user's biography compared to another.
     * @param other Other user with biography to be compared to this user's.
     * @return A {@code LinkedHashMap} containing differences of this user's biography compared to another.
     */
    public LinkedHashMap<String, List<String>> getDifferencesTo(User other) {
        LinkedHashMap<String, String> thisFieldList = getFieldMap();
        LinkedHashMap<String, String> otherFieldList = other.getFieldMap();
        LinkedHashMap<String, List<String>> differencesMap = new LinkedHashMap<>();

        thisFieldList.forEach((key, value) -> {
            String otherValue = otherFieldList.get(key);
            if (!value.equals(otherValue)) {
                differencesMap.put(key, new ArrayList<>(List.of(value, otherValue)));
            }
        });
        return differencesMap;
    }

    /**
     * Returns a {@code HashMap} representing this user's biography information.
     * @return {@code HashMap} representing this user's biography information.
     */
    public LinkedHashMap<String, String> getFieldMap() {
        if (fieldMap == null) {
            fieldMap = new LinkedHashMap<>();
            fieldMap.put(LABEL_NAME, name.toString());
            fieldMap.put(LABEL_DP_PATH, dpPath.toString());
            fieldMap.put(LABEL_PROFILE_DESCRIPTION, profileDesc.toString());
            fieldMap.put(LABEL_NRIC, nric.toString());
            fieldMap.put(LABEL_GENDER, gender.toString());
            fieldMap.put(LABEL_DATE_OF_BIRTH, dateOfBirth.toString());
            fieldMap.put(LABEL_CONTACT_NUMBER, contactNumbers.toString());
            fieldMap.put(LABEL_EMERGENCY_CONTACT, emergencyContacts.toString());
            fieldMap.put(LABEL_MEDICAL_CONDITION, medicalConditions.toString());
            fieldMap.put(LABEL_ADDRESS, address.toString());
            fieldMap.put(LABEL_GOALS, goals.toString());
            fieldMap.put(LABEL_OTHER_BIO_INFO, otherBioInfo.toString());
        }
        return fieldMap;
    }

    /**
     * Returns true if both users have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof User)) {
            return false;
        }

        User otherUser = (User) other;
        return otherUser.getName().equals(getName())
                && otherUser.getDpPath().equals(getDpPath())
                && otherUser.getProfileDesc().equals(getProfileDesc())
                && otherUser.getNric().equals(getNric())
                && otherUser.getGender().equals(getGender())
                && otherUser.getDateOfBirth().equals(getDateOfBirth())
                && otherUser.getContactNumbers().equals(getContactNumbers())
                && otherUser.getEmergencyContacts().equals(getEmergencyContacts())
                && otherUser.getMedicalConditions().equals(getMedicalConditions())
                && otherUser.getAddress().equals(getAddress())
                && otherUser.getGoals().equals(getGoals())
                && otherUser.getOtherBioInfo().equals(getOtherBioInfo());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, dpPath, profileDesc, nric, gender, dateOfBirth, contactNumbers, emergencyContacts,
                medicalConditions, address, goals, otherBioInfo);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" DP Path: ")
                .append(getDpPath())
                .append(" Profile Desc: ")
                .append(getProfileDesc())
                .append(" NRIC: ")
                .append(getNric())
                .append(" Gender: ")
                .append(getGender())
                .append(" Date Of Birth: ")
                .append(getDateOfBirth())
                .append(" Contact Numbers: ");
        getContactNumbers().forEach(contactNumber -> builder.append(contactNumber).append("|"));
        builder.deleteCharAt(builder.length() - 1);
        builder.append(" Emergency Contacts: ");
        getEmergencyContacts().forEach(emergencyContact -> builder.append(emergencyContact).append("|"));
        builder.deleteCharAt(builder.length() - 1);
        builder.append(" Medical Conditions: ");
        getMedicalConditions().forEach(condition -> builder.append(condition).append("|"));
        builder.deleteCharAt(builder.length() - 1);
        builder.append(" Address: ");
        builder.append(" Goals: ");
        getGoals().forEach(condition -> builder.append(condition).append("|"));
        builder.deleteCharAt(builder.length() - 1);
        builder.append(" Other Info: ").append(getOtherBioInfo());
        return builder.toString();
    }

}
