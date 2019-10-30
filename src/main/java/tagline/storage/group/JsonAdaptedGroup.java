//@@author e0031374
package tagline.storage.group;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import tagline.commons.exceptions.IllegalValueException;
import tagline.model.group.Group;
import tagline.model.group.GroupDescription;
import tagline.model.group.GroupName;
import tagline.model.group.MemberId;

/**
 * Jackson-friendly version of {@link Group}.
 */
public class JsonAdaptedGroup {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Group's %s field is missing!";

    private final String groupname;
    private final String groupdescription;
    private final List<JsonAdaptedMemberId> members = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedGroup} with the given group details.
     */
    @JsonCreator
    public JsonAdaptedGroup(@JsonProperty("groupname") String groupname,
        @JsonProperty("groupdescription") String groupdescription,
        @JsonProperty("members") List<JsonAdaptedMemberId> members) {
        this.groupname = groupname;
        this.groupdescription = groupdescription;
        if (members != null) {
            this.members.addAll(members);
        }
    }

    /**
     * Converts a given {@code Group} into this class for Jackson use.
     */
    public JsonAdaptedGroup(Group source) {
        groupname = source.getGroupName().value;
        groupdescription = source.getGroupDescription().value;
        members.addAll(source.getMemberIds().stream()
                .map(JsonAdaptedMemberId::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted group object into the model's {@code Group} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted group.
     */
    public Group toModelType() throws IllegalValueException {
        final List<MemberId> groupMembers = new ArrayList<>();
        for (JsonAdaptedMemberId member: members) {
            groupMembers.add(member.toModelType());
        }

        if (groupname == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                GroupName.class.getSimpleName()));
        }
        if (!GroupName.isValidGroupName(groupname)) {
            throw new IllegalValueException(GroupName.MESSAGE_CONSTRAINTS);
        }
        final GroupName modelGroupName = new GroupName(groupname);

        if (groupdescription == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                GroupDescription.class.getSimpleName()));
        }
        if (!GroupDescription.isValidGroupDescription(groupdescription)) {
            throw new IllegalValueException(GroupDescription.MESSAGE_CONSTRAINTS);
        }
        final GroupDescription modelGroupDescription = new GroupDescription(groupdescription);

        final Set<MemberId> modelMembers = new HashSet<>(groupMembers);

        return new Group(modelGroupName, modelGroupDescription, modelMembers);
    }
}
