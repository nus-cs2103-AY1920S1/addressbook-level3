package seedu.address.model.group;

/**
 * A group.
 */
public class Group {
    private static Integer counter = 0;
    private final GroupId groupId;
    private GroupName groupName;

    public Group(GroupName groupName) {
        this.groupName = groupName;
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
        output += "ID: " + groupId.toString() + " ";
        output += "Name: " + groupName.toString() + "\n";
        return output;
    }


}
