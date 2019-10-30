package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javafx.util.Pair;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.notif.Notif;

//@@author arjavibahety

/**
 * Jackson-friendly version of {@link Notif}
 *
 */
public class JsonAdaptedNotif {
    public static final String MISSING_BODY_ID = "Notif's body id field is missing!";
    public static final String MISSING_DATE = "Notif's date field is missing!";
    public static final String INVALID_BODY_ID_NUMBER = "Notif's id number of the body is wrong!";
    public static final String INVALID_DATE_TIME = "Notif's date time entry is wrong!";

    // Stores the ID number of the body, not the actual ID.
    private final String body;

    private final String dateTime;

    /**
     * Constructs a {@code JsonAdaptedNotif} with the given body details.
     */
    @JsonCreator
    public JsonAdaptedNotif(@JsonProperty("body") String body,
                            @JsonProperty("dateTime") String dateTime) {
        this.body = body;
        this.dateTime = dateTime;
    }

    /**
     * Converts a given {@code Notif and DateTime} into this class for Jackson use.
     */
    public JsonAdaptedNotif(Notif source) {
        this.body = Integer.toString(source.getBody().getIdNum().getIdNum());
        this.dateTime = source.getNotifCreationTime().getTime() + "";
    }

    /**
     * Converts this Jackson-friendly adapted notif object into the model's {@code Notif} object.
     *
     * @throws IllegalValueException if there were any data constraints violation in the adapted notif.
     */
    public Pair<Integer, Long> toModelType() throws IllegalValueException, NumberFormatException {
        // Convert Body
        final int actualBodyId;
        if (body == null) {
            throw new IllegalValueException(String.format(MISSING_BODY_ID));
        } else {
            try {
                actualBodyId = Integer.parseInt(body);
            } catch (NumberFormatException e) {
                throw new IllegalValueException(INVALID_BODY_ID_NUMBER);
            }
        }

        // Convert Date
        final long actualDate;
        if (dateTime == null) {
            throw new IllegalValueException(String.format(MISSING_DATE));
        } else {
            try {
                actualDate = Long.parseLong(dateTime);
                if (actualDate < 0) {
                    throw new IllegalValueException(INVALID_DATE_TIME);
                }
            } catch (NumberFormatException e) {
                throw new IllegalValueException(INVALID_DATE_TIME);
            }
        }

        return new Pair(actualBodyId, actualDate);
    }
}

//@@author
