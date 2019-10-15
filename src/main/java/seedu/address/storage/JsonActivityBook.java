package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ActivityBook;
import seedu.address.model.activity.Activity;

/**
 * An Immutable ActivityBook that is serializable to JSON format.
 */
@JsonRootName(value = "activitybook")
class JsonActivityBook {

    private final List<JsonAdaptedActivity> activityList = new ArrayList<>();

    /**
     * Constructs a {@code JsonActivityBook} with the given activities.
     */
    @JsonCreator
    public JsonActivityBook(@JsonProperty("activityList") List<JsonAdaptedActivity> activities) {
        this.activityList.addAll(activities);
    }

    /**
     * Converts a given {@code ActivityBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonActivityBook}.
     */
    public JsonActivityBook(ActivityBook source) {
        activityList.addAll(source.getActivityList().stream()
                .map(JsonAdaptedActivity::new).collect(Collectors.toList()));
    }

    /**
     * Converts this activity book into the model's {@code ActivityBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ActivityBook toModelType() throws IllegalValueException {
        ActivityBook activityBook = new ActivityBook();
        for (JsonAdaptedActivity jsonAdaptedActivity : activityList) {
            Activity activity = jsonAdaptedActivity.toModelType();
            activityBook.addActivity(activity);
        }
        return activityBook;
    }

}
