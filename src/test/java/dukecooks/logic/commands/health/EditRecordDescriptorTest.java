package dukecooks.logic.commands.health;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.testutil.health.EditRecordDescriptorBuilder;

public class EditRecordDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditRecordCommand.EditRecordDescriptor descriptorWithSameValues = new EditRecordCommand
                .EditRecordDescriptor(CommandTestUtil.DESC_GLUCOSE);
        Assertions.assertTrue(CommandTestUtil.DESC_GLUCOSE.equals(descriptorWithSameValues));

        // same object -> returns true
        Assertions.assertTrue(CommandTestUtil.DESC_GLUCOSE.equals(CommandTestUtil.DESC_GLUCOSE));

        // null -> returns false
        Assertions.assertFalse(CommandTestUtil.DESC_GLUCOSE.equals(null));

        // different types -> returns false
        Assertions.assertFalse(CommandTestUtil.DESC_GLUCOSE.equals(5));

        // different values -> returns false
        Assertions.assertFalse(CommandTestUtil.DESC_GLUCOSE.equals(CommandTestUtil.DESC_CALORIES));

        // different type -> returns false
        EditRecordCommand.EditRecordDescriptor editedAmy = new EditRecordDescriptorBuilder(CommandTestUtil.DESC_GLUCOSE)
                .withType(CommandTestUtil.VALID_TYPE_CALORIES).build();
        Assertions.assertFalse(CommandTestUtil.DESC_GLUCOSE.equals(editedAmy));

        // different timestamp -> returns false
        editedAmy = new EditRecordDescriptorBuilder(CommandTestUtil.DESC_GLUCOSE).withTimestamp(CommandTestUtil
                .VALID_TIMESTAMP_CALORIES).build();
        Assertions.assertFalse(CommandTestUtil.DESC_GLUCOSE.equals(editedAmy));
    }
}
