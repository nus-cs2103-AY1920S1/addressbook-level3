package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Performance;
import seedu.address.model.project.Meeting;
import seedu.address.model.project.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import java.text.ParseException;

public class JsonAdaptedPerformance {

    private final HashMap<String, List<JsonAdaptedMeeting>> meetingsAttended = new HashMap<>();
    private final HashMap<String, List<JsonAdaptedTask>> taskAssignment = new HashMap<>();

    /**
     * Constructs a {@code JsonAdaptedPerformance} with the given performance details.
     */
    @JsonCreator
    public JsonAdaptedPerformance(@JsonProperty("meetingsAttended") HashMap<String, List<JsonAdaptedMeeting>> meetingsAttended,
                                  @JsonProperty("taskAssignment") HashMap<String, List<JsonAdaptedTask>> taskAssignment) {
        if (meetingsAttended != null) {
            this.meetingsAttended.putAll(meetingsAttended);
        }

        if (taskAssignment != null) {
            this.taskAssignment.putAll(taskAssignment);
        }
    }

    /**
     * Converts a given {@code Performance} into this class for Jackson use.
     */
    public JsonAdaptedPerformance(Performance source) {
        if (source.getMeetingsAttended() != null) {
            source.getMeetingsAttended().forEach((title, meetingList) -> {
                List<JsonAdaptedMeeting> jsonAdaptedMeetingList = meetingList.stream()
                        .map(JsonAdaptedMeeting::new)
                        .collect(Collectors.toList());
                this.meetingsAttended.put(title, jsonAdaptedMeetingList);
            });
        }

        if (source.getTaskAssignment() != null) {
            source.getTaskAssignment().forEach((title, taskList) -> {
                List<JsonAdaptedTask> jsonAdaptedTaskList = taskList.stream()
                        .map(JsonAdaptedTask::new)
                        .collect(Collectors.toList());
                this.taskAssignment.put(title, jsonAdaptedTaskList);
            });
        }
    }

    /**
     * Converts this Jackson-friendly Performance object into the model's {@code Performance} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Performance.
     */
    public Performance toModelType() throws IllegalValueException, ParseException {

        final HashMap<String, List<Meeting>> modelMeetings = new HashMap<>(meetingToModelType(this.meetingsAttended));
        final HashMap<String, List<Task>> modelTasks = new HashMap<>(taskToModelType(this.taskAssignment));

        return new Performance(modelMeetings, modelTasks);
    }

    private HashMap<String, List<Meeting>> meetingToModelType(HashMap<String, List<JsonAdaptedMeeting>> meetingsAttended) throws IllegalValueException, ParseException {
        final HashMap<String, List<Meeting>> modelMeetings = new HashMap<>();

        for (String title : meetingsAttended.keySet()) {
            List<JsonAdaptedMeeting> jsonMeetingList = meetingsAttended.get(title);
            List<Meeting> meetingList = new ArrayList<>();

            for (JsonAdaptedMeeting meeting : jsonMeetingList) {
                meetingList.add(meeting.toModelType());
            }

            modelMeetings.put(title, meetingList);
        }

        return modelMeetings;
    }

    private HashMap<String, List<Task>> taskToModelType(HashMap<String, List<JsonAdaptedTask>> taskAssignment) throws ParseException {
        final HashMap<String, List<Task>> modelTasks = new HashMap<>();

        for (String title : taskAssignment.keySet()) {
            List<JsonAdaptedTask> jsonTaskList = taskAssignment.get(title);
            List<Task> taskList = new ArrayList<>();

            for (JsonAdaptedTask task : jsonTaskList) {
                taskList.add(task.toModelType());
            }

            modelTasks.put(title, taskList);
        }

        return modelTasks;
    }
}
