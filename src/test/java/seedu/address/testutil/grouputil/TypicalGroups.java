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

    //THIS GROUP HAS USER, ALICE PAULINE, BENSON MEIER.
    public static final GroupName GROUP_NAME1 = new GroupName("group1");
    //THIS GROUP HAS USER, ALICE PAULINE, BENSON MEIER.
    public static final GroupName GROUP_NAME2 = new GroupName("group2");
    //THIS GROUP HAS USER, ALICE PAULINE, CARL KURZ.
    public static final GroupName GROUP_NAME3 = new GroupName("group3");

    public static final GroupDescription GROUP_DESCRIPTION1 = new GroupDescription("description1");
    public static final GroupDescription GROUP_DESCRIPTION2 = new GroupDescription("description2");
    public static final GroupDescription GROUP_DESCRIPTION3 = new GroupDescription("description3");

    public static final Role GROUP_ROLE1 = new Role("Role1");
    public static final Role GROUP_ROLE2 = new Role("Role2");
    public static final Role GROUP_ROLE3 = new Role("Role3");

    public static final GroupDescriptor GROUP1 =
            new GroupDescriptor(GROUP_NAME1, GROUP_DESCRIPTION1, GROUP_ROLE1);
    public static final GroupDescriptor GROUP2 =
            new GroupDescriptor(GROUP_NAME2, GROUP_DESCRIPTION2, GROUP_ROLE2);
    public static final GroupDescriptor GROUP3 =
            new GroupDescriptor(GROUP_NAME3, GROUP_DESCRIPTION3, GROUP_ROLE3);

    public static final GroupName GROUP_NAME0 = new GroupName("group0");
    public static final GroupDescription GROUP_DESCRIPTION0 = new GroupDescription("description0");
    public static final Role GROUP_ROLE0 = new Role("Role0");
    public static final GroupDescriptor GROUP0 =
            new GroupDescriptor(GROUP_NAME0, GROUP_DESCRIPTION0, GROUP_ROLE0);

    /**
     * Generates a typical GroupList.
     *
     * Group list will consist of 3 groups. Group 0 will not be included in the list.
     *
     * @return GroupList
     */
    public static GroupList generateTypicalGroupList() {
        GroupList groupList = new GroupList();
        try {
            groupList.addGroup(GROUP1);
            groupList.addGroup(GROUP2);
            groupList.addGroup(GROUP3);
        } catch (DuplicateGroupException e) {
            e.printStackTrace();
        }
        return groupList;
    }
}
