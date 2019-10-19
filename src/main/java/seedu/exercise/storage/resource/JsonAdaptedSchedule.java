package seedu.exercise.storage.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.exercise.commons.exceptions.IllegalValueException;
import seedu.exercise.model.property.Date;
import seedu.exercise.model.resource.Regime;
import seedu.exercise.model.resource.Schedule;

/**
 * Jackson-friendly version of {@link Schedule}.
 */
public class JsonAdaptedSchedule extends JsonAdaptedResource<Schedule> {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Schedule's %s field is missing!";

    private final JsonAdaptedRegime regime;
    private final String date;

    @JsonCreator
    public JsonAdaptedSchedule(@JsonProperty("regime") JsonAdaptedRegime regime, @JsonProperty("date") String date) {
        this.date = date;
        this.regime = regime;
    }

    public JsonAdaptedSchedule(Schedule schedule) {
        this.date = schedule.getDate().toString();
        this.regime = new JsonAdaptedRegime(schedule.getRegime());
    }

    /**
     * Converts this Jackson-friendly adapted schedule object into the model's {@code Schedule} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted schedule.
     */
    public Schedule toModelType() throws IllegalValueException {
        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(date)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date modelDate = new Date(date);

        if (regime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Regime.class.getSimpleName()));
        }

        Regime modelRegime = regime.toModelType();
        return new Schedule(modelRegime, modelDate);
    }
}
