package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;
import seedu.address.model.timetable.TimeRange;
import seedu.address.model.timetable.TimeTable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String profilePicture;
    private final String address;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();
    private final List<String> projects = new ArrayList<>();
    private final List<JsonAdaptedTimeRange> timetable = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email, @JsonProperty("profilePicture") String profilePicture,
                             @JsonProperty("address") String address, @JsonProperty("tagged") List<JsonAdaptedTag> tagged,
                             @JsonProperty("projects") List<String> projects, @JsonProperty("timetable") List<JsonAdaptedTimeRange> timetable) {

        this.name = name;
        this.phone = phone;
        this.email = email;
        this.profilePicture = profilePicture;
        this.address = address;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
        if (projects != null) {
            this.projects.addAll(projects);
        }
        if (timetable != null) {
            this.timetable.addAll(timetable);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        profilePicture = source.getProfilePicture().value;
        address = source.getAddress().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        projects.addAll(source.getProjects());
        if (source.getTimeTable() != null) {
            timetable.addAll(source.getTimeTable().getTimeRanges().stream()
                    .map(JsonAdaptedTimeRange::new)
                    .collect(Collectors.toList()));
        }
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
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
        if (!Phone.isValidPhone(phone)) {
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

        if (!ProfilePicture.isValidFilePath(profilePicture)) {
            throw new IllegalValueException(ProfilePicture.MESSAGE_CONSTRAINTS);
        }
        final ProfilePicture modelProfilePicture = new ProfilePicture(profilePicture);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        final Set<Tag> modelTags = new HashSet<>(personTags);

        final List<String> modelProjectList = new ArrayList<>();
        modelProjectList.addAll(projects);
        List<TimeRange> timeRanges = new ArrayList<>();
        for (JsonAdaptedTimeRange timeRange : timetable) {
            timeRanges.add(timeRange.toModelType());
        }
        final TimeTable timeTable = new TimeTable(timeRanges);

        Person person = new Person(modelName, modelPhone, modelEmail, modelProfilePicture, modelAddress, modelTags, timeTable);

        person.getProjects().addAll(modelProjectList);
        return person;
    }

}
