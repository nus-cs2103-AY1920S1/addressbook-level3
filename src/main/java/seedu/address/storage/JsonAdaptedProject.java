package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.project.Meeting;
import seedu.address.model.finance.Finance;
import seedu.address.model.project.Project;
import seedu.address.model.project.Description;
import seedu.address.model.project.Task;
import seedu.address.model.project.Title;
import seedu.address.model.timetable.Timetable;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Jackson-friendly version of {@link Project}.
 */
class JsonAdaptedProject {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Project's %s field is missing!";

    private final String title;
    private final String description;
    private final List<String> members = new ArrayList<>();
    private final List<JsonAdaptedTask> tasks = new ArrayList<>();
    private final List<JsonAdaptedMeeting> meetingList = new ArrayList<>();
    private final JsonAdaptedFinance finance;

    /**
     * Constructs a {@code JsonAdaptedProject} with the given project details.
     */
    @JsonCreator
    public JsonAdaptedProject(@JsonProperty("title") String title, @JsonProperty("description") String description, @JsonProperty("members") List<String> members,
                              @JsonProperty("tasks") List<JsonAdaptedTask> tasks, @JsonProperty("meetingList") List<JsonAdaptedMeeting> meetingList,
                              @JsonProperty("finance") JsonAdaptedFinance finance) {
        this.title = title;
        this.description = description;

        if (members != null) {
            this.members.addAll(members);
        }

        if (tasks != null) {
            this.tasks.addAll(tasks);
        }
        if (meetingList != null) {
            this.meetingList.addAll(meetingList);
        }
        this.finance = finance;
    }
    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedProject(Project source) {
        title = source.getTitle().title;
        description = source.getDescription().description;
        members.addAll(source.getMemberNames());
        tasks.addAll(source.getTasks().stream()
                .map(JsonAdaptedTask::new)
                .collect(Collectors.toList()));
        meetingList.addAll(source.getListOfMeeting().stream()
                .map(JsonAdaptedMeeting::new)
                .collect(Collectors.toList()));
        finance = new JsonAdaptedFinance(source.getFinance());
    }

    /**
     * Converts this Jackson-friendly adapted project object into the model's {@code Project} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted project.
     */
    public Project toModelType() throws IllegalValueException, ParseException {

        final List<Task> taskList = new ArrayList<>();
        for (JsonAdaptedTask task : tasks) {
            taskList.add(task.toModelType());
        }

        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName()));
        }
        final Title modelTitle = new Title(title);

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        final Description modelDescription = new Description(description);

        final List<String> modelPersonList = new ArrayList<>();
        for (String person : members) {
            modelPersonList.add(person);
        }


        final List<Task> modelTasks = new ArrayList<>(taskList);

        //need to convert the List<JsonAdaptedMeeting> to List<Meeting> then put it in the Set<Meeting> and set it to the given project.
        final List<Meeting> meetings = new ArrayList<>();
        for (JsonAdaptedMeeting meeting : meetingList) {
            meetings.add(meeting.toModelType());
        }
        final Finance modelFinance = finance.toModelType();

        Project project = new Project(modelTitle, modelDescription, modelPersonList, modelTasks, modelFinance, new Timetable());

        project.setListOfMeeting(meetings);

        return project;
    }

}
