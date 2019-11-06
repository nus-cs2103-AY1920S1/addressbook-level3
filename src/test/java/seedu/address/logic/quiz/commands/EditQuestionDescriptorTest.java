package seedu.address.logic.quiz.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.quiz.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.quiz.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.quiz.commands.CommandTestUtil.VALID_ANSWER_BOB;
import static seedu.address.logic.quiz.commands.CommandTestUtil.VALID_CATEGORY_BOB;
import static seedu.address.logic.quiz.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.quiz.commands.CommandTestUtil.VALID_TAG_LECTURE;
import static seedu.address.logic.quiz.commands.CommandTestUtil.VALID_TYPE_BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.quiz.commands.EditCommand.EditQuestionDescriptor;
import seedu.address.testutil.EditQuestionDescriptorBuilder;

public class EditQuestionDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditQuestionDescriptor descriptorWithSameValues = new EditQuestionDescriptor(DESC_AMY);
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
        EditQuestionDescriptor editedAmy = new EditQuestionDescriptorBuilder(DESC_AMY).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditQuestionDescriptorBuilder(DESC_AMY).withAnswer(VALID_ANSWER_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditQuestionDescriptorBuilder(DESC_AMY).withCategory(VALID_CATEGORY_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different address -> returns false
        editedAmy = new EditQuestionDescriptorBuilder(DESC_AMY).withType(VALID_TYPE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditQuestionDescriptorBuilder(DESC_AMY).withTags(VALID_TAG_LECTURE).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }
}
