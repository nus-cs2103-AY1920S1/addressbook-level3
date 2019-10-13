package seedu.address.storage.diary.photo;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.diary.photo.Photo;

/**
 * Jackson-friendly version of {@link Photo}.
 */
public class JsonAdaptedPhoto {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Diary entry's %s field is missing!";

    private Path imagePath;
    private String description;
    private LocalDateTime dateTaken;

    /**
     * Constructs a {@code JsonAdaptedDiary} with the given diary details.
     */
    @JsonCreator
    public JsonAdaptedPhoto(
            @JsonProperty("imagepath") Path imagePath,
            @JsonProperty("description") String description,
            @JsonProperty("date") LocalDateTime dateTaken) {
        requireAllNonNull(imagePath, description, dateTaken);
        this.imagePath = imagePath;
        this.description = description;
        this.dateTaken = dateTaken;
    }

    /**
     * Converts a given {@code Diary} into this class for Jackson use.
     */
    public JsonAdaptedPhoto(Photo source) {
        requireNonNull(source);
        this.imagePath = Paths.get(source.getImage().getUrl());
        this.description = source.getDescription();
        this.dateTaken = source.getDateTaken();
    }

    /**
     * Converts this Jackson-friendly adapted diary object into the model's {@code Diary} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted diary.
     */
    public Photo toModelType() throws IllegalValueException {
        return new Photo(imagePath, description, dateTaken);
    }
}
