package seedu.address.model.util;

import seedu.address.model.group.GroupDescription;
import seedu.address.model.group.GroupDescriptor;
import seedu.address.model.group.GroupList;
import seedu.address.model.group.GroupName;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.mapping.Role;

/**
 * Sample GroupList.
 */
public class SampleGroupList {

    public static final GroupDescriptor GROUP1 = new GroupDescriptor(
            new GroupName("CS2103"),
            new GroupDescription("Software engineering project group"),
            new Role("Leader")
    );

    public static final GroupDescriptor GROUP2 = new GroupDescriptor(
            new GroupName("Friends"),
            new GroupDescription("Group of close friends"),
            new Role("Director")
    );

    /**
     * Generates a sample GroupList.
     */
    public static GroupList generateSampleGroupList() {
        try {
            GroupList groupList = new GroupList();
            groupList.addGroup(GROUP1);
            groupList.addGroup(GROUP2);
            return groupList;
        } catch (DuplicateGroupException e) {
            return null;
        }


    }

}
