package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupId;
import seedu.address.model.group.GroupName;
import seedu.address.model.group.GroupRemark;

/**
 * Constructs a {@code JsonAdaptedGroup} with the given Group details.
 */
public class JsonAdaptedGroup {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Group's %s field is missing!";

    private final String groupId;
    private final String groupName;
    private final String groupRemark;

    /**
     * Constructs a {@code JsonAdaptedGroup} with the given Group details.
     */
    @JsonCreator
    public JsonAdaptedGroup(@JsonProperty("groupId") String groupId,
                            @JsonProperty("groupName") String groupName,
                            @JsonProperty("groupRemark") String groupRemark) {

        this.groupId = groupId;
        this.groupName = groupName;
        this.groupRemark = groupRemark;
    }

    /**
     * Converts a given {@code Group} into this class for Jackson use.
     */
    public JsonAdaptedGroup(Group source) {
        this.groupId = source.getGroupId().toString();
        this.groupName = source.getGroupName().toString();
        this.groupRemark = source.getGroupRemark().toString();
    }


    /**
     * Converts this Jackson-friendly adapted group object into the model's {@code Group} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted group.
     */
    public Group toModelType() throws IllegalValueException {
        if (groupId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    GroupId.class.getSimpleName()));
        }
        final GroupId modelGroupId = new GroupId(groupId);

        if (groupName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    GroupName.class.getSimpleName()));
        }
        final GroupName modelGroupName = new GroupName(groupName);

        if (groupRemark == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    GroupRemark.class.getSimpleName()));
        }
        final GroupRemark modelGroupRemark = new GroupRemark(groupRemark);

        return new Group(modelGroupId, modelGroupName, modelGroupRemark);

    }

}
