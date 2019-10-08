package seedu.address.storage;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.AcadCalendar;

/**
 * Jackson-friendly version of {@link AcadCalendar}.
 */
class JsonAdaptedAcadCalendar {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "AcadCalendar's %s field is missing!";

    private final Map<String, String> startDates = new HashMap<>();

    /**
     * Constructs a {@code JsonAdaptedAcadCalendar} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedAcadCalendar(
            @JsonProperty("startDates") Map<String, String> startDates) {
        if (startDates != null) {
            this.startDates.putAll(startDates);
        }
    }

    /**
     * Converts a given {@code AcadCalendar} into this class for Jackson use.
     */
    public JsonAdaptedAcadCalendar(AcadCalendar source) {
        this.startDates.putAll(source.getStartDates());
    }

    /**
     * Converts this Jackson-friendly adapted acadCalendar object into the model's {@code AcadCalendar} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted acadCalendar.
     */
    public AcadCalendar toModelType() throws IllegalValueException {
        Map<String, String> modelStartDates = new HashMap<>();
        //TODO: checks
        modelStartDates.putAll(startDates);;
        return new AcadCalendar(modelStartDates);
    }
}
