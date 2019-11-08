package seedu.address.model.display.sidepanel;

import java.util.ArrayList;

import seedu.address.model.group.Group;
import seedu.address.model.group.GroupDescription;
import seedu.address.model.group.GroupName;
import seedu.address.model.person.Person;

/**
 * Group display model
 */
public class GroupDisplay extends Display {
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
        for (Person person: persons) {
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
}
