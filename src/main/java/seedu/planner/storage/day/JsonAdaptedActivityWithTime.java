package seedu.planner.storage.day;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.planner.commons.exceptions.IllegalValueException;
import seedu.planner.model.activity.Activity;
import seedu.planner.model.day.ActivityWithTime;
import seedu.planner.model.day.Day;
import seedu.planner.storage.activity.JsonAdaptedActivity;

/**
 * Jackson-friendly version of {@link Day}.
 */
public class JsonAdaptedActivityWithTime {

    private final JsonAdaptedActivity activity;
    private final String startDateTime;
    private final String endTime;

    /**
     * Constructs a {@code JsonAdaptedActivityWithTime} with the given days details.
     */
    @JsonCreator
    public JsonAdaptedActivityWithTime(@JsonProperty("activity") JsonAdaptedActivity activity,
                          @JsonProperty("startTime") String startTime, @JsonProperty("endTime") String endTime) {
        this.activity = activity;
        this.startDateTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Converts a given {@code Day} into this class for Jackson use.
     */
    public JsonAdaptedActivityWithTime(ActivityWithTime source) {
        activity = new JsonAdaptedActivity(source.getActivity());
        startDateTime = source.getStartDateTime().toString();
        endTime = source.getEndDateTime().toString();
    }

    /**
     * Converts various Jackson-friendly adapted class objects into the day's {@code ActivityWithTime} object.
     */
    public ActivityWithTime toModelType() throws IllegalValueException {
        final Activity modelActivity = activity.toModelType();
        LocalDateTime parsedDateTime = LocalDateTime.parse(startDateTime, ISO_LOCAL_DATE_TIME);
        return new ActivityWithTime(modelActivity, parsedDateTime);
    }

}
