package seedu.billboard.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.billboard.logic.commands.CommandTestUtil.DESC_DINNER;
import static seedu.billboard.logic.commands.CommandTestUtil.DESC_TAXES;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_AMOUNT_TAXES;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_NAME_TAXES;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_DESCRIPTION_TAXES;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_TAG_TAXES;

import org.junit.jupiter.api.Test;

import seedu.billboard.logic.commands.EditCommand.EditExpenseDescriptor;
import seedu.billboard.testutil.EditExpenseDescriptorBuilder;

public class EditExpenseDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditExpenseDescriptor descriptorWithSameValues = new EditExpenseDescriptor(DESC_DINNER);
        assertEquals(DESC_DINNER, descriptorWithSameValues);

        // same object -> returns true
        assertEquals(DESC_DINNER, DESC_DINNER);

        // null -> returns false
        assertNotEquals(null, DESC_DINNER);

        // different types -> returns false
        assertNotEquals(5, DESC_DINNER);

        // different values -> returns false
        assertNotEquals(DESC_DINNER, DESC_TAXES);

        // different name -> returns false
        EditExpenseDescriptor editedDinner = new EditExpenseDescriptorBuilder(DESC_DINNER).withName(VALID_NAME_TAXES).build();
        assertNotEquals(DESC_DINNER, editedDinner);

        // different description -> returns false
        editedDinner = new EditExpenseDescriptorBuilder(DESC_DINNER).withDescription(VALID_DESCRIPTION_TAXES).build();
        assertNotEquals(DESC_DINNER, editedDinner);


        // different amount -> returns false
        editedDinner = new EditExpenseDescriptorBuilder(DESC_DINNER).withAmount(VALID_AMOUNT_TAXES).build();
        assertNotEquals(DESC_DINNER, editedDinner);

        // different tags -> returns false
        editedDinner = new EditExpenseDescriptorBuilder(DESC_DINNER).withTags(VALID_TAG_TAXES).build();
        assertNotEquals(DESC_DINNER, editedDinner);
    }
}
