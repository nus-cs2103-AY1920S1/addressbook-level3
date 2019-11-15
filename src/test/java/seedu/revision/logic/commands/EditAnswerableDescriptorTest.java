package seedu.revision.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.revision.logic.commands.CommandTestUtil.DESC_ALPHA;
import static seedu.revision.logic.commands.CommandTestUtil.DESC_BETA;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_CATEGORY_GREENFIELD;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_DIFFICULTY_BETA;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_MCQ_QUESTION_2;

import org.junit.jupiter.api.Test;

import seedu.revision.logic.commands.main.EditCommand;
import seedu.revision.logic.commands.main.EditCommand.EditAnswerableDescriptor;
import seedu.revision.testutil.builder.EditAnswerableDescriptorBuilder;

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
        EditCommand.EditAnswerableDescriptor editedAmy = new EditAnswerableDescriptorBuilder(DESC_ALPHA)
                .withQuestion(VALID_MCQ_QUESTION_2).build();
        assertFalse(DESC_ALPHA.equals(editedAmy));

        // different difficulty -> returns false
        editedAmy = new EditAnswerableDescriptorBuilder(DESC_ALPHA).withDifficulty(VALID_DIFFICULTY_BETA).build();
        assertFalse(DESC_ALPHA.equals(editedAmy));

        // different categories -> returns false
        editedAmy = new EditAnswerableDescriptorBuilder(DESC_ALPHA).withCategories(VALID_CATEGORY_GREENFIELD).build();
        assertFalse(DESC_ALPHA.equals(editedAmy));
    }
}
