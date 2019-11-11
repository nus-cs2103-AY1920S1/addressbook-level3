package seedu.weme.storage;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.weme.commons.exceptions.IllegalValueException;
import seedu.weme.model.meme.Description;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.path.ImagePath;
import seedu.weme.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Meme}.
 */
class JsonAdaptedMeme {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Meme's %s field is missing!";

    private final String description;
    private final String filePath;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();
    private final boolean isArchived;

    /**
     * Constructs a {@code JsonAdaptedMeme} with the given meme details.
     */
    @JsonCreator
    public JsonAdaptedMeme(@JsonProperty("filePath") String filePath, @JsonProperty("description") String description,
                           @JsonProperty("tagged") List<JsonAdaptedTag> tagged,
                           @JsonProperty("isArchived") boolean isArchived) {

        this.filePath = filePath;
        this.description = description;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
        this.isArchived = isArchived;
    }

    /**
     * Converts a given {@code Meme} into this class for Jackson use.
     *
     * @param folderPath the path to the folder the meme images are saved in.
     */
    public JsonAdaptedMeme(Meme source, Path folderPath) {
        filePath = folderPath.relativize(source.getImagePath().getFilePath()).toString(); // store only filename
        description = source.getDescription().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        isArchived = source.isArchived();
    }

    /**
     * Converts this Json-friendly adapted meme object into the model's {@code Meme} object.
     *
     * @param folderPath the path to the folder the meme images are saved in.
     * @throws IllegalValueException if there were any data constraints violated in the adapted meme.
     */
    public Meme toModelType(Path folderPath) throws IllegalValueException {
        final List<Tag> memeTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            memeTags.add(tag.toModelType());
        }

        if (filePath == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ImagePath.class.getSimpleName()));
        }
        String imagePathString = folderPath.resolve(filePath).toString(); // convert to be usable by Weme
        if (!ImagePath.isValidFilePath(imagePathString)) {
            throw new IllegalValueException(ImagePath.MESSAGE_CONSTRAINTS);
        }
        final ImagePath modelUrl = new ImagePath(imagePathString);

        if (description == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        final Set<Tag> modelTags = new HashSet<>(memeTags);
        return new Meme(modelUrl, modelDescription, modelTags, isArchived);
    }

}
