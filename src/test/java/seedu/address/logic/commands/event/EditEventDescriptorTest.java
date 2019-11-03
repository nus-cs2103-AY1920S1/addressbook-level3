package seedu.address.logic.commands.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BUFFET;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_CATEGORY_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DESCRIPTION_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_PRICE_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_TIMESTAMP_BIRTHDAY;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.event.EditEventCommand.EditEventDescriptor;
import seedu.address.testutil.EditEventDescriptorBuilder;

public class EditEventDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditEventDescriptor descriptorWithSameValues = new EditEventDescriptor(DESC_BUFFET);
        assertTrue(DESC_BUFFET.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_BUFFET.equals(DESC_BUFFET));

        // null -> returns false
        assertFalse(DESC_BUFFET.equals(null));

        // different types -> returns false
        assertFalse(DESC_BUFFET.equals(5));

        // different description -> returns false
        EditEventDescriptor editedBuffet = new EditEventDescriptorBuilder(DESC_BUFFET)
                .withDescription(VALID_EVENT_DESCRIPTION_BIRTHDAY).build();
        assertFalse(DESC_BUFFET.equals(editedBuffet));

        // different price -> returns false
        editedBuffet = new EditEventDescriptorBuilder(DESC_BUFFET).withPrice(VALID_EVENT_PRICE_BIRTHDAY).build();
        assertFalse(DESC_BUFFET.equals(editedBuffet));

        // different category -> returns false
        editedBuffet = new EditEventDescriptorBuilder(DESC_BUFFET).withCategory(VALID_EVENT_CATEGORY_BIRTHDAY).build();
        assertFalse(DESC_BUFFET.equals(editedBuffet));

        // different timestamp -> returns false
        editedBuffet = new EditEventDescriptorBuilder(DESC_BUFFET)
                .withTimestamp(VALID_EVENT_TIMESTAMP_BIRTHDAY).build();
        assertFalse(DESC_BUFFET.equals(editedBuffet));
    }
}
