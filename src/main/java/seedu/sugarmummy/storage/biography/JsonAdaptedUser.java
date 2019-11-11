package seedu.sugarmummy.storage.biography;

import static seedu.sugarmummy.model.biography.BioFieldName.LABEL_DP_PATH;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.sugarmummy.commons.exceptions.IllegalValueException;
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

/**
 * Jackson-friendly version of {@link User}.
 */
class JsonAdaptedUser {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "User's %s field is missing!";
    private static Map<String, String> fieldsContainingInvalidReferences = new LinkedHashMap<>();

    private final String name;
    private final String dpPath;
    private final String profileDesc;
    private final String nric;
    private final String gender;
    private final String dateOfBirth;
    private final List<JsonAdaptedContactNumbers> contactNumbers = new ArrayList<>();
    private final List<JsonAdaptedEmergencyContacts> emergencyContacts = new ArrayList<>();
    private final List<JsonAdaptedMedicalConditions> medicalConditions = new ArrayList<>();
    private final String address;
    private final List<JsonAdaptedGoals> goals = new ArrayList<>();
    private final String otherInfo;

    /**
     * Constructs a {@code JsonAdaptedUser} with the given user details.
     */
    @JsonCreator
    public JsonAdaptedUser(@JsonProperty("name") String name, @JsonProperty("dpPath") String dpPath,
                           @JsonProperty("profileDesc") String profileDesc,
                           @JsonProperty("nric") String nric, @JsonProperty("gender") String gender,
                           @JsonProperty("dateOfBirth") String dateOfBirth,
                           @JsonProperty("contactNumbers") List<JsonAdaptedContactNumbers> contactNumbers,
                           @JsonProperty("emergencyContacts") List<JsonAdaptedEmergencyContacts> emergencyContacts,
                           @JsonProperty("medicalConditions") List<JsonAdaptedMedicalConditions> medicalConditions,
                           @JsonProperty("address") String address,
                           @JsonProperty("goals") List<JsonAdaptedGoals> goals,
                           @JsonProperty("otherInfo") String otherInfo) {
        this.name = name;
        this.dpPath = dpPath;
        this.profileDesc = profileDesc;
        this.nric = nric;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;

        if (contactNumbers != null) {
            this.contactNumbers.addAll(contactNumbers);
        }
        if (emergencyContacts != null) {
            this.emergencyContacts.addAll(emergencyContacts);
        }
        if (medicalConditions != null) {
            this.medicalConditions.addAll(medicalConditions);
        }

        this.address = address;

        if (goals != null) {
            this.goals.addAll(goals);
        }
        this.otherInfo = otherInfo;
    }

    /**
     * Converts a given {@code User} into this class for Jackson use.
     */
    public JsonAdaptedUser(User source) {
        name = source.getName().fullName;
        dpPath = source.getDpPath().displayPicPath;
        profileDesc = source.getProfileDesc().profileDesc;
        nric = source.getNric().nric;
        gender = source.getGender().gender;
        dateOfBirth = source.getDateOfBirth().dateOfBirth;
        contactNumbers.addAll(source.getContactNumbers().stream()
                .map(JsonAdaptedContactNumbers::new)
                .collect(Collectors.toList()));
        emergencyContacts.addAll(source.getEmergencyContacts().stream()
                .map(JsonAdaptedEmergencyContacts::new)
                .collect(Collectors.toList()));
        medicalConditions.addAll(source.getMedicalConditions().stream()
                .map(JsonAdaptedMedicalConditions::new)
                .collect(Collectors.toList()));
        address = source.getAddress().address;
        goals.addAll(source.getGoals().stream()
                .map(JsonAdaptedGoals::new)
                .collect(Collectors.toList()));
        otherInfo = source.getOtherBioInfo().otherInfo;
    }

    /**
     * Returns a map of fields in the json file that contain invalid references. Map returned maps a field to the
     * corresponding String representation of the invalid path.
     *
     * @return Map of fields in the json file that contain invalid references.
     */
    public static Map<String, String> getFieldsContainingInvalidReferences() {
        return fieldsContainingInvalidReferences;
    }

