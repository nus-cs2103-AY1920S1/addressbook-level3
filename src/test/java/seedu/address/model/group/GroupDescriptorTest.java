package seedu.address.model.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP_NAME1;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP_NAME2;

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
        groupDescriptor.setGroupName(GROUP_NAME1);
        assertTrue(groupDescriptor.isAnyFieldEdited());
    }

    @Test
    void getGroupName_emptyNameField() {
        assertTrue(groupDescriptor.getGroupName().equals(GroupName.emptyGroupName()));
    }

    @Test
    void getGroupName() {
        groupDescriptor.setGroupName(GROUP_NAME1);
        assertEquals(GROUP_NAME1, groupDescriptor.getGroupName());
        assertNotEquals(GROUP_NAME2, groupDescriptor.getGroupName());
    }



    @Test
    void setGroupName() {
        groupDescriptor.setGroupName(GROUP_NAME1);
        assertTrue(GROUP_NAME1.equals(groupDescriptor.getGroupName()));
        assertFalse(GROUP_NAME2.equals(groupDescriptor.getGroupName()));
    }

}
