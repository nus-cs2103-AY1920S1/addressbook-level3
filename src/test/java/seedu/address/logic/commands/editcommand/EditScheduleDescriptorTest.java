package seedu.address.logic.commands.editcommand;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_FRIDAY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_MONDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CALENDAR_FRIDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_FRIDAY;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.editcommand.EditScheduleCommand.EditScheduleDescriptor;
import seedu.address.testutil.EditScheduleDescriptorBuilder;


public class EditScheduleDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditScheduleDescriptor descriptorWithSameValues = new EditScheduleDescriptor(DESC_MONDAY);
        assertTrue(DESC_MONDAY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_MONDAY.equals(DESC_MONDAY));

        // null -> returns false
        assertFalse(DESC_MONDAY.equals(null));

        // different types -> returns false
        assertFalse(DESC_MONDAY.equals(5));

        // different values -> returns false
        assertFalse(DESC_MONDAY.equals(DESC_FRIDAY));

        // different date -> returns false
        EditScheduleDescriptor editedMonday = new EditScheduleDescriptorBuilder(DESC_MONDAY)
                .withDate(VALID_CALENDAR_FRIDAY).build();
        assertFalse(DESC_MONDAY.equals(editedMonday));

        // different time -> returns false
        editedMonday = new EditScheduleDescriptorBuilder(DESC_MONDAY).withTime(VALID_CALENDAR_FRIDAY).build();
        assertFalse(DESC_MONDAY.equals(editedMonday));

        // different venue -> returns false
        editedMonday = new EditScheduleDescriptorBuilder(DESC_MONDAY).withVenue(VALID_VENUE_FRIDAY).build();
        assertFalse(DESC_MONDAY.equals(editedMonday));

        // different tags -> returns false
        editedMonday = new EditScheduleDescriptorBuilder(DESC_MONDAY).withTags(VALID_TAG_FRIDAY).build();
        assertFalse(DESC_MONDAY.equals(editedMonday));
    }

}