    /**
     * Converts this Jackson-friendly adapted user object into the sugarmummy.recmfood.model's {@code User} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted user.
     */
    public User toModelType() throws IllegalValueException {

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (dpPath == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DisplayPicPath.class.getSimpleName()));
        }
        if (!DisplayPicPath.isValidDisplayPicPath(dpPath)) {
            throw new IllegalValueException(DisplayPicPath.MESSAGE_CONSTRAINTS);
        } else if (!dpPath.isEmpty()) {
            try {
                Image image = ImageIO.read(new File(dpPath));
                if (image == null) {
                    throw new IOException();
                }
            } catch (IOException e) {
                fieldsContainingInvalidReferences.put(LABEL_DP_PATH, dpPath);
            }
        }
        final DisplayPicPath modelDpPath = fieldsContainingInvalidReferences.containsKey(LABEL_DP_PATH)
                ? new DisplayPicPath("")
                : new DisplayPicPath(dpPath);

        if (profileDesc == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, ProfileDesc.class.getName()));
        }
        if (!ProfileDesc.isValidProfileDesc(profileDesc)) {
            throw new IllegalValueException(ProfileDesc.MESSAGE_CONSTRAINTS);
        }
        final ProfileDesc modelProfileDesc = new ProfileDesc(profileDesc);

        if (nric == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Nric.class.getSimpleName()));
        }
        if (!Nric.isValidNric(nric)) {
            throw new IllegalValueException(Nric.MESSAGE_CONSTRAINTS);
        }
        final Nric modelNric = new Nric(nric);

        if (gender == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Gender.class.getSimpleName()));
        }
        if (!Gender.isValidGender(gender)) {
            throw new IllegalValueException(Gender.MESSAGE_CONSTRAINTS);
        }
        final Gender modelGender = new Gender(gender);

        if (dateOfBirth == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DateOfBirth.class.getSimpleName()));
        }
        if (!DateOfBirth.isValidDateOfBirth(dateOfBirth)) {
            throw new IllegalValueException(DateOfBirth.MESSAGE_CONSTRAINTS);
        }
        final DateOfBirth modelDateOfBirth = new DateOfBirth(dateOfBirth);

        final List<Phone> userContactNumbers = new ArrayList<>();
        for (JsonAdaptedContactNumbers contactNumber : contactNumbers) {
            userContactNumbers.add(contactNumber.toModelType());
        }
        if (userContactNumbers.isEmpty()) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final List<Phone> modelContactNumbers = new ArrayList<>(userContactNumbers);

        final List<Phone> userEmergencyContacts = new ArrayList<>();
        for (JsonAdaptedEmergencyContacts emergencyContact : emergencyContacts) {
            userEmergencyContacts.add(emergencyContact.toModelType());
        }
        if (userEmergencyContacts.isEmpty()) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final List<Phone> modelEmergencyContacts = new ArrayList<>(userEmergencyContacts);

        final List<MedicalCondition> userMedicalCondition = new ArrayList<>();
        for (JsonAdaptedMedicalConditions medicalCondition : medicalConditions) {
            userMedicalCondition.add(medicalCondition.toModelType());
        }
        if (userMedicalCondition.isEmpty()) {
            throw new IllegalValueException(MedicalCondition.MESSAGE_CONSTRAINTS);
        }
        final List<MedicalCondition> modelMedicalConditions = new ArrayList<>(userMedicalCondition);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address
                    .class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        final List<Goal> userGoals = new ArrayList<>();
        for (JsonAdaptedGoals goal : goals) {
            userGoals.add(goal.toModelType());
        }
        final List<Goal> modelGoals = new ArrayList<>(userGoals);

        if (!OtherBioInfo.isValidOtherBioInfo(otherInfo)) {
            throw new IllegalValueException(OtherBioInfo.MESSAGE_CONSTRAINTS);
        }
        final OtherBioInfo modelOtherBioInfo = new OtherBioInfo(otherInfo);

        return new User(modelName, modelDpPath, modelProfileDesc, modelNric, modelGender, modelDateOfBirth,
                modelContactNumbers, modelEmergencyContacts, modelMedicalConditions, modelAddress, modelGoals,
                modelOtherBioInfo);
    }

}
