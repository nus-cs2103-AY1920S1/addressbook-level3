package seedu.address.model.group;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GroupListTest {

    GroupList groupList;
    GroupDescriptor groupDescriptor1;
    GroupDescriptor groupDescriptor2;

    @BeforeEach
    void init(){
        groupList = new GroupList();
        groupDescriptor1 = new GroupDescriptor(new GroupName("group1"), new GroupRemark("remark1"));
        groupDescriptor2 = new GroupDescriptor(new GroupName("group2"), new GroupRemark("remark2"));

    }

    @Test
    void addGroup() {
        Group group = groupList.addGroup(groupDescriptor1);

        assertEquals(group.getGroupName().toString(), groupDescriptor1.getGroupName().toString());
        assertEquals(group.getGroupRemark().toString(), groupDescriptor1.getGroupRemark().toString());
    }

    @Test
    void deleteGroup() {
        Group group = groupList.addGroup(groupDescriptor1);
        assertTrue(groupList.deleteGroup(group.getGroupId()));
        assertFalse(groupList.deleteGroup(group.getGroupId()));
    }

    @Test
    void editGroup() {
        Group group1 = groupList.addGroup(groupDescriptor1);
        Group group2 = groupList.editGroup(group1.getGroupName(), groupDescriptor2);

        assertEquals(group2.getGroupName(), groupDescriptor2.getGroupName());
        assertEquals(group1.getGroupId(), group2.getGroupId());
    }

    @Test
    void findGroup() {
        assertNull(groupList.findGroup(groupDescriptor1.getGroupName()));
        groupList.addGroup(groupDescriptor1);
        assertNotNull(groupList.findGroup(groupDescriptor1.getGroupName()));
    }

    @Test
    void testFindGroup() {
        Group group = groupList.addGroup(groupDescriptor1);
        assertNotNull(groupList.findGroup(group.getGroupId()));
    }
}