package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Description;
import seedu.address.model.reminders.conditions.TagsCondition;
import seedu.address.model.tag.Tag;
/**
 * Jackson-friendly version of {@link TagsCondition}.
 */
public class JsonAdaptedTagsCondition {
    public final String desc;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    /**
     * Constructs a {@code JsonAdaptedTagsCondition} with the given condition details.
     */
    @JsonCreator
    public JsonAdaptedTagsCondition(@JsonProperty("desc") String desc,
                                        @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.desc = desc;
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }
    public JsonAdaptedTagsCondition(TagsCondition source) {
        this.desc = source.getDesc().toString();
        this.tags.addAll(source.getTagList().stream().map(JsonAdaptedTag::new).collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted TagsCondition object into the model's {@code TagsCondition} object.
     * @return
     * @throws IllegalValueException
     */
    public TagsCondition toModelType() throws IllegalValueException {
        final Description modelDesc = new Description(desc);
        final List<Tag> modelTagList = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            modelTagList.add(tag.toModelType());
        }
        return new TagsCondition(modelDesc, modelTagList);
    }
}
