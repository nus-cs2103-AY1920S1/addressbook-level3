package seedu.address.testutil.grouputil;

import seedu.address.model.group.GroupDescription;
import seedu.address.model.group.GroupDescriptor;
import seedu.address.model.group.GroupList;
import seedu.address.model.group.GroupName;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.mapping.Role;

/**
 * Typical Groups.
 */
public class TypicalGroups {

    public static final GroupName GROUPNAME1 = new GroupName("group1");
    public static final GroupName GROUPNAME2 = new GroupName("group2");
    public static final GroupName GROUPNAME3 = new GroupName("group3");

    public static final GroupDescription GROUPDESCRIPTION1 = new GroupDescription("description1");
    public static final GroupDescription GROUPDESCRIPTION2 = new GroupDescription("description2");
    public static final GroupDescription GROUPDESCRIPTION3 = new GroupDescription("description3");


    public static final GroupDescriptor GROUP1 =
            new GroupDescriptor(GROUPNAME1, GROUPDESCRIPTION1, Role.emptyRole());
    public static final GroupDescriptor GROUP2 =
            new GroupDescriptor(GROUPNAME2, GROUPDESCRIPTION2, Role.emptyRole());
    public static final GroupDescriptor GROUP3 =
            new GroupDescriptor(GROUPNAME3, GROUPDESCRIPTION3, Role.emptyRole());

    public static final GroupName GROUPNAME0 = new GroupName("group0");
    public static final GroupDescription GROUPDESCRIPTION0 = new GroupDescription("description0");
    public static final GroupDescriptor GROUP0 =
            new GroupDescriptor(GROUPNAME0, GROUPDESCRIPTION0, Role.emptyRole());

    /**
     * Generates a typical GroupList.
     *
     * @return GroupList
     */
    public static GroupList generateTypicalGroupList() throws DuplicateGroupException {
        GroupList groupList = new GroupList();
        groupList.addGroup(GROUP1);
        groupList.addGroup(GROUP2);
        groupList.addGroup(GROUP3);
        return groupList;
    }
}
