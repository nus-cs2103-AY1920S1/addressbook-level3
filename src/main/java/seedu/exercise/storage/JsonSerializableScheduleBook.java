package seedu.exercise.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.exercise.commons.exceptions.IllegalValueException;
import seedu.exercise.model.ReadOnlyScheduleBook;
import seedu.exercise.model.ScheduleBook;
import seedu.exercise.model.schedule.Schedule;

/**
 * An Immutable ScheduleBook that is serializable to JSON format.
 */
@JsonRootName(value = "schedulebook")
public class JsonSerializableScheduleBook {

    public static final String MESSAGE_DUPLICATE_SCHEDULE = "Schedule list contains duplicate schedules";

    private final List<JsonAdaptedSchedule> schedules = new ArrayList<>();

    @JsonCreator
    public JsonSerializableScheduleBook(@JsonProperty("schedules") List<JsonAdaptedSchedule> schedules) {
        this.schedules.addAll(schedules);
    }

    public JsonSerializableScheduleBook(ReadOnlyScheduleBook source) {
        schedules
                .addAll(source.getScheduleList()
                        .stream()
                        .map(JsonAdaptedSchedule::new)
                        .collect(Collectors.toList()));
    }

    /**
     * Converts this schedule book into the model's {@code ScheduleBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ScheduleBook toModelType() throws IllegalValueException {
        ScheduleBook scheduleBook = new ScheduleBook();
        for (JsonAdaptedSchedule schedule : schedules) {
            Schedule scheduleModel = schedule.toModelType();
            if (scheduleBook.hasSchedule(scheduleModel)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_SCHEDULE);
            }
            scheduleBook.addSchedule(scheduleModel);
        }
        return scheduleBook;
    }
}
