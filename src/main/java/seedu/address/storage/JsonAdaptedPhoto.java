package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.common.Photo;

/**
 * Jackson-friendly version of {@link Photo}.
 */
public class JsonAdaptedPhoto {

    private String imagePath;

    /**
     * Constructs a {@code JsonAdaptedTripPhoto} with the given details.
     */
    @JsonCreator
    public JsonAdaptedPhoto(
            @JsonProperty("imagePath") String imagePath) {
        requireNonNull(imagePath);
        this.imagePath = imagePath;
    }

    /**
     * Converts a given {@code Photo} into this class for Jackson use.
     */
    public JsonAdaptedPhoto(Photo source) {
        requireNonNull(source);
        this.imagePath = source.getImageFilePath();
    }

    /**
     * Converts this Jackson-friendly adapted diary object into the model's {@code Photo} object.
     */
    public Photo toModelType() {
        return new Photo(imagePath);
    }
}
