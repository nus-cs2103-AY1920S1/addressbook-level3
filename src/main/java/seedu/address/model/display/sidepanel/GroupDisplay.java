package seedu.address.model.display.sidepanel;

import java.util.ArrayList;

import seedu.address.model.group.Group;
import seedu.address.model.group.GroupDescription;
import seedu.address.model.group.GroupName;
import seedu.address.model.person.Person;

/**
 * Group display model.
 */
public class GroupDisplay {

    private final GroupName groupName;
    private final GroupDescription groupDescription;

    public GroupDisplay(GroupName groupName, GroupDescription groupDescription) {
        this.groupName = groupName;
        this.groupDescription = groupDescription;
    }

    public GroupDisplay(Group group) {
        this.groupName = group.getGroupName();
        this.groupDescription = group.getGroupDescription();
    }

    public GroupDisplay(ArrayList<Person> persons) {
        this.groupName = new GroupName("Temporary Group");
        String description = "A group of:\n";
        for (Person person : persons) {
            description += person.getName().toString() + "\n";
        }
        this.groupDescription = new GroupDescription(description);

    }

    public GroupName getGroupName() {
        return groupName;
    }

    public GroupDescription getGroupDescription() {
        return groupDescription;
    }

    /**
     * Checks if this GroupDisplay is equal to another.
     */
    public boolean equals(GroupDisplay other) {
        if (other == this) {
            return true;
        } else if (other != null) {
            return this.groupName.equals(other.groupName)
                    && this.groupDescription.equals(other.groupDescription);
        } else {
            return false;
        }
    }
}
