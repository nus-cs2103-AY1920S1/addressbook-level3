package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Department;
import seedu.address.model.person.Email;
import seedu.address.model.person.Interviewer;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Slot;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Interviewer}.
 */
public class JsonAdaptedInterviewer extends JsonAdaptedPerson {

    private final List<JsonAdaptedSlot> availabilities = new ArrayList<>();
    private final String department;
    private final String email;

    /**
     * Constructs a {@code JsonAdaptedInterviewer} with the given interviewer details.
     */
    @JsonCreator
    public JsonAdaptedInterviewer(
            @JsonProperty("availabilities") List<JsonAdaptedSlot> availabilities,
            @JsonProperty("department") String department,
            @JsonProperty("email") String email,
            @JsonProperty("name") String name,
            @JsonProperty("phone") String phone,
            @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        super(name, phone, tagged);
        this.department = department;
        this.email = email;
        if (availabilities != null) {
            this.availabilities.addAll(availabilities);
        }
    }

    /**
     * Converts a given {@code Interviewer} into this class for Jackson use.
     */
    public JsonAdaptedInterviewer(Interviewer source) {
        super(source.getName().fullName, source.getPhone().value,
                source.getTags().stream().map(JsonAdaptedTag::new).collect(Collectors.toList()));
        department = source.getDepartment().department;
        email = source.getEmail().value;
        availabilities.addAll(source.getAvailabilities()
                .stream()
                .map(JsonAdaptedSlot::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted interviewer object into the model's {@code Interviewer} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted interviewer.
     */
    public Interviewer toModelType() throws IllegalValueException {
        final String name = getName();
        final String phone = getPhone();
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : getTagged()) {
            personTags.add(tag.toModelType());
        }
        final List<Slot> personAvailabilities = new ArrayList<>();
        for (JsonAdaptedSlot s: availabilities) {
            personAvailabilities.add(s.toModelType());
        }

        // check name
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);
        // check phone
        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);
        // check department
        if (department == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Department.class.getSimpleName()));
        }
        final Department modelDepartment = new Department(department);
        // check email
        if (email == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        final Email modelEmail = new Email(email);
        // no need to check tags
        final Set<Tag> modelTags = new HashSet<>(personTags);
        // no need to check availabilities
        final List<Slot> modelAvailabilities = new ArrayList<>(personAvailabilities);

        return new Interviewer.InterviewerBuilder(modelName, modelPhone, modelTags)
                    .department(modelDepartment)
                    .availabilities(modelAvailabilities)
                    .email(modelEmail)
                    .build();
    }
}
