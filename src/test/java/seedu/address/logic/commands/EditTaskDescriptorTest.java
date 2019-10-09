package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DESC_FINANCE;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DESC_PUBLICITY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PUBLICITY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_NAME_PUBLICITY;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.address.testutil.EditTaskDescriptorBuilder;

public class EditTaskDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditTaskDescriptor descriptorWithSameValues = new EditCommand.EditTaskDescriptor(TASK_DESC_FINANCE);
        assertTrue(TASK_DESC_FINANCE.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(TASK_DESC_FINANCE.equals(TASK_DESC_FINANCE));

        // null -> returns false
        assertFalse(TASK_DESC_FINANCE.equals(null));

        // different types -> returns false
        assertFalse(TASK_DESC_FINANCE.equals(5));

        // different values -> returns false
        assertFalse(TASK_DESC_FINANCE.equals(TASK_DESC_PUBLICITY));

        // different name -> returns false
        EditTaskDescriptor editedFinanceTask = new EditTaskDescriptorBuilder(TASK_DESC_FINANCE)
                .withName(VALID_TASK_NAME_PUBLICITY).build();
        assertFalse(TASK_DESC_FINANCE.equals(editedFinanceTask));

        // different tags -> returns false
        editedFinanceTask = new EditTaskDescriptorBuilder(TASK_DESC_FINANCE).withTags(VALID_TAG_PUBLICITY).build();
        assertFalse(TASK_DESC_FINANCE.equals(editedFinanceTask));
    }
}
