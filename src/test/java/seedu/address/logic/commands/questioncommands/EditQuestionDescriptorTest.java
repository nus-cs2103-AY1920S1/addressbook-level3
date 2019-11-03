package seedu.address.logic.commands.questioncommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ALGEBRA;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CONCEPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_CONCEPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIFFICULTY_CONCEPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_BODY_CONCEPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_CONCEPT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.questioncommands.EditQuestionCommand.EditQuestionDescriptor;
import seedu.address.testutil.EditQuestionDescriptorBuilder;

class EditQuestionDescriptorTest {
    @Test
    void equals() {
        // same values -> returns true
        EditQuestionDescriptor descriptorWithSameValues = new EditQuestionDescriptor(DESC_ALGEBRA);
        assertEquals(DESC_ALGEBRA, descriptorWithSameValues);

        // same object -> returns true
        assertEquals(DESC_ALGEBRA, DESC_ALGEBRA);

        // null -> returns false
        assertNotEquals(null, DESC_ALGEBRA);

        // different types -> returns false
        assertNotEquals(5, DESC_ALGEBRA);

        // different values -> returns false
        assertNotEquals(DESC_ALGEBRA, DESC_CONCEPT);

        // different name -> returns false
        EditQuestionDescriptor editedAmy = new EditQuestionDescriptorBuilder(DESC_ALGEBRA)
                .withQuestionBody(VALID_QUESTION_BODY_CONCEPT).build();
        assertNotEquals(DESC_ALGEBRA, editedAmy);

        // different phone -> returns false
        editedAmy = new EditQuestionDescriptorBuilder(DESC_ALGEBRA).withAnswer(VALID_ANSWER_CONCEPT).build();
        assertNotEquals(DESC_ALGEBRA, editedAmy);

        // different email -> returns false
        editedAmy = new EditQuestionDescriptorBuilder(DESC_ALGEBRA).withSubject(VALID_SUBJECT_CONCEPT).build();
        assertNotEquals(DESC_ALGEBRA, editedAmy);

        // different address -> returns false
        editedAmy = new EditQuestionDescriptorBuilder(DESC_ALGEBRA).withDifficulty(VALID_DIFFICULTY_CONCEPT).build();
        assertNotEquals(DESC_ALGEBRA, editedAmy);
    }
}
