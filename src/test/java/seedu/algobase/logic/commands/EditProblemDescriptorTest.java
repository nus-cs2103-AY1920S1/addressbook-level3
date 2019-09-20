package seedu.algobase.logic.commands;

import org.junit.jupiter.api.Test;
import seedu.algobase.logic.commands.EditCommand.EditProblemDescriptor;
import seedu.algobase.testutil.EditProblemDescriptorBuilder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.algobase.logic.commands.CommandTestUtil.*;

public class EditProblemDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditProblemDescriptor descriptorWithSameValues = new EditProblemDescriptor(DESC_AMY);
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
        EditProblemDescriptor editedAmy = new EditProblemDescriptorBuilder(DESC_AMY).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different author -> returns false
        editedAmy = new EditProblemDescriptorBuilder(DESC_AMY).withAuthor(VALID_PHONE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different weblink -> returns false
        editedAmy = new EditProblemDescriptorBuilder(DESC_AMY).withWeblink(VALID_EMAIL_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different description -> returns false
        editedAmy = new EditProblemDescriptorBuilder(DESC_AMY).withDescription(VALID_ADDRESS_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditProblemDescriptorBuilder(DESC_AMY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }
}
