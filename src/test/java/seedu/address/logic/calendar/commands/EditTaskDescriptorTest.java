package seedu.address.logic.calendar.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.calendar.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.calendar.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.calendar.commands.CommandTestUtil.VALID_TASKDAY_BOB;
import static seedu.address.logic.calendar.commands.CommandTestUtil.VALID_TASKDESCRIPTION_BOB;
import static seedu.address.logic.calendar.commands.CommandTestUtil.VALID_TASKTAG_HUSBAND;
import static seedu.address.logic.calendar.commands.CommandTestUtil.VALID_TASKTIME_BOB;
import static seedu.address.logic.calendar.commands.CommandTestUtil.VALID_TASKTITLE_BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.EditTaskDescriptorBuilder;



public class EditTaskDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditCommand.EditTaskDescriptor descriptorWithSameValues = new EditCommand.EditTaskDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different name -> returns false
        EditCommand.EditTaskDescriptor editedAmy =
            new EditTaskDescriptorBuilder(DESC_AMY).withTaskTitle(VALID_TASKTITLE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditTaskDescriptorBuilder(DESC_AMY).withTaskTime(VALID_TASKTIME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditTaskDescriptorBuilder(DESC_AMY).withTaskDescription(VALID_TASKDESCRIPTION_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different address -> returns false
        editedAmy = new EditTaskDescriptorBuilder(DESC_AMY).withTaskDay(VALID_TASKDAY_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditTaskDescriptorBuilder(DESC_AMY).withTaskTags(VALID_TASKTAG_HUSBAND).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }
}
