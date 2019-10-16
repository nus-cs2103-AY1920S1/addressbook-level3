package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.project.Meeting;
import seedu.address.model.project.Project;
import seedu.address.model.project.Description;
import seedu.address.model.project.Title;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Jackson-friendly version of {@link Project}.
 */
class JsonAdaptedProject {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Project's %s field is missing!";

    private final String title;
    private final String description;
    private final List<JsonAdaptedMeeting> meetingList = new ArrayList<>();
    ;

    /**
     * Constructs a {@code JsonAdaptedProject} with the given project details.
     */
    @JsonCreator
    public JsonAdaptedProject(@JsonProperty("title") String title, @JsonProperty("description") String description, @JsonProperty("meetingList") List<JsonAdaptedMeeting> meetingList) {
        this.title = title;
        this.description = description;
        if (meetingList != null) {
            this.meetingList.addAll(meetingList);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedProject(Project source) {
        title = source.getTitle().title;
        description = source.getDescription().description;
        meetingList.addAll(source.getListOfMeeting().stream()
                .map(JsonAdaptedMeeting::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted project object into the model's {@code Project} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted project.
     */
    public Project toModelType() throws IllegalValueException, ParseException {
        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName()));
        }
        final Title modelTitle = new Title(title);

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName()));
        }
        final Description modelDescription = new Description(description);

        //need to convert the List<JsonAdaptedMeeting> to List<Meeting> then put it in the Set<Meeting> and set it to the given project.
        final List<Meeting> meetings = new ArrayList<>();
        for (JsonAdaptedMeeting meeting : meetingList) {
            meetings.add(meeting.toModelType());
        }

        Project project = new Project(modelTitle, modelDescription);

        Set<Meeting> meetingsList = new HashSet<>(meetings);
        project.setListOfMeeting(meetingsList);

        return project;
    }

}
