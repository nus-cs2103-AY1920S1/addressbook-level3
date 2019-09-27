package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_HISTORY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_2;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.testutil.EditFlashCardDescriptorBuilder;

public class EditFlashCardDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditPersonDescriptor descriptorWithSameValues = new EditPersonDescriptor(DESC_1);
        assertTrue(DESC_1.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_1.equals(DESC_1));

        // null -> returns false
        assertFalse(DESC_1.equals(null));

        // different types -> returns false
        assertFalse(DESC_1.equals(5));

        // different values -> returns false
        assertFalse(DESC_1.equals(DESC_2));

        // different name -> returns false
        EditPersonDescriptor editedAmy =
                new EditFlashCardDescriptorBuilder(DESC_1).withQuestion(VALID_QUESTION_2).build();
        assertFalse(DESC_1.equals(editedAmy));

        // different answer -> returns false
        editedAmy = new EditFlashCardDescriptorBuilder(DESC_1).withAnswer(VALID_ANSWER_2).build();
        assertFalse(DESC_1.equals(editedAmy));

        // different address -> returns false
        editedAmy = new EditFlashCardDescriptorBuilder(DESC_1).withRating(VALID_RATING_2).build();
        assertFalse(DESC_1.equals(editedAmy));

        // different categories -> returns false
        editedAmy = new EditFlashCardDescriptorBuilder(DESC_1).withCategories(VALID_CATEGORY_HISTORY).build();
        assertFalse(DESC_1.equals(editedAmy));
    }
}
