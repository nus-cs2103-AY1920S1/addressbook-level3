package dukecooks.logic.commands.dashboard;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.testutil.dashboard.EditDashboardDescriptorBuilder;

public class EditTaskDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditTaskCommand.EditTaskDescriptor descriptorWithSameValues =
                new EditTaskCommand.EditTaskDescriptor(CommandTestUtil.DESC_HW);
        Assertions.assertTrue(CommandTestUtil.DESC_HW.equals(descriptorWithSameValues));

        // same object -> returns true
        Assertions.assertTrue(CommandTestUtil.DESC_HW.equals(CommandTestUtil.DESC_HW));

        // null -> returns false
        Assertions.assertFalse(CommandTestUtil.DESC_HW.equals(null));

        // different types -> returns false
        Assertions.assertFalse(CommandTestUtil.DESC_HW.equals(5));

        // different values -> returns false
        Assertions.assertFalse(CommandTestUtil.DESC_HW.equals(CommandTestUtil.DESC_PLAY));

        // different name -> returns false
        EditTaskCommand.EditTaskDescriptor editedHw = new EditDashboardDescriptorBuilder(CommandTestUtil.DESC_PLAY)
                .withDashboardName(CommandTestUtil.VALID_DASHBOARDNAME_YOGA).build();
        Assertions.assertFalse(CommandTestUtil.DESC_HW.equals(editedHw));

        // different date -> returns false
        editedHw = new EditDashboardDescriptorBuilder(CommandTestUtil.DESC_HW).withTaskDate(CommandTestUtil
                .VALID_TASKDATE2).build();
        Assertions.assertFalse(CommandTestUtil.DESC_HW.equals(editedHw));
    }
}
