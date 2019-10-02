package seedu.address.model.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUPNAME1;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUPNAME2;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUPREMARK1;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUPREMARK2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class GroupDescriptorTest {

    private GroupDescriptor groupDescriptor;

    @BeforeEach
    void init() {
        groupDescriptor = new GroupDescriptor();
    }

    @Test
    void isAnyFieldEdited_false() {
        assertFalse(groupDescriptor.isAnyFieldEdited());
    }

    @Test
    void isAnyFieldEdited_true() {
        groupDescriptor.setGroupName(GROUPNAME1);
        assertTrue(groupDescriptor.isAnyFieldEdited());
    }

    @Test
    void getGroupName_emptyNameField() {
        assertTrue(groupDescriptor.getGroupName().equals(GroupName.emptyGroupName()));
    }

    @Test
    void getGroupName() {
        groupDescriptor.setGroupName(GROUPNAME1);
        assertEquals(GROUPNAME1, groupDescriptor.getGroupName());
        assertNotEquals(GROUPNAME2, groupDescriptor.getGroupName());
    }

    @Test
    void getGroupRemark_emptyRemarkField() {
        assertTrue(groupDescriptor.getGroupRemark().equals(GroupRemark.emptyRemark()));
    }

    @Test
    void getGroupRemark() {
        groupDescriptor.setGroupRemark(GROUPREMARK1);
        assertEquals(GROUPREMARK1, groupDescriptor.getGroupRemark());
        assertNotEquals(GROUPREMARK2, groupDescriptor.getGroupRemark());
    }

    @Test
    void setGroupName() {
        groupDescriptor.setGroupName(GROUPNAME1);
        assertTrue(GROUPNAME1.equals(groupDescriptor.getGroupName()));
        assertFalse(GROUPNAME2.equals(groupDescriptor.getGroupName()));
    }

    @Test
    void setGroupRemark() {
        groupDescriptor.setGroupRemark(GROUPREMARK1);
        assertTrue(GROUPREMARK1.equals(groupDescriptor.getGroupRemark()));
        assertFalse(GROUPREMARK2.equals(groupDescriptor.getGroupRemark()));
    }
}
