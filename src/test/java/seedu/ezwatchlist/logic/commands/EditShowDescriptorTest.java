package seedu.ezwatchlist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.*;

import org.junit.jupiter.api.Test;

import seedu.ezwatchlist.logic.commands.EditCommand.EditShowDescriptor;
import seedu.ezwatchlist.testutil.EditShowDescriptorBuilder;

public class EditShowDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditShowDescriptor descriptorWithSameValues = new EditShowDescriptor(DESC_AMY);
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
        EditShowDescriptor editedAmy = new EditShowDescriptorBuilder(DESC_AMY).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different show -> returns false
        editedAmy = new EditShowDescriptorBuilder(DESC_AMY).withDateOfRelease(VALID_DATE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different date -> returns false
        editedAmy = new EditShowDescriptorBuilder(DESC_AMY).withDateOfRelease(VALID_DATE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different running time -> returns false
        editedAmy = new EditShowDescriptorBuilder(DESC_AMY).withRunningTime(VALID_RUNNING_TIME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different actors -> returns false
        editedAmy = new EditShowDescriptorBuilder(DESC_AMY).withActors(VALID_ACTOR_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }
}
