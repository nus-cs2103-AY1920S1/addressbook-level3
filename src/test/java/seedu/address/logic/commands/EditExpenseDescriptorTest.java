package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_VODKA;
import static seedu.address.logic.commands.CommandTestUtil.DESC_RUM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_RUM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_RUM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_RUM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_ALCOHOL;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditCommand.EditExpenseDescriptor;
import seedu.address.testutil.EditExpenseDescriptorBuilder;

public class EditExpenseDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditExpenseDescriptor descriptorWithSameValues = new EditExpenseDescriptor(DESC_VODKA);
        assertTrue(DESC_VODKA.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_VODKA.equals(DESC_VODKA));

        // null -> returns false
        assertFalse(DESC_VODKA.equals(null));

        // different types -> returns false
        assertFalse(DESC_VODKA.equals(5));

        // different values -> returns false
        assertFalse(DESC_VODKA.equals(DESC_RUM));

        // different name -> returns false
        EditExpenseDescriptor editedVodka = new EditExpenseDescriptorBuilder(DESC_VODKA).withName(VALID_NAME_RUM).build();
        assertFalse(DESC_VODKA.equals(editedVodka));

        // different amount -> returns false
        editedVodka = new EditExpenseDescriptorBuilder(DESC_VODKA).withAmount(VALID_AMOUNT_RUM).build();
        assertFalse(DESC_VODKA.equals(editedVodka));

        // different date -> returns false
        editedVodka = new EditExpenseDescriptorBuilder(DESC_VODKA).withDate(VALID_DATE_RUM).build();
        assertFalse(DESC_VODKA.equals(editedVodka));

        // different tags -> returns false
        editedVodka = new EditExpenseDescriptorBuilder(DESC_VODKA).withTags(VALID_TAG_ALCOHOL).build();
        assertFalse(DESC_VODKA.equals(editedVodka));
    }
}
