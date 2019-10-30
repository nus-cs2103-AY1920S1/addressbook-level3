package seedu.address.logic.commands.questioncommands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ALGEBRA;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CONCEPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_CONCEPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIFFICULTY_CONCEPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_BODY_CONCEPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_CONCEPT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.questioncommands.EditQuestionCommand.EditQuestionDescriptor;
import seedu.address.testutil.EditQuestionDescriptorBuilder;

public class EditQuestionDescriptorTest {
    @Test
    public void equals() {
        // same values -> returns true
        EditQuestionDescriptor descriptorWithSameValues = new EditQuestionDescriptor(DESC_ALGEBRA);
        assertTrue(DESC_ALGEBRA.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_ALGEBRA.equals(DESC_ALGEBRA));

        // null -> returns false
        assertFalse(DESC_ALGEBRA.equals(null));

        // different types -> returns false
        assertFalse(DESC_ALGEBRA.equals(5));

        // different values -> returns false
        assertFalse(DESC_ALGEBRA.equals(DESC_CONCEPT));

        // different name -> returns false
        EditQuestionDescriptor editedAmy = new EditQuestionDescriptorBuilder(DESC_ALGEBRA)
                .withQuestionBody(VALID_QUESTION_BODY_CONCEPT).build();
        assertFalse(DESC_ALGEBRA.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditQuestionDescriptorBuilder(DESC_ALGEBRA).withAnswer(VALID_ANSWER_CONCEPT).build();
        assertFalse(DESC_ALGEBRA.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditQuestionDescriptorBuilder(DESC_ALGEBRA).withSubject(VALID_SUBJECT_CONCEPT).build();
        assertFalse(DESC_ALGEBRA.equals(editedAmy));

        // different address -> returns false
        editedAmy = new EditQuestionDescriptorBuilder(DESC_ALGEBRA).withDifficulty(VALID_DIFFICULTY_CONCEPT).build();
        assertFalse(DESC_ALGEBRA.equals(editedAmy));
    }
}
