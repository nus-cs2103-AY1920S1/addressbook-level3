package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY_DIARY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB_DIARY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditDiaryCommand.EditDiaryDescriptor;
import seedu.address.testutil.EditDiaryDescriptorBuilder;

public class EditDiaryDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditDiaryDescriptor descriptorWithSameValues = new EditDiaryDescriptor(DESC_AMY_DIARY);
        assertTrue(DESC_AMY_DIARY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY_DIARY.equals(DESC_AMY_DIARY));

        // null -> returns false
        assertFalse(DESC_AMY_DIARY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY_DIARY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY_DIARY.equals(DESC_BOB_DIARY));

        // different name -> returns false
        EditDiaryDescriptor editedAmy = new EditDiaryDescriptorBuilder(DESC_AMY_DIARY).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY_DIARY.equals(editedAmy));
    }
}
