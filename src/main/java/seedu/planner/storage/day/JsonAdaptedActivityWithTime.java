package seedu.planner.storage.day;

import java.time.LocalTime;

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
    private final String startTime;
    private final String endTime;

    /**
     * Constructs a {@code JsonAdaptedActivityWithTime} with the given days details.
     */
    @JsonCreator
    public JsonAdaptedActivityWithTime(@JsonProperty("activity") JsonAdaptedActivity activity,
                          @JsonProperty("startTime") String startTime, @JsonProperty("endTime") String endTime) {
        this.activity = activity;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Converts a given {@code Day} into this class for Jackson use.
     */
    public JsonAdaptedActivityWithTime(ActivityWithTime source) {
        activity = new JsonAdaptedActivity(source.getActivity());
        startTime = source.getStartTime().toString();
        endTime = source.getEndTime().toString();
    }

    /**
     * Converts various Jackson-friendly adapted class objects into the day's {@code ActivityWithTime} object.
     */
    public ActivityWithTime toModelType() throws IllegalValueException {
        final Activity modelActivity = activity.toModelType();
        String[] stArray = startTime.split(":");
        int startHour = Integer.parseInt(stArray[0]);
        int startMin = Integer.parseInt(stArray[1]);
        LocalTime activityStartTime = LocalTime.of(startHour, startMin);
        return new ActivityWithTime(modelActivity, activityStartTime);
    }

}
