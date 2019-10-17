package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ALPHA;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BETA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_BETA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIFFICULTY_BETA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_GREENFIELD;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditCommand.EditAnswerableDescriptor;
import seedu.address.testutil.EditAnswerableDescriptorBuilder;

public class EditAnswerableDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditCommand.EditAnswerableDescriptor descriptorWithSameValues = new EditAnswerableDescriptor(DESC_ALPHA);
        assertTrue(DESC_ALPHA.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_ALPHA.equals(DESC_ALPHA));

        // null -> returns false
        assertFalse(DESC_ALPHA.equals(null));

        // different types -> returns false
        assertFalse(DESC_ALPHA.equals(5));

        // different values -> returns false
        assertFalse(DESC_ALPHA.equals(DESC_BETA));

        // different name -> returns false
        EditCommand.EditAnswerableDescriptor editedAmy = new EditAnswerableDescriptorBuilder(DESC_ALPHA).withQuestion(VALID_QUESTION_BETA).build();
        assertFalse(DESC_ALPHA.equals(editedAmy));

        // different difficulty -> returns false
        editedAmy = new EditAnswerableDescriptorBuilder(DESC_ALPHA).withDifficulty(VALID_DIFFICULTY_BETA).build();
        assertFalse(DESC_ALPHA.equals(editedAmy));

        // different categories -> returns false
        editedAmy = new EditAnswerableDescriptorBuilder(DESC_ALPHA).withCategories(VALID_CATEGORY_GREENFIELD).build();
        assertFalse(DESC_ALPHA.equals(editedAmy));
    }
}
