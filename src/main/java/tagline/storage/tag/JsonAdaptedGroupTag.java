package tagline.storage.tag;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import tagline.commons.exceptions.IllegalValueException;
import tagline.model.group.GroupName;
import tagline.model.tag.GroupTag;

/**
 * Jackson-friendly version of {@link GroupTag}.
 */
@JsonTypeName("groupTag")
public class JsonAdaptedGroupTag extends JsonAdaptedTag {
    private String groupName;

    @JsonCreator
    public JsonAdaptedGroupTag(@JsonProperty("groupName") String groupName) {
        super();
        this.groupName = groupName;
    }

    public JsonAdaptedGroupTag(GroupTag source) {
        super();
        this.groupName = source.getGroupName().value;
    }

    @Override
    public GroupTag toModelType() throws IllegalValueException {
        return new GroupTag(new GroupName(groupName));
    }
}
