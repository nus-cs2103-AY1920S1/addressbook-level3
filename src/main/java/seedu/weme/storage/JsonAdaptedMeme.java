package seedu.weme.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.weme.commons.exceptions.IllegalValueException;
import seedu.weme.model.meme.Description;
import seedu.weme.model.meme.ImagePath;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Meme}.
 */
class JsonAdaptedMeme {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Meme's %s field is missing!";

    private final String description;
    private final String filePath;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedMeme} with the given meme details.
     */
    @JsonCreator
    public JsonAdaptedMeme(@JsonProperty("filePath") String filePath, @JsonProperty("weme") String description,
                           @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {

        this.filePath = filePath;
        this.description = description;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Meme} into this class for Jackson use.
     */
    public JsonAdaptedMeme(Meme source) {
        filePath = source.getFilePath().toString();
        description = source.getDescription().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted meme object into the model's {@code Meme} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted meme.
     */
    public Meme toModelType() throws IllegalValueException {
        final List<Tag> memeTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            memeTags.add(tag.toModelType());
        }

        if (filePath == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ImagePath.class.getSimpleName()));
        }
        if (!ImagePath.isValidFilePath(filePath)) {
            throw new IllegalValueException(ImagePath.MESSAGE_CONSTRAINTS);
        }
        final ImagePath modelUrl = new ImagePath(filePath);

        if (description == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        final Set<Tag> modelTags = new HashSet<>(memeTags);
        return new Meme(modelUrl, modelDescription, modelTags);
    }

}
