package seedu.address.testutil.grouputil;

import seedu.address.model.group.GroupDescriptor;
import seedu.address.model.group.GroupList;
import seedu.address.model.group.GroupName;
import seedu.address.model.group.GroupRemark;

/**
 * Typical Groups.
 */
public class TypicalGroups {

    public static final GroupName GROUPNAME1 = new GroupName("group1");
    public static final GroupName GROUPNAME2 = new GroupName("group2");
    public static final GroupName GROUPNAME3 = new GroupName("group3");

    public static final GroupRemark GROUPREMARK1 = new GroupRemark("remark1");
    public static final GroupRemark GROUPREMARK2 = new GroupRemark("remark2");
    public static final GroupRemark GROUPREMARK3 = new GroupRemark("remark3");

    public static final GroupDescriptor GROUP1 = new GroupDescriptor(GROUPNAME1, GROUPREMARK1);
    public static final GroupDescriptor GROUP2 = new GroupDescriptor(GROUPNAME2, GROUPREMARK2);
    public static final GroupDescriptor GROUP3 = new GroupDescriptor(GROUPNAME3, GROUPREMARK3);

    public static final GroupName GROUPNAME0 = new GroupName("group0");
    public static final GroupRemark GROUPREMARK0 = new GroupRemark("remark0");
    public static final GroupDescriptor GROUP0 = new GroupDescriptor(GROUPNAME0, GROUPREMARK0);

    /**
     * Generates a typical GroupList.
     *
     * @return GroupList
     */
    public static GroupList generateTypicalGroupList() {
        GroupList groupList = new GroupList();
        groupList.addGroup(GROUP1);
        groupList.addGroup(GROUP2);
        groupList.addGroup(GROUP3);
        return groupList;
    }
}
