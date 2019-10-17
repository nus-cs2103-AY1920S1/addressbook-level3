package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Department;
import seedu.address.model.person.Email;
import seedu.address.model.person.EmailType;
import seedu.address.model.person.Emails;
import seedu.address.model.person.Faculty;
import seedu.address.model.person.Interviewee;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Slot;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Interviewee}.
 */
public class JsonAdaptedInterviewee extends JsonAdaptedPerson {

    // TODO: Probably need to store type of role E.g interviewee/interviewer
    private final String faculty;
    private final Integer yearOfStudy;
    private final List<JsonAdaptedDepartment> departmentChoices = new ArrayList<>(); // choice of departments
    private final List<JsonAdaptedSlot> availableTimeslots = new ArrayList<>(); // allocated interview time slots
    private final Map<String, List<String>> emails = new HashMap<>(); // personal, NUS emails etc

    /**
     * Constructs a {@code JsonAdaptedInterviewee} with the given interviewee details.
     */
    @JsonCreator
    public JsonAdaptedInterviewee(
            @JsonProperty("faculty") String faculty, @JsonProperty("yearOfStudy") Integer yearOfStudy,
            @JsonProperty("departmentChoices") List<JsonAdaptedDepartment> departmentChoices,
            @JsonProperty("availableTimeslots") List<JsonAdaptedSlot> availableTimeslots,
            @JsonProperty("emails") HashMap<String, List<String>> emails,
            @JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("address") String address, @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        super(name, phone, address, tagged);
        this.faculty = faculty;
        this.yearOfStudy = yearOfStudy;
        if (departmentChoices != null) {
            this.departmentChoices.addAll(departmentChoices);
        }
        if (availableTimeslots != null) {
            this.availableTimeslots.addAll(availableTimeslots);
        }
        if (emails != null) {
            this.emails.putAll(emails);
        }
    }

    /**
     * Converts a given {@code Interviewee} into this class for Jackson use.
     */
    public JsonAdaptedInterviewee(Interviewee source) {
        super(source.getName().fullName, source.getPhone().value, source.getAddress().value,
                source.getTags().stream().map(JsonAdaptedTag::new).collect(Collectors.toList()));
        faculty = source.getFaculty().faculty;
        yearOfStudy = source.getYearOfStudy();
        departmentChoices.addAll(source.getDepartmentChoices()
                .stream()
                .map(JsonAdaptedDepartment::new)
                .collect(Collectors.toList()));
        availableTimeslots.addAll(source.getAvailableTimeslots()
                .stream()
                .map(JsonAdaptedSlot::new)
                .collect(Collectors.toList()));
        emails.putAll(source.getEmails().getAllEmails().entrySet()
                .stream()
                .collect(Collectors.toMap(
                    entry -> entry.getKey().toString(),
                    entry -> entry.getValue().stream().map(Email::toString).collect(Collectors.toList()))
                ));
    }

    /**
     * Converts this Jackson-friendly adapted interviewee object into the model's {@code Interviewee} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted interviewee.
     */
    public Interviewee toModelType() throws IllegalValueException {
        final String name = getName();
        final String phone = getPhone();
        final String address = getAddress();
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : getTagged()) {
            personTags.add(tag.toModelType());
        }
        final List<Department> departments = new ArrayList<>();
        for (JsonAdaptedDepartment d: departmentChoices) {
            departments.add(d.toModelType());
        }
        final List<Slot> personSlots = new ArrayList<>();
        for (JsonAdaptedSlot s: availableTimeslots) {
            personSlots.add(s.toModelType());
        }
        final HashMap<EmailType, List<Email>> personEmails = new HashMap<>();
        for (Map.Entry<String, List<String>> entry: emails.entrySet()) {
            personEmails.put(
                    EmailType.valueOf(entry.getKey()),
                    entry.getValue().stream().map(Email::new).collect(Collectors.toList())
            );
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
        // check address
        if (address == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);
        // check faculty
        if (faculty == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Faculty.class.getSimpleName()));
        }
        final Faculty modelFaculty = new Faculty(faculty);
        // check year of study
        if (yearOfStudy == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Integer.class.getSimpleName()));
        }
        final Integer modelYearOfStudy = Integer.valueOf(yearOfStudy);
        // no need to check departments departments
        final List<Department> modelDepartments = new ArrayList<>(departments);
        // no need to check tags
        final Set<Tag> modelTags = new HashSet<>(personTags);
        // no need to check timeslots
        final List<Slot> modelSlots = new ArrayList<>(personSlots);
        // no need to check emails
        final Emails modelEmails = new Emails(personEmails);

        return new Interviewee.IntervieweeBuilder(modelName, modelPhone, modelAddress, modelTags)
                .faculty(modelFaculty)
                .yearOfStudy(modelYearOfStudy)
                .departmentChoices(modelDepartments)
                .availableTimeslots(modelSlots)
                .emails(modelEmails)
                .build();
    }
}
