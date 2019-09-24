package seedu.algobase.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.algobase.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.algobase.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_AUTHOR_BOB;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_WEBLINK_BOB;

import org.junit.jupiter.api.Test;

import seedu.algobase.logic.commands.EditCommand.EditProblemDescriptor;
import seedu.algobase.testutil.EditProblemDescriptorBuilder;


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
        editedAmy = new EditProblemDescriptorBuilder(DESC_AMY).withAuthor(VALID_AUTHOR_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different weblink -> returns false
        editedAmy = new EditProblemDescriptorBuilder(DESC_AMY).withWeblink(VALID_WEBLINK_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different description -> returns false
        editedAmy = new EditProblemDescriptorBuilder(DESC_AMY).withDescription(VALID_DESCRIPTION_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditProblemDescriptorBuilder(DESC_AMY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }
}
