package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.UploadPictureCommand;
import seedu.address.model.student.Address;
import seedu.address.model.student.DisplayPicture;
import seedu.address.model.student.Email;
import seedu.address.model.student.MedicalCondition;
import seedu.address.model.student.Name;
import seedu.address.model.student.ParentPhone;
import seedu.address.model.student.Phone;
import seedu.address.model.student.Student;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Student}.
 */
class JsonAdaptedStudent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Student's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String parentPhone;
    private final String address;
    private final String medicalCondition;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();
    private final String displayPicture;

    /**
     * Constructs a {@code JsonAdaptedStudent} with the given student details.
     */
    @JsonCreator
    public JsonAdaptedStudent(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                              @JsonProperty("email") String email, @JsonProperty("parentPhone") String parentPhone,
                              @JsonProperty("address") String address,
                              @JsonProperty("displayPicture") String displayPicture,
                              @JsonProperty("medicalCondition") String medicalCondition,
                              @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.parentPhone = parentPhone;
        this.address = address;
        this.displayPicture = displayPicture;
        if (medicalCondition != null) {
            this.medicalCondition = medicalCondition;
        } else {
            this.medicalCondition = "NIL";
        }
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Student} into this class for Jackson use.
     */
    public JsonAdaptedStudent(Student source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        parentPhone = source.getParentPhone().value;
        address = source.getAddress().value;
        medicalCondition = source.getMedicalCondition().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        displayPicture = source.getDisplayPictureFilePath();
    }

    /**
     * Converts this Jackson-friendly adapted student object into the model's {@code Student} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted student.
     */
    public Student toModelType() throws IllegalValueException {
        final List<Tag> studentTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            studentTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidParentPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (parentPhone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ParentPhone.class.getSimpleName()));
        }
        if (!ParentPhone.isValidParentPhone(parentPhone)) {
            throw new IllegalValueException(ParentPhone.MESSAGE_CONSTRAINTS);
        }
        final ParentPhone modelParentPhone = new ParentPhone(parentPhone);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        if (displayPicture == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DisplayPicture.class.getSimpleName()));
        }
        if (!DisplayPicture.isValidFormat(displayPicture)) {
            throw new IllegalValueException(UploadPictureCommand.MESSAGE_WRONG_FORMAT);
        }
        final DisplayPicture modelDisplayPicture = new DisplayPicture(displayPicture);

        if (medicalCondition == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    MedicalCondition.class.getSimpleName()));
        }
        if (!MedicalCondition.isValidMedicalCondition(medicalCondition)) {
            throw new IllegalValueException(MedicalCondition.MESSAGE_CONSTRAINTS);
        }
        final MedicalCondition modelMedicalCondition = new MedicalCondition(medicalCondition);

        final Set<Tag> modelTags = new HashSet<>(studentTags);
        return new Student(modelName, modelPhone, modelEmail, modelParentPhone, modelAddress,
                modelDisplayPicture, modelMedicalCondition, modelTags);
    }

}
