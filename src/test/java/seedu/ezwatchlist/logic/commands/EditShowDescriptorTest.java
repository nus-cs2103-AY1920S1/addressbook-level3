package seedu.ezwatchlist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.DESC_ANNABELLE;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.DESC_BOB_THE_BUILDER;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.VALID_ACTOR_BOB_THE_BUILDER;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.VALID_DATE_BOB_THE_BUILDER;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.VALID_RUNNING_TIME_BOB_THE_BUILDER;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.VALID_SHOW_NAME_BOB_THE_BUILDER;

import org.junit.jupiter.api.Test;

import seedu.ezwatchlist.logic.commands.EditCommand.EditShowDescriptor;
import seedu.ezwatchlist.testutil.EditShowDescriptorBuilder;

public class EditShowDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditShowDescriptor descriptorWithSameValues = new EditShowDescriptor(DESC_ANNABELLE);
        assertTrue(DESC_ANNABELLE.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_ANNABELLE.equals(DESC_ANNABELLE));

        // null -> returns false
        assertFalse(DESC_ANNABELLE.equals(null));

        // different types -> returns false
        assertFalse(DESC_ANNABELLE.equals(5));

        // different values -> returns false
        assertFalse(DESC_ANNABELLE.equals(DESC_BOB_THE_BUILDER));

        // different name -> returns false
        EditShowDescriptor editedAmy = new EditShowDescriptorBuilder(DESC_ANNABELLE)
                .withName(VALID_SHOW_NAME_BOB_THE_BUILDER).build();
        assertFalse(DESC_ANNABELLE.equals(editedAmy));

        // different show -> returns false
        editedAmy = new EditShowDescriptorBuilder(DESC_ANNABELLE).withDateOfRelease(VALID_DATE_BOB_THE_BUILDER).build();
        assertFalse(DESC_ANNABELLE.equals(editedAmy));

        // different date -> returns false
        editedAmy = new EditShowDescriptorBuilder(DESC_ANNABELLE).withDateOfRelease(VALID_DATE_BOB_THE_BUILDER).build();
        assertFalse(DESC_ANNABELLE.equals(editedAmy));

        // different running time -> returns false
        editedAmy = new EditShowDescriptorBuilder(DESC_ANNABELLE)
                .withRunningTime(VALID_RUNNING_TIME_BOB_THE_BUILDER).build();
        assertFalse(DESC_ANNABELLE.equals(editedAmy));

        // different actors -> returns false
        editedAmy = new EditShowDescriptorBuilder(DESC_ANNABELLE).withActors(VALID_ACTOR_BOB_THE_BUILDER).build();
        assertFalse(DESC_ANNABELLE.equals(editedAmy));
    }
}
