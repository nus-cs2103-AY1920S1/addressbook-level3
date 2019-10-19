package seedu.exercise.storage.serializablebook;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.resource.Schedule;
import seedu.exercise.storage.resource.JsonAdaptedSchedule;

/**
 * An Immutable ScheduleBook that is serializable to JSON format.
 */
@JsonRootName(value = "schedulebook")
public class JsonSerializableScheduleBook extends SerializableResourceBook<JsonAdaptedSchedule, Schedule> {


    @JsonCreator
    public JsonSerializableScheduleBook(@JsonProperty("jsonResources") List<JsonAdaptedSchedule> schedules) {
        super(schedules);
    }

    public JsonSerializableScheduleBook(ReadOnlyResourceBook<Schedule> source) {
        super(source, JsonAdaptedSchedule.class);
    }


}
