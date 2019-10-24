package seedu.address.storage.day;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.day.ActivityWithTime;
import seedu.address.model.day.Day;

/**
 * Jackson-friendly version of {@link Day}.
 */
public class JsonAdaptedDay {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Day's %s field is missing!";

    private final List<JsonAdaptedActivityWithTime> activityWithTimes = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedDay} with the given days details.
     */
    @JsonCreator
    public JsonAdaptedDay(@JsonProperty("activityWithTimes") List<JsonAdaptedActivityWithTime> activityWithTimes) {
        if (activityWithTimes != null) {
            this.activityWithTimes.addAll(activityWithTimes);
        }
    }

    /**
     * Converts a given {@code Day} into this class for Jackson use.
     */
    public JsonAdaptedDay(Day source) {
        activityWithTimes.addAll(source.getListOfActivityWithTime().stream()
                .map(JsonAdaptedActivityWithTime::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted activityWithTimes object into the model's {@code Day} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted contacts.
     */
    public Day toModelType() throws IllegalValueException {
        try {
            final List<ActivityWithTime> dayActivities = new ArrayList<>();
            for (JsonAdaptedActivityWithTime activities : activityWithTimes) {
                dayActivities.add(activities.toModelType());
            }
            return new Day(dayActivities);
        } catch (CommandException ce) {
            throw new IllegalValueException(ce.getMessage());
        }


    }

}
