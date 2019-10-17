package tagline.storage.tag;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import tagline.commons.exceptions.IllegalValueException;
import tagline.model.tag.ReadOnlyTagList;
import tagline.model.tag.Tag;
import tagline.model.tag.TagList;

/**
 * An Immutable TagList that is serializable to JSON format.
 */
public class JsonSeriazableTagList {

    public static final String MESSAGE_DUPLICATE_TAG = "Tag list contains duplicate tag(s).";

    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableNoteBook} with the given notes.
     */
    @JsonCreator
    public JsonSeriazableTagList(@JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.tags.addAll(tags);
    }

    /**
     * Converts a given {@code ReadOnlyNoteBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableNoteBook}.
     */
    public JsonSeriazableTagList(ReadOnlyTagList source) {
        tags.addAll(source.getTagList().stream().map(JsonAdaptedTag::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code NoteBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public TagList toModelType() throws IllegalValueException {
        TagList tagList = new TagList();
        for (JsonAdaptedTag jsonAdaptedTag : tags) {
            Tag tag = jsonAdaptedTag.toModelType();
            if (tagList.containsTag(tag)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TAG);
            }
            tagList.addTag(tag);
        }
        return tagList;
    }

}
