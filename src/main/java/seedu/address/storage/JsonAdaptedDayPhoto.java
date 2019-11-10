package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.itinerary.Photo;


/**
 * Jackson-friendly version of {@link Photo}.
 */
public class JsonAdaptedDayPhoto {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "One of the fields provided is invalid!\n"
            + "Cause: %1$s";

    private String imagePath;

    /**
     * Constructs a {@code JsonAdaptedTripPhoto} with the given details.
     */
    @JsonCreator
    public JsonAdaptedDayPhoto(
            @JsonProperty("imagePath") String imagePath) {
        requireNonNull(imagePath);
        this.imagePath = imagePath;
    }

    /**
     * Converts a given {@code Photo} into this class for Jackson use.
     */
    public JsonAdaptedDayPhoto(Photo source) {
        requireNonNull(source);
        this.imagePath = source.getImageFilePath();
    }

    /**
     * Converts this Jackson-friendly adapted diary object into the model's {@code Photo} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted diary.
     */
    public Photo toModelType() throws IllegalValueException {
        Photo photo;
        try {
            photo = new Photo(imagePath);
        } catch (IllegalArgumentException ex) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, ex.getMessage()));
        }

        return photo;
    }
}
