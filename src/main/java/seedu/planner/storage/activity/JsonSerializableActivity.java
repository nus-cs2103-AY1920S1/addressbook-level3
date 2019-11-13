package seedu.planner.storage.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.planner.commons.exceptions.IllegalValueException;
import seedu.planner.model.ActivityManager;
import seedu.planner.model.ReadOnlyActivity;
import seedu.planner.model.activity.Activity;
//@@author OneArmyj
/**
 * An Immutable Activity that is serializable to JSON format.
 */
@JsonRootName(value = "activityManager")
public class JsonSerializableActivity {

    public static final String MESSAGE_DUPLICATE_ACTIVITY = "Activities list contains one or more duplicate "
        + "activity.";

    private final List<JsonAdaptedActivity> activities = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableActivity} with the given activities.
     */
    @JsonCreator
    public JsonSerializableActivity(
        @JsonProperty("activities") List<JsonAdaptedActivity> activities) {
        this.activities.addAll(activities);
    }

    /**
     * Converts a given {@code ReadOnlyActivity} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableActivity}.
     */
    public JsonSerializableActivity(ReadOnlyActivity source) {
        activities.addAll(source.getActivityList().stream().map(JsonAdaptedActivity::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts these activity data into the model's {@code ActivityManager} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ActivityManager toModelType() throws IllegalValueException {
        ActivityManager activity = new ActivityManager();
        for (JsonAdaptedActivity jsonAdaptedActivity : activities) {
            Activity activ = jsonAdaptedActivity.toModelType();
            if (activity.hasActivity(activ)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ACTIVITY);
            }
            activity.addActivity(activ);
        }
        return activity;
    }

}
