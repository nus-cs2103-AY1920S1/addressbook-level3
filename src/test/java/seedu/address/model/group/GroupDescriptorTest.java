package seedu.address.model.group;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GroupDescriptorTest {

    private GroupDescriptor groupDescriptor;

    @BeforeEach
    void init(){
        groupDescriptor = new GroupDescriptor();
    }

    @Test
    void isAnyFieldEdited() {

        assertFalse(groupDescriptor.isAnyFieldEdited());
        groupDescriptor.setGroupName(new GroupName("name"));
        assertTrue(groupDescriptor.isAnyFieldEdited());
    }

    @Test
    void getGroupName() {
        assertNull(groupDescriptor.getGroupName());

        GroupName groupName = new GroupName("name");
        groupDescriptor.setGroupName(groupName);
        assertEquals(groupName, groupDescriptor.getGroupName());
    }

    @Test
    void getGroupRemark() {
        assertNull(groupDescriptor.getGroupRemark());

        GroupRemark groupRemark = new GroupRemark("remark");
        groupDescriptor.setGroupRemark(groupRemark);
        assertEquals(groupRemark, groupDescriptor.getGroupRemark());
    }
}