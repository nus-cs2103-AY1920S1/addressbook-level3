package dukecooks.storage.profile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dukecooks.commons.exceptions.IllegalValueException;
import dukecooks.model.profile.medical.MedicalHistory;
import dukecooks.model.profile.person.BloodType;
import dukecooks.model.profile.person.DoB;
import dukecooks.model.profile.person.Gender;
import dukecooks.model.profile.person.Height;
import dukecooks.model.profile.person.Name;
import dukecooks.model.profile.person.Person;
import dukecooks.model.profile.person.Weight;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String dateOfBirth;
    private final String gender;
    private final String bloodGroup;
    private final String weight;
    private final String height;
    private final List<JsonAdaptedMedicalHistory> medicalHistories = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name,
            @JsonProperty("dateOfBirth") String dateOfBirth,
            @JsonProperty("gender") String gender,
            @JsonProperty("bloodGroup") String bloodGroup,
            @JsonProperty("weight") String weight,
            @JsonProperty("height") String height,
            @JsonProperty("medicalHistories") List<JsonAdaptedMedicalHistory> medicalHistories) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.weight = weight;
        this.height = height;

        if (medicalHistories != null) {
            this.medicalHistories.addAll(medicalHistories);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        dateOfBirth = source.getDateOfBirth().dateOfBirth.toString();
        gender = source.getGender().gender;
        bloodGroup = source.getBloodType().bloodGroup;
        weight = String.valueOf(source.getWeight().weight);
        height = String.valueOf(source.getHeight().height);
        medicalHistories.addAll(source.getMedicalHistories().stream()
                .map(JsonAdaptedMedicalHistory::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<MedicalHistory> personMedicalHistories = new ArrayList<>();
        for (JsonAdaptedMedicalHistory tag : medicalHistories) {
            personMedicalHistories.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (dateOfBirth == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, DoB.class.getSimpleName()));
        }
        if (!DoB.isValidDate(dateOfBirth)) {
            throw new IllegalValueException(DoB.MESSAGE_CONSTRAINTS);
        }
        final DoB modelDateOfBirth = new DoB(dateOfBirth);

        if (gender == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Gender.class.getSimpleName()));
        }
        if (!Gender.isValidGender(gender)) {
            throw new IllegalValueException(Gender.MESSAGE_CONSTRAINTS);
        }
        final Gender modelGender = new Gender(gender);

        if (bloodGroup == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    BloodType.class.getSimpleName()));
        }
        if (!BloodType.isValidBloodType(bloodGroup)) {
            throw new IllegalValueException(BloodType.MESSAGE_CONSTRAINTS);
        }
        final BloodType modelBloodType = new BloodType(bloodGroup);

        if (weight == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Weight.class.getSimpleName()));
        }
        if (!Weight.isValidNumber(weight)) {
            throw new IllegalValueException(Weight.MESSAGE_CONSTRAINTS);
        }
        final Weight modelWeight = new Weight(weight);

        if (height == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Height.class.getSimpleName()));
        }
        if (!Height.isValidNumber(height)) {
            throw new IllegalValueException(Height.MESSAGE_CONSTRAINTS);
        }
        final Height modelHeight = new Height(height);

        final Set<MedicalHistory> modelMedicalHistories = new HashSet<>(personMedicalHistories);
        return new Person(modelName, modelDateOfBirth, modelGender, modelBloodType,
                modelWeight, modelHeight, modelMedicalHistories);
    }

}
