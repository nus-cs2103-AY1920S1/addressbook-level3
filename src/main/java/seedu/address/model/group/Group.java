package seedu.address.model.group;

/**
 * A group.
 */
public class Group {
    private static Integer counter = 0;
    private final GroupId groupId;
    private GroupName groupName;
    private GroupRemark groupRemark;

    public Group(GroupDescriptor groupDescriptor) {
        this.groupName = groupDescriptor.getGroupName();
        this.groupRemark = groupDescriptor.getGroupRemark();
        this.groupId = new GroupId(counter);
        counter += 1;
    }

    public GroupName getGroupName() {
        return this.groupName;
    }

    public GroupId getGroupId() {
        return this.groupId;
    }

    /**
     * Converts to String.
     * @return String
     */
    public String toString() {
        String output = "";
        output += "(" + groupId.toString() + ") ";
        output += groupName.toString();
        return output;
    }


}
