package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.common.Photo;

/**
 * Jackson-friendly version of {@link Photo}.
 */
class JsonAdaptedPhoto {

    private static final String MISSING_IMAGE_PATH_MESSAGE = "A photo is missing its image path in the storage file!";

    private final String imagePath;

    /**
     * Constructs a {@code JsonAdaptedTripPhoto} with the given details.
     */
    @JsonCreator
    public JsonAdaptedPhoto(@JsonProperty("imagePath") String imagePath) {
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
        if (imagePath == null) {
            throw new IllegalArgumentException(MISSING_IMAGE_PATH_MESSAGE);
        }

        return new Photo(imagePath);
    }
}
