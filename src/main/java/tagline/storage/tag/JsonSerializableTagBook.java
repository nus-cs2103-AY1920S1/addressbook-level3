package tagline.storage.tag;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import tagline.commons.core.LogsCenter;
import tagline.commons.exceptions.IllegalValueException;
import tagline.model.tag.ContactTag;
import tagline.model.tag.ReadOnlyTagBook;
import tagline.model.tag.Tag;
import tagline.model.tag.TagBook;

/**
 * An Immutable UniqueTagList that is serializable to JSON format.
 */
public class JsonSerializableTagBook {

    public static final String MESSAGE_DUPLICATE_TAG = "Tag list contains duplicate tag(s).";
    private static final Logger logger = LogsCenter.getLogger(JsonSerializableTagBook.class);

    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableNoteBook} with the given notes.
     */
    @JsonCreator
    public JsonSerializableTagBook(@JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.tags.addAll(tags);
    }

    /**
     * Converts a given {@code ReadOnlyNoteBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableNoteBook}.
     */
    public JsonSerializableTagBook(ReadOnlyTagBook source) {
        tags.addAll(source.getTagList().stream()
            .flatMap(tag -> {
                if (tag instanceof ContactTag) {
                    return Stream.of(new JsonAdaptedContactTag((ContactTag) tag));
                } else {
                    logger.warning("Unknown type of tag: " + tag.toString());
                    return Stream.empty();
                }
            })
            .collect(Collectors.toList()));
    }

    /**
     * Converts this tag book into the model's {@code TagBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public TagBook toModelType() throws IllegalValueException {
        TagBook tagBook = new TagBook();
        for (JsonAdaptedTag jsonAdaptedTag : tags) {
            Tag tag = jsonAdaptedTag.toModelType();
            if (tagBook.hasTag(tag)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TAG);
            }
            tagBook.addTag(tag);
        }
        return tagBook;
    }

}
